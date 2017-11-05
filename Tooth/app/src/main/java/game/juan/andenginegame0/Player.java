package game.juan.andenginegame0;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import game.juan.app.c;


class Player extends Sprite {
    private Body body;

    private int count;



    final Vector2 force = new Vector2(0,2000); //주안 - 이 값으로 플레이어 속도조절
    public Player(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
    }

    void createRectUnit(PhysicsWorld physicsWorld, Scene scene,
                        final float ratiox, final float ratioy){

        final FixtureDef FIX = PhysicsFactory.createFixtureDef(3.0F ,0.5F, 0.3F);
        FIX.filter.categoryBits = ConstantsSet.PLAYER_CATG_BITS;
        FIX.filter.maskBits = ConstantsSet.PLAYER_MASK_BITS;
        body = PhysicsFactory.createCircleBody(physicsWorld,
                getX() + (getWidth()*(1.0f-ratiox)/2.0f),
                getY() + (getHeight()*(1.0f-ratioy)),
                getWidth()/2.5f,
                BodyDef.BodyType.DynamicBody,FIX);
        body.setUserData(new EData(EData.TYPE_PLAYER));
        scene.attachChild(this);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(this,body,true,true));
    }
    Body getBody(){
        return this.body;
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

        String sensorData=c.str;



        System.out.println("SensorData : "+sensorData);


        body.applyForce(force,body.getWorldCenter());
        /* 주안 Q 10. 앞 설명 부분만 좀 느리게 간다는 등 속도 조절이 필요해요
            이런 식으로 추가하면 됩니다 , 숫자 2 부분 바꿔서 원하는 속도로 바꾸세요(아래 하나 해놨음)
           if(getY() >= 7000 && getY()<=8000){
            body.setLinearVelocity(body.getLinearVelocity().x,2);
             }
         */
        if(getY() >= 0 && getY()<=2560 *2){ //주안 -특정 섹션 지날때 느려지게 하려면 이런식으로 조건 넣으시구요 아래에 숫자 2 바꿔서 속도조절하세요
            if(sensorData.equals("1")) {
                System.out.println("Speed : 20");
                body.setLinearVelocity(body.getLinearVelocity().x, 20);
            }else if(sensorData.equals("3")){
                System.out.println("Speed : 600");
                body.setLinearVelocity(body.getLinearVelocity().x, 600);
            }
        }

        if(getY() >= 2560*7 && getY()<=2560*9){
            if(sensorData.equals("1")) {
                System.out.println("Speed : 20");
                body.setLinearVelocity(body.getLinearVelocity().x, 20);
            }else if(sensorData.equals("3")){
                System.out.println("Speed : 600");
                body.setLinearVelocity(body.getLinearVelocity().x, 600);
            }
        }
        if(getY() >= 2560*14 && getY()<=2560*16){
            if(sensorData.equals("1")) {
                System.out.println("Speed : 20");
                body.setLinearVelocity(body.getLinearVelocity().x, 20);
            }else if(sensorData.equals("3")){
                System.out.println("Speed : 600");
                body.setLinearVelocity(body.getLinearVelocity().x, 600);
            }
        }
        if(getY() >= 2560*21 && getY()<=2560*23){
            if(sensorData.equals("1")) {
                System.out.println("Speed : 20");
                body.setLinearVelocity(body.getLinearVelocity().x, 20);
            }else if(sensorData.equals("3")){
                System.out.println("Speed : 600");
                body.setLinearVelocity(body.getLinearVelocity().x, 600);
            }
        }


    }
}