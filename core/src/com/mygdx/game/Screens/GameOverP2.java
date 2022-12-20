package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;

public class GameOverP2 implements Screen {
    MyGdxGame game;
    Texture img;

    public GameOverP2 (MyGdxGame game){
        this.game = game;
    }

    @Override
    public void show() {
        img = new Texture("gameoverp2.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(img, 0, 0);

//        if (Gdx.input.isTouched()) {
//            System.out.println("X: " + Gdx.input.getX());
//            System.out.println("Y: " + Gdx.input.getY());
//        }
        //resume
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
        game.batch.dispose();
        img.dispose();
    }
}