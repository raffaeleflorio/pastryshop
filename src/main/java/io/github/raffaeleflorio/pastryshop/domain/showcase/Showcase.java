package io.github.raffaeleflorio.pastryshop.domain.showcase;

import io.github.raffaeleflorio.pastryshop.domain.pastry.Pastry;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

import java.util.function.BiConsumer;

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
   * @param description The pastry description
   * @param quantity    The available quantity
   * @since 1.0.0
   */
  void add(JsonObject description, Number quantity);

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
        (x, y) -> {
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
    public Fake(final BiConsumer<JsonObject, Number> addFn, final JsonArray description) {
      this.addFn = addFn;
      this.description = description;
    }

    @Override
    public void add(final JsonObject description, final Number quantity) {
      addFn.accept(description, quantity);
    }

    @Override
    public JsonArray description() {
      return description;
    }

    private final BiConsumer<JsonObject, Number> addFn;
    private final JsonArray description;
  }
}
