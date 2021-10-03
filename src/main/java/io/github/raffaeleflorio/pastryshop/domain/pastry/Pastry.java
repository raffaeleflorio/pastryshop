package io.github.raffaeleflorio.pastryshop.domain.pastry;

import jakarta.json.JsonObject;

/**
 * A produced pastry
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Pastry {
  /**
   * Sells itself
   *
   * @param quantity The quantity
   * @since 1.0.0
   */
  void sell(Number quantity);

  /**
   * Builds the description
   *
   * @return The description
   * @since 1.0.0
   */
  JsonObject description();

  /**
   * Builds the price
   *
   * @return The price
   * @since 1.0.0
   */
  Number price();
}
