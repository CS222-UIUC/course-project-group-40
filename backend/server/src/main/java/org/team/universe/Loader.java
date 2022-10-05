package org.team.universe;

import static ai.djl.repository.zoo.ModelZoo.listModels;

import ai.djl.MalformedModelException;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import java.io.IOException;

/** Loader to load PyTorch TorchScript model */
public class Loader {
  public static ZooModel<Image, Classifications> loadFromAny(String input)
      throws ModelNotFoundException, MalformedModelException, IOException {
    // create Criteria
    // offical Doc http://docs.djl.ai/docs/load_model.html
    Criteria<Image, Classifications> criteria =
        Criteria.builder()
            .setTypes(Image.class, Classifications.class) // defines input and output data type
            .optModelUrls(input) // search models in specified path
            .build();

    ZooModel<Image, Classifications> model = criteria.loadModel();
    return model;
  }

  public static ZooModel<Image, Classifications> loadFromFile(String script_path)
      throws ModelNotFoundException, MalformedModelException, IOException {
    // create Criteria
    // offical Doc http://docs.djl.ai/docs/load_model.html
    Criteria<Image, Classifications> criteria =
        Criteria.builder()
            .setTypes(Image.class, Classifications.class) // defines input and output data type
            .optModelUrls("file://" + script_path) // search models in specified path
            .build();

    System.out.println(listModels(criteria));
    ZooModel<Image, Classifications> model = criteria.loadModel();
    return model;
  }

  public static ZooModel<Image, Classifications> loadFromHttp(String link)
      throws ModelNotFoundException, MalformedModelException, IOException {
    // create Criteria
    // offical Doc http://docs.djl.ai/docs/load_model.html
    Criteria<Image, Classifications> criteria =
        Criteria.builder()
            .setTypes(Image.class, Classifications.class) // defines input and output data type
            .optModelUrls(link) // search models in specified path
            .build();

    ZooModel<Image, Classifications> model = criteria.loadModel();
    return model;
  }
}
