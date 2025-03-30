package com.kata.stringcalculator;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class StringCalculator {

  public int add(String input) {

    if (StringUtils.isBlank(input)) {
      return 0;
    }

    if (input.length() == 1) {
      return Integer.parseInt(input);
    }

    if (input.length() == 3) {
      return Integer.parseInt(String.valueOf(input.charAt(0)))
          + Integer.parseInt(String.valueOf(input.charAt(2)));
    }

    return Arrays.stream(input.split(","))
        .map(num -> Integer.parseInt(num))
        .reduce(0, (sum, num) -> sum + num);
  }
}
