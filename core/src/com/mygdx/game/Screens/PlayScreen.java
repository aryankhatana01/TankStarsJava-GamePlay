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
import com.mygdx.game.Sprites.Projectile;
import com.mygdx.game.Sprites.Projectile2;
import com.mygdx.game.Sprites.Tank;
import com.mygdx.game.Sprites.Tank2;
import com.sun.tools.javac.jvm.Code;

import java.util.concurrent.TimeUnit;

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

    private Tank tank1;
    private Tank2 tank2;

    private int h1 = 100;
    private int h2 = 100;

    private Projectile projectile1;
    private Projectile2 projectile2;

    public PlayScreen(MyGdxGame game){
        this.game = game;
        hud = new Hud(game.batch);
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(MyGdxGame.V_WIDTH / MyGdxGame.PPM,
                MyGdxGame.V_HEIGHT / MyGdxGame.PPM, gameCam);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/MyGdxGame.PPM);
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        world = new World(new Vector2(0, -1), true);
        b2dr = new Box2DDebugRenderer();

        tank1 = new Tank(world);
        projectile1 = new Projectile(world, (float) 4000);

        tank2 = new Tank2(world);
        projectile2 = new Projectile2(world, (float) 4000);

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

    private float convertRad2Deg(float rad) {
        return (float) ((rad*180)/3.14);
    }

    public void handleInput(int tanksFlg){
//        int tanksFlg = 0;
//        float currentAngle = (float) 0.087;
//        System.out.println(currentAngle);
//        System.out.println(tanksFlg);
        if (tanksFlg==0) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && tank1.b2body.getLinearVelocity().x <= 0.37){
                tank1.b2body.applyLinearImpulse(new Vector2(0.1f, 0), tank1.b2body.getWorldCenter(), true);
//                projectile1.b2body.applyLinearImpulse(new Vector2(0.1f, 0), projectile1.b2body.getWorldCenter(), true);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && tank1.b2body.getLinearVelocity().x >= -0.37){
                tank1.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), tank1.b2body.getWorldCenter(), true);
//                projectile1.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), projectile1.b2body.getWorldCenter(), true);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP)){
                tank1.b2body.setTransform(tank1.b2body.getPosition(), (float) ((tank1.b2body.getAngle())+0.087));
//                projectile1.b2body.setTransform(projectile1.b2body.getPosition(), (float) ((projectile1.b2body.getAngle())+0.087));
            }

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                tank1.b2body.setTransform(tank1.b2body.getPosition(), (float) ((tank1.b2body.getAngle())-0.087));
//                projectile1.b2body.setTransform(projectile1.b2body.getPosition(), (float) ((projectile1.b2body.getAngle())-0.087));
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                Vector2 force = new Vector2((float) (Math.cos(tank1.b2body.getAngle())*projectile1.power), (float) (Math.sin(tank1.b2body.getAngle())*projectile1.power));
                projectile1 = new Projectile(world, tank1.b2body.getPosition().x);
                projectile1.b2body.applyForce(force, projectile1.b2body.getPosition(), true);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
                projectile1.power = projectile1.power+10;
//                System.out.println(projectile1.power);
//                projectile1.b2body.setTransform(projectile1.b2body.getPosition(), (float) ((projectile1.b2body.getAngle())-0.087));
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                projectile1.power = projectile1.power-10;
//                projectile1.b2body.setTransform(projectile1.b2body.getPosition(), (float) ((projectile1.b2body.getAngle())-0.087));
            }

//            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
//                projectile1 = new Projectile(world, tank1.b2body.getPosition().x);
//            }
        }else {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && tank2.b2body.getLinearVelocity().x <= 0.37){
                tank2.b2body.applyLinearImpulse(new Vector2(0.1f, 0), tank2.b2body.getWorldCenter(), true);
//                projectile2.b2body.applyLinearImpulse(new Vector2(0.1f, 0), projectile2.b2body.getWorldCenter(), true);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && tank2.b2body.getLinearVelocity().x >= -0.37) {
                tank2.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), tank2.b2body.getWorldCenter(), true);
