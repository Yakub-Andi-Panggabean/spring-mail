package com.special.gitf.mail.util;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.special.gitf.mail.domain.Email;
import com.special.gitf.mail.domain.MailAttachment;

public class MailSenderUtil {


  public static void sendMail(JavaMailSenderImpl sender, Email email) throws Exception {
    try {
      final MimeMessage message = sender.createMimeMessage();
      final MimeMessageHelper helper = new MimeMessageHelper(message);
      helper.setFrom(email.getFrom());
      helper.setTo(email.getDestination());
      helper.setText(email.getContent(), true);
      helper.setSubject(email.getSubject());
      sender.send(message);
    } catch (final MailException mailException) {
      throw new RuntimeException(mailException.getMessage());
    }
  }


  public static void sendMailWithAttachment(JavaMailSenderImpl sender,
      MailAttachment mailAttachment) throws Exception {
    try {
      final MimeMessage message = sender.createMimeMessage();
      final MimeMessageHelper helper = new MimeMessageHelper(message);
      helper.setFrom(mailAttachment.getFrom());
      helper.setTo(mailAttachment.getDestination());
      helper.setText(mailAttachment.getContent(), true);
      helper.setSubject(mailAttachment.getSubject());
      helper.addAttachment(mailAttachment.getAttachmentName(), mailAttachment.getAttachment());
      sender.send(message);
    } catch (final MailException mailException) {
      throw new RuntimeException(mailException.getMessage());
    }

  }

}
