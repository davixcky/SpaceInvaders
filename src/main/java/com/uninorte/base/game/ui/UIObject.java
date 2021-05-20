package com.uninorte.base.game.ui;

import com.uninorte.base.game.states.State;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class UIObject {

	protected State parent;
	protected float x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected boolean hovering = false;

	protected float distanceX, distanceY;
	protected boolean movement = false;

	public UIObject(State parent, float x, float y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.parent = parent;
		bounds = new Rectangle((int) x, (int) y, width, height);
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
	public abstract void onClick();

	public abstract void onMouseChanged(MouseEvent e);

	public abstract void onObjectDragged(MouseEvent e);

	public abstract void onObjectKeyPressed(KeyEvent e);

	public void onMousePressed(MouseEvent e) {
		hovering = bounds.contains(e.getX(), e.getY());

		if (hovering) {
			distanceX = e.getX() - bounds.x;
			distanceY = e.getY() - bounds.y;
			movement = true;

			parent.getUiManager().setFocusedElement(this);
		}
	}

	public void onMouseDragged(MouseEvent e) {
		if (hovering && movement && State.getCurrentState() == parent)
			onObjectDragged(e);
	}
	
	public void onMouseMove(MouseEvent e){
		hovering = bounds.contains(e.getX(), e.getY());
		if (hovering)
			onMouseChanged(e);
	}
	
	public void onMouseRelease(MouseEvent e){
		if (hovering && State.getCurrentState() == parent) {
			distanceX = distanceY = 0;
			movement = false;
			onClick();
		}
	}

	public void onKeyPressed(KeyEvent e) {
		if (parent.getUiManager().getFocusedElement() == this)
			onObjectKeyPressed(e);
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isHovering() {
		return hovering;
	}

	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}

}