//                projectile2.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), projectile2.b2body.getWorldCenter(), true);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP)){
                tank2.b2body.setTransform(tank2.b2body.getPosition(), (float) ((tank2.b2body.getAngle())-0.087));
//                projectile2.b2body.setTransform(projectile2.b2body.getPosition(), (float) ((projectile2.b2body.getAngle())-0.087));
            }

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                tank2.b2body.setTransform(tank2.b2body.getPosition(), (float) ((tank2.b2body.getAngle())+0.087));
//                projectile2.b2body.setTransform(projectile2.b2body.getPosition(), (float) ((projectile2.b2body.getAngle())+0.087));
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                Vector2 force = new Vector2((float) (Math.cos(tank2.b2body.getAngle())*projectile2.power), (float) (Math.sin(tank2.b2body.getAngle())*projectile2.power));
                projectile2 = new Projectile2(world, tank2.b2body.getPosition().x);
                projectile2.b2body.applyForce(force, projectile2.b2body.getPosition(), true);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
                projectile2.power = projectile2.power+10;
//                System.out.println(projectile1.power);
//                projectile1.b2body.setTransform(projectile1.b2body.getPosition(), (float) ((projectile1.b2body.getAngle())-0.087));
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                projectile2.power = projectile2.power-10;
//                projectile1.b2body.setTransform(projectile1.b2body.getPosition(), (float) ((projectile1.b2body.getAngle())-0.087));
            }

//            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
//                projectile2 = new Projectile2(world, tank2.b2body.getPosition().x);
//            }
        }

