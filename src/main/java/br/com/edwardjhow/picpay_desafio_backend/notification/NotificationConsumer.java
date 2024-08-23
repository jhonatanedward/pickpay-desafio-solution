package br.com.edwardjhow.picpay_desafio_backend.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import br.com.edwardjhow.picpay_desafio_backend.authorization.UnauthorizedTransactionException;
import br.com.edwardjhow.picpay_desafio_backend.transaction.Transaction;

@Service
public class NotificationConsumer {
  private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);
  private RestClient restClient;

  // Instável: http://o4d9z.mocklab.io/notify
  public NotificationConsumer(RestClient.Builder builder) {
    this.restClient = builder.baseUrl(
        "https://util.devi.tools/api/v1/notify")
        .build();
  }

  @KafkaListener(topics = "transaction-notification", groupId = "picpay-desafio-backend")
  public void receiveNotification(Transaction transaction) {
    LOGGER.info("notifying transaction {}...", transaction);
    var responseEntity = restClient.post().retrieve()
    .onStatus(HttpStatusCode::isError, (request, response) -> { 
        throw new UnauthorizedTransactionException("Unauthorized!"); 
      })
    .toEntity(Notification.class);

    LOGGER.info("notification has been sent {}...", responseEntity.getBody());
  }
}