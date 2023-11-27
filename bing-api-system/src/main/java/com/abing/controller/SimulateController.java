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
        return ResultUtils.success(simulateService.convertChinese2Pinyin(chinese));
    }


    @GetMapping("/qq")
    public BaseResponse<String> getQQImage(QQRequest qqRequest){
        return ResultUtils.success(simulateService.fetchQQAvatar(qqRequest));
    }


    /**
     * 测试使用
     * @param message
     * @return
     */
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
