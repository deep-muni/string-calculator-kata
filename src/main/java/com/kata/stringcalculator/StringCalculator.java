package com.kata.stringcalculator;

import org.apache.commons.lang3.StringUtils;

public class StringCalculator {

  public int add(String input) {

    if (StringUtils.isBlank(input)) {
      return 0;
    }

    if (input.length() == 1) {
      return Integer.parseInt(input);
    }

    return Integer.parseInt(String.valueOf(input.charAt(0)))
        + Integer.parseInt(String.valueOf(input.charAt(2)));
  }
}
