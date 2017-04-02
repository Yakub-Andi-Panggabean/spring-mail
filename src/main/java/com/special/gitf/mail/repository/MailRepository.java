package com.special.gitf.mail.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.special.gitf.mail.domain.MailAction;
import com.special.gitf.mail.domain.MailTransaction;
import com.special.gitf.mail.util.CommonUtil;

@Repository
public class MailRepository {


  private static final Logger log = LoggerFactory.getLogger(MailRepository.class);

  @Autowired
  private Environment env;

  @Autowired
  Connection connection;

  public void insertMailTransaction(MailTransaction transaction) throws Exception {

    log.debug("transaction : {}", transaction.toString());

    final PreparedStatement statement =
        connection.prepareStatement(env.getProperty(CommonUtil.INSERT_MAIL_TRANSACTION_KEY));
    statement.setString(1, transaction.getId());
    statement.setString(2, transaction.getUserId());
    statement.setString(3, transaction.getActionId());
    statement.setBoolean(4, transaction.isStatus());
    statement.setDate(5, new Date(transaction.getTransactionDateTime().getTime()));
    statement.executeUpdate();

  }



  public void insertMailAction(MailAction action) throws Exception {

    log.debug("transaction : {}", action.toString());

    final PreparedStatement statement =
        connection.prepareStatement(env.getProperty(CommonUtil.INSERT_MAIL_CONFIG_KEY));
    statement.setString(1, action.getId());
    statement.setString(2, action.getActionName());
    statement.setString(3, action.getTemplate());
    statement.setDate(4, new Date(action.getCreatedDate().getTime()));
    statement.setString(5, action.getCreatedBy());
    statement.setDate(6, new Date(action.getUpdatedDate().getTime()));
    statement.executeUpdate();
  }


  public List<String> findInactiveUserEmail() throws Exception {

    final List<String> list = new ArrayList<>();

    final PreparedStatement statement =
        connection.prepareStatement(env.getProperty(CommonUtil.FIND_INACTIVE_USER_KEY));
    statement.setString(1, "0");
    final ResultSet rs = statement.executeQuery();

    while (rs.next()) {

      list.add(rs.getString("email"));

    }

    return list;
  }



}
