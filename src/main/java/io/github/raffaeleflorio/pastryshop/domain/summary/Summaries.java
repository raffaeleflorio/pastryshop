package io.github.raffaeleflorio.pastryshop.domain.summary;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

import java.util.Optional;
import java.util.function.Consumer;
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
   * {@link Summaries} validated according some rules
   *
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  interface Validated extends Summaries {

  }

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
      this(id -> {
        throw new IllegalStateException("Unable to build a summary from a fake");
      });
    }

    /**
     * Builds a fake that throws exception
     *
     * @param summaryFn The function to build summary
     * @since 1.0.0
     */
    public Fake(final Function<CharSequence, Optional<Summary>> summaryFn) {
      this(
        x -> {
          throw new IllegalStateException("Unable to add to a fake");
        },
        x -> {
          throw new IllegalStateException("Unable to remove from a fake");
        },
        summaryFn,
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
      final Consumer<JsonObject> addFn,
      final Consumer<CharSequence> removeFn,
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
      addFn.accept(description);
    }

    @Override
    public void remove(final CharSequence id) {
      removeFn.accept(id);
    }

    @Override
    public Optional<Summary> summary(final CharSequence id) {
      return summaryFn.apply(id);
    }

    @Override
    public JsonArray description() {
      return description;
    }

    private final Consumer<JsonObject> addFn;
    private final Consumer<CharSequence> removeFn;
    private final Function<CharSequence, Optional<Summary>> summaryFn;
    private final JsonArray description;
  }
}
