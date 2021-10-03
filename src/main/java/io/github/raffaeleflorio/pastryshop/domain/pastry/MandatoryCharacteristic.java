package io.github.raffaeleflorio.pastryshop.domain.pastry;


import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

import java.util.Optional;

/**
 * {@link Summaries} which adds only if a characteristic is present
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class MandatoryCharacteristic implements ValidatedSummaries {
  /**
   * Builds summary
   *
   * @param origin    The summaries to decorate
   * @param expected  The expected characteristic
   * @param exception The exception to throw
   * @since 1.0.0
   */
  public MandatoryCharacteristic(final Summaries origin, final CharSequence expected, final RuntimeException exception) {
    this.origin = origin;
    this.expected = expected;
    this.exception = exception;
  }

  @Override
  public void add(final JsonObject description) {
    if (!description.containsKey(expected.toString())) {
      throw exception;
    }
    origin.add(description);
  }

  @Override
  public void remove(final CharSequence id) {
    origin.remove(id);
  }

  @Override
  public Optional<Summary> summary(final CharSequence id) {
    return origin.summary(id);
  }

  @Override
  public JsonArray description() {
    return origin.description();
  }

  private final Summaries origin;
  private final CharSequence expected;
  private final RuntimeException exception;
}
