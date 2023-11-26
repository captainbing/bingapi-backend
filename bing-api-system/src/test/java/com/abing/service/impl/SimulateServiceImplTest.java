package com.abing.service.impl;

import cn.hutool.core.io.FileUtil;
import com.abing.SystemApplication;
import com.abing.common.ErrorCode;
import com.abing.exception.BusinessException;
import com.abing.utils.CaptchaUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author CaptainBing
 * @Date 2023/11/26 14:13
 * @Description
 */
@SpringBootTest(classes = SystemApplication.class)
class SimulateServiceImplTest {

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private Configuration freeMarkerConfiguration;

    @Test
    void convertChinese2Pinyin() {
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
            convertCaptchaMap.put("userName","你好呀世界");
            template.process(convertCaptchaMap,writer);
            String emailTemplate = FileUtil.readString(genFilePath, StandardCharsets.UTF_8);
            mimeMessageHelper.setText(emailTemplate,true);
        } catch (MailException | TemplateException | IOException e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"邮件发送出现问题");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(mimeMessage);
    }
}