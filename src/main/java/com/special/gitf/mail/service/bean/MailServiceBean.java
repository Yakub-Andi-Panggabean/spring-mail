package com.special.gitf.mail.service.bean;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.special.gitf.mail.domain.MailTransaction;
import com.special.gitf.mail.domain.PasswordReminder;
import com.special.gitf.mail.domain.User;
import com.special.gitf.mail.repository.MailRepository;
import com.special.gitf.mail.service.MailTransactionService;
import com.special.gitf.mail.util.CommonUtil;

@Service
@Transactional(readOnly = true)
public class MailServiceBean implements MailTransactionService {

  private static final Logger logger = LoggerFactory.getLogger(MailServiceBean.class);

  @Autowired
  private JavaMailSenderImpl sender;

  @Autowired
  private MailRepository repository;

  @Value("${mail.user}")
  private String mailSender;


  @Override
  @Transactional(readOnly = false)
  public void findRegisrationConfirmationMailRequest() throws Exception {

    // find inactive user
    final List<User> inactiveUsers = repository.findInactiveUserEmail();

    for (int i = 0; i < inactiveUsers.size(); i++) {


      logger.debug("user already activated : {}",
          repository.isUserAlreadyActivated(inactiveUsers.get(i).getId()));

      // generate sequence
      final String sequence = repository.findSequence();

      // insert record with registration confirmation config id into mail transaction
      repository.insertMailTransaction(new MailTransaction(sequence, inactiveUsers.get(i).getId(),
          repository.findMailActionByActionCode(CommonUtil.ACTIVATE_USER_ACTION).getId(), false,
          new Date()));

      repository.updateUserStatus(CommonUtil.WAITING_CONFIRMATION, inactiveUsers.get(i).getId());

    }

  }

  @Override
  @Transactional(readOnly = false)
  public void findForgotPasswordConfirmationMailRequest() throws Exception {

    // find password reminder request
    final List<PasswordReminder> passwordReminder = repository.findPasswordReminderRequest();

    for (int i = 0; i < passwordReminder.size(); i++) {

      // insert record with registration confirmation config id into mail transaction
      repository.insertMailTransaction(
          new MailTransaction(repository.findSequence(), passwordReminder.get(i).getUserId(),
              CommonUtil.FORGOT_PASSWORD_ACTION, false, new Date()));

    }

  }

  @Override
  public void findBookingInvoiceMailRequest() throws Exception {



  }

  @Override
  public List<MailTransaction> findUnprocessedMailTransactionRequest() throws Exception {
    return repository.findUnfinishedMailTransactions();
  }



}
