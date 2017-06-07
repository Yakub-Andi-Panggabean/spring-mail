package com.special.gitf.mail.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.special.gitf.mail.domain.MailAction;
import com.special.gitf.mail.domain.MailTransaction;
import com.special.gitf.mail.domain.PasswordReminder;
import com.special.gitf.mail.domain.User;
import com.special.gitf.mail.util.CommonUtil;

@Repository
public class MailRepository {


  private static final Logger log = LoggerFactory.getLogger(MailRepository.class);

  @Autowired
  private Environment env;

  @Autowired
  Connection connection;

  public void insertMailTransaction(MailTransaction transaction) throws Exception {

    // log.debug("transaction : {}", transaction.toString());

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
    statement.setString(2, action.getActionCode());
    statement.setString(3, action.getTemplate());
    statement.setDate(4, new Date(action.getCreatedDate().getTime()));
    statement.setString(5, action.getCreatedBy());
    statement.setDate(6, new Date(action.getUpdatedDate().getTime()));
    statement.executeUpdate();
  }


  public List<User> findInactiveUserEmail() throws Exception {

    final List<User> list = new ArrayList<>();

    final PreparedStatement statement =
        connection.prepareStatement(env.getProperty(CommonUtil.FIND_INACTIVE_USER_KEY));
    statement.setString(1, "0");
    final ResultSet rs = statement.executeQuery();

    while (rs.next()) {

      list.add(
          new User(rs.getString("user_id"), rs.getString("user_name"), rs.getString("user_email")));

    }

    return list;
  }

  public List<PasswordReminder> findPasswordReminderRequest() throws Exception {

    final List<PasswordReminder> passwordReminders = new ArrayList<>();

    final PreparedStatement statement =
        connection.prepareStatement(env.getProperty(CommonUtil.FIND_PASSWORD_REMINDER_REQUEST_KEY));
    statement.setBoolean(1, false);

    final ResultSet rs = statement.executeQuery();
    while (rs.next()) {
      passwordReminders.add(new PasswordReminder(rs.getInt(1), rs.getString("user_id"),
          rs.getBoolean("reminder_status"), rs.getDate("created_datetime")));
    }

    return passwordReminders;

  }


  public List<MailTransaction> findUnfinishedMailTransactions() throws Exception {

    final List<MailTransaction> mailTransactions = new ArrayList<>();

    final PreparedStatement statement = connection
        .prepareStatement(env.getProperty(CommonUtil.FIND_UNPROCESSED_MAIL_TRANSACTION_KEY));
    statement.setBoolean(1, false);
    final ResultSet rs = statement.executeQuery();

    while (rs.next()) {
      mailTransactions.add(new MailTransaction(rs.getString("id"), rs.getString("user_id"),
          rs.getString("config_id"), rs.getBoolean("status"), rs.getDate("transaction_datetime")));
    }

    return mailTransactions;
  }

  public MailTransaction findMailTransactionByUserId(String userId, String configId)
      throws Exception {

    final PreparedStatement statement = connection.prepareStatement(
        env.getProperty(CommonUtil.FIND_MAIL_TRANSACTION_BY_USER_ID_AND_CONFIG_ID_KEY));
    statement.setString(1, userId);
    statement.setString(2, configId);

    final ResultSet rs = statement.executeQuery();

    if (rs.next()) {
      return new MailTransaction(rs.getString("id"), rs.getString("user_id"),
          rs.getString("config_id"), rs.getBoolean("status"), rs.getDate("transaction_datetime"));
    }

    return null;

  }


  public boolean isUserAlreadyActivated(String userId) throws Exception {
    return findMailTransactionByUserId(userId, CommonUtil.ACTIVATE_USER_ACTION) != null;
  }

  public MailAction findMailActionByActionCode(String actionCode) throws Exception {
    final PreparedStatement statement = connection
        .prepareStatement(env.getProperty(CommonUtil.FIND_MAIL_ACTION_BY_ACTION_CODE_KEY));
    statement.setString(1, actionCode);
    final ResultSet rs = statement.executeQuery();
    if (rs.next()) {
      return new MailAction(rs.getString("id"), rs.getString("action_code"),
          rs.getString("mail_subject"), rs.getString("mail_template"), rs.getDate("created_date"),
          rs.getString("created_by"), rs.getDate("updated_date"), rs.getString("updated_by"));
    }

    return null;
  }


  public MailAction findMailActionByActionID(String actionId) throws Exception {

    final PreparedStatement statement =
        connection.prepareStatement(env.getProperty(CommonUtil.FIND_MAIL_ACTION_BY_ID_KEY));
    statement.setString(1, actionId);
    final ResultSet rs = statement.executeQuery();

    if (rs.next()) {
      return new MailAction(rs.getString("id"), rs.getString("action_code"),
          rs.getString("mail_subject"), rs.getString("mail_template"), rs.getDate("created_date"),
          rs.getString("created_by"), rs.getDate("updated_date"), rs.getString("updated_by"));
    }

    return null;
  }


  public synchronized String findSequence() throws Exception {
    final PreparedStatement statement =
        connection.prepareStatement(env.getProperty(CommonUtil.FIND_SEQUENCE_KEY));
    statement.setString(1, CommonUtil.EMAIL_SEQUENCE_ID);
    final ResultSet rs = statement.executeQuery();

    if (rs.next()) {
      return rs.getString("SEQUENCE_ID");
    }

    return "";
  }


  public void updateUserStatus(String status, String userId) throws Exception {

    final PreparedStatement statement =
        connection.prepareStatement(env.getProperty(CommonUtil.UPDATE_USER_STATUS_KEY));

    statement.setString(1, status);
    statement.setString(2, userId);

    statement.executeUpdate();

  }


  public User findUserById(String userId) throws Exception {
    final PreparedStatement statement =
        connection.prepareStatement(env.getProperty(CommonUtil.FIND_USER_BY_USER_ID_KEY));

    statement.setString(1, userId);

    final ResultSet rs = statement.executeQuery();

    if (rs.next()) {
      return new User(rs.getString("user_id"), rs.getString("user_name"),
          rs.getString("user_email"));
    }
    return null;
  }

  public void updateMailTransactionStatus(String id) throws Exception {
    final PreparedStatement statement =
        connection.prepareStatement(env.getProperty(CommonUtil.UPDATE_MAIL_TRANSACTION_STATUS_KEY));
    statement.setBoolean(1, true);
    statement.setString(2, id);

    statement.executeUpdate();
  }


  public void updatePasswordReminderStatus(int id) throws Exception {
    final PreparedStatement statement = connection
        .prepareStatement(env.getProperty(CommonUtil.UPDATE_PASSWORD_REMINDER_STATUS_KEY));
    statement.setBoolean(1, true);
    statement.setInt(2, id);

    statement.executeUpdate();
  }


  public List<Map<String, Object>> findMailAttachmentByTransactionId(String transactionId)
      throws Exception {

    final List<Map<String, Object>> list = new ArrayList<>();


    final PreparedStatement statement = connection
        .prepareStatement(env.getProperty(CommonUtil.FIND_MAIL_ATTACHMENT_BY_MAIL_TRANS_ID));

    statement.setString(1, transactionId);

    final ResultSet rs = statement.executeQuery();

    while (rs.next()) {

      final Map<String, Object> map = new HashMap<>();
      map.put("id", rs.getInt("id"));
      map.put("mail_transaction_id", rs.getString("mail_transaction_id"));
      map.put("attachment", rs.getString("attachment"));
      map.put("attachment_name", rs.getString("attachment_name"));
      map.put("created_date", rs.getDate("created_date"));

      list.add(map);
    }

    return list;

  }



}
