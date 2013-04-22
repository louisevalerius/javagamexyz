package com.blogspot.javagamexyz.gamexyz.components;

import com.artemis.Component;

public class FadingMessage extends Component {

	public String label;
	public float duration, currentTime;
	public float vx, vy;
	
	public FadingMessage(String label, float duration) {
		this(label,duration,0,0);
	}
	
	public FadingMessage(String label, float duration, float vx, float vy) {
		this.label = label;
		this.duration = duration;
		this.vx = vx;
		this.vy = vy;
		currentTime = 0f;
	}
	
}
