package com.special.gitf.mail.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.special.gitf.mail.service.MailTransactionService;
import com.special.gitf.mail.service.daemon.MailRequestFinderDaemon;
import com.special.gitf.mail.util.CommonUtil;

@Component
@PropertySource("classpath:bean-config.xml")
public class BeanConfiguration {

  @Value("${mail.server}")
  private String mailServer;

  @Value("${mail.server.port}")
  private int mailPort;

  @Value("${mail.user}")
  private String mailUser;

  @Value("${mail.password}")
  private String mailPassword;

  @Value("${db.url}")
  private String url;

  @Value("${db.driver}")
  private String driver;

  @Value("${db.user}")
  private String user;

  @Value("${db.password}")
  private String password;

  @Bean
  Connection initConnection() {
    Connection connection = null;
    try {
      Class.forName(driver);
      connection = DriverManager.getConnection(url, user, password);
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return connection;
  }

  @Bean
  JavaMailSenderImpl initMailSender() {
    final JavaMailSenderImpl sender = new JavaMailSenderImpl();
    sender.setHost(mailServer);
    sender.setPort(mailPort);

    sender.setUsername(mailUser);
    sender.setPassword(mailPassword);

    final Properties props = new Properties();

    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", true);
    props.put("mail.smtp.starttls.enable", true);
    props.put("mail.smtp.debug", true);
    // props.put("mail.smtp.ssl.enable", "true");

    sender.setJavaMailProperties(props);

    return sender;
  }

  @Bean(name = CommonUtil.REGISTRATION_CONFIRMATION_BEAN)
  MailRequestFinderDaemon findRegistrationConfirmationRequest(MailTransactionService service) {
    return new MailRequestFinderDaemon(service, CommonUtil.ACTIVATE_USER_ACTION);
  }



}
