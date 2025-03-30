package com.kata.stringcalculator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringCalculatorShould {

  private StringCalculator stringCalculator;

  @BeforeEach
  void setup() {
    stringCalculator = new StringCalculator();
  }

  @Test
  void return_0_for_null_or_empty_string() {
    assertThat(stringCalculator.add("")).isZero();
    assertThat(stringCalculator.add(null)).isZero();
  }

  @Test
  void return_same_number_for_single_digit_in_string() {
    assertThat(stringCalculator.add("1")).isEqualTo(1);
  }
}
