package io.github.raffaeleflorio.pastryshop.infrastructure;

import io.github.raffaeleflorio.pastryshop.domain.summary.*;
import io.github.raffaeleflorio.pastryshop.infrastructure.summary.InMemorySummaries;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The entrypoint
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
@SpringBootApplication
@Configuration
public class Main {
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

  @Bean
  Summaries summaries() {
    return new MandatoryName(
      new MandatoryPrice(
        new MandatoryImage(
          new MandatoryIngredients(
            new InMemorySummaries()
          )
        )
      )
    );
  }
}
