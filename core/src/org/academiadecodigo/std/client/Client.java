package org.academiadecodigo.std.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import org.academiadecodigo.std.DirectionType;

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
public class Client implements InputProcessor, Runnable {

    DirectionType dir = DirectionType.NULL;
    byte[] bytes = new byte[2];
    String hostName = "127.0.0.1";
    int portNumber = 8888;
    DatagramSocket socket = null;


    public Client(){
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println("Error creating socket" +e.getMessage());
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            dir = DirectionType.UP;
            return true;
        } if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dir = DirectionType.DOWN;
            return true;
        } if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dir = DirectionType.LEFT;
            return true;
        } if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dir = DirectionType.RIGHT;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        dir = DirectionType.NULL;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void convertToBytes() {
        bytes = dir.getNumber().getBytes();
    }


    @Override
    public void run() {

        while (true) {


            try {
                keyDown(0);
                convertToBytes();
                DatagramPacket sendPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(hostName), portNumber);
                socket.send(sendPacket);
                keyUp(0);
            } catch (IOException e) {
                System.out.println("ERROR Sending! " + e.getMessage());
            }
        }
    }
}
