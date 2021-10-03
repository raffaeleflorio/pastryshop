package io.github.raffaeleflorio.pastryshop.infrastructure.http;

import io.github.raffaeleflorio.pastryshop.domain.showcase.Showcase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/showcase")
public class ShowcaseController {
  public ShowcaseController(final Showcase showcase) {
    this.showcase = showcase;
  }

  @GetMapping(produces = "application/json")
  public String description() {
    return showcase.description().toString();
  }

  private final Showcase showcase;
}
