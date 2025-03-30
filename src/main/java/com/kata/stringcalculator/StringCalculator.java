package com.kata.stringcalculator;

import com.kata.stringcalculator.exceptions.NegativeNumberNotAllowedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringCalculator {

  public int add(String input) {

    if (StringUtils.isBlank(input)) {
      return 0;
    }

    List<String> delimiters = new ArrayList<>(Arrays.asList(",", "\n"));

    if (input.startsWith("//")) {
      int separatorIndex = input.indexOf("\n");
      String customDelimiter = input.substring(2, separatorIndex);

      delimiters.add(Pattern.quote(customDelimiter));
      input = input.substring(separatorIndex + 1);
    }

    String delimiter = String.join("|", delimiters);
    List<Integer> numbers = Arrays.stream(input.split(delimiter)).map(Integer::parseInt).toList();
    List<Integer> negatives = numbers.stream().filter(num -> num < 0).toList();

    if (!negatives.isEmpty()) {
      throw new NegativeNumberNotAllowedException(
          String.format("negatives not allowed - %s", negatives));
    }

    return numbers.stream().filter(num -> num <= 1000).reduce(0, Integer::sum);
  }
}
