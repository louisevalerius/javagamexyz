package com.blogspot.javagamexyz.gamexyz.maps;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.blogspot.javagamexyz.gamexyz.custom.FloatPair;
import com.blogspot.javagamexyz.gamexyz.custom.Pair;
import com.blogspot.javagamexyz.gamexyz.utils.MyMath;

public class MapTools {
	
	public static final int col_multiple = 34;
	public static final int row_multiple = 38;
	public static final String name = "hex";

	public static Array<Pair> getNeighbors(int x, int y, int n) {
		Array<Pair> coordinates = new Array<Pair>();
		int min;
		int myrow;
		for (int row = y-n; row<y+n+1; row++) {
			min = MyMath.min(2*(row-y+n), n, -2*(row-y-n)+1);
			for (int col = x-min; col < x+min+1; col++) {
				if ((col < 0) || (col >= width())) continue;
				if (x==col && y==row) continue;
				else if (x % 2 == 0) myrow = 2*y-row;
				else myrow = row;
				if ((myrow < 0) || (myrow >= height())) continue;
				coordinates.add(new Pair(col,myrow));
			}
		}
		return coordinates;
	}
	
	public static Array<Pair> getNeighbors(int x, int y) {
		return getNeighbors(x,y,1);
	}
	
	public static int distance(int x0, int y0, int x1, int y1) {
		int dx = Math.abs(x1-x0);
		int dy = Math.abs(y1-y0);
		
		// The distance can be tricky, because of how the columns are shifted.
		// Different cases must be considered, because the dx and dy above
		// are not sufficient to determine distance.
		
		if ((dx)%2 == 0) {		// distance from even->even or odd->odd column
								// important to know since evens and odds are offset
			return MyMath.max(dx,dx/2 + dy);
		}
		
		// Otherwise the distance must be even->odd
		else if (((x0 % 2 == 0) && (y0 > y1)) || ((x1 % 2 == 0) && (y1 > y0))) { // even on top
			return MyMath.max(dx, (dx-1)/2 + dy);
		}
		// otherwise odd must be on top
		return MyMath.max(dx,  (dx+1)/2 + dy);
	}
	
	public static Pair window2world(float x, float y, OrthographicCamera camera) {
		Vector3 pos = new Vector3(x,y,0);
		camera.unproject(pos);
		int posx = (int)((pos.x - 6f) / (float)col_multiple);
		int posy = (int)((pos.y - (float)row_multiple*(posx%2)/2) / (float)row_multiple);
		return new Pair(posx,posy);
	}
	
	public static FloatPair world2window(int x, int y) {
		float posX= 6f + (x + 0.5f)*34;
		float posY= 38*(0.5f + 0.5f*(x%2) + y);
		
		return new FloatPair(posX, posY);
	}
	
	public static int width() {
		int power = MyMath.pow(2,HexMapGenerator.n);
		return HexMapGenerator.wmult*power + 1;
	}
	
	public static int height() {
		int power = MyMath.pow(2,HexMapGenerator.n);
		return HexMapGenerator.hmult*power+1;
	}
}

