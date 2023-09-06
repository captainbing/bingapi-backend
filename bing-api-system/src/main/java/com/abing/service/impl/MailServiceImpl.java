package com.abing.service.impl;

import com.abing.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author abing
 * @Date 2023/7/21 9:17
 * @Description
 */
@Service
public class MailServiceImpl implements MailService {
    @Value("${spring.mail.from}")
    private String FROM;
    @Resource
    private JavaMailSender javaMailSender;

    /**
     * @param to
     * @return
     */
    @Override
    public String sendMessage(String to) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(FROM);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("测试邮件");
        //支持解析html标签
        String content = "<h1 style='color:red'>哈哈哈</h1>";
        simpleMailMessage.setText(content+"I LOVE YOU FOREVER" + "mail send success");
        javaMailSender.send(simpleMailMessage);
        return "mail send";
    }
}
