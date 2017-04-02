package com.special.gitf.mail.service.daemon;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.special.gitf.mail.service.MailService;

@Component
public class DaemonThread implements Runnable {

  @Autowired
  private Connection connection;

  @Autowired
  private MailService service;

  @Override
  public void run() {
    while (true) {
      try {
        // service.sendMail(connection);
        TimeUnit.SECONDS.sleep(5);
        break;
      } catch (final Exception e) {
        e.printStackTrace();
        break;
      }
    }

  }

}
