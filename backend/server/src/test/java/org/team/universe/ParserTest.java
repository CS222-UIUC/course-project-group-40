package org.team.universe;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

/** Unit test for Parser. */
public class ParserTest {

  @Test
  public void nonPointerTest() {
    String[] args = null;
    Parser param = new Parser(args);
    assertTrue(param.isError());
  }

  @Test
  public void isErrorTrueTest() {
    String[] args = {"--modelpath"};
    Parser param = new Parser(args);

    assertTrue(param.isError());
  }

  @Test
  public void isErrorFalseTest() {
    String[] args = {"--modelpath", "Documents/m.pt"};
    Parser param = new Parser(args);

    assertTrue(!param.isError());
  }

  @Test
  public void isHelpTrueTest() {
    String[] args = {"--help"};
    Parser param = new Parser(args);

    assertTrue(!param.isError());
    assertTrue(param.isHelp());
  }

  @Test
  public void isHelpFalseTest() {
    String[] args = {"--help", "--modelpath"};
    Parser param = new Parser(args);

    assertTrue(param.isError());
    assertTrue(!param.isHelp());
  }

  @Test
  public void isVersionFalseTest() {
    String[] args = {"--version", "--modelpath"};
    Parser param = new Parser(args);

    assertTrue(param.isError());
    assertTrue(!param.isVersion());
  }

  @Test
  public void isVersionTrueTest() {
    String[] args = {"--version"};
    Parser param = new Parser(args);

    assertTrue(!param.isError());
    assertTrue(param.isVersion());
  }

  @Test
  public void getModelpathErrorTest() {
    String[] args = {"--modelpath"};
    Parser param = new Parser(args);

    assertTrue(param.isError());
    assertTrue(param.getModelpath() == null);
  }

  @Test
  public void getModelpathValueTest() {
    String[] args = {"--modelpath", "model.pt"};
    Parser param = new Parser(args);

    assertTrue(param.getModelpath().equals("model.pt"));
  }

  @Test
  public void getAddressErrorTest() {
    String[] args = {"--address"};
    Parser param = new Parser(args);

    assertTrue(param.isError());
    assertTrue(param.getAddress() == null);
  }

  @Test
  public void getAddressValueTest() {
    String[] args = {"--address", "127.0.0.1"};
    Parser param = new Parser(args);

    assertTrue(param.getAddress().equals("127.0.0.1"));
  }

  @Test
  public void getPortErrorTest() {
    String[] args = {"--port"};
    Parser param = new Parser(args);

    assertTrue(param.isError());
    assertTrue(param.getPort() == null);
  }

  @Test
  public void getPortValueTest() {
    String[] args = {"--modelpath", "Documents/m.pt", "--address", "1010", "--port", "1010"};
    Parser param = new Parser(args);

    assertTrue(param.getPort().equals("1010"));
  }

  @Test
  public void getResultTest() {
    String[] args = {"--modelpath", "Documents/m.pt", "--address", "1010", "--port", "1010"};
    Parser param = new Parser(args);
    Parameters result = param.getResult();

    assertTrue(result.getPort().equals("1010"));
  }
}
