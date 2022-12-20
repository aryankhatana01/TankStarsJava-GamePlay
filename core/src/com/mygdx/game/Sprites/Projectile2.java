package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;

public class Projectile2 extends Sprite {
    public World world;
    public Body b2body;
    private Texture projimg;

    public Projectile2(World world, float x){
        this.world = world;
        defineProf(x);
        projimg = new Texture("fireball.png");
        setBounds(0,0, 8/MyGdxGame.PPM, 8/MyGdxGame.PPM);
        setRegion(projimg);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
    }

    public void defineProf(float x){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, 100/MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef  = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5/MyGdxGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
        b2body.setTransform(b2body.getPosition(), (float) 3.14);
    }

    public void dispose() {
        projimg.dispose();
//        world.destroyBody(b2body);
        world.step(0 ,0 ,0);
        b2body.setActive(false);
    }

}