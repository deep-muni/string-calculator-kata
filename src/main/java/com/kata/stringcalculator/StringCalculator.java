package com.kata.stringcalculator;

import org.apache.commons.lang3.StringUtils;

public class StringCalculator {

  public int add(String input) {

    if (StringUtils.isBlank(input)) {
      return 0;
    }

    return Integer.parseInt(input);
  }
}
