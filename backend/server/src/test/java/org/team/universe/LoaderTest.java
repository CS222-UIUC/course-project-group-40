package org.team.universe;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;

import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.Assert.assertTrue;

/** Unit test for Loader. */
public class LoaderTest {
  @Test
  public void testLoaderFromAnyBrokenFiles() {

    String modelUrl = "squeezenet_v1.1_error.zip";
    try {
      Loader.loadFromHttp(modelUrl);
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
      Loader.loadFromHttp(modelUrl);
    } catch (ModelNotFoundException e) {
      assertTrue(true);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(false);
    }
  }

  @Test
  public void testLoaderFromHttp() {
    String modelUrl =
        "https://alpha-djl-demos.s3.amazonaws.com/model/djl-blockrunner/mxnet_resnet18.zip?model_name=resnet18_v1";
    try {
      Loader.loadFromHttp(modelUrl);
    } catch (ModelNotFoundException e) {
      assertTrue(false);
    } catch (MalformedModelException e) {
      assertTrue(false);
    } catch (IOException e) {
      assertTrue(true);
    }
  }
}
