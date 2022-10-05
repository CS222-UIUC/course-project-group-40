package org.team.universe;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertTrue;

public class ParametersTest {

  @Test
  public void getModelpathNullTest() {
    Parameters param = new Parameters();
    String[] args = {"--modelpath"};

    try {
      Objects.requireNonNull(args, "Null parameter array");
      JCommander jcommander = new JCommander(param);
      jcommander.setProgramName("UniversialRecognition");
      jcommander.parse(args);
    } catch (ParameterException e) {
      assertTrue(true);
    }
  }

  @Test
  public void getModelpathNotNullTest() {
    Parameters param = new Parameters();
    String[] args = {"--modelpath", "Documents/model.pt", "-p", "1010"};

    Objects.requireNonNull(args, "Null parameter array");
    JCommander jcommander = new JCommander(param);
    jcommander.setProgramName("UniversialRecognition");
    jcommander.parse(args);

    if (param.getModelpath() == null) {
      assertTrue(false);
    }
  }

  @Test
  public void getModelpathValueTest() {
    Parameters param = new Parameters();
    String[] args = {"--modelpath", "Documents/model.pt", "-p", "1010"};

    Objects.requireNonNull(args, "Null parameter array");
    JCommander jcommander = new JCommander(param);
    jcommander.setProgramName("UniversialRecognition");
    jcommander.parse(args);

    if (param.getModelpath().equals("Documents/model.pt")) {
      assertTrue(true);
    }
  }

  @Test
  public void getAddressNullTest() {
    Parameters param = new Parameters();
    String[] args = {"--modelpath", "Documents/model.pt", "-p", "1010"};

    Objects.requireNonNull(args, "Null parameter array");
    JCommander jcommander = new JCommander(param);
    jcommander.setProgramName("UniversialRecognition");
    jcommander.parse(args);

    if (param.getAddress() == null) {
      assertTrue(true);
    }
  }

  @Test
  public void getAddressNotNullTest() {
    Parameters param = new Parameters();
    String[] args = {"--modelpath", "Documents/model.pt", "-a", "127.0.0.1", "-p", "1010"};

    Objects.requireNonNull(args, "Null parameter array");
    JCommander jcommander = new JCommander(param);
    jcommander.setProgramName("UniversialRecognition");
    jcommander.parse(args);

    if (param.getAddress() == null) {
      assertTrue(false);
    }
  }

  @Test
  public void getAddressValueTest() {
    Parameters param = new Parameters();
    String[] args = {"--modelpath", "Documents/model.pt", "-a", "127.0.0.1", "-p", "1010"};

    Objects.requireNonNull(args, "Null parameter array");
    JCommander jcommander = new JCommander(param);
    jcommander.setProgramName("UniversialRecognition");
    jcommander.parse(args);

    if (!param.getAddress().equals("127.0.0.1")) {
      assertTrue(false);
    }
  }

  @Test
  public void getPortNullTest() {
    Parameters param = new Parameters();
    String[] args = {"--modelpath", "Documents/model.pt", "-a", "127.0.0.1", "-p"};

    try {
      Objects.requireNonNull(args, "Null parameter array");
      JCommander jcommander = new JCommander(param);
      jcommander.setProgramName("UniversialRecognition");
      jcommander.parse(args);
    } catch (ParameterException e) {
      assertTrue(true);
    }
  }

  @Test
  public void getPortNotNullTest() {
    Parameters param = new Parameters();
    String[] args = {"--modelpath", "Documents/model.pt", "-a", "127.0.0.1", "-p", "1010"};

    Objects.requireNonNull(args, "Null parameter array");
    JCommander jcommander = new JCommander(param);
    jcommander.setProgramName("UniversialRecognition");
    jcommander.parse(args);

    if (param.getPort() == null) {
      assertTrue(false);
    }
  }

  @Test
  public void getPortValueTest() {
    Parameters param = new Parameters();
    String[] args = {"--modelpath", "Documents/model.pt", "-a", "127.0.0.1", "-p", "1010"};

    Objects.requireNonNull(args, "Null parameter array");
    JCommander jcommander = new JCommander(param);
    jcommander.setProgramName("UniversialRecognition");
    jcommander.parse(args);

    if (!param.getPort().equals("1010")) {
      assertTrue(false);
    }
  }

  @Test
  public void isHelpFalseTest() {
    Parameters param = new Parameters();
    String[] args = {"--modelpath", "Documents/model.pt"};

    Objects.requireNonNull(args, "Null parameter array");
    JCommander jcommander = new JCommander(param);
    jcommander.setProgramName("UniversialRecognition");
    jcommander.parse(args);

    if (param.isHelp()) {
      assertTrue(false);
    }
  }

  @Test
  public void isHelpTrueTest() {
    Parameters param = new Parameters();
    String[] args = {"--help"};

    Objects.requireNonNull(args, "Null parameter array");
    JCommander jcommander = new JCommander(param);
    jcommander.setProgramName("UniversialRecognition");
    jcommander.parse(args);

    if (!param.isHelp()) {
      assertTrue(false);
    }
  }

  @Test
  public void isVersionFalseTest() {
    Parameters param = new Parameters();
    String[] args = {"--help"};

    Objects.requireNonNull(args, "Null parameter array");
    JCommander jcommander = new JCommander(param);
    jcommander.setProgramName("UniversialRecognition");
    jcommander.parse(args);

    if (param.isVersion()) {
      assertTrue(false);
    }
  }

  @Test
  public void isVersionTrueTest() {
    Parameters param = new Parameters();
    String[] args = {"-v"};

    Objects.requireNonNull(args, "Null parameter array");
    JCommander jcommander = new JCommander(param);
    jcommander.setProgramName("UniversialRecognition");
    jcommander.parse(args);

    if (!param.isVersion()) {
      assertTrue(false);
    }
  }
}
