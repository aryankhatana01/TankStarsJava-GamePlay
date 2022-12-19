package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Tank;
import com.sun.tools.javac.jvm.Code;

public class PlayScreen implements Screen {

    private final MyGdxGame game;

    private final OrthographicCamera gameCam;
    private final Viewport gamePort;

    private Hud hud;

    private World world;
    private Box2DDebugRenderer b2dr;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private Tank tank;

    public PlayScreen(MyGdxGame game){
        this.game = game;
        hud = new Hud(game.batch);
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(MyGdxGame.V_WIDTH/MyGdxGame.PPM,
                MyGdxGame.V_HEIGHT/MyGdxGame.PPM, gameCam);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/MyGdxGame.PPM);
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        world = new World(new Vector2(0, -1), true);
        b2dr = new Box2DDebugRenderer();

        tank = new Tank(world);
//        BodyDef bdef = new BodyDef();
//        PolygonShape shape = new PolygonShape();
//        FixtureDef fdef = new FixtureDef();
//        Body body;

//        for(RectangleMapObject obj : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
//            Rectangle rect = obj.getRectangle();
//
//            bdef.type = BodyDef.BodyType.StaticBody;
//            bdef.position.set(rect.getX() + rect.getWidth()/2, rect.getY() + rect.getHeight()/2);
//
//            body = world.createBody(bdef);
//
//            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
//            fdef.shape = shape;
//            body.createFixture(fdef);
//        }
        for(PolylineMapObject obj : map.getLayers().get(1).getObjects().getByType(PolylineMapObject.class)){
            Shape shape;
            shape = createPolyLine(obj);
            Body body;
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);
            body.createFixture(shape, 1.0f);
            shape.dispose();
        }

    }

    private static ChainShape createPolyLine(PolylineMapObject polyline){
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length/2];

        for(int i=0; i<worldVertices.length; i++){
            worldVertices[i] = new Vector2(vertices[i*2]/MyGdxGame.PPM, vertices[i*2 + 1]/MyGdxGame.PPM);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices);
        return cs;
    }

    public void handleInput(){

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && tank.b2body.getLinearVelocity().x <= 0.37){
            tank.b2body.applyLinearImpulse(new Vector2(0.1f, 0), tank.b2body.getWorldCenter(), true);
//            tank.b2body.setLinearVelocity(0,0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && tank.b2body.getLinearVelocity().x >= -0.37)
            tank.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), tank.b2body.getWorldCenter(), true);
    }

    public void update(float dt){
        world.step(1/60f, 6, 2);
        handleInput();
        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor((float) 0.384, (float) 0.67, (float) 0.984,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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

    }
}
