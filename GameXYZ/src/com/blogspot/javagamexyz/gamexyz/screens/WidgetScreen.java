package com.blogspot.javagamexyz.gamexyz.screens;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.blogspot.javagamexyz.gamexyz.GameXYZ;

public class WidgetScreen extends AbstractScreen {
	
	Dialog dialog;
	Stage stage;
	
	public WidgetScreen(GameXYZ game, SpriteBatch batch, World world) {
		super(game, world, batch);
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		FileHandle file = new FileHandle("resources/uiskin/uiskin.json"); 
		Skin skin = new Skin(file); 
		
		dialog = new Dialog("BlabberFuck", skin);
		stage.addActor(dialog);
		
		dialog.getContentTable().left();
		dialog.getContentTable().defaults().fillX().expandX();
		Label l = new Label("What what what what do you want with me honey?", skin);

		l.setWrap(true);
		dialog.text(l);
		dialog.setX(250);
		dialog.setY(150);
		dialog.getContentTable().row();
		//dialog.getContentTable().add(new Label("H?",skin));
//		dialog.getContentTable().add("WHAAAAH").expandX().fillX();
		l.setAlignment(Align.center);
		dialog.debug();
		dialog.getContentTable().debug();
		
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		stage.act(delta);
		stage.draw();
		Dialog.drawDebug(stage);
	}
}