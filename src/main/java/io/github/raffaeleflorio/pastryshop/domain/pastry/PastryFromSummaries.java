package io.github.raffaeleflorio.pastryshop.domain.pastry;

import io.github.raffaeleflorio.pastryshop.domain.showcase.Showcase;
import io.github.raffaeleflorio.pastryshop.domain.summary.Summaries;
import io.github.raffaeleflorio.pastryshop.domain.summary.Summary;
import jakarta.json.JsonObject;

/**
 * A saleable {@link Pastry} from {@link Summaries}
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class PastryFromSummaries implements Pastry {
  /**
   * Builds a pastry
   *
   * @param name      The pastry name
   * @param summaries The summaries
   * @param showcase  The showcase where to sell
   * @since 1.0.0
   */
  public PastryFromSummaries(final CharSequence name, final Summaries summaries, final Showcase showcase) {
    this.name = name;
    this.summaries = summaries;
    this.showcase = showcase;
  }

  @Override
  public void sell(final Number quantity) {
    showcase.add(description(), quantity);
  }

  @Override
  public JsonObject description() {
    return summary().description();
  }

  private Summary summary() {
    return summaries
      .summary(name)
      .orElseThrow(() -> new IllegalStateException("Missing pastry"));
  }

  @Override
  public Number price() {
    return description().getJsonNumber("price").numberValue();
  }

  @Override
  public Boolean expired() {
    return false;
  }

  private final CharSequence name;
  private final Summaries summaries;
  private final Showcase showcase;
}
