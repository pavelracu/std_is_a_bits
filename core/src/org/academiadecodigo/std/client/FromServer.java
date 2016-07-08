package org.academiadecodigo.std.client;
import com.badlogic.gdx.utils.Array;
import org.academiadecodigo.std.sprites.Cell;
import org.academiadecodigo.std.tools.B2WorldCreator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import static org.academiadecodigo.std.tools.B2WorldCreator.getCells;

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


    public FromServer(){
        try {
            socket = new DatagramSocket(9999);
        } catch (SocketException e) {
            System.out.println("Ganda Sout para o Socket que falhou!" +e.getMessage());
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
                getCells().clear();
                for(int i=0; i<updateObjects.length-2; i++){
                    String[] parse = updateObjects[i].split(",");
                    getCells().add(new Cell(B2WorldCreator.getScreen(),Float.valueOf(parse[0]),Float.valueOf(parse[1])));
                }
            } catch (IOException e) {
                System.out.println("ERROR Sending! " + e.getMessage());
            }
        }

    }

}
