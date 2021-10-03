package io.github.raffaeleflorio.pastryshop.domain.pastry;

import jakarta.json.Json;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.pastryshop.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ShowcaseTest {
  @Nested
  class FakeTest {
    @Test
    void testDefaultAddingException() {
      assertThat(
        () -> new Showcase.Fake().add("any", 123),
        throwsWithMessage(IllegalStateException.class, "Unable to add to a fake")
      );
    }

    @Test
    void testDefaultDescriptionToEmptyJsonArray() {
      var description = Json.createArrayBuilder().build();
      assertThat(
        new Showcase.Fake().description(),
        equalTo(description)
      );
    }
  }
}
