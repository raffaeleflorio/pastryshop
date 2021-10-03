package io.github.raffaeleflorio.pastryshop.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MainIntegrationTest {
  @Test
  void testDeploy() {
    Main.main(new String[]{});
  }
}
