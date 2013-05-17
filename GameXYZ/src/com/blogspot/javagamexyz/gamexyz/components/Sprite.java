package com.blogspot.javagamexyz.gamexyz.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sprite extends Component {
	
	public enum Layer {
		DEFAULT,
		BACKGROUND,
		ACTORS_1,
		ACTORS_2,
		ACTORS_3,
		PARTICLES;
		
		public int getLayerId() {
			return ordinal();
		}
	}
	
	public Sprite(String name) {
		this.name = name;
		this.layer = Layer.DEFAULT;
		this.r = 1f;
		this.g = 1f;
		this.b = 1f;
		this.a = 1f;
		this.scaleX = 1f;
		this.scaleY = 1f;
		this.rotation = 0f;
		index = 0;
	}
	
	public TextureRegion region;
	public String name;
	public float r,g,b,a,scaleX,scaleY,rotation;
	public int x, y, width, height;
	public Layer layer;
	public int index;
}
