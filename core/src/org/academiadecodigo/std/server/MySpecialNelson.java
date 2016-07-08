package org.academiadecodigo.std.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by glitch
 * Bashtard$ Bootcamp @ Academia de Código
 * Fundão 08/07/16 --
 */
public class MySpecialNelson implements Runnable {

    private LinkedList<String> queue;
    DatagramSocket socket = null;
    private InetAddress remotePlayerAddress;
    private int remotePlayerPort;

    public MySpecialNelson(LinkedList<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        try {
            socket = new DatagramSocket(8888);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        byte[] recvBuffer = new byte[1024];

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(recvBuffer, recvBuffer.length);
            remotePlayerAddress = receivePacket.getAddress();
            remotePlayerPort = receivePacket.getPort();

            try {
                socket.receive(receivePacket);
                String command = new String(recvBuffer, 0, receivePacket.getLength());
                command += ";" + remotePlayerAddress.toString() + ";" + remotePlayerPort;
                synchronized (queue) {
                    queue.add(command);
                }

            } catch (IOException e) {
                System.out.println("ERROR Receiving! " + e.getMessage());
            }
        }

    }

    public InetAddress getRemotePlayerAddress() {
        return remotePlayerAddress;
    }

    public int getRemotePlayerPort() {
        return remotePlayerPort;
    }
}
