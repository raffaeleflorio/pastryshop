package io.github.raffaeleflorio.pastryshop.domain.summary;

import jakarta.json.Json;
import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.pastryshop.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class JsonSummaryTest {
  @Test
  void testDescription() {
    var description = Json.createObjectBuilder()
      .add("name", "a pastry")
      .add("price", 42)
      .add("another characteristic", "a value")
      .build();
    assertThat(
      new JsonSummary(description, new Summaries.Fake()).description(),
      equalTo(description)
    );
  }

  @Test
  void testTrack() {
    assertThat(
      () -> new JsonSummary(
        Json.createObjectBuilder()
          .add("name", "pastry name")
          .add("ingredients", Json.createArrayBuilder().add("first").add("second"))
          .add("price", 123456789)
          .build(),
        new Summaries.Fake()
      ).track(),
      throwsWithMessage(IllegalStateException.class, "Unable to add to a fake")
    );
  }
}
