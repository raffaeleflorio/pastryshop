package io.github.raffaeleflorio.pastryshop.domain.showcase;

import jakarta.json.Json;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class LocalizedShowcaseTest {
  @Test
  void testAddDescriptionWithManufacturedAppended() {
    var initialDescription = Json.createObjectBuilder()
      .add("a pastry", "characteristic")
      .build();
    var expectedDescription = Json.createObjectBuilder(initialDescription)
      .add("manufactured", LocalDate.EPOCH.toString())
      .build();
    new LocalizedShowcase(
      new Showcase.Fake(
        (addedDescription, addedQuantity) -> assertThat(addedDescription, equalTo(expectedDescription))),
      () -> LocalDate.EPOCH
    ).add(initialDescription, 123);
  }

  @Test
  void testAddQuantity() {
    var expectedQuantity = 52.123;
    new LocalizedShowcase(
      new Showcase.Fake(
        (addedDescription, addedQuantity) -> assertThat(addedQuantity, equalTo(expectedQuantity)))
    ).add(Json.createObjectBuilder().build(), expectedQuantity);
  }

  @Test
  void testDescription() {
    var description = Json.createArrayBuilder().build();
    assertThat(
      new LocalizedShowcase(new Showcase.Fake()).description(),
      equalTo(description)
    );
  }
}
