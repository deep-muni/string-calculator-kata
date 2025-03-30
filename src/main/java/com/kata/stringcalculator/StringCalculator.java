package com.kata.stringcalculator;

import static java.util.Arrays.stream;

import com.kata.stringcalculator.exceptions.NegativeNumberNotAllowedException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class StringCalculator {

  public static final int MAXIMUM = 1000;
  private static final String NEW_LINE = "\n";
  private static final String DELIMITER_SEPARATOR = "|";
  private static final String DEFAULT_DELIMITERS = ",|\n";
  private static final String CUSTOM_DELIMITER_PREFIX = "//";
  private static final String CUSTOM_DELIMITER_REGEX = "[\\[\\]]+";
  private static final String NEGATIVES_NOT_ALLOWED_MESSAGE = "negatives not allowed - %s";

  private static boolean containsCustomDelimiter(String input) {
    return input.startsWith(CUSTOM_DELIMITER_PREFIX);
  }

  private static boolean isGreaterThanMaximum(Integer num) {
    return num > MAXIMUM;
  }

  private static boolean shouldMultiply(String delimiters) {
    List<String> list = Arrays.asList(delimiters.split(Pattern.quote(DELIMITER_SEPARATOR)));
    return list.size() == 3 && Pattern.matches(list.getLast(), "*");
  }

  private static String extractDelimiters(String input) {
    if (!containsCustomDelimiter(input)) {
      return DEFAULT_DELIMITERS;
    }

    int separatorIndex = input.indexOf(NEW_LINE);
    String delimiterPart = input.substring(2, separatorIndex);

    return DEFAULT_DELIMITERS
        + DELIMITER_SEPARATOR
        + stream(delimiterPart.split(CUSTOM_DELIMITER_REGEX))
            .filter(StringUtils::isNotBlank)
            .map(Pattern::quote)
            .collect(Collectors.joining(DELIMITER_SEPARATOR));
  }

  private static List<Integer> extractValidNumbers(String input, String delimiters) {
    if (containsCustomDelimiter(input)) {
      input = input.substring(input.indexOf(NEW_LINE) + 1);
    }

    return stream(input.split(delimiters))
        .map(Integer::parseInt)
        .filter(num -> !isGreaterThanMaximum(num))
        .toList();
  }

  private static void validateNumbers(List<Integer> numbers) {
    List<Integer> negatives = numbers.stream().filter(num -> num < 0).toList();

    if (!negatives.isEmpty()) {
      throw new NegativeNumberNotAllowedException(
          String.format(NEGATIVES_NOT_ALLOWED_MESSAGE, negatives));
    }
  }

  private static int compute(List<Integer> numbers, boolean shouldMultiply) {
    return shouldMultiply
        ? numbers.stream().reduce(1, (mul, num) -> mul * num)
        : numbers.stream().reduce(0, Integer::sum);
  }

  public int evaluate(String input) {
    if (StringUtils.isBlank(input)) {
      return 0;
    }

    String delimiters = extractDelimiters(input);
    List<Integer> numbers = extractValidNumbers(input, delimiters);

    validateNumbers(numbers);
    return compute(numbers, shouldMultiply(delimiters));
  }
}
