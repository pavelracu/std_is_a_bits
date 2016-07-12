package org.academiadecodigo.std.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import org.academiadecodigo.std.Tumor;
import org.academiadecodigo.std.sprites.Player;

/**
 * Created by vi.KING David Neves on 12/07/16.
 */
public class TouchController implements GestureDetector.GestureListener{

    private Player player;

    public TouchController(Player player) {
        GestureDetector gd = new GestureDetector(this);
        Gdx.input.setInputProcessor(gd);
        this.player = player;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        player.getB2Body().applyForceToCenter(new Vector2(velocityX / Tumor.PPM, -velocityY / Tumor.PPM), true);

        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
