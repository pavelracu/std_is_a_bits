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
import org.academiadecodigo.std.STDIsABits;

/**
 * Created by Helia Marcos, David Neves, Nuno Pereira, Nelson Oliveira, Pavel Racu and Luis Salvado on 07/07/2016.
 */
public class MenuScreen implements Screen {

    private STDIsABits game;
    private OrthographicCamera cam;
    private Viewport viewport;

    private AssetManager manager;
    private Music music;

    private Texture texture;


    public MenuScreen(STDIsABits game, AssetManager manager) {
        this.game = game;
        this.manager = manager;

        cam = new OrthographicCamera();
        viewport = new FitViewport(STDIsABits.WIDTH, STDIsABits.HEIGHT, cam);

        cam.position.set(viewport.getWorldWidth() / 2, viewport.getScreenHeight() / 2, 0);

        music = manager.get("audio/music.mp3", Music.class);
        music.setLooping(true);
        music.play();

        texture = new Texture("start.jpg");
    }


    public void update(float dt) {

        handleInput(dt);
    }

    public void handleInput(float dt) {

        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

            game.setScreen(new PlayScreen(game, manager));
            dispose();
        }
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
}
