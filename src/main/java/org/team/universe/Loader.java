package org.team.universe;

import java.io.IOException;
import java.nio.file.*;
import java.awt.image.*;
import ai.djl.*;
import ai.djl.inference.*;
import ai.djl.modality.*;
import ai.djl.modality.cv.*;
import ai.djl.modality.cv.util.*;
import ai.djl.modality.cv.transform.*;
import ai.djl.modality.cv.translator.*;
import ai.djl.repository.zoo.*;
import ai.djl.translate.*;
import ai.djl.training.util.*;

/**
 * Loader to load PyTorch TorchScript model
 *
 */
public class Loader {
    public static ZooModel<Image, Classifications> load( String script_path ) throws ModelNotFoundException, MalformedModelException, IOException {
        // create Criteria
        // offical Doc http://docs.djl.ai/docs/load_model.html
        Criteria<Image, Classifications> criteria = Criteria.builder()
                .setTypes(Image.class, Classifications.class) // defines input and output data type
                .optModelUrls("file://" + script_path) // search models in specified path
                .build();

        ZooModel<Image, Classifications> model = criteria.loadModel();
        return model;
    }
}
