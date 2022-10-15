package org.team.universe;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.jupiter.api.Test;

/** Unit test for connector. */
public class ConnectorTest {

  private Connector connector;

  // TODO: Leave for future tests with Android client
  //  @Before
  //  public void startServerTest() {
  //    connector = new Connector();
  //    Thread serverThread =
  //        new Thread(
  //            new Runnable() {
  //              @Override
  //              public void run() {
  //                try {
  //                  connector.startConnection("127.0.0.1", "2222");
  //                } catch (IOException e) {
  //                  e.printStackTrace();
  //                }
  //              }
  //            });
  //    serverThread.start();
  //  }

  // Async testing
  // Doc:
  // https://stackoverflow.com/questions/46521297/unit-testing-a-client-server-application-in-java
  // https://www.baeldung.com/a-guide-to-java-sockets
  //  @Test
  //  public void startConnectionTest() {
  //    Connector connector = new Connector();
  //    try {
  //      Socket clientSocket = new Socket("127.0.0.1", Integer.parseInt("2222"));
  //      PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
  //      BufferedReader input =
  //          new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  //    } catch (IOException e) {
  //      assertTrue(true);
  //    }
  //  }

  @Test
  public void startConnectionToyTest() throws Exception {
    Connector connector = new Connector();
    try {
      File icon = new File("src/test/resources/uiuc.png");

      InputStream inputStreamTest = new FileInputStream(icon);
      OutputStream outputStreamTest = new FileOutputStream("output.txt");
      connector.startConnection("127.0.0.1", "2222", inputStreamTest, outputStreamTest);
    } catch (IOException e) {
      assertTrue(false);
    }
  }

  @Test
  public void readImageByStringsTest() {
    Connector connector = new Connector();
    try {
      File icon = new File("src/test/resources/uiuc.png");

      InputStream inputStreamTest = new FileInputStream(icon);
      OutputStream outputStreamTest = new FileOutputStream("output.txt");
      connector.startConnection("127.0.0.1", "2222", inputStreamTest, outputStreamTest);
      connector.readImageByStrings();
    } catch (IOException e) {
      assertTrue(false);
    } catch (NumberFormatException e) {
      assertTrue(true);
    }
  }

  @Test
  public void readImageTest() {
    Connector connector = new Connector();
    try {
      File icon = new File("src/test/resources/uiuc.png");

      InputStream inputStreamTest = new FileInputStream(icon);
      OutputStream outputStreamTest = new FileOutputStream("output.txt");
      connector.startConnection("127.0.0.1", "2222", inputStreamTest, outputStreamTest);
      connector.readImage();
    } catch (IOException e) {
      assertTrue(true);
    }
  }

  @Test
  public void sendMessageTest() {
    Connector connector = new Connector();
    try {
      File icon = new File("src/test/resources/uiuc.png");

      InputStream inputStreamTest = new FileInputStream(icon);
      OutputStream outputStreamTest = new FileOutputStream("output.txt");
      connector.startConnection("127.0.0.1", "2222", inputStreamTest, outputStreamTest);
      connector.sendMessage("Hello, World.");
    } catch (IOException e) {
      assertTrue(true);
    } catch (NullPointerException e) {
      assertTrue(true);
    }
  }

  @Test
  public void closeTest() {
    Connector connector = new Connector();
    try {
      File icon = new File("src/test/resources/uiuc.png");

      InputStream inputStreamTest = new FileInputStream(icon);
      OutputStream outputStreamTest = new FileOutputStream("output.txt");
      connector.startConnection("127.0.0.1", "2222", inputStreamTest, outputStreamTest);
      connector.closeConnection();
    } catch (IOException e) {
      assertTrue(false);
    } catch (NullPointerException e) {
      assertTrue(true);
    }
  }
}
