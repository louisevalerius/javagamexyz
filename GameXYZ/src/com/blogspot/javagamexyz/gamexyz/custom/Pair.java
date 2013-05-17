package com.blogspot.javagamexyz.gamexyz.custom;

public class Pair {
	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int x, y;
	
	public boolean equals(Object obj) {
		if (obj instanceof Pair) return equals((Pair)obj);
		return false;
	}
	
	public boolean equals(Pair p) {
		if (x == p.x && y == p.y) return true;
		return false;
	}
	
	public String toString() {
		return "("+x+","+y+")";
	}
}
