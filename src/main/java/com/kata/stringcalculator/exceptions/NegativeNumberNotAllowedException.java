package com.kata.stringcalculator.exceptions;

public class NegativeNumberNotAllowedException extends RuntimeException {

  public NegativeNumberNotAllowedException(String message) {
    super(message);
  }
}
