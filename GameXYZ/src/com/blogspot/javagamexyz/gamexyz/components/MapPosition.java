package com.blogspot.javagamexyz.gamexyz.components;

import com.artemis.Component;

public class MapPosition extends Component {
	
	public MapPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public MapPosition() {
		this(0,0);
	}
	
	public MapPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float x, y;	
}
