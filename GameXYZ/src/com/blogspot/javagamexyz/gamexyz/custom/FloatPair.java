package com.blogspot.javagamexyz.gamexyz.custom;

public class FloatPair {
	public FloatPair(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float x,y;
	
	public boolean equals(Object obj) {
		if (obj instanceof FloatPair) return equals((FloatPair)obj);
		return false;
	}
	
	public boolean equals(FloatPair p) {
		return (x == p.x && y == p.y);
	}
	
	public String toString() {
		return "("+x+","+y+")";
	}
}
