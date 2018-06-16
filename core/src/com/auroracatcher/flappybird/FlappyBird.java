package com.auroracatcher.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
    ShapeRenderer shapeRenderer;

	Texture[] birds;
	int flapState = 0;
	float birdY = 0;
	float velocity = 0;
	Circle birdCircle;

	int gameState = 0;
	int gravity = 2;

	Texture topTube;
	Texture bottomTube;
	float gap = 600; // gap between top and bottom tubes
    Random random;
    float tubeVelocity = 4;
    int numberTubes = 4;
    float[] tubeX = new float[numberTubes];
    float[] tubeOffset = new float[numberTubes];
    float distanceBetweenTubes;

    Rectangle[] topTubeRectangles;
    Rectangle[] bottomTubeRectangles;

    @Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds = new Texture[] {
			new Texture("bird.png"), new Texture("bird2.png")
		};
		birdY = Gdx.graphics.getHeight() / 2 - birds[flapState].getHeight() / 2;
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        random = new Random();
        distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;
        shapeRenderer = new ShapeRenderer();
        birdCircle = new Circle();
        topTubeRectangles = new Rectangle[numberTubes];
        bottomTubeRectangles = new Rectangle[numberTubes];
        for (int i = 0; i < numberTubes; i++) {
            tubeOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap);
            tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * distanceBetweenTubes;

            topTubeRectangles[i] = new Rectangle();
            bottomTubeRectangles[i] = new Rectangle();
        }



	}

	@Override
	public void render () {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1) {
            if (Gdx.input.justTouched()) {
                velocity = -30;
            }

            for (int i = 0; i < numberTubes; i++) {
                if (tubeX[i] < -topTube.getWidth()) {
                    tubeX[i] += numberTubes * distanceBetweenTubes;
                    tubeOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap);
                }
                tubeX[i] -= tubeVelocity;

                batch.draw(topTube,
                        tubeX[i],
                        Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
                batch.draw(bottomTube,
                        tubeX[i],
                        Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);
                topTubeRectangles[i] = new Rectangle(tubeX[i],
                        Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i],
                        topTube.getWidth(),
                        topTube.getHeight());
                bottomTubeRectangles[i] = new Rectangle(tubeX[i],
                        Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i],
                        bottomTube.getWidth(),
                        bottomTube.getHeight());

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


		batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);
		batch.end();



		// check collisions
        birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + birds[flapState].getHeight() / 2, birds[flapState].getHeight() / 2);



//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

        for (int i = 0; i < numberTubes; i++) {
//            shapeRenderer.rect(tubeX[i],
//                    Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i],
//                    topTube.getWidth(),
//                    topTube.getHeight());
//            shapeRenderer.rect(tubeX[i],
//                    Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i],
//                    bottomTube.getWidth(),
//                    bottomTube.getHeight());

            if (Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) {
                Gdx.app.log("hit the wall", "hehe");
            }
        }
//        shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		birds[0].dispose();
		birds[1].dispose();
	}
}
