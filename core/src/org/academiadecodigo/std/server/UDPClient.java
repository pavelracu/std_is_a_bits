package org.academiadecodigo.std.server;

import java.io.IOException;
import java.net.*;

/**
 * Created by glitch for <Bashtard$ Bootcamp @ Academia de Código - Fundão 09/06/16.
 */
public class UDPClient {

    String hostName = "127.0.0.1";
    int portNumber = 8888;

    byte[] sendBuffer;
    byte[] recvBuffer = new byte[1024];

    DatagramSocket socket = null;
    private UDPServer server;
    private InetAddress address;
    private int port;


    public UDPClient(InetAddress address, final int port, DatagramSocket socket, UDPServer server) {

        this.address = address;
        this.port = port;
        this.server = server;
        this.socket = socket;

        while (true) {

            Thread input = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            DatagramSocket outputSocket = new DatagramSocket();
                            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, InetAddress.getByName(hostName), portNumber);
                            outputSocket.send(sendPacket);
                        } catch (SocketException e) {
                            e.printStackTrace();
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            input.start();

            // STEP4.1: Create and receive UDP datagram packet from the socket
            DatagramPacket receivePacket = new DatagramPacket(recvBuffer, recvBuffer.length);
            try {
                socket.receive(receivePacket);
                System.out.println(new String(recvBuffer, 0, receivePacket.getLength()));
            } catch (IOException e) {
                System.out.println("ERROR Receiving! " + e.getMessage());
            }


        }
    }
}
