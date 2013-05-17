package com.blogspot.javagamexyz.gamexyz.abilities;

import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.blogspot.javagamexyz.gamexyz.abilities.Action2.ActionProcessor;
import com.blogspot.javagamexyz.gamexyz.abilities.Action2.FieldCalculator;
import com.blogspot.javagamexyz.gamexyz.abilities.Action2.RangeCalculator;
import com.blogspot.javagamexyz.gamexyz.abilities.Action2.ScoreCalculator;
import com.blogspot.javagamexyz.gamexyz.components.Damage;
import com.blogspot.javagamexyz.gamexyz.components.Stats;
import com.blogspot.javagamexyz.gamexyz.custom.Pair;
import com.blogspot.javagamexyz.gamexyz.maps.MapTools;
import com.blogspot.javagamexyz.gamexyz.utils.MyMath;

public class ActionFactory {

	//public Action3(int strength, int mpCost, int baseProbability, int range, int field)
	
	public static Action2 physicalAttack(String name, int strength, int baseProbability, int range) {
		return new Action2(name,strength, 0, baseProbability, range, 1);
	}
	
	public static Action2 spinAttack(String name, int strength, int baseProbability) {
		Action2 action = new Action2(name, strength, 0, baseProbability, 0, -1);
		action.fieldCalculator = new FieldCalculator() {
			@Override
			public Array<Pair> getField(Pair target, Action2 action) {
				Array<Pair> field = MapTools.getNeighbors(target.x, target.y);
				return field;
			}
		};
		
		action.rangeCalculator = spellRangeCalculator();
		return action;
	}
	
	public static Action2 magicAttack(String name, int strength, int mpCost, int baseProbability, int range, int field) {
		return new Action2(name, strength, mpCost, baseProbability, range, field,
				magicAttackActionProcessor(),
				magicAttackScoreCalculator(),
				spellFieldCalculator(),
				spellRangeCalculator());
	}
	
	public static Action2 cure(String name, int strength, int mpCost, int range, int field) {
		return new Action2(name, strength, mpCost, 100, range, field,
				cureActionProcessor(),
				cureScoreCalculator(),
				spellFieldCalculator(),
				spellRangeCalculator());
	}
	
	// Cure stuff:
	private static ActionProcessor cureActionProcessor() {
		ActionProcessor ap = new ActionProcessor() {
			
			@Override
			public void process(Entity sourceE, Array<Entity> targets, Action2 action) {
				Stats source = sourceE.getComponent(Stats.class);
				source.magic -= action.mpCost;
				source.xp += 10;
				
				int cureAmt;
				for (Entity targetE : targets) {
					cureAmt = (int)(MathUtils.random(0.8f,1.2f)*(action.strength + source.getIntelligence()));
					if (cureAmt < 1) cureAmt = 1;

					targetE.addComponent(new Damage(-1*cureAmt));
					targetE.changedInWorld();
				}
			}

		};
		return ap;
	}
	
	private static ScoreCalculator cureScoreCalculator() {
		ScoreCalculator sc = new ScoreCalculator() {
			
			@Override
			public ImmutableBag<Float> calculateScore(Stats source, ImmutableBag<Stats> targets, Action2 action) {
				if (action.mpCost > source.magic) return null;
				
				Bag<Float> scoreBag = new Bag<Float>();
				// Get cost for source
				scoreBag.add(0.1f*(float)action.mpCost / (float)source.magic);
				
				int cureAmt;
				// Get score for each target
				for (int i = 0; i < targets.size(); i++) {
					Stats target = targets.get(i);
					cureAmt = (int)(action.strength + source.getIntelligence());
					float curePercent = (float)MyMath.min(cureAmt, target.maxHealth - target.health) / (float)target.maxHealth;
					scoreBag.add(-1f*MathUtils.sinDeg(90*curePercent));
				}
				
				return scoreBag;
			}
		};
		return sc;
	}
	
	private static ActionProcessor magicAttackActionProcessor() {
		ActionProcessor ap = new ActionProcessor() {
			
			@Override
			public void process(Entity sourceE, Array<Entity> targets, Action2 action) {
				Stats source = sourceE.getComponent(Stats.class);
				source.magic -= action.mpCost;
				source.xp += 10;
				
				int dmgAmt;
				for (Entity targetE : targets) {
					Stats target = targetE.getComponent(Stats.class);
					dmgAmt = (int)(MathUtils.random(0.8f,1.2f)*(action.strength + source.getStrength() - target.getHardiness()));
					if (dmgAmt < 1) dmgAmt = 1;

					targetE.addComponent(new Damage(dmgAmt));
					targetE.changedInWorld();
				}
			}

		};
		return ap;
	}
	
	private static ScoreCalculator magicAttackScoreCalculator() {
		ScoreCalculator sc = new ScoreCalculator() {
			
			@Override
			public ImmutableBag<Float> calculateScore(Stats source, ImmutableBag<Stats> targets, Action2 action) {
				if (action.mpCost > source.magic) return null;
				
				Bag<Float> scoreBag = new Bag<Float>();
				// Get cost for source
				scoreBag.add(0.1f*(float)action.mpCost / (float)source.magic);
				
				// Get score for each target
				for (int i = 0; i < targets.size(); i++) {
					Stats target = targets.get(i);
					int dmgAmt = (int)(action.strength + source.getStrength() - target.getHardiness());
					if (dmgAmt < 1) dmgAmt = 1;
					float dmgPercent = (float)MyMath.min(dmgAmt, target.maxHealth - target.health) / (float)target.health;
					scoreBag.add(dmgPercent);
				}
				
				return scoreBag;
			}
		};
		return sc;
	}
	
	private static FieldCalculator spellFieldCalculator() {
		FieldCalculator fc = new FieldCalculator() {
			
			@Override
			public Array<Pair> getField(Pair target, Action2 action) {
				Array<Pair> field = MapTools.getNeighbors(target.x, target.y, action.field - 1);
				field.add(target);
				return field;
			}
		};
		return fc;
	}
	
	private static RangeCalculator spellRangeCalculator() {
		RangeCalculator rc = new RangeCalculator() {
			
			@Override
			public Array<Pair> getRange(Pair source, Action2 action) {
				Array<Pair> range = MapTools.getNeighbors(source.x, source.y, action.range);
				range.add(source);
				return range;
			}
		};
		return rc;
	}
}
