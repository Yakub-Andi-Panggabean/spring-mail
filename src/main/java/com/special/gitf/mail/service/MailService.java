package com.special.gitf.mail.service;

import java.sql.Connection;

public interface MailService {

  void sendMail(Connection connection) throws Exception;

}
