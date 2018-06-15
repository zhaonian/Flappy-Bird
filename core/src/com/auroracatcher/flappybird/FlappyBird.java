package com.auroracatcher.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture background;

	private Texture[] birds;
	private int flapState = 0;
	private float birdY = 0;
	private float velocity = 0;
	private int gameState = 0;
	private int gravity = 2;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds = new Texture[] {
			new Texture("bird.png"), new Texture("bird2.png")
		};
		birdY = Gdx.graphics.getHeight() / 2 - birds[flapState].getHeight() / 2;
	}

	@Override
	public void render () {
		if (Gdx.input.justTouched()) {
			Gdx.app.log("Touched!", "Touched!!!");
			gameState = 1;
		}

		if (gameState == 1) {
			if (Gdx.input.justTouched()) {
				velocity = -20;
			}
			if (birdY > 0 || velocity < 0) {
				velocity += gravity;
				birdY -= velocity;
			}
		} else {
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		}

		flapState ^= 1;
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		birds[0].dispose();
		birds[1].dispose();
	}
}
