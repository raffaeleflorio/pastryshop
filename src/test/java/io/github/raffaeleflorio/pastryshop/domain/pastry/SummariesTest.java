package io.github.raffaeleflorio.pastryshop.domain.pastry;

import jakarta.json.Json;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.pastryshop.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;

class SummariesTest {
  @Nested
  class FakeTest {
    @Test
    void testDefaultAddException() {
      var anyJson = Json.createObjectBuilder().build();
      assertThat(
        () -> new Summaries.Fake().add(anyJson),
        throwsWithMessage(IllegalStateException.class, "Unable to add to a fake")
      );
    }

    @Test
    void testDefaultRemoveException() {
      assertThat(
        () -> new Summaries.Fake().remove("any id"),
        throwsWithMessage(IllegalStateException.class, "Unable to remove from a fake")
      );
    }

    @Test
    void testDefaultSummary() {
      assertThat(
        () -> new Summaries.Fake().summary("any id"),
        throwsWithMessage(IllegalStateException.class, "Unable to build a summary from a fake")
      );
    }
  }
}
