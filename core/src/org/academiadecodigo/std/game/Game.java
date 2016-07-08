package org.academiadecodigo.std.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

/**
 * Created by glitch
 * Bashtard$ Bootcamp @ Academia de Código
 * Fundão 07/07/16 --
 */
public class Game {

    private DatagramSocket gameSocket;
    private Player player;
    private byte[] receiveData = new byte[1024];
    private HashMap<String,String[]> players = new HashMap<String, String[]>();


    public Game(String host,int portNumber) {

        try {
            gameSocket = new DatagramSocket();

            player = new Player(InetAddress.getByName(host),portNumber,gameSocket);
            Thread thread = new Thread(player);
            thread.start();

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            gameSocket.receive(receivePacket);

            player.setPort(receivePacket.getPort());

            String message = new String(receiveData, 0, receivePacket.getLength());

            parseJSON(message);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
