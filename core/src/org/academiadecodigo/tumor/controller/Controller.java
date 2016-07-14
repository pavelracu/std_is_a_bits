package org.academiadecodigo.tumor.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import org.academiadecodigo.tumor.Tumor;
import org.academiadecodigo.tumor.sprites.Player;

/**
 * Created by vi.KING David Neves on 12/07/16.
 */
public class Controller implements GestureDetector.GestureListener, InputProcessor {

    private Player player1;
    private Player player2;

    private float x;
    private float y;


    public Controller(Player player1, Player player2) {

        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        im.addProcessor(gd);
        im.addProcessor(this);


        Gdx.input.setInputProcessor(im);

        this.player1 = player1;
        this.player2 = player2;
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

        if ( x > Gdx.graphics.getWidth() / 2) {

            player2.getB2Body().applyForceToCenter(new Vector2(velocityX / Tumor.PPM, -velocityY / Tumor.PPM), true);
            System.out.println("a");
        } else {
            player1.getB2Body().applyForceToCenter(new Vector2(velocityX / Tumor.PPM, -velocityY / Tumor.PPM), true);
            System.out.println("b");
        }
        return true;
    }


    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        this.x = x - deltaX;
        this.y = y - deltaY;

        return true;
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

        if (initialPointer1.x < Gdx.graphics.getWidth() / 2) {
            player1.getB2Body().applyForceToCenter(new Vector2((pointer1.x - initialPointer1.x) / Tumor.PPM, -(pointer1.y - initialPointer1.y) / Tumor.PPM), true);
            player2.getB2Body().applyForceToCenter(new Vector2((pointer2.x - initialPointer2.x) / Tumor.PPM, -(pointer2.y - initialPointer2.y) / Tumor.PPM), true);
            System.out.println("a1");
        } else {
            player2.getB2Body().applyForceToCenter(new Vector2((pointer1.x - initialPointer1.x) / Tumor.PPM, -(pointer1.y - initialPointer1.y) / Tumor.PPM), true);
            player1.getB2Body().applyForceToCenter(new Vector2((pointer2.x - initialPointer2.x) / Tumor.PPM, -(pointer2.y - initialPointer2.y) / Tumor.PPM), true);
            System.out.println("b1");
        }


        return true;
    }

    @Override
    public void pinchStop() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
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
}
