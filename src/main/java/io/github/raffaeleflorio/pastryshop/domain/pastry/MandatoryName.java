package io.github.raffaeleflorio.pastryshop.domain.pastry;


import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

import java.util.Optional;

/**
 * {@link Summaries} which adds only if name is present
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class MandatoryName implements Summaries.Validated {
  /**
   * Builds summaries
   *
   * @param origin The summaries to decorate
   * @since 1.0.0
   */
  public MandatoryName(final Summaries origin) {
    this(origin, new IllegalStateException("Missing name characteristic"));
  }

  /**
   * Builds summaries
   *
   * @param origin    The summaries to decorate
   * @param exception The exception to throw
   * @since 1.0.0
   */
  public MandatoryName(final Summaries origin, final RuntimeException exception) {
    this(
      new MandatoryCharacteristic(origin, "name", exception)
    );
  }

  private MandatoryName(final Summaries.Validated origin) {
    this.origin = origin;
  }

  @Override
  public void add(final JsonObject description) {
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

  private final Summaries.Validated origin;
}
