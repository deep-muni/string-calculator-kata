package com.kata.stringcalculator;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class StringCalculator {

  public int add(String input) {

    if (StringUtils.isBlank(input)) {
      return 0;
    }

    String delimiters = "[,\n]";
    String numbers = input;

    if (input.startsWith("//")) {
      int separatorIndex = input.indexOf("\n");
      String delimiterPart = input.substring(2, separatorIndex);

      delimiters = "[,\n" + delimiterPart + "]";
      numbers = input.substring(separatorIndex + 1);
    }

    List<Integer> negatives =
        Arrays.stream(numbers.split(delimiters))
            .map(Integer::parseInt)
            .filter(num -> num < 0)
            .toList();

    if (!negatives.isEmpty()) {
      throw new RuntimeException(String.format("negatives not allowed - %s", negatives));
    }

    return Arrays.stream(numbers.split(delimiters)).map(Integer::parseInt).reduce(0, Integer::sum);
  }
}
