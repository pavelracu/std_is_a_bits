package org.academiadecodigo.std.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.academiadecodigo.std.Tumor;
import org.academiadecodigo.std.controller.Controller;
import org.academiadecodigo.std.scenes.Hud;
import org.academiadecodigo.std.sprites.Cell;
import org.academiadecodigo.std.sprites.Player;
import org.academiadecodigo.std.tools.B2WorldCreator;
import org.academiadecodigo.std.tools.WorldContactListener;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;

/**
 * Created by Helia Marcos, David Neves, Nuno Pereira, Nelson Oliveira, Pavel Racu and Luis Salvado on 07/07/2016.
 */
public class PlayScreen implements Screen {

    private Tumor game;
    private AssetManager manager;

    //createQueue
    private LinkedList<String> queue;

    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private Hud hud;
    private Controller controller;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    private Player player1;
    private Player player2;

    private Cell cell;
    private boolean isMultiplayer;

    DatagramSocket socket = null;

    byte[] sendBuffer = new byte[1024];

    String stringDir = "";
    String command = "";
    String[] strings = new String[3];

    public PlayScreen(Tumor game, AssetManager manager, LinkedList queue, boolean isMultiplayer) throws SocketException {
        this.queue = queue;
        this.game = game;
        this.manager = manager;
        this.isMultiplayer = isMultiplayer;

        socket = new DatagramSocket();

        init();
    }

    private void init() throws SocketException {

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Tumor.WIDTH / Tumor.PPM, Tumor.HEIGHT / Tumor.PPM, gameCam);
        hud = new Hud(game.sb);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("game1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Tumor.PPM);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true);

        creator = new B2WorldCreator(this);

        player1 = new Player(this, 40, 460, new Texture("virus.png"), Tumor.VIRUS1_BIT, 1);


        player2 = new Player(this, Tumor.WIDTH - 40, Tumor.HEIGHT - 460, new Texture("virus02.png"), Tumor.VIRUS2_BIT, 2);


        world.setContactListener(new WorldContactListener(manager));

        controller = new Controller(game);

    }

    public void handleInput() {

        if (isMultiplayer) {
            handlePlayer1Input();
            handleNetwork1Input();

        } else {
            handlePlayer1Input();
            handlePlayer2Input();
        }
    }


    private void handleNetwork1Input() {

        if (stringDir.equals("1")) {
            player2.getB2Body().applyForceToCenter(new Vector2(0, Player.PLAYER_SPEED), true);
            //player2.getB2Body().setLinearVelocity(0,Player.PLAYER_SPEED );
        }
        if (stringDir.equals("2")) {
            player2.getB2Body().applyForceToCenter(new Vector2(0, -Player.PLAYER_SPEED), true);
            //player2.getB2Body().setLinearVelocity(0, -Player.PLAYER_SPEED);
        }
        if (stringDir.equals("3")) {
            player2.getB2Body().applyForceToCenter(new Vector2(-Player.PLAYER_SPEED, 0), true);
            //player2.getB2Body().setLinearVelocity(-Player.PLAYER_SPEED, 0);
        }
        if (stringDir.equals("4")) {
            player2.getB2Body().applyForceToCenter(new Vector2(Player.PLAYER_SPEED, 0), true);
            //player2.getB2Body().setLinearVelocity(Player.PLAYER_SPEED, 0);
        }
    }

    private void handlePlayer1Input() {
        if (controller.isUpPressed()) {
            player1.getB2Body().applyForceToCenter(new Vector2(0, Player.PLAYER_SPEED), true);
        }
        if (controller.isDownPressed()) {
            player1.getB2Body().applyForceToCenter(new Vector2(0, -Player.PLAYER_SPEED), true);
        }
        if (controller.isLeftPressed()) {
            player1.getB2Body().applyForceToCenter(new Vector2(-Player.PLAYER_SPEED, 0), true);
        }
        if (controller.isRightPressed()) {
            player1.getB2Body().applyForceToCenter(new Vector2(Player.PLAYER_SPEED, 0), true);
        }
    }

    private void handlePlayer2Input() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player2.getB2Body().applyForceToCenter(new Vector2(0, Player.PLAYER_SPEED), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player2.getB2Body().applyForceToCenter(new Vector2(0, -Player.PLAYER_SPEED), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player2.getB2Body().applyForceToCenter(new Vector2(-Player.PLAYER_SPEED, 0), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player2.getB2Body().applyForceToCenter(new Vector2(Player.PLAYER_SPEED, 0), true);
        }
    }

    private void setToSteady(Player player) {
        player.getB2Body().setLinearVelocity(0, 0);
    }

    public void update(float dt) throws IOException {

        if (hud.isTimeUp() || isGameOver()) {
            gameOver();
        }
//        DatagramPacket sendPacket;
//
//        if (isMultiplayer) {
//
//            synchronized (queue) {
//                command = queue.pollLast();
//                strings = command.split(";");
//                stringDir = strings[0];
//            }
//
//            if (stringDir == null) stringDir = "0";
//
//
//        }


        handleInput();

        world.step(1 / 60f, 6, 2);

        hud.clearScore();
//        for (Cell cell : creator.getCells()) {
//            cell.update(dt);
//            if (cell.player == player1) {
//                hud.addScore(1, 1);
//            } else if (cell.player == player2) {
//                hud.addScore(1, 2);
//            }
//        }
//
//        if (MathUtils.random(100) < 10) {
//            for (Cell cell : creator.getCells()) {
//                cell.getB2Body().applyForceToCenter(new Vector2(MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f)), true);
//
//            }
//        }
//        for (Cell cell : creator.getCells()) {
//            cell.update(dt);
//            hud.addScore(1, cell.getState());
//
//        }

//        if (MathUtils.random(100) < 10) {
//            for (Cell cell : creator.getCells()) {
//                cell.getB2Body().applyForceToCenter(new Vector2(MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f)), true);
//
//            }
//        }

        player1.update(dt);
        player2.update(dt);

        hud.update(dt);

        renderer.setView(gameCam);

//        if(isMultiplayer) {
//
//            String pos = "";
//
//            for (Cell cell : creator.getCells()) {
//                pos += cell.getB2Body().getPosition().x + "," + cell.getB2Body().getPosition().y + "," + cell.getState() +";";
//            }
//
//            pos += player1.getB2Body().getPosition().x + "," + player1.getB2Body().getPosition().y + ";" +
//                    player2.getB2Body().getPosition().x + "," + player2.getB2Body().getPosition().y + ";";
//
//            sendBuffer = pos.getBytes();
//
//            System.out.println(strings[1]);
//            sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, InetAddress.getByName("192.168.1.20"), 9999);
//
//            socket.send(sendPacket);
//
//        }

    }

    public boolean isGameOver() {
        for (Cell cell : creator.getCells()) {
            if (cell.getState() == 0) {
                return false;
            }
        }
        return true;
    }

    private void gameOver() {

        game.setScreen(new GameOverScreen(game, manager));

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {

        try {
            update(dt);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //b2dr.render(world, gameCam.combined);

        game.sb.setProjectionMatrix(gameCam.combined);
        game.sb.begin();
        player1.draw(game.sb);
        player2.draw(game.sb);

        for (Cell cell : creator.getCells()) {
            cell.draw(game.sb);
        }

        game.sb.end();

        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            controller.draw();
        }

        game.sb.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {

        gamePort.update(width, height);
        controller.resize(width, height);
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

        map.dispose();
        renderer.dispose();
        world.dispose();
        //b2dr.dispose();
        hud.dispose();
    }


    public World getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return map;
    }
}
