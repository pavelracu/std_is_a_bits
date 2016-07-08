package org.academiadecodigo.std;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.academiadecodigo.std.screens.MenuScreen;

public class STDIsABits extends Game {

	public static final int WIDTH = 1600;
	public static final int HEIGHT = 960;
	public static final float PPM = 100;

	public static final short EDGE_BIT = 1;
	public static final short BALL_BIT = 2;
	public static final short VIRUS1_BIT = 4;
	public static final short VIRUS2_BIT = 8;

	public SpriteBatch sb;

	private AssetManager manager;
	
	@Override
	public void create () {

		sb = new SpriteBatch();
		

		setScreen(new MenuScreen(this, manager));

	}

	@Override
	public void render () {

		super.render();
	}
	
	@Override
	public void dispose () {

		super.dispose();
		manager.dispose();
		sb.dispose();
	}
}
