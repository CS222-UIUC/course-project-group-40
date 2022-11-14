package org.team.universe;

import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
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
  public void startConnectionByteArrayTest() {
    Connector connector = new Connector();
    try {
      connector.startConnectionByteArray("127.0.0.1", "1040");
    } catch (IOException e) {
      assertTrue(false);
    }
  }

  @Test
  public void reconnectTest() {
    Connector connector = new Connector();
    try {
      connector.startConnectionByteArray("127.0.0.1", "1040");
      connector.reconnectByteArray();
    } catch (IOException e) {
      assertTrue(false);
    } catch (NullPointerException e) {
      assertTrue(true);
    }
  }

  @Test
  public void readImageByByteTest() {
    Connector connector = new Connector();
    try {
      connector.startConnectionByteArray("127.0.0.1", "1040");
      BufferedImage image = connector.readImageByteArray();
      if (image == null) {
        assertTrue(true);
      }
      connector.closeConnection();
      connector.reconnectByteArray();
    } catch (IOException e) {
      assertTrue(false);
    } catch (NullPointerException e) {
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
      connector.sendMessage(inputStreamTest);
    } catch (IOException e) {
      assertTrue(true);
    } catch (NullPointerException e) {
      assertTrue(true);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void sendOCRMessageTest() {
    Connector connector = new Connector();
    try {
      File icon = new File("src/test/resources/uiuc.png");

      InputStream inputStreamTest = new FileInputStream(icon);
      OutputStream outputStreamTest = new FileOutputStream("output.txt");
      connector.startConnection("127.0.0.1", "2222", inputStreamTest, outputStreamTest);
      connector.sendOCRMessage(inputStreamTest);
    } catch (IOException e) {
      assertTrue(true);
    } catch (NullPointerException e) {
      assertTrue(true);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void sendObjectMessageTest() {
    Connector connector = new Connector();
    try {
      File icon = new File("src/test/resources/uiuc.png");

      InputStream inputStreamTest = new FileInputStream(icon);
      OutputStream outputStreamTest = new FileOutputStream("output.txt");
      connector.startConnection("127.0.0.1", "2222", inputStreamTest, outputStreamTest);
      connector.sendObjectMessage(inputStreamTest);
    } catch (IOException e) {
      assertTrue(true);
    } catch (NullPointerException e) {
      assertTrue(true);
    } catch (InterruptedException e) {
      e.printStackTrace();
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

  @Test
  public void parserMessageTest() {
    Connector connector = new Connector();
    String parsed = connector.parserMessage("[[('网站', [0.70039225, 0.9907616])]]\n");
    //    String parsed2 =
    //        connector.parserMessage(
    //            "[[('伊利诺伊大学尼巴纳-香槟T', [0.9898179, 0.99173236, 0.699735, 0.988277, 0.99156404,
    // 0.99948126, 0.68767715, 0.99439704, 0.9390116, 0.99303824, 0.76593804, 0.36590272])]]\n");
    //    String test = "[[('伊利诺伊大学尼巴纳-香槟T', [0.9898179, 0.99173236, 0.699735, 0.988277, 0.99156404,
    // 0.99948126, 0.68767715,, 0.99439704, 0.9390116, 0.99303824, 0.76593804, 0.36590272])]]";
    //    String parsed3 = connector.parserMessage(test);
  }

  @Test
  public void shutDownServerTest() {
    Connector connector = new Connector();
    try {
      File icon = new File("src/test/resources/uiuc.png");

      InputStream inputStreamTest = new FileInputStream(icon);
      OutputStream outputStreamTest = new FileOutputStream("output.txt");
      connector.shutDownServer();
    } catch (IOException e) {
      assertTrue(false);
    } catch (NullPointerException e) {
      assertTrue(true);
    }
  }
}
