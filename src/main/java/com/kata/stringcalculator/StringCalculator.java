package com.kata.stringcalculator;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class StringCalculator {

  public int add(String input) {

    if (StringUtils.isBlank(input)) {
      return 0;
    }

    if (input.startsWith("//")) {
      int separatorIndex = input.indexOf("\n");
      String delimiterPart = input.substring(2, separatorIndex);
      String numberPart = input.substring(separatorIndex + 1);

      return Arrays.stream(numberPart.split("[,\n" + delimiterPart + "]"))
          .map(Integer::parseInt)
          .reduce(0, Integer::sum);
    }

    return Arrays.stream(input.split("[,\n]")).map(Integer::parseInt).reduce(0, Integer::sum);
  }
}
