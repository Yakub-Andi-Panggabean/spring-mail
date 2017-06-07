package com.special.gitf.mail.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.codec.Base64;

import com.special.gitf.mail.domain.Email;
import com.special.gitf.mail.domain.MailAttachment;

public class MailSenderUtil {

  private static final Logger log = LoggerFactory.getLogger(MailSenderUtil.class);


  public static void sendMail(JavaMailSenderImpl sender, Email email) throws Exception {
    try {

      log.debug("send email to {} from  {}", email.getDestination(), email.getFrom());

      final MimeMessage message = sender.createMimeMessage();
      final MimeMessageHelper helper = new MimeMessageHelper(message);
      helper.setFrom(email.getFrom());
      helper.setTo(email.getDestination());
      helper.setText(email.getContent(), true);
      helper.setSubject(email.getSubject());
      sender.send(message);
    } catch (final Exception mailException) {
      throw new RuntimeException(mailException.getMessage());
    }

  }


  public static void sendMailWithAttachment(JavaMailSenderImpl sender,
      MailAttachment mailAttachment) throws Exception {
    try {
      final MimeMessage message = sender.createMimeMessage();
      final MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(mailAttachment.getFrom());
      helper.setTo(mailAttachment.getDestination());
      helper.setText(mailAttachment.getContent(), true);
      helper.setSubject(mailAttachment.getSubject());

      if (mailAttachment.getAttachmentNames().size() == mailAttachment.getAttachments().size()) {

        for (int i = 0; i < mailAttachment.getAttachmentNames().size(); i++) {

          helper.addAttachment(mailAttachment.getAttachmentNames().get(i),
              mailAttachment.getAttachments().get(i));

        }

      } else {

        throw new RuntimeException("unmatched name and file size exception, array out of bond");

      }


      sender.send(message);
    } catch (final MailException mailException) {
      throw new RuntimeException(mailException.getMessage());
    }

  }


  public static synchronized String readMailTemplate(String templateLocation) throws Exception {

    final StringBuffer builder = new StringBuffer();
    String line;

    final File file = new File(templateLocation);
    if (!file.exists()) {
      throw new RuntimeException("file " + templateLocation + " is not exist");
    } else {
      final BufferedReader reader = new BufferedReader(new FileReader(templateLocation));

      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }
    }

    return builder.toString();

  }

  public static String encodeToBase64(String token) {
    final StringBuilder generatedToken = new StringBuilder();
    final byte[] encodedValue = Base64.encode(token.getBytes());
    return generatedToken.append(new String(encodedValue)).toString();
  }

  public static String generateUuid() {

    final UUID randomUuid = UUID.randomUUID();
    final String uuid = randomUuid.toString().replaceAll("-", "");
    return uuid;

  }

}
