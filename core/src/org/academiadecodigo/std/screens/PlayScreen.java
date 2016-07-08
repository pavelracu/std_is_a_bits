package org.academiadecodigo.std.screens;

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
import org.academiadecodigo.std.STDIsABits;
import org.academiadecodigo.std.scenes.Hud;
import org.academiadecodigo.std.sprites.Cell;
import org.academiadecodigo.std.sprites.Player;
import org.academiadecodigo.std.tools.B2WorldCreator;
import org.academiadecodigo.std.tools.WorldContactListener;

/**
 * Created by Helia Marcos, David Neves, Nuno Pereira, Nelson Oliveira, Pavel Racu and Luis Salvado on 07/07/2016.
 */
public class PlayScreen implements Screen {

    private STDIsABits game;
    private AssetManager manager;

    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    private Player player1;
    private Player player2;

    private Cell cell;

    public PlayScreen(STDIsABits game, AssetManager manager) {

        this.game = game;
        this.manager = manager;

        init();
    }

    private void init() {

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(STDIsABits.WIDTH / STDIsABits.PPM, STDIsABits.HEIGHT / STDIsABits.PPM, gameCam);
        hud = new Hud(game.sb);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("game1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / STDIsABits.PPM);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true);

        creator = new B2WorldCreator(this);

        player1 = new Player(this, 40, 460, new Texture("blueball.png"), STDIsABits.VIRUS1_BIT);


        player2 = new Player(this, STDIsABits.WIDTH - 40, STDIsABits.HEIGHT - 460, new Texture("redball.png"), STDIsABits.VIRUS2_BIT);


         world.setContactListener(new WorldContactListener());
    }

    public void handleInput(float dt) {

        handlePlayer1Input();
        handlePlayer2Input();

    }

    private void handlePlayer1Input() {
        if (Gdx.input.isKeyPressed(Input.Keys.W) && player1.getB2Body().getPosition().y + player1.getHeight() / 2 < STDIsABits.HEIGHT / STDIsABits.PPM) {
            player1.getB2Body().applyForceToCenter(new Vector2(0, Player.PLAYER_SPEED), true);
            //player1.getB2Body().setLinearVelocity(0, Player.PLAYER_SPEED);
        } if (Gdx.input.isKeyPressed(Input.Keys.S) && player1.getB2Body().getPosition().y - player1.getHeight() / 2 > 0) {
            player1.getB2Body().applyForceToCenter(new Vector2(0, -Player.PLAYER_SPEED), true);
            //player1.getB2Body().setLinearVelocity(0, -Player.PLAYER_SPEED);
        } if (Gdx.input.isKeyPressed(Input.Keys.A) && player1.getB2Body().getPosition().x - player1.getWidth() / 2 > 0) {
            player1.getB2Body().applyForceToCenter(new Vector2(-Player.PLAYER_SPEED, 0), true);
            //player1.getB2Body().setLinearVelocity(-Player.PLAYER_SPEED, 0);
        } if (Gdx.input.isKeyPressed(Input.Keys.D) && player1.getB2Body().getPosition().x + player1.getWidth() / 2 < STDIsABits.WIDTH / STDIsABits.PPM) {
            player1.getB2Body().applyForceToCenter(new Vector2(Player.PLAYER_SPEED, 0), true);
            //player1.getB2Body().setLinearVelocity(Player.PLAYER_SPEED, 0);
        }
    }

    private void handlePlayer2Input() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && player2.getB2Body().getPosition().y + player2.getHeight() / 2 < STDIsABits.HEIGHT / STDIsABits.PPM) {
            player2.getB2Body().applyForceToCenter(new Vector2(0, Player.PLAYER_SPEED), true);
            //player2.getB2Body().setLinearVelocity(0,Player.PLAYER_SPEED );
        } if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && player2.getB2Body().getPosition().y - player2.getHeight() / 2 > 0) {
            player2.getB2Body().applyForceToCenter(new Vector2(0, -Player.PLAYER_SPEED), true);
            //player2.getB2Body().setLinearVelocity(0, -Player.PLAYER_SPEED);
        } if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player2.getB2Body().getPosition().x - player2.getWidth() / 2 > 0) {
            player2.getB2Body().applyForceToCenter(new Vector2(-Player.PLAYER_SPEED, 0), true);
            //player2.getB2Body().setLinearVelocity(-Player.PLAYER_SPEED, 0);
        } if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player2.getB2Body().getPosition().x + player2.getWidth() / 2 < STDIsABits.WIDTH / STDIsABits.PPM) {
            player2.getB2Body().applyForceToCenter(new Vector2(Player.PLAYER_SPEED, 0), true);
            //player2.getB2Body().setLinearVelocity(Player.PLAYER_SPEED, 0);
        }
    }

    private void setToSteady(Player player) {
        player.getB2Body().setLinearVelocity(0, 0);
    }

    public void update(float dt) {

        if (hud.isTimeUp() || isGameOver()) {
            gameOver();
        }

        handleInput(dt);

        world.step(1 / 60f, 6, 2);

        hud.clearScore();
        for (Cell cell : creator.getCells()) {
            cell.update(dt);
            if (cell.player == player1) {
                hud.addScore(1, 1);
            } else if (cell.player == player2) {
                hud.addScore(1, 2);
            }
        }

        if (MathUtils.random(100) < 10) {
            for (Cell cell : creator.getCells()) {
                cell.getB2Body().applyForceToCenter(new Vector2(MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f)), true);

                System.out.println("sgd");
            }
        }

        player1.update(dt);
        player2.update(dt);

        hud.update(dt);

        renderer.setView(gameCam);
    }

    public boolean isGameOver() {
        for (Cell cell : creator.getCells()) {
           if (cell.getPlayer() == null) {
                return false;
            }
        }
        return true;
    }

    private void gameOver() {

        game.setScreen(new GameOverScreen(game, manager));
        //dispose();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {

        update(dt);

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

        game.sb.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {

        gamePort.update(width, height);
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
