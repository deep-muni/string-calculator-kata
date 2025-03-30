package com.kata.stringcalculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringCalculatorShould {

  @Test
  void return_0_for_null_or_empty_string() {
    StringCalculator stringCalculator = new StringCalculator();
    int result = stringCalculator.add("");
    assertThat(result).isZero();
  }

}
