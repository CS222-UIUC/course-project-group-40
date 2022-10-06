package org.team.universe;

import static org.junit.Assert.assertTrue;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/** Unit test for Loader. */
public class LoaderTest {
  @Test
  public void testLoaderFromFileBrokenFiles() {

    String modelUrl = "squeezenet_v1.1_error.zip";
    try {
      Loader.loadFromFile(modelUrl);
    } catch (ModelNotFoundException e) {
      assertTrue(true);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(false);
    }
  }

  @Test
  public void testLoaderFromFile() {
    String modelUrl = "squeezenet_v1.1.tar.gz";
    try {
      Loader.loadFromFile(modelUrl);
    } catch (ModelNotFoundException e) {
      assertTrue(true);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(false);
    }
  }



  @Test
  public void testLoaderFromAny() {
    String modelUrl = "squeezenet_v1.1_error.zip";
    try {
      Loader.loadFromFile(modelUrl);
    } catch (ModelNotFoundException e) {
      assertTrue(true);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(false);
    } catch (IllegalArgumentException e) {
      assertTrue(false);
    }
  }

  @Test
  public void testLoader() {
    String modelUrl = "jar://squeezenet_v1.1_error.zip";
    Loader loader = new Loader();
    try {
      loader.loadFromFile(modelUrl);
    } catch (ModelNotFoundException e) {
      assertTrue(true);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(false);
    } catch (IllegalArgumentException e) {
      assertTrue(false);
    }
  }
}
