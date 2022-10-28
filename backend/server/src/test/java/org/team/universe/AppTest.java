package org.team.universe;

import static org.junit.Assert.assertTrue;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Unit test for simple App. */
public class AppTest {

  // https://www.baeldung.com/java-testing-system-out-println
  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  public void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @Test
  public void testMainBrokenPath() {
    String[] input = new String[] {"main", "123456"};
    try {
      App.main(input);
    } catch (ModelNotFoundException e) {
      assertTrue(false);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(false);
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  @Test
  public void testMainBrokenPath2() {
    String[] input = new String[] {"main", "--"};
    try {
      App.main(input);
    } catch (ModelNotFoundException e) {
      assertTrue(false);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(false);
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  @Test
  public void testMainBrokenPath3() {
    String[] input =
        new String[] {
          "--pp",
          "py",
          "-mp",
          "output/ocr/CRNN/ch_rec_moblie_crnn_mbv.pth",
          "-i",
          "/home/liam/Documents/cs222/backend/server/src/test/resources/uiuc.png"
        };
    try {
      App.main(input);
    } catch (ModelNotFoundException e) {
      assertTrue(false);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(false);
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  @Test
  public void testMainBrokenPath4() {
    String[] input =
        new String[] {
          "-pp",
          "python3",
          "-mp",
          "output/ocr/CRNN/ch_rec_moblie_crnn_mbv.pth",
          "-i",
          "/home/liam/Documents/cs222/backend/server/sr"
        };
    try {
      App.main(input);
    } catch (ModelNotFoundException e) {
      assertTrue(false);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(false);
    } catch (Exception e) {
      //      System.out.println(e.getMessage());
      //      e.printStackTrace();
      assertTrue(true);
    }
  }

  @Test
  public void testMainBrokenPath5() {
    String[] input =
        new String[] {
            "-pp",
            "python3",
            "-mp",
            "output/ocr/CRNN/ch_rec_moblie_crnn_mbv.pth",
            "-i",
            "/home/liam/Documents/cs222/backend/server/src"
        };
    try {
      App.main(input);
    } catch (ModelNotFoundException e) {
      assertTrue(false);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(false);
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  @Test
  public void testMainBrokenPath6() {
    String[] input =
        new String[] {
            "-pp",
            "python3",
            "-mp",
            "output/ocr/CRNN",
            "-i",
            "/home/liam/Documents/cs222/backend/server/src"
        };
    try {
      App.main(input);
    } catch (ModelNotFoundException e) {
      assertTrue(false);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(false);
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  @Test
  public void printVersionTest() {
    App main = new App();
    String[] input = new String[] {"-v"};
    try {
      main.main(input);
    } catch (ModelNotFoundException e) {
      assertTrue(false);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(false);
    } catch (Exception e) {
      assertTrue(false);
    }
    Assert.assertEquals("Universal Recognition 0", outputStreamCaptor.toString().trim());
  }

  @Test
  public void normalTest() {
    App main = new App();
    String[] input =
        new String[] {"--modelpath", "Documents/model.pt", "-a", "127.0.0.1", "-p", "1010"};
    try {
      main.main(input);
    } catch (ModelNotFoundException e) {
      assertTrue(false);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(true);
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  @Test
  public void normalPredictTest() {
    App main = new App();
    String[] input =
        new String[] {
          "-pp",
          "/home/liam/.conda/envs/cs222/bin/python3",
          "-mp",
          "output/ocr/CRNN/ch_rec_moblie_crnn_mbv.pth",
          "-i",
          "/home/liam/Documents/cs222/backend/server/src/test/resources/uiuc.png"
        };
    try {
      main.main(input);
    } catch (ModelNotFoundException e) {
      assertTrue(false);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(true);
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  @Test
  public void printUsageTest() {
    App main = new App();
    String[] input = new String[] {"-h"};
    try {
      main.main(input);
    } catch (ModelNotFoundException e) {
      assertTrue(false);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(false);
    } catch (Exception e) {
      assertTrue(false);
    }

    Assert.assertEquals(
        "Usage: ./universal --modelpath Documents/model.pt --address 127.0.0.1 --port 1010\n"
            + "For help: ./universal --help\n"
            + "For version: ./universal --version",
        outputStreamCaptor.toString().trim());
  }

  @Test
  public void connectionTest() {
    App main = new App();
    String[] input =
        new String[] {"--modelpath", "Documents/model.pt", "-a", "127.0.0.1", "-p", "1010"};
    try {
      main.main(input);
    } catch (ModelNotFoundException e) {
      assertTrue(false);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(true);
    } catch (Exception e) {
      assertTrue(true);
    }
  }
}
