package br.com.edwardjhow.picpay_desafio_backend.transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.edwardjhow.picpay_desafio_backend.authorization.UnauthorizedTransactionException;

@ControllerAdvice
public class TransactionExceptionHandler {

  @ExceptionHandler(InvalidTransactionException.class)
  public ResponseEntity<Object> handle(InvalidTransactionException exception) {
    return ResponseEntity.badRequest().body(exception.getMessage());
  }

  @ExceptionHandler(UnauthorizedTransactionException.class)
  public ResponseEntity<Object> handle(UnauthorizedTransactionException exception) {
    return ResponseEntity.badRequest().body(exception.getMessage());
  }
}