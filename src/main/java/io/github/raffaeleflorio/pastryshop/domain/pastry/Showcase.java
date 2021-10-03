package io.github.raffaeleflorio.pastryshop.domain.pastry;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

/**
 * {@link Pastry} showcase
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Showcase {
  /**
   * Adds an item
   *
   * @param description The item description
   * @since 1.0.0
   */
  void add(JsonObject description);

  /**
   * Builds the description
   *
   * @return The description
   * @since 1.0.0
   */
  JsonArray description();

  /**
   * {@link Showcase} validated according some rules
   *
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  interface Validated extends Showcase {

  }

  /**
   * {@link Showcase} useful for testing
   *
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake implements Showcase {
    /**
     * Builds an empty fake unable to add items
     *
     * @since 1.0.0
     */
    public Fake() {
      this(
        () -> {
          throw new IllegalStateException("Unable to add to a fake");
        },
        Json.createArrayBuilder().build()
      );
    }

    /**
     * Builds a fake
     *
     * @param addFn       The add function
     * @param description The description
     * @since 1.0.0
     */
    public Fake(final Runnable addFn, final JsonArray description) {
      this.addFn = addFn;
      this.description = description;
    }

    @Override
    public void add(final JsonObject description) {
      addFn.run();
    }

    @Override
    public JsonArray description() {
      return description;
    }

    private final Runnable addFn;
    private final JsonArray description;
  }
}
