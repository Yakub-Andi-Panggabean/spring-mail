package com.special.gitf.mail.service.bean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.special.gitf.mail.domain.Email;
import com.special.gitf.mail.domain.MailAction;
import com.special.gitf.mail.domain.MailAttachment;
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

  @Value("${reset_password.url}")
  private String passwordResetUrl;

  @Value("${login.confirmation.userReplacement}")
  private String userNameReplacement;

  @Value("${login.confirmation.urlReplacement}")
  private String urlReplacement;

  @Override
  @Transactional(readOnly = false)
  public void sendPasswordReminderMail(MailTransaction transaction) throws Exception {

    final StringBuilder builder = new StringBuilder();

    final MailAction action = repository.findMailActionByActionID(transaction.getActionId());

    String mailContent = action.getTemplate();

    final User user = repository.findUserById(transaction.getUserId());

    if (user == null) {
      throw new RuntimeException("user with id " + transaction.getUserId() + " is not found");
    }

    builder.append(user.getId()).append("||")
        .append(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date()));
    builder.append("||").append(MailSenderUtil.generateUuid());

    final String encoded64 = MailSenderUtil.encodeToBase64(builder.toString());

    final String api = passwordResetUrl.concat(encoded64);

    mailContent = mailContent.replaceAll(userNameReplacement, user.getName());
    mailContent = mailContent.replaceAll(urlReplacement, api);

    final Email email = new Email(mailContent, user.getEmail(), action.getSubject(), serverMail);

    MailSenderUtil.sendMail(sender, email);

    logger.info("send password reminder email to {}", email.getDestination());

    repository.updateMailTransactionStatus(transaction.getId());



  }

  @Override
  public void sendRegistrationConfirmationMail(MailTransaction transaction) throws Exception {

    final StringBuilder builder = new StringBuilder();

    final MailAction action = repository.findMailActionByActionID(transaction.getActionId());

    String mailContent = action.getTemplate();

    final User user = repository.findUserById(transaction.getUserId());

    if (user == null) {
      throw new RuntimeException("user with id " + transaction.getUserId() + " is not found");
    }

    builder.append(user.getId()).append("||")
        .append(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date()));
    builder.append("||").append(MailSenderUtil.generateUuid());

    final String encoded64 = MailSenderUtil.encodeToBase64(builder.toString());

    final String api = apiUrl.concat(encoded64);

    mailContent = mailContent.replaceAll(userNameReplacement, user.getName());
    mailContent = mailContent.replaceAll(urlReplacement, api);

    final Email email = new Email(mailContent, user.getEmail(), action.getSubject(), serverMail);

    MailSenderUtil.sendMail(sender, email);

    logger.info("send registration confirmation email to {}", email.getDestination());

    repository.updateMailTransactionStatus(transaction.getId());

  }

  @Override
  public void sendBookingInvoiceMail(MailTransaction mailTransaction) throws Exception {

    logger.debug("send email with attachment");

    final MailAttachment mailAttachment = new MailAttachment();



    final MailAction action = repository.findMailActionByActionID(mailTransaction.getActionId());

    final String mailContent = action.getTemplate();

    final User user = repository.findUserById(mailTransaction.getUserId());

    if (user != null) {

      final List<File> attachmentFiles = new ArrayList<>();
      final List<String> attachmentNames = new ArrayList<>();

      for (final Map<String, Object> map : repository
          .findMailAttachmentByTransactionId(mailTransaction.getId())) {

        final File file = new File(map.get("attachment").toString());

        if (file.exists()) {
          attachmentFiles.add(file);
          attachmentNames.add(map.get("attachment_name").toString());
        } else {
          logger.error("cannot find file with name ");
        }

      }

      mailAttachment.setContent(mailContent);
      mailAttachment.setDestination(user.getEmail());
      mailAttachment.setFrom(serverMail);
      mailAttachment.setSubject(action.getSubject());
      mailAttachment.setAttachments(attachmentFiles);
      mailAttachment.setAttachmentNames(attachmentNames);



      MailSenderUtil.sendMailWithAttachment(sender, mailAttachment);

      logger.info("send invoice email to {}", user.getEmail());

      repository.updateMailTransactionStatus(mailTransaction.getId());
    } else {
      throw new RuntimeException("user cannot be found");
    }

  }

}
