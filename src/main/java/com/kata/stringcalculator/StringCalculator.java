package com.kata.stringcalculator;

import com.kata.stringcalculator.exceptions.NegativeNumberNotAllowedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class StringCalculator {

  private static final String NEW_LINE = "\n";
  private static final String MULTIPLICATION = "*";
  private static final String DELIMITER_SEPARATOR = "|";
  private static final String CUSTOM_DELIMITER_PREFIX = "//";
  private static final String CUSTOM_DELIMITER_REGEX = "[\\[\\]]+";
  private static final List<String> DEFAULT_DELIMITERS = Arrays.asList(",", NEW_LINE);
  private static final String NEGATIVES_NOT_ALLOWED_MESSAGE = "negatives not allowed - %s";

  public int add(String input) {

    if (StringUtils.isBlank(input)) {
      return 0;
    }

    boolean shouldMultiply = false;

    List<String> delimiters = new ArrayList<>(DEFAULT_DELIMITERS);

    if (input.startsWith(CUSTOM_DELIMITER_PREFIX)) {
      int separatorIndex = input.indexOf(NEW_LINE);
      String delimiterPart = input.substring(2, separatorIndex);

      List<String> customDelimiters =
          Arrays.stream(delimiterPart.split(CUSTOM_DELIMITER_REGEX))
              .filter(StringUtils::isNotBlank)
              .map(Pattern::quote)
              .toList();

      if (customDelimiters.size() == 1
          && Pattern.matches(customDelimiters.getFirst(), MULTIPLICATION)) {
        shouldMultiply = true;
      }

      delimiters.addAll(customDelimiters);
      input = input.substring(separatorIndex + 1);
    }

    String delimiter = String.join(DELIMITER_SEPARATOR, delimiters);
    List<Integer> numbers = Arrays.stream(input.split(delimiter)).map(Integer::parseInt).toList();
    List<Integer> negatives = numbers.stream().filter(num -> num < 0).toList();

    if (!negatives.isEmpty()) {
      throw new NegativeNumberNotAllowedException(
          String.format(NEGATIVES_NOT_ALLOWED_MESSAGE, negatives));
    }

    if (shouldMultiply) {
      return numbers.stream().filter(num -> num <= 1000).reduce(1, (mul, num) -> mul * num);
    }

    return numbers.stream().filter(num -> num <= 1000).reduce(0, Integer::sum);
  }
}
