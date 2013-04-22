package com.blogspot.javagamexyz.gamexyz.components;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;

public class Abilities extends Component {
	
	public boolean attack;
	
	public Abilities() {
		if (MathUtils.random(0,2) == 1) attack = true;
		else attack = false;
	}

}
