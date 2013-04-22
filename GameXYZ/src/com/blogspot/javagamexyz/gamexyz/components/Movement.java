package com.blogspot.javagamexyz.gamexyz.components;

import com.artemis.Component;
import com.blogspot.javagamexyz.gamexyz.pathfinding.Path;

public class Movement extends Component {
	
	public Path path;
	public float elapsedTime;
	
	public Movement(Path path) {
		this.path = path;
		elapsedTime = 0;
	}

}
