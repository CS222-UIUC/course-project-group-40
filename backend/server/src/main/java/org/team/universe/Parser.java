package org.team.universe;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.util.Objects;

/** Parser of parameter inputs of main program */
public class Parser {

  private boolean help = false;
  private boolean version = false;
  private Parameters result;

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

  // Public getter functions
  public boolean isHelp() {
    return help;
  }

  public boolean isVersion() {
    return version;
  }

  public boolean isError() {
    return result == null;
  }

  public String getModelpath() {
    if (result != null) {
      return result.getModelpath();
    } else {
      return null;
    }
  }

  public String getAddress() {
    if (result != null) {
      return result.getAddress();
    } else {
      return null;
    }
  }

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