//        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//            if (tanksFlg==0) {
//                tanksFlg=1;
//            }else {
//                tanksFlg=0;
//            }
//        }

    }

    public void collisionDetection() {
        float ProjPosX = projectile1.b2body.getPosition().x;
        float ProjPosY = projectile1.b2body.getPosition().y;
//        System.out.println("proj: " + ProjPosX + " " + ProjPosY);
        float Tank2X = tank2.b2body.getPosition().x;
        float Tank2Y = tank2.b2body.getPosition().y;
//        System.out.println("tank: " + Tank2X + " " + Tank2Y);
        float Tank1X = tank1.b2body.getPosition().x;
        int whichTnk = 0;
        int scoreSub=0;
        if (ProjPosY <= Tank2Y+(tank2.getHeight()*1.4)) {
            if ((ProjPosX >= Tank2X-tank2.tankHitRadius) && (ProjPosX <= Tank2X+tank2.tankHitRadius) || (ProjPosX >= Tank1X-tank1.tankHitRadius) && (ProjPosX <= Tank1X+tank1.tankHitRadius)) {
//                System.out.println("Hit");
                if ((ProjPosX >= Tank2X-tank2.tankHitRadius) && (ProjPosX <= Tank2X+tank2.tankHitRadius)) {
                    whichTnk = 0;
                }else {
                    whichTnk = 1;
                }

                if (whichTnk==0) {
                    float distance = Tank2X - ProjPosX;
                    if (distance<0) {
                        distance = distance*-1;
                        tank2.b2body.applyLinearImpulse(new Vector2(-0.05f, 0), tank2.b2body.getWorldCenter(), true);
                    }else {
                        tank2.b2body.applyLinearImpulse(new Vector2(0.05f, 0), tank2.b2body.getWorldCenter(), true);
                    }
                    scoreSub = (int) (20-(distance*10));
                }else {
                    float distance = Tank1X - ProjPosX;
                    if (distance<0) {
                        distance = distance*-1;
                        tank1.b2body.applyLinearImpulse(new Vector2(-0.05f, 0), tank1.b2body.getWorldCenter(), true);
                    }else {
                        tank1.b2body.applyLinearImpulse(new Vector2(0.05f, 0), tank1.b2body.getWorldCenter(), true);
                    }
                    scoreSub = (int) (20-(distance*10));
                }
//                projectile1.dispose();
            }
//            projectile1.dispose();
            // TODO: Dispose the Imaged and Bodies after collision
        }
        // 0.05 is an arbitrary number
        if (ProjPosY <= Tank2Y+0.05) {
            if (whichTnk==0) {
                h2-=scoreSub;
            }else {
                h1-=scoreSub;
            }
            projectile1.dispose();
        }
    }

    public void collisionDetectionP2() {
        float ProjPosX = projectile2.b2body.getPosition().x;
        float ProjPosY = projectile2.b2body.getPosition().y;
//        System.out.println(ProjPosX + " " + ProjPosY);
        float Tank1X = tank1.b2body.getPosition().x;
        float Tank1Y = tank1.b2body.getPosition().y;
//        System.out.println(Tank2X + " " + Tank2Y);
        float Tank2X = tank2.b2body.getPosition().x;
        int whichTnk = 0;
        int scoreSub=0;
        if (ProjPosY <= Tank1Y+(tank1.getHeight()*1.4)) {
            if ((ProjPosX >= Tank2X-tank2.tankHitRadius) && (ProjPosX <= Tank2X+tank2.tankHitRadius) || (ProjPosX >= Tank1X-tank1.tankHitRadius) && (ProjPosX <= Tank1X+tank1.tankHitRadius)) {
//                System.out.println("Hit");
                if ((ProjPosX >= Tank1X-tank1.tankHitRadius) && (ProjPosX <= Tank1X+tank1.tankHitRadius)) {
                    whichTnk = 0;
                }else {
                    whichTnk = 1;
                }

                if (whichTnk==0) {
                    float distance = Tank1X - ProjPosX;
                    if (distance<0) {
                        distance = distance*-1;
                        tank1.b2body.applyLinearImpulse(new Vector2(-0.05f, 0), tank1.b2body.getWorldCenter(), true);
                    }else {
                        tank1.b2body.applyLinearImpulse(new Vector2(0.05f, 0), tank1.b2body.getWorldCenter(), true);
                    }
                    scoreSub = (int) (20-(distance*10));
                }else {
                    float distance = Tank2X - ProjPosX;
                    if (distance<0) {
                        distance = distance*-1;
                        tank2.b2body.applyLinearImpulse(new Vector2(-0.05f, 0), tank2.b2body.getWorldCenter(), true);
                    }else {
                        tank2.b2body.applyLinearImpulse(new Vector2(0.05f, 0), tank2.b2body.getWorldCenter(), true);
                    }
                    scoreSub = (int) (20-(distance*10));
                }
            }

            if (ProjPosY <= Tank1Y+0.05) {
                if (whichTnk==0) {
                    h1-=scoreSub;
                }else {
                    h2-=scoreSub;
                }
                projectile2.dispose();
            }
        }
    }

    public void update(float dt){
        world.step(1/60f, 6, 2);
        tank1.update(dt);
        tank2.update(dt);
        projectile1.update(dt);
        projectile2.update(dt);
        int tanksFlg=0;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (tanksFlg==0) {
                tanksFlg=1;
            }else {
                tanksFlg=0;
            }
        }
        collisionDetection();
        collisionDetectionP2();
        hud.update(dt, h1, h2, convertRad2Deg(tank1.b2body.getAngle()), 180-(convertRad2Deg(tank2.b2body.getAngle())), projectile1.power, projectile2.power);
        handleInput(tanksFlg);
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

        game.batch.setProjectionMatrix(gameCam.combined);

        game.batch.begin();
        tank1.draw(game.batch);
        game.batch.end();

        game.batch.begin();
        tank2.draw(game.batch);
        game.batch.end();

        game.batch.begin();
        projectile1.draw(game.batch);
        game.batch.end();

        game.batch.begin();
        projectile2.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (h1<=0) {
            game.setScreen(new GameOverP2(game));
        }

        if (h2<=0) {
            game.setScreen(new GameOverP1(game));
        }

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
