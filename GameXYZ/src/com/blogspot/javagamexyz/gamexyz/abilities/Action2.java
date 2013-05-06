package com.blogspot.javagamexyz.gamexyz.abilities;

import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.blogspot.javagamexyz.gamexyz.components.Damage;
import com.blogspot.javagamexyz.gamexyz.components.Stats;
import com.blogspot.javagamexyz.gamexyz.custom.Pair;
import com.blogspot.javagamexyz.gamexyz.maps.MapTools;

public class Action2 {
	
	public Action2(int strength, int mpCost, int baseProbability, int range, int field,
			ActionProcessor actionProcessor,
			ScoreCalculator scoreCalculator,
			FieldCalculator fieldCalculator,
			RangeCalculator rangeCalculator) {
		this.strength = strength;
		this.baseProbability = baseProbability;
		this.mpCost = mpCost;
		this.range = range;
		this.field = field;
		this.actionProcessor = actionProcessor;
		this.scoreCalculator = scoreCalculator;
		this.fieldCalculator = fieldCalculator;
		this.rangeCalculator = rangeCalculator;
	}
	
	public Action2(int strength, int mpCost, int baseProbability, int range, int field) {
		this.mpCost = mpCost;
		this.range = range;
		this.field = field;
		this.strength = strength;
		this.baseProbability = baseProbability;
		
		actionProcessor = new ActionProcessor() {
			@Override
			public void process(Entity sourceE, Array<Entity> targets, Action2 action) {
				Stats source = sourceE.getComponent(Stats.class);
				source.magic -= action.mpCost;
				boolean hitOnce = false;
				
				for (Entity targetE : targets) {
					Stats target = targetE.getComponent(Stats.class);
					int damage = 0;
					int probability = action.baseProbability; // +source.agility() - target.agility() +blah blah blah...
					if (MathUtils.random(100) < probability) { //HIT
						if (!hitOnce) {
							source.xp += 10;
							hitOnce = true;
						}
						damage = (int)(MathUtils.random(0.8f,1.2f)*(action.strength + source.getStrength() - target.getHardiness()));
						if (damage < 1) damage = 1;
					}

					targetE.addComponent(new Damage(damage));
					targetE.changedInWorld();
				}
			}
		};
		
		scoreCalculator = new ScoreCalculator() {
			@Override
			public ImmutableBag<Float> calculateScore(Stats source, ImmutableBag<Stats> targets, Action2 action) {				
				// If we can't even cast it, then don't bother
				int MP = source.magic;
				if (action.mpCost > MP) return null;
				
				Bag<Float> scoreBag = new Bag<Float>();
				
				// Get the cost to the source
				scoreBag.add(0.1f*(float)action.mpCost / (float)MP);
				
				// Get the scores for each target
				for (int i = 0; i < targets.size(); i++) {
					Stats target = targets.get(i);
					int HP = target.health;
					int damage = (int)(MathUtils.random(0.8f,1.2f)*(action.strength + source.getStrength() - target.getHardiness()));
					if (damage < 1) damage = 1;
					scoreBag.add((float)action.baseProbability/100f * (float)Math.min(damage, HP) / (float)HP);
				}
				
				return scoreBag;
			}
		};
		
		fieldCalculator = new FieldCalculator() {
			@Override
			public Array<Pair> getField(Pair target, Action2 action) {
				Array<Pair> field = MapTools.getNeighbors(target.x, target.y, action.field-1);
				field.add(target);
				return field;
			}
		};
		
		rangeCalculator = new RangeCalculator() {
			@Override
			public Array<Pair> getRange(Pair source, Action2 action) {
				return MapTools.getNeighbors(source.x, source.y, action.range);
			}
		};
	}
	
	public int mpCost, range, field, baseProbability, strength;
	
	public ActionProcessor actionProcessor;
	public ScoreCalculator scoreCalculator;
	public FieldCalculator fieldCalculator;
	public RangeCalculator rangeCalculator;
	
	public interface ActionProcessor {
		public void process(Entity source, Array<Entity> targets, Action2 action);
	}
	
	public interface ScoreCalculator {
		public ImmutableBag<Float> calculateScore(Stats source, ImmutableBag<Stats> target, Action2 action);
	}
	
	public interface FieldCalculator {
		public Array<Pair> getField(Pair target, Action2 action);
	}
	
	public interface RangeCalculator {
		public Array<Pair> getRange(Pair source, Action2 action);
	}
}
