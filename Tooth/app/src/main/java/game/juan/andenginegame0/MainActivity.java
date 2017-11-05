package game.juan.andenginegame0;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.ui.activity.BaseGameActivity;

public class MainActivity extends BaseGameActivity {

    private SmoothCamera mCamera;
    private Scene scene;

    private static final int CAMERA_WIDTH = 1600;
    private static final int CAMERA_HEIGHT = 2560;

    Stage stage;


    PhysicsWorld physicsWorld;

    @Override
    public EngineOptions onCreateEngineOptions() {

        final float maxVelocityX = 0f;
        final float maxVelocityY = 3000f;
        final float maxZoomFactoryChange = 0.5f;

        //Initialize camera
        mCamera = new SmoothCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT,
                maxVelocityX, maxVelocityY, maxZoomFactoryChange);
        EngineOptions engineOptions = new EngineOptions(true
                , ScreenOrientation.PORTRAIT_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
                mCamera);
        mCamera.setCenter(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2);

        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback)
            throws Exception {

        stage = new Stage();
        stage.load(1,this);

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
        Log.e("COMP!!!", "G start");
        this.scene = new Scene();
        this.scene.setBackground(new Background(0.188f, 0.173f, 0.173f));
        physicsWorld = new PhysicsWorld(new Vector2(0, 0), false);

        this.scene.registerUpdateHandler(physicsWorld);
        stage.create(this,physicsWorld,scene,mCamera);
        pOnCreateSceneCallback.onCreateSceneFinished(this.scene);

    }



    @Override
    public void onPopulateScene(Scene pScene,
                                OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }



}