package com.abing.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import com.abing.api.utils.SignUtils;
import com.abing.common.BaseResponse;
import com.abing.common.ErrorCode;
import com.abing.common.ResultUtils;
import com.abing.exception.BusinessException;
import com.abing.model.domain.User;
import com.abing.model.dto.search.QQRequest;
import com.abing.service.SimulateService;
import com.abing.service.UserService;
import com.abing.utils.CaptchaUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Author CaptainBing
 * @Date 2023/11/24 17:18
 * @Description 模拟接口
 */
@RestController
@RequestMapping("/simulate")
@RequiredArgsConstructor
@Slf4j
public class SimulateController {


    private final SimulateService simulateService;

    private final UserService userService;

    private final JavaMailSender javaMailSender;

    private final Configuration freeMarkerConfiguration;


    @GetMapping("/convert")
    public BaseResponse<String> convertChinese2Pinyin(@RequestParam(value = "chinese",required = false) String chinese,
                                                      HttpServletRequest request){
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
//        String body = request.getHeader("body");
        if (StringUtils.isAnyEmpty(accessKey,nonce,timestamp,sign)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        User user = userService.getOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getAccessKey, accessKey));

        if (!accessKey.equals(user.getAccessKey())){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"accessKey不匹配");
        }

        // TODO 校验nonce唯一字符串
        if (!(Long.parseLong(nonce) > 1000L)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"nonce不唯一");
        }

        // TODO 校验过期时间 与当前时间不能超过5分钟
        LocalDateTime startTime = LocalDateTimeUtil.of(Long.parseLong(timestamp));
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minus5MinutesTime = now.minusMinutes(5);
        int status = startTime.compareTo(minus5MinutesTime);
        // 大于0则超过五分钟
        if (status < 0){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"timestamp已过期");
        }
        String serverSign = SignUtils.genSign(timestamp, user.getSecretKey());
        if (!sign.equals(serverSign)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"sign不匹配");
        }
        return ResultUtils.success(simulateService.convertChinese2Pinyin(chinese));
    }


    @GetMapping("/qq")
    public BaseResponse<String> getQQImage(QQRequest qqRequest){
        return ResultUtils.success(simulateService.fetchQQAvatar(qqRequest));
    }


    @GetMapping("/mail")
    public BaseResponse<String> sendMail(String message){

        String userAccount = "750321038@qq.com";
        String captcha = CaptchaUtils.random6Captcha();
        // TODO  邮箱发送验证码
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setFrom("errorbing@163.com");
            mimeMessageHelper.setTo(userAccount);
            mimeMessageHelper.setSubject("登录Postwoman 发送验证码");
            Template template = freeMarkerConfiguration.getTemplate("EmailTemplate.html.ftl");
            String workDirectory = System.getProperty("user.dir");
            String genFilePath = workDirectory + File.separator + "EmailTemplate.html";
            FileWriter writer = new FileWriter(genFilePath);
            Map<String, Object> convertCaptchaMap = CaptchaUtils.convertCaptchaToMap(captcha);
            template.process(convertCaptchaMap,writer);
            String emailTemplate = FileUtil.readString(genFilePath, StandardCharsets.UTF_8);
            mimeMessageHelper.setText(emailTemplate,true);
        } catch (MailException | TemplateException | IOException e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"邮件发送出现问题");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(mimeMessage);

        return ResultUtils.success(message);
    }

}
