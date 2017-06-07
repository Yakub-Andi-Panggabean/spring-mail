package com.special.gitf.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.special.gitf.mail.service.daemon.MailDeliveryProcessorDaemon;
import com.special.gitf.mail.service.daemon.MailRequestFinderDaemon;
import com.special.gitf.mail.util.CommonUtil;

@SpringBootApplication
public class MailServiceApplication {

  public static void main(String[] args) {
    final ConfigurableApplicationContext context =
        SpringApplication.run(MailServiceApplication.class, args);
    final MailDeliveryProcessorDaemon daemon = context.getBean(MailDeliveryProcessorDaemon.class);
    final MailRequestFinderDaemon registrationConfirmation =
        (MailRequestFinderDaemon) context.getBean(CommonUtil.REGISTRATION_CONFIRMATION_BEAN);
    final MailRequestFinderDaemon passwordReminder =
        (MailRequestFinderDaemon) context.getBean(CommonUtil.PASSWORD_REMINDER_BEAN);


    final Thread thread = new Thread(daemon);
    final Thread registrationConfirmationThread = new Thread(registrationConfirmation);
    final Thread passwordReminderThread = new Thread(passwordReminder);


    thread.start();
    registrationConfirmationThread.start();
    passwordReminderThread.start();


  }
}
