package org.academiadecodigo.std.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.webkit.ThemeClient;
import org.academiadecodigo.std.client.Client;
import org.academiadecodigo.std.server.MySpecialNelson;

import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.academiadecodigo.std.Tumor;

/**
 * Created by Helia Marcos, David Neves, Nuno Pereira, Nelson Oliveira, Pavel Racu and Luis Salvado on 07/07/2016.
 */
public class MenuScreen implements Screen {

    private Tumor game;
    private OrthographicCamera cam;
    private Viewport viewport;

    private AssetManager manager;
    private Music music;

    private Texture texture;
    private boolean isMultiplayer = true;
    ExecutorService pool = Executors.newFixedThreadPool(4); //Create a pool where we limit the max number of simultaneous threads.


    public MenuScreen(Tumor game, AssetManager manager) {
        this.game = game;
        this.manager = manager;

        cam = new OrthographicCamera();
        viewport = new FitViewport(Tumor.WIDTH, Tumor.HEIGHT, cam);

        cam.position.set(viewport.getWorldWidth() / 2, viewport.getScreenHeight() / 2, 0);

        music = manager.get("Skies.mp3", Music.class);
        music.setLooping(true);
        music.play();

        texture = new Texture("start.jpg");
    }


    public void update(float dt) throws SocketException {

        handleInput(dt);
    }

    public void handleInput(float dt) throws SocketException {

        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            LinkedList<String> queue = new LinkedList<String>();
            if (isMultiplayer) {
                MySpecialNelson mySpecialNelson = new MySpecialNelson(queue);
                //Client mySpecialClient = new Client();
                pool.submit(mySpecialNelson);
                //pool.submit(mySpecialClient);

                //Thread mySpecialNelson1 = new Thread(new MySpecialNelson(queue));
                //Thread myClient = new Thread(new Client());
                //mySpecialNelson1.start();
                //myClient.start();

                while (!mySpecialNelson.isConnected()){
                    System.out.println("Ca ganda wait!");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        System.out.println("Ca ganda wait , falhou!");
                    }
                }
            }
            //criar playscreen(game,manager,queue);
            game.setScreen(new PlayScreen(game, manager, queue, isMultiplayer()));

            dispose();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {

        try {
            update(dt);

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.sb.setProjectionMatrix(cam.combined);
            game.sb.begin();
            game.sb.draw(texture, 0, cam.position.y - cam.viewportHeight / 2);

            game.sb.end();

        } catch (SocketException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void resize(int width, int height) {

        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //texture.dispose();
    }

    public void setMultiplayer() {
        isMultiplayer = true;
    }

    public boolean isMultiplayer() {
        return isMultiplayer;
    }
}
