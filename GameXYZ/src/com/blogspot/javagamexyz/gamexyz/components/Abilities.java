package com.blogspot.javagamexyz.gamexyz.components;

import com.artemis.Component;
import com.badlogic.gdx.utils.Array;
import com.blogspot.javagamexyz.gamexyz.abilities.Action2;

public class Abilities extends Component {
	
	public Array<Action2> actions;
	
	public Abilities() {
		actions = new Array<Action2>();
	}
	
	public Array<Action2> getActions() {
		return actions;
	}

}
