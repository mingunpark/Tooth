package game.juan.andenginegame0;

import android.content.Context;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by juan on 2017. 10. 10..
 */

public class Stage {
    private static final int CAMERA_WIDTH = 1600;
    private static final int CAMERA_HEIGHT = 2560;

    BitmapTextureAtlas mBitmapTextureAtlas;

    ITextureRegion playerTexture;

    ITextureRegion backgroundTexture1, backgroundTexture2, backgroundTexture3, backgroundTexture4,
            backgroundTexture5, backgroundTexture6, backgroundTexture7, backgroundTexture8,
            backgroundTexture0, backgroundTexture30, backgroundTexture60, backgroundTexture90;

    ITextureRegion germTexture;

    ITextureRegion toptoothRegion;

    ITextureRegion jointTextureRegion1, jointTextureRegion2,jointTextureRegion3,jointTextureRegion4, jointTextureRegion5 , jointTextureRegion6;

    ITextureRegion itemTextureRegion, itemTextureRegion2, itemTextureRegion3, itemTextureRegion4;
    ITextureRegion slowTextureRegion1, slowTextureRegion2,slowTextureRegion3, slideTextureRegion, slideTextureRegion2;



    Sprite toptooth;
    Player player;

    ArrayList<int[]> germs_xs;
    ArrayList<int[]> germs_ys;

    ArrayList<int[]> obstacle_xs;
    ArrayList<int[]> obstacle_ys;
    ArrayList<int[]> obstacle_types;
    ArrayList<float[]> obstacle_angles;

    public void load(int stage, BaseGameActivity activity){
        germs_xs = new ArrayList<>();
        germs_ys = new ArrayList<>();
        obstacle_xs= new ArrayList<>();
        obstacle_ys= new ArrayList<>();
        obstacle_types = new ArrayList<>();
        obstacle_angles = new ArrayList<>();

        for(int i=0;i<28;i++) {
            loadSectionData(activity,i+1);
            for(int j=0;j<germs_ys.get(i).length;j++){
                germs_ys.get(i)[j]+=i*2560;
            }
            for(int j=0;j<obstacle_ys.get(i).length;j++){
                obstacle_ys.get(i)[j]+=i*2560;
            }
        }

        loadBackgroundImage(stage,activity);
        loadOnHudImage(activity);
        loadPlayerImage(activity);
        loadObstacleImage(activity);
        loadGermImage(activity);
    }

