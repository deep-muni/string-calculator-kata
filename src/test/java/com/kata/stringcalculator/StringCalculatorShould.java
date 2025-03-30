package com.kata.stringcalculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.kata.stringcalculator.exceptions.NegativeNumberNotAllowedException;
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

  @Test
  void throw_exception_when_negative_number_is_present_in_string() {
    Exception exception =
        assertThrows(
            NegativeNumberNotAllowedException.class, () -> stringCalculator.add("-1,-2,3,4"));
    assertThat(exception.getMessage()).isEqualTo("negatives not allowed - [-1, -2]");

    exception =
        assertThrows(
            NegativeNumberNotAllowedException.class, () -> stringCalculator.add("//;\n-1;-2,3\n4"));
    assertThat(exception.getMessage()).isEqualTo("negatives not allowed - [-1, -2]");
  }

  @Test
  void return_sum_for_input_string_ignoring_numbers_above_1000() {
    assertThat(stringCalculator.add("1\n1000,3000\n4")).isEqualTo(1005);
    assertThat(stringCalculator.add("//;\n1;1000,3000\n4")).isEqualTo(1005);
  }

  @Test
  void return_sum_for_input_string_with_single_custom_delimiter_special_character() {
    assertThat(stringCalculator.add("//.\n1.2,3\n4")).isEqualTo(10);
  }

  @Test
  void return_sum_for_input_string_with_multiple_custom_delimiter_with_any_length() {
    assertThat(stringCalculator.add("//[;;;][*]\n1;;;2,3\n4*5")).isEqualTo(15);
  }

  @Test
  void return_multiplication_of_numbers_when_custom_delimiter_is_asterisk() {
    assertThat(stringCalculator.add("//*\n1*2,3\n4")).isEqualTo(24);
    assertThat(stringCalculator.add("//[*]\n1*2,3\n4")).isEqualTo(24);
    assertThat(stringCalculator.add("//[*]\n1*2,3\n4*1000\n1001")).isEqualTo(24000);
  }
}
