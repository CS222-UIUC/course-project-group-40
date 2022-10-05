package org.team.universe;

/** Main program of the server project. */
public class App {
  private static int currentVersion = 0;

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

    // load pre-trained PyTorch TorchScript model locally
    //     ZooModel<Image, Classifications> model = new Loader().loadFromFile( script_path );

    // TODO: receive one image from Android devices

    // TODO: classify the received image or recongize texts

    // TODO: return result to Android devices

  }

  // Output sample usage
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

  // Output current version
  private static void printVersion() {
    System.out.println("Universal Recognition " + String.valueOf(currentVersion) + "\n");
  }
}
