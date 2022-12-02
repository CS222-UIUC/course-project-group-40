package org.team.universe;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

/** Main program of the server project. */
public class App {
  private static final int currentVersion = 0;
  private static final String serverAddress = "127.0.0.1";

  private static Boolean isRunningTest = null;

  /** Check test status. */
  private static boolean isRunningTest() {
    // https://stackoverflow.com/questions/2341943/how-can-i-find-out-if-code-is-running-inside-a-junit-test-or-not

    if (isRunningTest == null) {
      isRunningTest = true;
      try {
        Class.forName("org.junit.Test");
      } catch (ClassNotFoundException e) {
        isRunningTest = false;
      }
    }
    return isRunningTest;
  }

  /** Main program. */
  public static void main(String[] args) throws Exception {
    // Parser inputs
    Parser parser = new Parser(args);

    // Abnormally exits
    if (parser.isError()) {
      throw new Exception("Parameters are incorrect, check the usage.");
    } else if (parser.isHelp()) {
      // Normally exits with output of sample usage
      printUsage();
      return;
    } else if (parser.isVersion()) {
      // Normally exits with output of current version
      printVersion();
      return;
    }

    File predict = new File("../vision_model/src/predict.py");
    if (!(predict.exists() && !predict.isDirectory())) {
      // Predict file does not exist, abnormally exists
      throw new Exception("Python prediction file does not exist, check the program.");
    }

    File model = new File("../vision_model/" + parser.getModelpath());
    if (!(model.exists() && !model.isDirectory())) {
      // Model file does not exist, abnormally exists
      throw new Exception("Model file does not exist, check the path.");
    }

    // TODO: receive one image from Android devices
    Connector connector = new Connector();

    // Test code
    if (isRunningTest()) {
      while (true) {
        // classify the received image or recongize texts
        // WARNING: we use the argument `Imagepath` for local test only
        File image = new File(parser.getImagepath());
        if (!(image.exists() && !image.isDirectory())) {
          // Image file does not exist, abnormally exists
          throw new Exception("Image file does not exist, check the path.");
        }

        // Start a new process in Java
        // https://stackoverflow.com/questions/15464111/run-cmd-commands-through-java
        ProcessBuilder builder =
            new ProcessBuilder(
                // for Github Action tests only
                "python3",
                "src/predict.py",
                "--model_path",
                model.getPath(),
                "--img_path",
                image.getPath());
        builder.redirectErrorStream(true);

        builder.directory(new File("../vision_model/"));
        Process process = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while (true) {
          line = r.readLine();
          if (line == null) {
            break;
          }
          System.out.println(line);
        }
        break;
      }
    } else {
      // Practical code
      connector.startConnectionByteArray(serverAddress, parser.getPort());
      while (true) {
        connector.reconnectByteArray();
        BufferedImage image = connector.readImageByteArray();
        File stored_image = new File("received_image.png");
        ImageIO.write(image, "png", stored_image);
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("Store image in: " + stored_image.getAbsolutePath());

        // Execute ML prediction for OCR
        ProcessBuilder builder =
            new ProcessBuilder(
                parser.getPythonpath(),
                "src/predict.py",
                "--model_path",
                model.getPath(),
                "--img_path",
                stored_image.getAbsolutePath());
        builder.redirectErrorStream(true);

        builder.directory(new File("../vision_model/"));
        Process process = builder.start();
        connector.sendOcrMessage(process.getInputStream());

        // Execute ML prediction for Object Detection
        ProcessBuilder builderObject =
            new ProcessBuilder(
                parser.getPythonpath(),
                "src/predict.py",
                "--model_path",
                "../vision_model/output/object",
                "--img_path",
                stored_image.getAbsolutePath(),
                "--task",
                "obj");
        builderObject.redirectErrorStream(true);

        builderObject.directory(new File("../vision_model/"));
        Process processObject = builderObject.start();
        connector.sendObjectMessage(processObject.getInputStream());
        connector.closeConnection();
      }
    }
    connector.shutDownServer();
  }

  /** Output sample usage. */
  private static void printUsage() {
    System.out.println(
        "Usage: "
            + "./universal "
            + "--modelpath Documents/model.pt "
            + "--address 127.0.0.1 "
            + "--port 1080\n"
            + "For help: "
            + "./universal --help\n"
            + "For version: "
            + "./universal --version\n");
  }

  /** Output current version. */
  private static void printVersion() {
    System.out.println("Universal Recognition " + currentVersion + "\n");
  }
}
