package org.example.datn.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmailService {

    @Getter
    JavaMailSender emailSender;
    TemplateEngine templateEngine;

    @Value("spring.mail.username")
    @NonFinal
    String fromEmailAddress;

    int OTP_EXPIRED_MIN = 15;

    public void sendResetPasswordEmail(String receiverEmail, String name, String otp) {
        send(receiverEmail, name, otp, "email-forgot-password", "Quên mật khẩu");
    }

    private void send(String receiverEmail, String name, String otp, String emailTemplate, String subject) {
        try {
            var ctx = new Context();
            ctx.setVariable("name", name);
            ctx.setVariable("expiredMin", OTP_EXPIRED_MIN);
            ctx.setVariable("otp", otp);

            var mimeMessage = emailSender.createMimeMessage();
            var message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject("ZIAZA - " + subject);
            message.setFrom(fromEmailAddress);
            message.setTo(receiverEmail);

            var htmlContent = templateEngine.process(emailTemplate, ctx);
            message.setText(htmlContent, true);

            emailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Send register email to: " + receiverEmail + " fail. Exception: " + e.getMessage(), e);
        }
    }
}
