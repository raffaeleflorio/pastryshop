package io.github.raffaeleflorio.pastryshop.infrastructure.pastry;

import io.github.raffaeleflorio.pastryshop.domain.pastry.Summaries;
import io.github.raffaeleflorio.pastryshop.domain.pastry.Summary;
import jakarta.json.JsonObject;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * {@link Summaries} adapted as a Spring {@link Component}
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
@Component
public class SummariesAsComponent implements Summaries {
  /**
   * Builds summaries
   *
   * @since 1.0.0
   */
  public SummariesAsComponent() {
    this(new InMemorySummaries());
  }

  private SummariesAsComponent(final Summaries origin) {
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

  private final Summaries origin;
}
