package br.com.edwardjhow.picpay_desafio_backend.notification;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.edwardjhow.picpay_desafio_backend.transaction.Transaction;


@Service
public class NotificationProducer {
  private final KafkaTemplate<String, Transaction> kafkaTemplate;

  public NotificationProducer(KafkaTemplate<String, Transaction> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendNotification(Transaction transaction) {
    kafkaTemplate.send("transaction-notification", transaction);
  }
}