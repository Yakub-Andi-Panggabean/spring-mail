package com.special.gitf.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.special.gitf.mail.service.daemon.DaemonThread;

@SpringBootApplication
public class MailServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(
				MailServiceApplication.class, args);
		DaemonThread daemon = context.getBean(DaemonThread.class);
		Thread thread = new Thread(daemon);
		thread.start();
	}
}
