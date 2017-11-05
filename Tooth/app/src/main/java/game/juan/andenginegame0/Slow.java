package game.juan.andenginegame0;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by GP62 on 2017-10-24.
 */

public class Slow extends Sprite{

    Player player;
    /*주안 Q1. Slow 부분
     - 이걸로 느려지는 속도 조절 , 아래 slowSpeed = 2 에서 2 숫자 바꿔서 원하는 느려지는 속조로 해보세요
     */
    final float slowSpeed = 5;

    public Slow(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
    }
    public void setPlayer(Player p){
        this.player = p;
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        if(this.contains(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2)){
            player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x,slowSpeed);

        }
    }
}