package org.team.universe;

import ai.djl.MalformedModelException;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import org.team.universe.Loader;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ModelNotFoundException, MalformedModelException, IOException {
        // load pre-trained PyTorch TorchScript model locally

//        ZooModel<Image, Classifications> model = new Loader().loadFromFile( script_path );

        // TODO: receive one image from Android devices

        // TODO: classify the received image or recongize texts

        // TODO: return result to Android devices

    }
}
