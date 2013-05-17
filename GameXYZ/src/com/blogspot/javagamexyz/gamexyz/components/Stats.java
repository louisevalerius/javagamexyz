package com.blogspot.javagamexyz.gamexyz.components;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;
import com.blogspot.javagamexyz.gamexyz.abilities.Action2;
import com.blogspot.javagamexyz.gamexyz.abilities.CharacterClassFactory;
import com.blogspot.javagamexyz.gamexyz.abilities.CharacterClassFactory.CharacterClass;


public class Stats extends Component {
	
	public int level, xp, next_xp;
	
	private int strength, strength_modifier;
	private int intelligence, intelligence_modifier;
	private int speed, speed_modifier;
	private int agility, agility_modifier;
	private int charisma, charisma_modifier;
	private int hardiness, hardiness_modifier;
	private int resistance, resistance_modifier;
	
	public int health, maxHealth, maxHealth_modifier;
	public int magic, maxMagic, maxMagic_modifier;
	
	public String name;
	
	public int actionPoints;
	
	public Action2 regularAttack;
	public CharacterClass primaryClass, secondaryClass;
	
	public Stats(boolean b) {
		this();
		actionPoints = 100;
	}
	
	public Stats() {
		level = 1;
		xp = 0;
		next_xp = 100;
		
		strength = 15 + MathUtils.random(-3, 3);
		intelligence = 15 + MathUtils.random(-3, 3);
		speed = 15 + MathUtils.random(-3, 3);
		agility = 15 + MathUtils.random(-3, 3);
		charisma = 15 + MathUtils.random(-3, 3);
		hardiness = 15 + MathUtils.random(-3, 3);
		resistance = 15 + MathUtils.random(-3, 3);
		
		health = maxHealth = (int)((5*hardiness + 4*strength + 2*resistance) / 11);
		health = maxHealth/2; // Start people off injured!
		magic = maxMagic = (int)((5*intelligence + 2*resistance) / 7);
		
		strength_modifier = intelligence_modifier = speed_modifier = agility_modifier = 
				charisma_modifier = hardiness_modifier = resistance_modifier = maxHealth_modifier =
				maxMagic_modifier = 0;
		
		name = names[MathUtils.random(names.length-1)];
		
		actionPoints = 0;
		
		regularAttack = new Action2("Attack", 7, 0, 90, 1, 1);
		
		primaryClass = CharacterClassFactory.knight();
		secondaryClass = CharacterClassFactory.healer();
		
	}
	
	private final String[] names = {"Rodann","Ranlan","Luhiri","Serl","Polm","Boray","Ostan","Inaes"};
	
	public int getAgility() {
		return agility + agility_modifier;
	}
	
	public int getHardiness() {
		return hardiness + hardiness_modifier;
	}
	
	public int getStrength() {
		return strength + strength_modifier;
	}
	
	public int getCharisma() {
		return charisma + charisma_modifier;
	}
	
	public int getIntelligence() {
		return intelligence + intelligence_modifier;
	}
	
	public int getResistance() {
		return resistance + resistance_modifier;
	}
	
	public int getSpeed() {
		return speed + speed_modifier;
	}
	
	public class Movable2 {
		public float energy;		// Roughly how far can you go?
		public float slowness;		// How many seconds it takes to slide from 1 tile to an adjacent
		
		public boolean[] terrainBlocked;
		public float[] terrainCost;
		
		public Movable2(float energy, float slowness) {
			this.energy = energy;
			this.slowness = slowness;
			
			terrainBlocked = new boolean[9];
			terrainCost = new float[9];
			
			for (int i = 0; i < terrainBlocked.length; i++) {
				terrainBlocked[i] = false;
				terrainCost[i] = 1.5f*Math.abs(i-4)+1;
			}
		}
	}
}
