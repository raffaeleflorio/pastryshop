package io.github.raffaeleflorio.pastryshop.domain.pastry;

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
}
