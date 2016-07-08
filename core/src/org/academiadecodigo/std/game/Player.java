package org.academiadecodigo.std.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by glitch
 * Bashtard$ Bootcamp @ Academia de Código
 * Fundão 07/07/16 --
 */
public class Player implements Runnable {

    private String name;
    private BufferedReader input;
    private InetAddress hostAddress;
    private int port;
    private DatagramSocket socket;
    private static String instructions= "--------------\nINSTRUCTIONS:\n--------------\n\nType the following and perform accordingly:\n - left\n - right\n - up\n - down\n - attack\n\nSurvive!!!";


    public Player (InetAddress hostAddress,int port,DatagramSocket gameSocket) {
        this.hostAddress = hostAddress;
        this.port = port;
        socket = gameSocket;
        input = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {

        try {
            System.out.println("Enter your name!!!");
            name = input.readLine();
            System.out.println("Hello and welcome "+name+"\n"+instructions);
            byte[] bytesToSend = name.getBytes();
            DatagramPacket packet = new DatagramPacket(bytesToSend, bytesToSend.length, hostAddress, port);
            socket.send(packet);

            while(true){

                String action = input.readLine();
                bytesToSend = action.getBytes();
                packet = new DatagramPacket(bytesToSend, bytesToSend.length, hostAddress, port);
                socket.send(packet);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void setPort(int port) {
        this.port = port;
    }
}
