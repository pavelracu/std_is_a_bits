package org.academiadecodigo.std.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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

/**
 * Created by neves on 07/07/2016.
 */
public class PlayScreen implements Screen{

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
    }

    public void handleInput(float dt) {


    }

    public void update(float dt) {

        if (hud.isTimeUp()){
            gameOver();
        }
        handleInput(dt);

        world.step(1 / 60f, 6, 2);

        for (Cell cell : creator.getCells()) {
            cell.update(dt);
        }


        hud.update(dt);

        renderer.setView(gameCam);
    }

    private void gameOver() {

        //game.setScreen(new GameOverScreen(game, manager));
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
