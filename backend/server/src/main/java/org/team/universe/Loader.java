package org.team.universe;

import static ai.djl.repository.zoo.ModelZoo.listModels;

import ai.djl.MalformedModelException;
import ai.djl.modality.Classifications;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
// import ai.djl.modality.cv.Image;

/** Loader to load PyTorch TorchScript model. */
public class Loader {
  /** Loader from local file path. */
  public static ZooModel<BufferedImage, Classifications> loadFromFile(String scriptPath)
      throws ModelNotFoundException, MalformedModelException, IOException {
    // create Criteria
    // offical Doc http://docs.djl.ai/docs/load_model.html
    Criteria<BufferedImage, Classifications> criteria =
        Criteria.builder()
            .setTypes(BufferedImage.class, Classifications.class) // define input & output data type
            .optModelPath(Paths.get(scriptPath)) // search models in specified path
            .build();

    System.out.println(listModels(criteria));
    ZooModel<BufferedImage, Classifications> model = criteria.loadModel();
    return model;
  }
}
