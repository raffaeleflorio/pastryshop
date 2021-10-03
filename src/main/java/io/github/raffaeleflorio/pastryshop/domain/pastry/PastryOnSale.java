package io.github.raffaeleflorio.pastryshop.domain.pastry;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.function.Supplier;

/**
 * An already on sale {@link Pastry}
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class PastryOnSale implements Pastry {
  /**
   * Builds a pastry on sale which expires after four days
   *
   * @param description The pastry description
   * @since 1.0.0
   */
  public PastryOnSale(final JsonObject description) {
    this(description, ZoneId.systemDefault());
  }

  /**
   * Builds a pastry on sale which expires after four days
   *
   * @param description The pastry description
   * @param zoneId      The timezone
   * @since 1.0.0
   */
  public PastryOnSale(final JsonObject description, final ZoneId zoneId) {
    this(description, () -> LocalDate.now(zoneId));
  }

  /**
   * Builds a pastry that expires in four days
   *
   * @param description The pastry description
   * @param now         The supplier of now
   * @since 1.0.0
   */
  public PastryOnSale(final JsonObject description, final Supplier<LocalDate> now) {
    this(description, now, 3L);
  }

  /**
   * Builds a pastry
   *
   * @param description The pastry description
   * @param now         The supplier of now
   * @param expireDays  The days after expiration
   * @since 1.0.0
   */
  public PastryOnSale(final JsonObject description, final Supplier<LocalDate> now, final Long expireDays) {
    this.description = description;
    this.now = now;
    this.expireDays = expireDays;
  }

  @Override
  public void sell(final Number quantity) {
    throw new IllegalStateException("Pastry already on sale");
  }

  @Override
  public JsonObject description() {
    return Json.createObjectBuilder(description)
      .add("expiration", expirationDate().toString())
      .add("price", price().doubleValue())
      .build();
  }

  private LocalDate expirationDate() {
    return manufacturedDate().plusDays(expireDays);
  }

  private LocalDate manufacturedDate() {
    return LocalDate.parse(description.getString("manufactured"));
  }

  @Override
  public Number price() {
    assertNotExpired();
    var priceMap = Map.<Long, Supplier<Number>>of(
      0L, this::initialPrice,
      1L, () -> initialPrice().doubleValue() * 0.8,
      2L, () -> initialPrice().doubleValue() * 0.2);
    return priceMap.get(soldDays()).get();
  }

  private Number initialPrice() {
    return description.getJsonNumber("price").numberValue();
  }

  private void assertNotExpired() {
    if (expired()) {
      throw new IllegalStateException("Expired pastry");
    }
  }

  private Long soldDays() {
    return manufacturedDate().until(now.get(), ChronoUnit.DAYS);
  }

  @Override
  public Boolean expired() {
    return soldDays() >= expireDays;
  }

  private final JsonObject description;
  private final Supplier<LocalDate> now;
  private final Long expireDays;
}
