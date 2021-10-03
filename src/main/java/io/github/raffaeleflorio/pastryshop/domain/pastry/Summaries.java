package io.github.raffaeleflorio.pastryshop.domain.pastry;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

import java.util.Optional;
import java.util.function.Function;

/**
 * Group of {@link Summary}
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Summaries {
  /**
   * Adds an item
   *
   * @param description The item description
   * @since 1.0.0
   */
  void add(JsonObject description);

  /**
   * Removes an item
   *
   * @param id The item id
   * @since 1.0.0
   */
  void remove(CharSequence id);

  /**
   * Builds a summary item
   *
   * @param id The id
   * @return The summary or empty
   * @since 1.0.0
   */
  Optional<Summary> summary(CharSequence id);

  /**
   * Builds the description
   *
   * @return The description
   * @since 1.0.0
   */
  JsonArray description();

  /**
   * {@link Summaries} useful for testing
   *
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake implements Summaries {
    /**
     * Builds a fake that throws exception
     *
     * @author Raffaele Florio (raffaeleflorio@protonmail.com)
     * @since 1.0.0
     */
    public Fake() {
      this(
        () -> {
          throw new IllegalStateException("Unable to add to a fake");
        },
        () -> {
          throw new IllegalStateException("Unable to remove from a fake");
        },
        id -> {
          throw new IllegalStateException("Unable to build a summary from a fake");
        },
        Json.createArrayBuilder().build()
      );
    }

    /**
     * Builds a fake
     *
     * @param addFn       The add function
     * @param removeFn    The remove function
     * @param summaryFn   The summary function
     * @param description The description
     * @since 1.0.0
     */
    public Fake(
      final Runnable addFn,
      final Runnable removeFn,
      final Function<CharSequence, Optional<Summary>> summaryFn,
      final JsonArray description
    ) {
      this.addFn = addFn;
      this.removeFn = removeFn;
      this.summaryFn = summaryFn;
      this.description = description;
    }

    @Override
    public void add(final JsonObject description) {
      addFn.run();
    }

    @Override
    public void remove(final CharSequence id) {
      removeFn.run();
    }

    @Override
    public Optional<Summary> summary(final CharSequence id) {
      return summaryFn.apply(id);
    }

    @Override
    public JsonArray description() {
      return description;
    }

    private final Runnable addFn;
    private final Runnable removeFn;
    private final Function<CharSequence, Optional<Summary>> summaryFn;
    private final JsonArray description;
  }
}
