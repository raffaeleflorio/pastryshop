package io.github.raffaeleflorio.pastryshop.domain.showcase;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

import java.time.LocalDate;
import java.util.function.Supplier;

/**
 * {@link Showcase} that localize the sales
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class LocalizedShowcase implements Showcase {
  /**
   * Builds a showcase with system dependent {@link LocalDate}
   *
   * @param origin The showcase to decorate
   * @since 1.0.0
   */
  public LocalizedShowcase(final Showcase origin) {
    this(origin, LocalDate::now);
  }

  /**
   * Builds a showcase
   *
   * @param origin The showcase to decorate
   * @param now    The now supplier
   * @since 1.0.0
   */
  public LocalizedShowcase(final Showcase origin, final Supplier<LocalDate> now) {
    this.origin = origin;
    this.now = now;
  }

  @Override
  public void add(final JsonObject description, final Number quantity) {
    origin.add(
      Json.createObjectBuilder(description).add("manufactured", manufacturedDate()).build(),
      quantity
    );
  }

  private String manufacturedDate() {
    return now.get().toString();
  }

  @Override
  public JsonArray description() {
    return origin.description();
  }

  private final Showcase origin;
  private final Supplier<LocalDate> now;
}
