package org.academiadecodigo.std.server;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by glitch
 * Bashtard$ Bootcamp @ Academia de Código
 * Fundão 08/07/16 --
 */
public class UDPServer {

    byte[] sendBuffer;
    byte[] recvBuffer = new byte[1024];
    private ExecutorService pool = Executors.newFixedThreadPool(2);


    DatagramSocket socket = null;


    public UDPServer(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.out.println("No socket available! " + e.getMessage());
        }

        startServer();
    }

    public void startServer() {

        while (true) {
            waitClientConnection();

        }
    }

    private synchronized void waitClientConnection() {

        Thread waitClientConnection = new Thread(new Runnable() {

            public void run() {
                while (true) {

                    DatagramPacket receivePacket = new DatagramPacket(recvBuffer, recvBuffer.length);

                    try {
                        socket.receive(receivePacket);
                        UDPClient clientConnection = new UDPClient(receivePacket.getAddress(), receivePacket.getPort(), new DatagramSocket(), getServer());

                        System.out.println(new String(recvBuffer, 0, receivePacket.getLength()));

                    } catch (IOException e) {
                        System.out.println("ERROR Receiving! " + e.getMessage());
                    }

                    String ack = "Recebido";
                    sendBuffer = ack.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, receivePacket.getAddress(), receivePacket.getPort());
                    try {
                        socket.send(sendPacket);
                    } catch (IOException e) {
                        System.out.println("ERROR Sending! " + e.getMessage());
                    }


                }
            }
        });
    }

    private UDPServer getServer(){
        return this;
    }
}
