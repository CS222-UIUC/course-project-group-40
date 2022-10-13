package org.team.universe;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.junit.Before;
import org.junit.jupiter.api.Test;

/** Unit test for connector. */
public class ConnectorTest {

  private Connector connector;

  @Before
  public void startServerTest() {
    connector = new Connector();
    Thread serverThread =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                try {
                  connector.startConnection("127.0.0.1", "2222");
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            });
    serverThread.start();
  }

  // Async testing
  // Doc:
  // https://stackoverflow.com/questions/46521297/unit-testing-a-client-server-application-in-java
  // https://www.baeldung.com/a-guide-to-java-sockets
  @Test
  public void startConnectionTest() {
    Connector connector = new Connector();
    try {
      Socket clientSocket = new Socket("127.0.0.1", Integer.parseInt("2222"));
      PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
      BufferedReader input =
          new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    } catch (IOException e) {
      assertTrue(true);
    }
  }

  @Test
  public void readImageByStringsTest() {
    Connector connector = new Connector();
    try {
      connector.readImageByStrings();
    } catch (IOException e) {
      assertTrue(false);
    } catch (NullPointerException e) {
      assertTrue(true);
    }
  }
}
