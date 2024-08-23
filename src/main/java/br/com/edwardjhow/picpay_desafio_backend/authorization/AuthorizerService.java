package br.com.edwardjhow.picpay_desafio_backend.authorization;

import org.apache.tomcat.util.http.parser.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import br.com.edwardjhow.picpay_desafio_backend.transaction.Transaction;

@Service
public class AuthorizerService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizerService.class);
  private RestClient restClient;

  public AuthorizerService(RestClient.Builder builder) {
    this.restClient = builder.baseUrl(
        "https://util.devi.tools/api/v2/authorize").build();
  }

  public void authorize(Transaction transaction) {
    LOGGER.info("authorizing transaction {}...", transaction);

    restClient.get()
      .retrieve()
      .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> { 
        throw new UnauthorizedTransactionException("Unauthorized!"); 
      })
      .body(String.class);
  }
}