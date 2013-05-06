package com.blogspot.javagamexyz.gamexyz.components;

import com.artemis.Component;
import com.blogspot.javagamexyz.gamexyz.AI.Plan;

public class AI extends Component {

	public Plan plan;
	public float timer;
	public boolean active;
	
	public boolean planDone;
	
	public void begin() {
		timer = 0;
		active = true;
		planDone = false;
	}
	
}
