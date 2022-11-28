package org.team.universe;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;

/**
 * Connector of the server project. It currently uses TCP network protocol to connect with Android
 * client.
 *
 * <p>Resources doc of network programming in Java: https://www.baeldung.com/a-guide-to-java-sockets
 * https://www.runoob.com/java/java-networking.html
 * https://blog.csdn.net/qq_53344479/article/details/124703708
 */
public class Connector {

  private ServerSocket serverSocket;
  private Socket clientSocket;

  private InputStream inputStream;
  private OutputStream outputStream;

  private BufferedImage bufferedImage;
  private PrintWriter output;

  private BufferedReader input;

  private static Boolean isRunningTest = null;

  /** Check test status. */
  private static boolean isRunningTest() {
    // https://stackoverflow.com/questions/2341943/how-can-i-find-out-if-code-is-running-inside-a-junit-test-or-not

    if (isRunningTest == null) {
      isRunningTest = true;
      try {
        Class.forName("org.junit.Test");
      } catch (ClassNotFoundException e) {
        isRunningTest = false;
      }
    }
    return isRunningTest;
  }

  /** Start to listen to clients and establish connection with Byte Array. */
  public void startConnectionByteArray(String address, String port) throws IOException {
    // set the socket of this server and keep listing
    int serverPort = Integer.parseInt(port);
    if (isRunningTest()) {
      File test = new File("output.txt");
      inputStream = new FileInputStream(test);
      outputStream = new FileOutputStream(test);
    } else {
      // TODO: wait the development of Android client
      // TODO: START
//      serverSocket = new ServerSocket(serverPort);
//      System.out.println("Initial Server Socket.");
      // TODO: END
    }
  }

  /** Continue to listen to clients and establish connection with Byte Array. */
  public void reconnectByteArray() throws IOException {
    if (isRunningTest()) {
      File test = new File("output.txt");
      inputStream = new FileInputStream(test);
      outputStream = new FileOutputStream(test);
    } else {
      // TODO: wait the development of Android client
      // TODO: START
//      System.out.println("Start to listen to Android Client");
//      clientSocket = serverSocket.accept();
//      System.out.println(
//          "Connected! Inet Address: "
//              + clientSocket.getInetAddress().toString()
//              + ", Port: "
//              + String.valueOf(clientSocket.getLocalPort()));
//
//      // obtain the streams to read and write to client
//      inputStream = clientSocket.getInputStream();
//      outputStream = clientSocket.getOutputStream();
      // TODO: END
    }
  }

  /** receive the image from client by Byte Array input stream. */
  public BufferedImage readImageByteArray() throws IOException {
    // TODO: wait the development of Android client
    // TODO: START
//    byte[] image_bytes = IOUtils.toByteArray(inputStream);
//    clientSocket.shutdownInput();
//
//    System.out.println("Received Image: " + String.valueOf(image_bytes.length) + "bytes");
//    ByteArrayInputStream image_input_stream = new ByteArrayInputStream(image_bytes);
//    bufferedImage = ImageIO.read(image_input_stream);
    // TODO: END
    return bufferedImage;
  }

  /** Start to listen to clients and establish connection. */
  @Deprecated
  public void startConnection(
      String address, String port, InputStream inputStreamTest, OutputStream outputStreamTest)
      throws IOException {
    // set the socket of this server and keep listing
    int serverPort = Integer.parseInt(port);

    inputStream = inputStreamTest;
    outputStream = outputStreamTest;
  }

  /** receive the image from client by buffered image stream. */
  public void readImage() throws IOException {
    bufferedImage = ImageIO.read(inputStream);
  }

  /** receive the image from client by buffered strings. */
  @Deprecated
  public void readImageByStrings() throws IOException {
    input = new BufferedReader(new InputStreamReader((inputStream)));
    output = new PrintWriter(outputStream);

    // Start to read image
    String height = input.readLine();
    String width = input.readLine();
  }

  /** send OCR result to client. Old function */
  @Deprecated
  public void sendMessage(InputStream processInputStream) throws IOException, InterruptedException {
    // Send the result of calculations with Deep Learning models to client
    byte[] message = IOUtils.toByteArray(processInputStream);

    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    bufferedWriter.write(parserMessage(new String(message, StandardCharsets.UTF_8)));
    bufferedWriter.flush();
  }

  /** send OCR result to client. */
  public void sendOcrMessage(InputStream processInputStream)
      throws IOException, InterruptedException {
    // Send the result of calculations with Deep Learning models to client
    byte[] message = IOUtils.toByteArray(processInputStream);

    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    bufferedWriter.write("OCR:" + new String(message, StandardCharsets.UTF_8) + "\t");
    bufferedWriter.flush();
    // TODO: wait the development of Android client
    // TODO: START
//    System.out.println("OCR - Original message: " + new String(message, StandardCharsets.UTF_8));
//    System.out.println(
//        "OCR - Sent message: " + parserMessage(new String(message, StandardCharsets.UTF_8)));
    // TODO: END
  }

  /** send Object detection result to client. */
  public void sendObjectMessage(InputStream processInputStream)
      throws IOException, InterruptedException {
    // Send the result of calculations with Deep Learning models to client
    byte[] message = IOUtils.toByteArray(processInputStream);

    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    bufferedWriter.write("Object:" + new String(message, StandardCharsets.UTF_8));
    bufferedWriter.flush();
    // TODO: wait the development of Android client
    // TODO: START
    //    clientSocket.shutdownOutput();
    //
    //    System.out.println("Object - Original message: " + new String(message,
    // StandardCharsets.UTF_8));
    //    System.out.println("Object - Sent message: " + new String(message,
    // StandardCharsets.UTF_8));
    // TODO: END
  }

  /** OCR result utility. */
  @Deprecated
  public String parserMessage(String message) {
    // recognized word
    String result = "";

    int wordStart = 4;
    int wordEnd = wordStart;
    int i = 0;
    for (; i < message.length(); i++) {
      if (message.charAt(i) == '\'' && message.charAt(i + 1) == ',') {
        break;
      }
    }
    wordEnd = i;
    result += message.substring(wordStart, wordEnd);
    result += "]\n";

    // each char with probability
    i += 4;
    int count = 0;
    int probabilityStart = i;
    for (; i < message.length(); i++) {
      if (message.charAt(i) == ']' && message.charAt(i + 1) == ')') {
        result += result.substring(count, count + 1);
        result += ": ";
        result += message.substring(probabilityStart, i);
        result += "\n";
        count += 1;
        break;
      }
      if (message.charAt(i) == ',') {
        result += result.substring(count, count + 1);
        result += ": ";
        result += message.substring(probabilityStart, i);
        result += "\n";
        probabilityStart = i + 2;
        count += 1;
      }
    }

    return "[" + result;
  }

  /** clean-up utility. */
  public void closeConnection() throws IOException {
    inputStream.close();
    outputStream.close();
    clientSocket.close();
  }

  /** Shut down server. */
  public void shutDownServer() throws IOException {
    serverSocket.close();
  }
}
