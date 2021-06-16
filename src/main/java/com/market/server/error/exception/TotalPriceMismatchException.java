package com.market.server.error.exception;

public class TotalPriceMismatchException extends IllegalArgumentException {
  public TotalPriceMismatchException(String msg) {
    super(msg);
  }
}
