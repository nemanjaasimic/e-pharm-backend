package com.isa.epharm.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Slf4j
@Component
public class EmailSender {
    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    public EmailSender(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void send(String to, String subject, String templateName, Context context) {
        final String body = templateEngine.process(templateName, context);
        sendMail(to, subject, body, true);
    }

    @Async
    public void send(String to, String subject, String text) {
        sendMail(to, subject, text, false);
    }

    private void sendMail(String to, String subject, String text, Boolean isHtml) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, isHtml);
            helper.setFrom("noreply@epharm.com");

            javaMailSender.send(mail);
        } catch (Exception e) {
            log.error(String.format("Error while sending email. Error message: %s", e.getMessage()));
        }
    }
}
