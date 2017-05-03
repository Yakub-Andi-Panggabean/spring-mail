package com.special.gitf.mail.service.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.special.gitf.mail.domain.Email;
import com.special.gitf.mail.domain.MailAction;
import com.special.gitf.mail.domain.MailTransaction;
import com.special.gitf.mail.domain.User;
import com.special.gitf.mail.repository.MailRepository;
import com.special.gitf.mail.service.MailDeliveryService;
import com.special.gitf.mail.util.MailSenderUtil;

@Service
@Transactional(readOnly = true)
public class MailDeliveryServiceBean implements MailDeliveryService {

  private static final Logger logger = LoggerFactory.getLogger(MailDeliveryService.class);

  @Autowired
  private JavaMailSenderImpl sender;

  @Autowired
  private MailRepository repository;

  @Value("${mail.user}")
  private String serverMail;

  @Value("${api.url}")
  private String apiUrl;

  @Override
  @Transactional(readOnly = false)
  public void sendPasswordReminderMail(MailTransaction transaction) throws Exception {



  }

  @Override
  public void sendRegistrationConfirmationMail(MailTransaction transaction) throws Exception {

    final StringBuilder builder = new StringBuilder();

    final MailAction action = repository.findMailActionByActionID(transaction.getActionId());

    String mailContent = MailSenderUtil.readMailTemplate(action.getTemplate());

    final User user = repository.findUserById(transaction.getUserId());

    if (user == null) {
      throw new RuntimeException("user with id " + transaction.getUserId() + " is not found");
    }

    builder.append(user.getId()).append("||")
        .append(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date()));
    builder.append("||").append(MailSenderUtil.generateUuid());

    final String encoded64 = MailSenderUtil.encodeToBase64(builder.toString());

    final String api = apiUrl.concat(encoded64);

    mailContent = mailContent.replaceAll("<<name>>", user.getName());
    mailContent = mailContent.replaceAll("<<url>>", api);


    logger.debug("mail content : {}", mailContent);

    final Email email =
        new Email(mailContent, user.getEmail(), "Registration Confrimation", serverMail);

    MailSenderUtil.sendMail(sender, email);

    repository.updateMailTransactionStatus(transaction.getId());

  }

  @Override
  public void sendBookingInvoiceMail(MailTransaction mailTransaction) throws Exception {
    // TODO Auto-generated method stub

  }

}
