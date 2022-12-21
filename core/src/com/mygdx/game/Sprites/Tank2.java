package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;

public class Tank2 extends Sprite {
    public World world;
    public Body b2body;

    private Texture tankimg;

    public int fuel = 100;

    public float tankHitRadius = (float) 0.5;

    public Tank2(World world){
        this.world = world;
        defineTank();
        tankimg = new Texture("Tank1-removebg-re.png");
        setBounds(0,0, 16/MyGdxGame.PPM, 16/MyGdxGame.PPM);
        setRegion(tankimg);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
    }
    public void defineTank(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(330/MyGdxGame.PPM, 80/MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef  = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5/MyGdxGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
        b2body.setTransform(b2body.getPosition(), (float) 3.14);
    }

}