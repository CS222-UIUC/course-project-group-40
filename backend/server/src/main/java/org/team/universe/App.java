package org.team.universe;

import java.awt.image.BufferedImage;

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

    // TODO: load pre-trained PyTorch TorchScript model locally

    // TODO: receive one image from Android devices
    Connector connector = new Connector();
    BufferedImage bufferedImage = null;
    while (true) {
      // TODO: catch exceptions and handle errors
      //    connector.startConnection(serverAddress, parser.getPort(), null, null);

      // TODO: classify the received image or recongize texts

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
