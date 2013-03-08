package com.blogspot.javagamexyz.gamexyz.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.blogspot.javagamexyz.gamexyz.custom.Animation;

public class SpriteAnimation extends Component {
	
	public Animation animation;
	public float stateTime;
	public float frameDuration;
	public int playMode;
	
	public TextureRegion getFrame() {
		return animation.getKeyFrame(stateTime);
	}
	
}
