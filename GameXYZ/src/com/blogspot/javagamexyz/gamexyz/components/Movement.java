package com.blogspot.javagamexyz.gamexyz.components;

import com.artemis.Component;
import com.blogspot.javagamexyz.gamexyz.maps.GameMap;
import com.blogspot.javagamexyz.gamexyz.pathfinding.Path;

public class Movement extends Component {
	
	public Path path;
	
	public Movement(int x0, int y0, int tx, int ty, GameMap gameMap) {
		path = gameMap.pathFinder.findPath(x0, y0, tx, ty);
	}

}
