package com.special.gitf.mail.service.daemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.special.gitf.mail.repository.MailRepository;
import com.special.gitf.mail.service.MailService;

@Service
@Transactional(readOnly = true)
public class MailDaemon implements MailService {

  @Autowired
  private JavaMailSenderImpl sender;

  @Autowired
  private MailRepository repository;

  @Value("${mail.user}")
  private String mailSender;



  @Override
  @Transactional(readOnly = false)
  public void sendMail(Connection connection) throws Exception {

    final PreparedStatement statement = connection
        .prepareStatement("select user_email as email from user_web where user_status = ?");
    statement.setInt(1, 0);
    final ResultSet result = statement.executeQuery();
    while (result.next()) {
      // send email
      final UUID token = UUID.randomUUID();

      processMessage(connection, token.toString().replace("-", "").toUpperCase(),
          result.getString("email"));

      TimeUnit.SECONDS.sleep(1);

    }

  }

  private void processMessage(Connection connection, String token, String destination)
      throws Exception {

    // final MimeMessage message = sender.createMimeMessage();
    // final MimeMessageHelper helper = new MimeMessageHelper(message);
    // helper.setFrom(mailSender);
    // helper.setTo(destination);
    // helper.setText(token, true);
    // sender.send(message);
    // insertToken(connection, destination, token);

  }



}
