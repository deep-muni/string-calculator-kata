package com.kata.stringcalculator;

import com.kata.stringcalculator.exceptions.NegativeNumberNotAllowedException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class StringCalculator {

  public int add(String input) {

    if (StringUtils.isBlank(input)) {
      return 0;
    }

    String delimiters = "[,\n]";

    if (input.startsWith("//")) {
      int separatorIndex = input.indexOf("\n");
      String delimiterPart = input.substring(2, separatorIndex);

      delimiters = "[,\n" + delimiterPart + "]";
      input = input.substring(separatorIndex + 1);
    }

    List<Integer> numbers = Arrays.stream(input.split(delimiters)).map(Integer::parseInt).toList();
    List<Integer> negatives = numbers.stream().filter(num -> num < 0).toList();

    if (!negatives.isEmpty()) {
      throw new NegativeNumberNotAllowedException(
          String.format("negatives not allowed - %s", negatives));
    }

    return numbers.stream().filter(num -> num <= 1000).reduce(0, Integer::sum);
  }
}
