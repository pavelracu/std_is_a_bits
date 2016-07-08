package org.academiadecodigo.std.client;

import com.badlogic.gdx.utils.Array;
import org.academiadecodigo.std.sprites.Cell;
import org.academiadecodigo.std.tools.B2WorldCreator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by glitch
 * Bashtard$ Bootcamp @ Academia de Código
 * Fundão 08/07/16 --
 */
public class FromServer implements Runnable {

    byte[] recvBuffer = new byte[1024];
    private DatagramSocket socket;
    private String serverMessage;
    private String[] updateObjects;


    public FromServer() {
        try {
            socket = new DatagramSocket(9999);
        } catch (SocketException e) {
            System.out.println("Ganda Sout para o Socket que falhou!" + e.getMessage());
        }
    }

    @Override
    public void run() {

        while (true) {

            try {

                DatagramPacket receivePacket = new DatagramPacket(recvBuffer, recvBuffer.length);
                socket.receive(receivePacket);
                serverMessage = new String(recvBuffer, 0, receivePacket.getLength());
                System.out.println(serverMessage);
                updateObjects = serverMessage.split(";");
                B2WorldCreator.setCells(updateObjects);


            } catch (IOException e) {
                System.out.println("ERROR Sending! " + e.getMessage());
            }
        }

    }

}
