package com.kata.stringcalculator;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class StringCalculator {

  public int add(String input) {

    if (StringUtils.isBlank(input)) {
      return 0;
    }

    return Arrays.stream(input.split("[,\n]")).map(Integer::parseInt).reduce(0, Integer::sum);
  }
}
