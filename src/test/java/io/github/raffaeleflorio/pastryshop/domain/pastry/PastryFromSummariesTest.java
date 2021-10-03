package io.github.raffaeleflorio.pastryshop.domain.pastry;

import io.github.raffaeleflorio.pastryshop.domain.showcase.Showcase;
import io.github.raffaeleflorio.pastryshop.domain.summary.Summaries;
import io.github.raffaeleflorio.pastryshop.domain.summary.Summary;
import jakarta.json.Json;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static io.github.raffaeleflorio.pastryshop.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class PastryFromSummariesTest {
  @Test
  void testPrice() {
    var price = 1234;
    assertThat(
      new PastryFromSummaries(
        "a pastry",
        new Summaries.Fake(
          x -> Optional.of(new Summary.Fake(Json.createObjectBuilder().add("price", price).build()))),
        new Showcase.Fake()
      ).price(),
      equalTo(price)
    );
  }

  @Test
  void testDescription() {
    var description = Json.createObjectBuilder().add("any", "characteristic").build();
    assertThat(
      new PastryFromSummaries(
        "a pastry",
        new Summaries.Fake(x -> Optional.of(new Summary.Fake(description))),
        new Showcase.Fake()
      ).description(),
      equalTo(description)
    );
  }

  @Test
  void testSellWithExistentPastry() {
    assertThat(
      () -> new PastryFromSummaries(
        "an existent pastry",
        new Summaries.Fake(x -> Optional.of(new Summary.Fake())),
        new Showcase.Fake()
      ).sell(1),
      throwsWithMessage(IllegalStateException.class, "Unable to add to a fake")
    );
  }

  @Test
  void testSellExceptionWithNonexistentPastry() {
    assertThat(
      () -> new PastryFromSummaries(
        "non existent pastry",
        new Summaries.Fake(x -> Optional.empty()),
        new Showcase.Fake()
      ).sell(1),
      throwsWithMessage(IllegalStateException.class, "Missing pastry")
    );
  }
}
