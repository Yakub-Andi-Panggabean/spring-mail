package com.special.gitf.mail.service.daemon;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.special.gitf.mail.domain.MailTransaction;
import com.special.gitf.mail.service.MailDeliveryService;
import com.special.gitf.mail.service.MailTransactionService;
import com.special.gitf.mail.util.CommonUtil;

@Component
public class MailDeliveryProcessorDaemon implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(MailDeliveryProcessorDaemon.class);

  @Autowired
  private MailTransactionService service;

  @Autowired
  private MailDeliveryService deliveryService;

  private final boolean isRunning = true;

  @Override
  public void run() {
    while (isRunning) {
      try {

        final List<MailTransaction> list = service.findUnprocessedMailTransactionRequest();

        if (list.size() > 0) {

          for (int i = 0; i < list.size(); i++) {

            final String actionCode =
                service.findActionById(list.get(i).getActionId()).getActionCode();

            if (actionCode != null) {

              switch (actionCode) {

                case CommonUtil.ACTIVATE_USER_ACTION:
                  deliveryService.sendRegistrationConfirmationMail(list.get(i));
                  break;
                case CommonUtil.FORGOT_PASSWORD_ACTION:
                  deliveryService.sendPasswordReminderMail(list.get(i));
                  break;
                case CommonUtil.SEND_INVOICE_ACTION:
                  deliveryService.sendBookingInvoiceMail(list.get(i));
                  break;
                default:
                  logger.info("unknow email action : {}", list.get(i).getActionId());

              }

            } else {
              throw new RuntimeException("null config");
            }



          }

        } else {
          logger.info("no unprocessed mail request found");
        }

        Thread.sleep(10000);


      } catch (final Exception e) {
        e.printStackTrace();
        logger.error("error occured with message : {}", e.getMessage());
        continue;
      }
    }

  }

}
