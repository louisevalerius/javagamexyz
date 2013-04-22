package com.blogspot.javagamexyz.gamexyz.components;

import com.artemis.Component;

public class Damage extends Component {

	public int power;
	public int accuracy;
	public Stats stats;
	
	public Damage(Stats stats) {
		this.stats = stats;
		power = stats.getStrength();
		accuracy = stats.getAgility();
	}
}
