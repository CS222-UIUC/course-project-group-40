package org.team.universe;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.util.Objects;

/** Parser of parameter inputs of main program. */
public class Parser {

  private boolean help = false;
  private boolean version = false;
  private Parameters result;

  /** Wrapper of Jcommander. */
  Parser(String[] args) {
    // Wrapper of parser
    this.result = new Parameters();

    // Detect errors
    try {
      Objects.requireNonNull(args, "Null parameter array");
      JCommander jcommander = new JCommander(this.result);
      jcommander.setProgramName("UniversialRecognition");
      jcommander.parse(args);
    } catch (NullPointerException e) {
      this.result = null;
    } catch (ParameterException e) {
      this.result = null;
    }

    // Extract values
    if (this.result != null) {
      this.help = this.result.isHelp();
      this.version = this.result.isVersion();
    }
  }

  /** Getter function for Help. */
  public boolean isHelp() {
    return help;
  }

  /** Getter function for Version. */
  public boolean isVersion() {
    return version;
  }

  /** Getter function for Error. */
  public boolean isError() {
    return result == null;
  }

  /** Getter function for Modelpath. */
  public String getModelpath() {
    if (result != null) {
      return result.getModelpath();
    } else {
      return null;
    }
  }

  /** Getter function for Address. */
  public String getAddress() {
    if (result != null) {
      return result.getAddress();
    } else {
      return null;
    }
  }

  /** Getter function for Port. */
  public String getPort() {
    if (result != null) {
      return result.getPort();
    } else {
      return null;
    }
  }

  // Deprecated currently due to security issues
  @Deprecated
  public Parameters getResult() {
    return result;
  }
}
