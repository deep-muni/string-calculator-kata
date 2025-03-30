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

  @Test
  void return_sum_for_input_with_two_integers() {
    assertThat(stringCalculator.add("1,2")).isEqualTo(3);
  }

  @Test
  void return_sum_for_input_with_more_than_two_integers() {
    assertThat(stringCalculator.add("1,2,3,4")).isEqualTo(10);
  }

  @Test
  void return_sum_for_input_string_with_new_line_as_delimiter() {
    assertThat(stringCalculator.add("1\n2,3\n4")).isEqualTo(10);
  }

  @Test
  void return_sum_for_input_string_with_single_custom_delimiter() {
    assertThat(stringCalculator.add("//;\n1;2,3\n4")).isEqualTo(10);
  }
}
