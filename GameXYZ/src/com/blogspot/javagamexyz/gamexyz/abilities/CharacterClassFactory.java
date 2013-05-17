package com.blogspot.javagamexyz.gamexyz.abilities;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class CharacterClassFactory {
	
	private static CharacterClass knight_instance, archer_instance, wizard_instance, healer_instance;
	
	public static CharacterClass knight() {
		if (knight_instance == null) {
			knight_instance = new CharacterClass("Knight", "A badass sword user");
			knight_instance.actions.add(ActionFactory.spinAttack("Spin Attack", 6, 90));
			knight_instance.actions.add(ActionFactory.cure("First Aid", 2, 0, 0, 1));
			knight_instance.actions.add(ActionFactory.cure("First Aid", 2, 0, 0, 1));
			knight_instance.actions.add(ActionFactory.cure("First Aid", 2, 0, 0, 1));
			knight_instance.actions.add(ActionFactory.cure("First Aid", 2, 0, 0, 1));
		}
		return knight_instance;
	}
	
	public static CharacterClass archer() {
		if (archer_instance == null) {
			archer_instance = new CharacterClass("Archer", "Shoots stuff");
			archer_instance.actions.add(ActionFactory.physicalAttack("Long Range Shot", 6, 70, 5));
			archer_instance.actions.add(ActionFactory.magicAttack("Fire Arrow", 4, 0, 90, 3, 2));
		}
		return archer_instance;
	}
	
	public static CharacterClass wizard() {
		if (wizard_instance == null) {
			wizard_instance = new CharacterClass("Wizard", "Casts spells");
			wizard_instance.actions.add(ActionFactory.magicAttack("Magic Missle", 6, 3, 80, 4, 1));
			wizard_instance.actions.add(ActionFactory.magicAttack("Fireball", 10, 8, 90, 4, 2));
			wizard_instance.actions.add(ActionFactory.magicAttack("Explosion", 7, 5, 80, 4, 2));
		}
		return wizard_instance;
	}
	
	public static CharacterClass healer() {
		if (healer_instance == null) {
			healer_instance = new CharacterClass("Healer", "...heals?");
			healer_instance.actions.add(ActionFactory.cure("Heal", 5, 5, 4, 1));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
			healer_instance.actions.add(ActionFactory.cure("Group Heal", 5, 10, 4, 2));
		}
		return healer_instance;
	}

	public static class CharacterClass {
		// To become this class, you need certain level proficiency in these other classes...
		public ObjectMap<CharacterClass,Integer> requirements;
		
		// Each class has a list of actions they can learn, a name, and a description
		public Array<Action2> actions;
		public String name;
		public String description;
		
		public CharacterClass(String name, String description) {
			this.name = name;
			this.description = description;
			actions = new Array<Action2>();
			requirements = new ObjectMap<CharacterClass, Integer>();
		}
	}
}
