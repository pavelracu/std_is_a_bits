package org.academiadecodigo.std.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import javafx.scene.control.Tab;
import org.academiadecodigo.std.STDIsABits;
import org.academiadecodigo.std.scenes.Hud;

import static com.badlogic.gdx.Gdx.graphics;

/**
 * Created by Helia Marcos, David Neves, Nuno Pereira, Nelson Oliveira, Pavel Racu and Luis Salvado on 07-07-2016.
 */
public class GameOverScreen implements Screen {

    private STDIsABits game;
    private OrthographicCamera cam;
    private Viewport viewport;

    private AssetManager manager;
    private Music music;
    private BitmapFont font;

    private int score;

    private Texture texture;

    private static Label scorePlayer;
    private Label winnerScore;

    public Stage stage;

    public GameOverScreen(STDIsABits game, AssetManager manager) {
        this.game = game;
        this.manager = manager;

        cam = new OrthographicCamera();
        viewport = new FitViewport(STDIsABits.WIDTH, STDIsABits.HEIGHT, cam);
        stage = new Stage(viewport, game.sb);

        Table table = new Table();
        table.bottom();
        table.setFillParent(true);
        String winner;
        int score;
        if(Hud.getScorePlayer1() > Hud.getScorePlayer2()){
            winner = new String("PLAYER 1");
            score = Hud.getScorePlayer1();
        }else {
            winner = new String("PLAYER 2");
            score = Hud.getScorePlayer2();
        }
        winnerScore = new Label(winner + " wins, " + score + " cells were infected!", new Label.LabelStyle(new BitmapFont(), Color.LIGHT_GRAY));
        winnerScore.setFontScale(3f);

        table.add(winnerScore).expandX().padBottom(250);
        //table.add(scorePlayer).expandX().padBottom();

        stage.addActor(table);

        cam.position.set(viewport.getWorldWidth() / 2, viewport.getScreenHeight(), 0);

        texture = new Texture("gameover.png");
    }

    public void handleInput(float dt) {

        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

            game.setScreen(new MenuScreen(game, manager));
            dispose();

        }

    }

    public void displayScore(){

    }

    public void update(float dt) {
        handleInput(dt);
    }

    @Override
    public void show() {


    }

    @Override
    public void render(float dt) {

        update(dt);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.sb.setProjectionMatrix(cam.combined);
        game.sb.begin();
        game.sb.draw(texture, 0, cam.position.y - cam.viewportHeight / 2);

        game.sb.end();
        stage.draw();

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
        texture.dispose();
    }
}
