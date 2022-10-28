package org.team.universe;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/** Main program of the server project. */
public class App {
  private static final int currentVersion = 0;
  private static String serverAddress = "127.0.0.1";

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

    // Check Python related files exist
    //    File python = new File(parser.getPythonpath());
    //    if (!(python.exists() && !python.isDirectory())) {
    //      // Ptyhon file does not exist, abnormally exists
    //      throw new Exception("Python executable does not exist, check the path.");
    //    }

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
    BufferedImage bufferedImage = null;
    while (true) {
      // TODO: catch exceptions and handle errors
      //    connector.startConnection(serverAddress, parser.getPort(), null, null);

      // TODO: classify the received image or recongize texts
      // WARNING: we use the argument `Imagepath` for local test only
      File image = new File(parser.getImagepath());
      if (!(image.exists() && !image.isDirectory())) {
        // Image file does not exist, abnormally exists
        throw new Exception("Image file does not exist, check the path.");
      }

      System.out.println("Working Directory = " + System.getProperty("user.dir"));

      // Start a new process in Java
      // https://stackoverflow.com/questions/15464111/run-cmd-commands-through-java
      ProcessBuilder builder =
          new ProcessBuilder(
              // for Github Action tests only
              "python3",

              // for local tests, you should COMMENT the above line and UNCOMMENT the following line
              // parser.getPythonpath(),
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

      // TODO: return result to Android devices
      //    String message = "Hello, World";
      //    connector.sendMessage(message);

      // TODO: clean-up resources and normally exit
      //    connector.closeConnection();

      // TODO: handle signal
      break;
    }
  }

  /** Output sample usage. */
  private static void printUsage() {
    System.out.println(
        "Usage: "
            + "./universal "
            + "--modelpath Documents/model.pt "
            + "--address 127.0.0.1 "
            + "--port 1010\n"
            + "For help: "
            + "./universal --help\n"
            + "For version: "
            + "./universal --version\n");
  }

  /** Output current version. */
  private static void printVersion() {
    System.out.println("Universal Recognition " + String.valueOf(currentVersion) + "\n");
  }
}
