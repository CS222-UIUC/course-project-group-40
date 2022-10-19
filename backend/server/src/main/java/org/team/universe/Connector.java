package org.team.universe;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;

/**
 * Connector of the server project. It currently uses TCP network protocol to connect with Andorid
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

  /** Start to listen to clients and establish connection. */
  public void startConnection(
      String address, String port, InputStream inputStreamTest, OutputStream outputStreamTest)
      throws IOException {
    // set the socket of this server and keep listing
    int serverPort = Integer.parseInt(port);

    // TODO: wait the development of Android client
    // For now, we need to wait for the development of Android client
    //    serverSocket = new ServerSocket(serverPort);
    //    clientSocket = serverSocket.accept();

    // obtain the streams to read and write to client
    //    inputStream = clientSocket.getInputStream();
    //    outputStream = clientSocket.getOutputStream();

    // Toy Tests
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

    int length = Integer.parseInt(height) * Integer.parseInt(width);
    byte[] bytes = new byte[length];
    // TODO: read lines from client
  }

  /** send result to client. */

  public void sendMessage(String message) {
    // TODO: Send the result of calculations with Deep Learning models to client
    output.write(message);
  }

  /** clean-up utility. */
  public void closeConnection() throws IOException {
    inputStream.close();
    outputStream.close();
    // TODO: wait for Andriod client
    //    output.close();
    //    clientSocket.close();
    //    serverSocket.close();
  }
}
