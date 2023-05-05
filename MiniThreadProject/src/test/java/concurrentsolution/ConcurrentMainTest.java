package concurrentsolution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConcurrentMainTest {

  @BeforeEach
  void setUp() {
    ConcurrentMain main = new ConcurrentMain();
  }

  @Test
  void testMain() throws InterruptedException {
    ConcurrentMain.main(new String[]{"src/test/resources/mainclass", String.valueOf(20)});
  }
}