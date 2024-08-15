package com.jeremias.beprepared.infra;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSender {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.sender-name}")
    private String senderName;
    @Value("${spring.mail.username}")
    private String from;

    @Async
    public void send(String subject, String to, String message, String attachment) {
        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(from, senderName);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(message);
            if (!attachment.isEmpty()) {
                FileSystemResource fileResource = new FileSystemResource(new File(attachment));
                mimeMessageHelper.addAttachment(Objects.requireNonNull(fileResource.getFilename()), fileResource);
            }
            this.javaMailSender.send(mailMessage);
        } catch (Exception e) {
            log.error("Error sending the email: ", e);
        }
    }
}
