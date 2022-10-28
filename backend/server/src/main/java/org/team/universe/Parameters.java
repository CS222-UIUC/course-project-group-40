package org.team.universe;

import com.beust.jcommander.Parameter;

/** GNU-Standard Command Line Options and Parameters. */
public class Parameters {
  // Official Doc: https://jcommander.org/#_overview

  // Options and descriptions for inputs
  @Parameter(
      names = {"--modelpath", "-mp"},
      description =
          "Path of the Torchscript file of Deep Learning model. "
              + "One of --modelpath or -mp must be provided.")
  private String modelpath;

  @Parameter(
      names = {"--address", "-a"},
      description =
          "Network address of Android client. " + "One of --address or -a must be provided.")
  private String address;

  @Parameter(
      names = {"--port", "-p"},
      description =
          "Port to connect with Android client. " + "One of --port or -p must be provided.")
  private String port;

  @Parameter(
      names = {"--image-path", "-i"},
      description = "Path of image file for testing only. ")
  private String imagepath;

  @Parameter(
      names = {"--pythonpath", "-pp"},
      description = "Path of the Python exec file" + "One of --pythonpath or -pp must be provided.")
  private String pythonpath;

  @Parameter(
      names = {"--help", "-h"},
      description = "Display usage.",
      help = true)
  private boolean help = false;

  @Parameter(
      names = {"--version", "-v"},
      description = "Display current version of Universal Recognition and exit normally.",
      help = true)
  private boolean version = false;

  /** Getter function for Modelpath. */
  public String getModelpath() {
    return modelpath;
  }

  /** Getter function for Imagepath. */
  public String getImagepath() {
    return imagepath;
  }

  /** Getter function for Pythonpath. */
  public String getPythonpath() {
    return pythonpath;
  }

  /** Getter function for Address. */
  public String getAddress() {
    return address;
  }

  /** Getter function for Port. */
  public String getPort() {
    return port;
  }

  /** Getter function for Help. */
  public boolean isHelp() {
    return help;
  }

  /** Getter function for Version. */
  public boolean isVersion() {
    return version;
  }
}
