package com.kata.stringcalculator;

import com.kata.stringcalculator.exceptions.NegativeNumberNotAllowedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class StringCalculator {

  public static final int MAXIMUM = 1000;
  private static final String NEW_LINE = "\n";
  private static final String MULTIPLICATION = "*";
  private static final String DELIMITER_SEPARATOR = "|";
  private static final String CUSTOM_DELIMITER_PREFIX = "//";
  private static final String CUSTOM_DELIMITER_REGEX = "[\\[\\]]+";
  private static final List<String> DEFAULT_DELIMITERS = Arrays.asList(",", NEW_LINE);
  private static final String NEGATIVES_NOT_ALLOWED_MESSAGE = "negatives not allowed - %s";

  private static boolean containsCustomDelimiter(String input) {
    return input.startsWith(CUSTOM_DELIMITER_PREFIX);
  }

  private static boolean isGreaterThanMaximum(Integer num) {
    return num > MAXIMUM;
  }

  private static boolean shouldMultiply(List<String> delimiters) {
    boolean shouldMultiply = false;

    if (delimiters.size() == 3 && Pattern.matches(delimiters.getLast(), MULTIPLICATION)) {
      shouldMultiply = true;
    }
    return shouldMultiply;
  }

  private static List<String> extractDelimiters(String input) {
    if (!containsCustomDelimiter(input)) {
      return DEFAULT_DELIMITERS;
    }

    int separatorIndex = input.indexOf(NEW_LINE);
    String delimiterPart = input.substring(2, separatorIndex);

    List<String> delimiters = new ArrayList<>(DEFAULT_DELIMITERS);
    List<String> customDelimiters =
        Arrays.stream(delimiterPart.split(CUSTOM_DELIMITER_REGEX))
            .filter(StringUtils::isNotBlank)
            .map(Pattern::quote)
            .toList();

    delimiters.addAll(customDelimiters);

    return delimiters;
  }

  private static List<Integer> extractValidNumbers(String input, List<String> delimiters) {
    if (containsCustomDelimiter(input)) {
      int separatorIndex = input.indexOf(NEW_LINE);
      input = input.substring(separatorIndex + 1);
    }
    return Arrays.stream(input.split(String.join(DELIMITER_SEPARATOR, delimiters)))
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
    if (shouldMultiply) {
      return numbers.stream().reduce(1, (mul, num) -> mul * num);
    }

    return numbers.stream().reduce(0, Integer::sum);
  }

  public int evaluate(String input) {

    if (StringUtils.isBlank(input)) {
      return 0;
    }

    List<String> delimiters = extractDelimiters(input);
    List<Integer> numbers = extractValidNumbers(input, delimiters);

    validateNumbers(numbers);

    return compute(numbers, shouldMultiply(delimiters));
  }
}
