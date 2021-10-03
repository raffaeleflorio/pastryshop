package io.github.raffaeleflorio.pastryshop.domain.summary;

import jakarta.json.Json;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.pastryshop.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class SummaryTest {
  @Nested
  class FakeTest {
    @Test
    void testDefaultTrackException() {
      assertThat(
        () -> new Summary.Fake().track(),
        throwsWithMessage(IllegalStateException.class, "Unable to track a fake")
      );
    }

    @Test
    void testDefaultDescriptionToEmptyJsonObject() {
      var emptyJsonObject = Json.createObjectBuilder().build();
      assertThat(
        new Summary.Fake().description(),
        equalTo(emptyJsonObject)
      );
    }
  }
}
