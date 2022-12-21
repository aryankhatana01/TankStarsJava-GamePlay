package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;

public class GameTypeSelection implements Screen {
    MyGdxGame game;
    Texture img;

    public GameTypeSelection (MyGdxGame game){
        this.game = game;
    }

    @Override
    public void show() {
        img = new Texture("three.jpg");
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

        //pvp
        if(Gdx.input.justTouched()) {
            if (Gdx.input.getY() < 311 && Gdx.input.getY() > 224 && Gdx.input.getX() > 441 && Gdx.input.getX() < 758) {
                if (Gdx.input.isTouched()) {
                    game.setScreen(new TankSelectionScreen1(game));
                }
            }

//            //pvc
//            else if (Gdx.input.getY() < 767 && Gdx.input.getY() > 619 && Gdx.input.getX() > 704 && Gdx.input.getX() < 1211) {
//                if (Gdx.input.isTouched()) {
//                    game.setScreen(new PVCTankSelection(game));
//                }
//            }
//
//            //back
            else if (Gdx.input.getY() < 130 && Gdx.input.getY() > 66 && Gdx.input.getX() > 69 && Gdx.input.getX() < 211) {
                if (Gdx.input.isTouched()) {
                    game.setScreen(new MainMenuScreen(game));
                }
            }
        }
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