    private void loadSectionData(Context context,int section){
        try{
            int germs_x[];
            int germs_y[];

            int obstacle_x[];
            int obstacle_y[];
            int obstacle_type[];
            float obstacle_angle[];

            JSONArray germData;
            JSONObject stageObject = loadJSONFromAsset(context,"section"+section+".json").getJSONObject("stage");

            germData = stageObject.getJSONArray("germs");
            int germ_size = germData.length();
            germs_x = new int[germ_size];
            germs_y = new int[germ_size];
            for(int i=0;i<germ_size;i++){
                germs_x[i] = germData.getJSONObject(i).getInt("x");
                germs_y[i] = germData.getJSONObject(i).getInt("y");
            }


            JSONArray obstacleData = stageObject.getJSONArray("obstacle");
            int obstacle_size = obstacleData.length();
            obstacle_x = new int[obstacle_size];
            obstacle_y = new int[obstacle_size];
            obstacle_type = new int[obstacle_size];
            obstacle_angle = new float[obstacle_size];

            for(int i=0;i<obstacle_size;i++){
                obstacle_x[i] = obstacleData.getJSONObject(i).getInt("x");
                obstacle_y[i] = obstacleData.getJSONObject(i).getInt("y");
                obstacle_type[i] = obstacleData.getJSONObject(i).getInt("type");
                obstacle_angle[i] = obstacleData.getJSONObject(i).getInt("angle");
            }

            germs_xs.add(germs_x);
            germs_ys.add(germs_y);

            obstacle_xs.add(obstacle_x);
            obstacle_ys.add(obstacle_y);
            obstacle_types.add(obstacle_type);
            obstacle_angles.add(obstacle_angle);


        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }
    private static JSONObject loadJSONFromAsset(Context context, String filename){
        String json = null;
        JSONObject object = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            object = new JSONObject(json);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return object;
    }
    private void loadOnHudImage(BaseGameActivity activity){
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/onhud/");
        BitmapTextureAtlas topBTA = new BitmapTextureAtlas(activity.getTextureManager(),1600,2560);
        toptoothRegion = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(topBTA, activity,"t_t_2.png",0,0 );
        topBTA.load();
    }

    BitmapTextureAtlas background_layer1;
    BitmapTextureAtlas background_layer2;
    BitmapTextureAtlas background_layer3;
    BitmapTextureAtlas background_layer4;
    BitmapTextureAtlas background_layer5;
    BitmapTextureAtlas background_layer6;
    BitmapTextureAtlas background_layer7;
    BitmapTextureAtlas background_layer8;
    BitmapTextureAtlas background_layer0;
    BitmapTextureAtlas background_layer30;
    BitmapTextureAtlas background_layer60;
    BitmapTextureAtlas background_layer90;

    private void loadBackgroundImage(int stage, BaseGameActivity activity){

        // this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR);


        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/background/");

        background_layer1 = new BitmapTextureAtlas(activity.getTextureManager(),640,1024);
        backgroundTexture1 = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(background_layer1, activity,"bg_"+stage+"_1.png",0,0 );
        backgroundTexture1.setTextureSize(1600,2560);
        background_layer1.load();

        background_layer2 = new BitmapTextureAtlas(activity.getTextureManager(),640,1024);
        backgroundTexture2 = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(background_layer2, activity,"bg_"+stage+"_2.png",0,0 );
        backgroundTexture2.setTextureSize(1600,2560);
        background_layer2.load();

        //임시

        background_layer0= new BitmapTextureAtlas(activity.getTextureManager(),640,2048);
        backgroundTexture0 = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(background_layer0, activity,"bg_0.png",0,0 );
        backgroundTexture0.setTextureSize(1600,5120);
        background_layer0.load();

        background_layer30= new BitmapTextureAtlas(activity.getTextureManager(),640,2048);
        backgroundTexture30 = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(background_layer30, activity,"bg_30.png",0,0 );
        backgroundTexture30.setTextureSize(1600,5120);
        background_layer30.load();

        background_layer60= new BitmapTextureAtlas(activity.getTextureManager(),640,2048);
        backgroundTexture60 = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(background_layer60, activity,"bg_60.png",0,0 );
        backgroundTexture60.setTextureSize(1600,5120);
        background_layer60.load();

        background_layer90= new BitmapTextureAtlas(activity.getTextureManager(),640,2048);
        backgroundTexture90 = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(background_layer90, activity,"bg_90.png",0,0 );
        backgroundTexture90.setTextureSize(1600,5120);
        background_layer90.load();

        background_layer3 = new BitmapTextureAtlas(activity.getTextureManager(),640,1024);
        backgroundTexture3 = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(background_layer3, activity,"bg_2_1.png",0,0 );
        backgroundTexture3.setTextureSize(1600,2560);
        background_layer3.load();

        background_layer4 = new BitmapTextureAtlas(activity.getTextureManager(),640,1024);
        backgroundTexture4 = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(background_layer4, activity,"bg_2_2.png",0,0 );
        backgroundTexture4.setTextureSize(1600,2560);
        background_layer4.load();

        background_layer5 = new BitmapTextureAtlas(activity.getTextureManager(),640,1024);
        backgroundTexture5 = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(background_layer5, activity,"bg_3_1.png",0,0 );
        backgroundTexture5.setTextureSize(1600,2560);
        background_layer5.load();

        background_layer6 = new BitmapTextureAtlas(activity.getTextureManager(),640,1024);
        backgroundTexture6 = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(background_layer6, activity,"bg_3_2.png",0,0 );
        backgroundTexture6.setTextureSize(1600,2560);
        background_layer6.load();

        background_layer7 = new BitmapTextureAtlas(activity.getTextureManager(),640,1024);
        backgroundTexture7 = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(background_layer7, activity,"bg_4_1.png",0,0 );
        backgroundTexture7.setTextureSize(1600,2560);
        background_layer7.load();

        background_layer8 = new BitmapTextureAtlas(activity.getTextureManager(),640,1024);
        backgroundTexture8 = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(background_layer8, activity,"bg_4_2.png",0,0 );
        backgroundTexture8.setTextureSize(1600,2560);
        background_layer8.load();

    }
    private void loadPlayerImage(BaseGameActivity activity){
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/player/");

        BitmapTextureAtlas playerBTA = new BitmapTextureAtlas(activity.getTextureManager(),256,256);
        playerTexture = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(playerBTA, activity,"tooth.png",0,0 );
        playerBTA.load();
    }
    private void loadObstacleImage(BaseGameActivity activity){
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/obstacle/");


        //img item
        BitmapTextureAtlas itemTexture = new BitmapTextureAtlas(activity.getTextureManager(),256,256);
        itemTextureRegion = BitmapTextureAtlasTextureRegionFactory.
                createFromAsset(itemTexture, activity,"tem_22.png",0,0);
        itemTexture.load();
        BitmapTextureAtlas itemTexture2 = new BitmapTextureAtlas(activity.getTextureManager(),256,128);
        itemTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.
                createFromAsset(itemTexture2, activity,"tem_1.png",0,0);
        itemTexture2.load();
        BitmapTextureAtlas itemTexture3 = new BitmapTextureAtlas(activity.getTextureManager(),256,128);
        itemTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.
                createFromAsset(itemTexture3, activity,"tem_2.png",0,0);
        itemTexture3.load();
        BitmapTextureAtlas itemTexture4 = new BitmapTextureAtlas(activity.getTextureManager(),1024,256);
        itemTextureRegion4 = BitmapTextureAtlasTextureRegionFactory.
                createFromAsset(itemTexture4, activity,"tem_3.png",0,0);
        itemTexture4.load();

        //img joint
        BitmapTextureAtlas jointTexture2 = new BitmapTextureAtlas(activity.getTextureManager(),320,80);
        jointTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.
                createFromAsset(jointTexture2, activity,"spin_2.png",0,0);
        jointTexture2.load();

        BitmapTextureAtlas jointTexture1 = new BitmapTextureAtlas(activity.getTextureManager(),80,80);
        jointTextureRegion1 = BitmapTextureAtlasTextureRegionFactory.
                createFromAsset(jointTexture1, activity,"spin_1.png",0,0);
        jointTexture1.load();

        BitmapTextureAtlas jointTexture4 = new BitmapTextureAtlas(activity.getTextureManager(),512,80);
        jointTextureRegion4 = BitmapTextureAtlasTextureRegionFactory.
                createFromAsset(jointTexture4, activity,"spin_4.png",0,0);
        jointTexture4.load();

        BitmapTextureAtlas jointTexture3= new BitmapTextureAtlas(activity.getTextureManager(),80,80);
        jointTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.
                createFromAsset(jointTexture3 , activity,"spin_3.png",0,0);
        jointTexture3 .load();

        BitmapTextureAtlas jointTexture5= new BitmapTextureAtlas(activity.getTextureManager(),640,80);
        jointTextureRegion5 = BitmapTextureAtlasTextureRegionFactory.
                createFromAsset(jointTexture5 , activity,"spin_5.png",0,0);
        jointTexture5 .load();

        BitmapTextureAtlas jointTexture6= new BitmapTextureAtlas(activity.getTextureManager(),1024,80);
        jointTextureRegion6 = BitmapTextureAtlasTextureRegionFactory.
                createFromAsset(jointTexture6 , activity,"spin_6.png",0,0);
        jointTexture6 .load();


        //img slow
        BitmapTextureAtlas slowTexture1 = new BitmapTextureAtlas(activity.getTextureManager(),256,256);
        slowTextureRegion1 = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(slowTexture1, activity,"slow_1.png",0,0 );
        slowTexture1.load();

        BitmapTextureAtlas slowTexture2 = new BitmapTextureAtlas(activity.getTextureManager(),512,256);
        slowTextureRegion2 = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(slowTexture2, activity,"slow_2.png",0,0 );
        slowTexture2.load();

        BitmapTextureAtlas slowTexture3 = new BitmapTextureAtlas(activity.getTextureManager(),1024,256);
        slowTextureRegion3 = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(slowTexture3, activity,"slow_3.png",0,0 );
        slowTexture3.load();

        //img slide
        BitmapTextureAtlas slideTexture = new BitmapTextureAtlas(activity.getTextureManager(),1024,128);
        slideTextureRegion = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(slideTexture, activity,"slide_1.png",0,0 );
        slideTexture.load();

        BitmapTextureAtlas slideTexture2 = new BitmapTextureAtlas(activity.getTextureManager(),1024,512);
        slideTextureRegion2 = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(slideTexture2, activity,"slide_2.png",0,0 );
        slideTexture2.load();


    }
    private void loadGermImage(BaseGameActivity activity){
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/germ/");

        BitmapTextureAtlas germBTA = new BitmapTextureAtlas(activity.getTextureManager(),128,128);
        germTexture = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(germBTA, activity,"germ.png",0,0 );
        germBTA.load();
    }
    public void create(BaseGameActivity activity, PhysicsWorld world, Scene scene, SmoothCamera camera){
        createOnHud(activity,camera);
        createBackground(activity,scene);
        createWalls(activity,world,scene);
        createPlayer(activity,world,scene,camera);
        createObstacle(activity,world,scene,player);
        createGerm(activity,world,scene,player);

        setupPhysics(world);
    }
    private void createOnHud(BaseGameActivity activity,SmoothCamera camera){
        toptooth = new Sprite(0, 0, toptoothRegion, activity.getVertexBufferObjectManager());

        HUD hud = new HUD();
        hud.attachChild(toptooth);
        camera.setHUD(hud);
    }

    private void createBackground(BaseGameActivity activity, Scene scene){

        //stage1
        final SpriteBatch staticSpriteBatch0 = new SpriteBatch(this.background_layer0, 1, activity.getVertexBufferObjectManager());
        staticSpriteBatch0.draw(backgroundTexture0, 0, 0, backgroundTexture0.getWidth(), backgroundTexture0.getHeight(), 1, 1, 1, 1, 1, 1);
        staticSpriteBatch0.submit();
        scene.attachChild(staticSpriteBatch0);

        final SpriteBatch staticSpriteBatch = new SpriteBatch(this.background_layer1, 3, activity.getVertexBufferObjectManager());
        staticSpriteBatch.draw(backgroundTexture1, 0, 2560*2, backgroundTexture1.getWidth(), backgroundTexture1.getHeight(), 1, 1, 1, 1, 1, 1);
        staticSpriteBatch.draw(backgroundTexture1, 0, 2560*4, backgroundTexture1.getWidth(), backgroundTexture1.getHeight(), 1, 1, 1, 1, 1,1);
        staticSpriteBatch.draw(backgroundTexture1, 0, 2560*6, backgroundTexture1.getWidth(), backgroundTexture1.getHeight(), 1, 1, 1, 1, 1,1);
        staticSpriteBatch.submit();
        scene.attachChild(staticSpriteBatch);

        final SpriteBatch staticSpriteBatch2 = new SpriteBatch(this.background_layer2, 2, activity.getVertexBufferObjectManager());
        staticSpriteBatch2.draw(backgroundTexture2, 0, 2560*3, backgroundTexture2.getWidth(), backgroundTexture2.getHeight(), 1, 1, 1, 1, 1, 1);
        staticSpriteBatch2.draw(backgroundTexture2, 0, 2560*5, backgroundTexture2.getWidth(), backgroundTexture2.getHeight(), 1, 1, 1, 1, 1,1);
        staticSpriteBatch2.submit();
        scene.attachChild(staticSpriteBatch2);

        //stage2
        final SpriteBatch staticSpriteBatch30 = new SpriteBatch(this.background_layer30, 1, activity.getVertexBufferObjectManager());
        staticSpriteBatch30.draw(backgroundTexture30, 0, 2560*7, backgroundTexture30.getWidth(), backgroundTexture30.getHeight(), 1, 1, 1, 1, 1, 1);
        staticSpriteBatch30.submit();
        scene.attachChild(staticSpriteBatch30);

        final SpriteBatch staticSpriteBatch3 = new SpriteBatch(this.background_layer3, 3, activity.getVertexBufferObjectManager());
        staticSpriteBatch3.draw(backgroundTexture3, 0, 2560*9, backgroundTexture3.getWidth(), backgroundTexture3.getHeight(), 1, 1, 1, 1, 1, 1);
        staticSpriteBatch3.draw(backgroundTexture3, 0, 2560*11, backgroundTexture3.getWidth(), backgroundTexture3.getHeight(), 1, 1, 1, 1, 1,1);
        staticSpriteBatch3.draw(backgroundTexture3, 0, 2560*13, backgroundTexture3.getWidth(), backgroundTexture3.getHeight(), 1, 1, 1, 1, 1,1);
        staticSpriteBatch3.submit();
        scene.attachChild(staticSpriteBatch3);

        final SpriteBatch staticSpriteBatch4 = new SpriteBatch(this.background_layer4, 2, activity.getVertexBufferObjectManager());
        staticSpriteBatch4.draw(backgroundTexture4, 0, 2560*10, backgroundTexture4.getWidth(), backgroundTexture4.getHeight(), 1, 1, 1, 1, 1, 1);
        staticSpriteBatch4.draw(backgroundTexture4, 0, 2560*12, backgroundTexture4.getWidth(), backgroundTexture4.getHeight(), 1, 1, 1, 1, 1,1);
        staticSpriteBatch4.submit();
        scene.attachChild(staticSpriteBatch4);

        //stage3
        final SpriteBatch staticSpriteBatch60 = new SpriteBatch(this.background_layer60, 1, activity.getVertexBufferObjectManager());
        staticSpriteBatch60.draw(backgroundTexture60, 0, 2560*14, backgroundTexture60.getWidth(), backgroundTexture60.getHeight(), 1, 1, 1, 1, 1, 1);
        staticSpriteBatch60.submit();
        scene.attachChild(staticSpriteBatch60);

        final SpriteBatch staticSpriteBatch5 = new SpriteBatch(this.background_layer5, 3, activity.getVertexBufferObjectManager());
        staticSpriteBatch5.draw(backgroundTexture5, 0, 2560*16, backgroundTexture5.getWidth(), backgroundTexture5.getHeight(), 1, 1, 1, 1, 1, 1);
        staticSpriteBatch5.draw(backgroundTexture5, 0, 2560*18, backgroundTexture5.getWidth(), backgroundTexture5.getHeight(), 1, 1, 1, 1, 1,1);
        staticSpriteBatch5.draw(backgroundTexture5, 0, 2560*20, backgroundTexture5.getWidth(), backgroundTexture5.getHeight(), 1, 1, 1, 1, 1,1);
        staticSpriteBatch5.submit();
        scene.attachChild(staticSpriteBatch5);

        final SpriteBatch staticSpriteBatch6 = new SpriteBatch(this.background_layer6, 2, activity.getVertexBufferObjectManager());
        staticSpriteBatch6.draw(backgroundTexture6, 0, 2560*17, backgroundTexture6.getWidth(), backgroundTexture6.getHeight(), 1, 1, 1, 1, 1, 1);
        staticSpriteBatch6.draw(backgroundTexture6, 0, 2560*19, backgroundTexture6.getWidth(), backgroundTexture6.getHeight(), 1, 1, 1, 1, 1,1);
        staticSpriteBatch6.submit();
        scene.attachChild(staticSpriteBatch6);

        //stage4
        final SpriteBatch staticSpriteBatch90 = new SpriteBatch(this.background_layer90, 1, activity.getVertexBufferObjectManager());
        staticSpriteBatch90.draw(backgroundTexture90, 0, 2560*21, backgroundTexture90.getWidth(), backgroundTexture90.getHeight(), 1, 1, 1, 1, 1, 1);
        staticSpriteBatch90.submit();
        scene.attachChild(staticSpriteBatch90);

        final SpriteBatch staticSpriteBatch7 = new SpriteBatch(this.background_layer7, 3, activity.getVertexBufferObjectManager());
        staticSpriteBatch7.draw(backgroundTexture7, 0, 2560*23, backgroundTexture7.getWidth(), backgroundTexture7.getHeight(), 1, 1, 1, 1, 1, 1);
        staticSpriteBatch7.draw(backgroundTexture7, 0, 2560*25, backgroundTexture7.getWidth(), backgroundTexture7.getHeight(), 1, 1, 1, 1, 1,1);
        staticSpriteBatch7.draw(backgroundTexture7, 0, 2560*27, backgroundTexture7.getWidth(), backgroundTexture7.getHeight(), 1, 1, 1, 1, 1,1);
        staticSpriteBatch7.submit();
        scene.attachChild(staticSpriteBatch7);

        final SpriteBatch staticSpriteBatch8 = new SpriteBatch(this.background_layer8, 2, activity.getVertexBufferObjectManager());
        staticSpriteBatch8.draw(backgroundTexture8, 0, 2560*24, backgroundTexture8.getWidth(), backgroundTexture8.getHeight(), 1, 1, 1, 1, 1, 1);
        staticSpriteBatch8.draw(backgroundTexture8, 0, 2560*26, backgroundTexture8.getWidth(), backgroundTexture8.getHeight(), 1, 1, 1, 1, 1,1);
        staticSpriteBatch8.submit();
        scene.attachChild(staticSpriteBatch8);



    }
    private void createPlayer(BaseGameActivity activity , final PhysicsWorld world, Scene scene, SmoothCamera camera){
        player = new Player(CAMERA_WIDTH/2,CAMERA_WIDTH/2+200, playerTexture, activity.getVertexBufferObjectManager());
        player.createRectUnit(world,scene,1,1);
//        scene.attachChild(player);
        camera.setChaseEntity(player);

        scene.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                if(toptooth!=null){
                    /*
                    주안 Q5 이빨 hud 차차 없어지게..!
                    아래 2560 이게 플레이어가 2560 위치를 넘어가면 toptooth 가 점점 위로 올라가는 형식이에여
                    응용해서 bottom 도 할수 있을거에여
                    * */
                    if(player.getY() >=2560){

                        toptooth.setPosition(toptooth.getX(),toptooth.getY()-1);
                        if(toptooth.getY()+toptooth.getHeight()<=0)
                            toptooth.setVisible(false);
                    }
                }
                //player.getBody().applyLinearImpulse(new Vector2(0, 30), player.getBody().getPosition());
            }

            @Override
            public void reset() {

            }
        });
    }
    private void createObstacle(BaseGameActivity activity , PhysicsWorld world, Scene scene, Player p){
        for(int j=0;j<28;j++) {
            for (int i = 0; i < obstacle_types.get(j).length; i++) {
                switch (obstacle_types.get(j)[i]) {
                    case 1: //JOINT3
                        MapBuilder.create_3bar_Obstacle(scene, world, activity, jointTextureRegion1, jointTextureRegion2, obstacle_xs.get(j)[i], obstacle_ys.get(j)[i]);
                        break;
                    case 2: //JOINT2_작은거
                        MapBuilder.create_2bar_Obstacle(scene,world,activity,jointTextureRegion3,jointTextureRegion5,obstacle_xs.get(j)[i],obstacle_ys.get(j)[i]);
                        break;
                    case 3: //JOINT2_긴거
                        MapBuilder.create_2bar_Obstacle(scene,world,activity,jointTextureRegion3,jointTextureRegion6,obstacle_xs.get(j)[i],obstacle_ys.get(j)[i]);
                        break;
                    case 4: //JOINT4
                        MapBuilder.create_4bar_Obstacle(scene,world,activity,jointTextureRegion3,jointTextureRegion5,obstacle_xs.get(j)[i],obstacle_ys.get(j)[i]);
                        break;
                    case 5: //SLOW1
                        MapBuilder.create_slow_Obstacle(scene,world,activity,slowTextureRegion1,obstacle_xs.get(j)[i],obstacle_ys.get(j)[i],obstacle_angles.get(j)[i],p);
                        break;
                    case 6: //SLOW2
                        MapBuilder.create_slow_Obstacle(scene,world,activity,slowTextureRegion2,obstacle_xs.get(j)[i],obstacle_ys.get(j)[i],obstacle_angles.get(j)[i],p);
                        break;
                    case 7: //SLOW3
                        MapBuilder.create_slow_Obstacle(scene,world,activity,slowTextureRegion3,obstacle_xs.get(j)[i],obstacle_ys.get(j)[i],obstacle_angles.get(j)[i],p);
                        break;
                    case 8: //ITEM22
                        MapBuilder.create_item1_Obstacle(scene,world,activity,itemTextureRegion,obstacle_xs.get(j)[i],obstacle_ys.get(j)[i]);
                        break;
                    case 9: //ITEM1
                        MapBuilder.create_item2_Obstacle(scene,world,activity,itemTextureRegion2,obstacle_xs.get(j)[i],obstacle_ys.get(j)[i],obstacle_angles.get(j)[i]);
                        break;
                    case 10:  //ITEM2
                        MapBuilder.create_item3_Obstacle(scene,world,activity,itemTextureRegion3,obstacle_xs.get(j)[i],obstacle_ys.get(j)[i],obstacle_angles.get(j)[i]);
                        break;
                    case 11: //SLIDE
                        MapBuilder.create_slide_Obstacle(scene,world,activity,slideTextureRegion,obstacle_xs.get(j)[i],obstacle_ys.get(j)[i],obstacle_angles.get(j)[i]);
                        break;
                    case 12: //SLIDE
                        MapBuilder.create_slide_Obstacle(scene,world,activity,slideTextureRegion2,obstacle_xs.get(j)[i],obstacle_ys.get(j)[i],obstacle_angles.get(j)[i]);
                        break;
                    case 13 : //ITEM3
                        MapBuilder.create_item3_Obstacle(scene,world,activity,itemTextureRegion4,obstacle_xs.get(j)[i],obstacle_ys.get(j)[i],obstacle_angles.get(j)[i]);
                        break;
                }
            }
        }

    }


    private void createGerm(BaseGameActivity activity , PhysicsWorld world, Scene scene,Player p){
        for(int i=0;i<28;i++){
            MapBuilder.create_germs(scene,world,activity,germTexture,germs_xs.get(i),germs_ys.get(i), p);
        }

    }
    private void createWalls(BaseGameActivity activity, PhysicsWorld physicsWorld,Scene scene){

        final FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(1.0F ,1.0F, 1F);
        fixtureDef.filter.maskBits=ConstantsSet.WALL_MASK_BITS;
        fixtureDef.filter.categoryBits=ConstantsSet.WALL_CATG_BITS;
        Rectangle ground_right = new Rectangle(CAMERA_WIDTH,0,0,CAMERA_HEIGHT*100,activity.getVertexBufferObjectManager());
        Rectangle ground_left = new Rectangle(0,0,0,CAMERA_HEIGHT*100,activity.getVertexBufferObjectManager());
        PhysicsFactory.createBoxBody(physicsWorld,ground_right, BodyDef.BodyType.StaticBody,fixtureDef);
        scene.attachChild(ground_right);
        PhysicsFactory.createBoxBody(physicsWorld,ground_left, BodyDef.BodyType.StaticBody,fixtureDef);
        scene.attachChild(ground_left);
    }


    private void setupPhysics(PhysicsWorld physicsWorld){
        physicsWorld.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Body bodyA = fixtureA.getBody();
                Object oa = bodyA.getUserData();

                Fixture fixtureB = contact.getFixtureB();
                Body bodyB = fixtureB.getBody();
                Object ob = bodyB.getUserData();

                if(oa!=null && ob!=null){
                    int typeA = ((EData)oa).getType();
                    int typeB = ((EData)ob).getType();
                    if(typeA==EData.TYPE_GERM && typeB == EData.TYPE_PLAYER){
                        ((EData)oa).setShould_remove();
                    }else if(typeB==EData.TYPE_GERM && typeA == EData.TYPE_PLAYER){
                        ((EData)ob).setShould_remove();
                    }

                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

}