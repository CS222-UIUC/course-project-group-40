package org.team.universe;

import static org.junit.Assert.assertTrue;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/** Unit test for simple App. */
public class AppTest {
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
      assertTrue(true);
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
      assertTrue(true);
    } catch (Exception e) {
      assertTrue(true);
    }
  }
}
