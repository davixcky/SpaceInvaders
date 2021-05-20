package com.uninorte.base.game.ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import com.uninorte.base.game.Handler;

public class UIManager {

	private Handler handler;
	private ArrayList<UIObject> objects;

	private UIObject focusedElement = null;

	public UIManager(Handler handler){
		this.handler = handler;
		objects = new ArrayList<UIObject>();
	}
	
	public void update(){
		for(UIObject o : objects)
			o.update();
	}
	
	public void render(Graphics g){
		for(UIObject o : objects)
			o.render(g);
	}

	public void onMousePressed(MouseEvent e) {
		for(UIObject o : objects)
			o.onMousePressed(e);
	}
	
	public void onMouseMoved(MouseEvent e){
		for(UIObject o : objects)
			o.onMouseMove(e);
	}
	
	public void onMouseReleased(MouseEvent e){
		for(UIObject o : objects)
			o.onMouseRelease(e);
	}

	public void onMouseDragged(MouseEvent e) {
		for (UIObject o: objects)
			o.onMouseDragged(e);
	}

	public void onKeyPressed(KeyEvent e) {
		for (UIObject o: objects)
			o.onKeyPressed(e);
	}
	
	public void addObjects(UIObject ...objects){
		this.objects.addAll(Arrays.asList(objects));
	}

	public void removeObject(UIObject o){
		objects.remove(o);
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<UIObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<UIObject> objects) {
		this.objects = objects;
	}

	public UIObject getFocusedElement() {
		return focusedElement;
	}

	public void setFocusedElement(UIObject focusedElement) {
		this.focusedElement = focusedElement;
	}
}
