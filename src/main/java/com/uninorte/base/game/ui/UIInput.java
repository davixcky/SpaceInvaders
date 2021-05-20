package com.uninorte.base.game.ui;

import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Text;
import com.uninorte.base.game.states.State;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class UIInput extends UIObject {

    private boolean enterPressed;
    private StringBuilder text;
    private ClickListener listener;
    private int minChar, maxChar;

    public UIInput(State parent, float x, float y) {
        super(parent, x, y, UIButton.btnImage.getWidth(), UIButton.btnImage.getHeight());

        enterPressed = false;
        text = new StringBuilder();

        minChar = 4;
        maxChar = 10;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(UIButton.btnImage, (int) x, (int) y, null);
        Text.drawString(g, text.toString(),
                (int) x + width / 2,
                (int) y + 11,
                true,
                Color.white,
                Assets.getFont(Assets.FontsName.SLKSCR, 13));
    }

    @Override
    public void onClick() {
        enterPressed = false;
    }

    @Override
    public void onMouseChanged(MouseEvent e) {
        Rectangle newBounds = new Rectangle(bounds.x - 20, bounds.y - 20, bounds.width + 40, bounds.height + 40);
        hovering = newBounds.contains(e.getX(), e.getY());
    }

    @Override
    public void onObjectDragged(MouseEvent e) {
    }

    @Override
    public void onObjectKeyPressed(KeyEvent e) {
        if (!enterPressed) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_BACK_SPACE -> {
                    deleteLastChar();
                    return;
                }
                case KeyEvent.VK_ENTER -> {
                    enterPressed();
                    return;
                }
            }

            if (text.length() < maxChar)
                text.append(e.getKeyChar());
        }
    }

    private void deleteLastChar() {
        if (text.length() > 0) {
            text.deleteCharAt(text.length() - 1);
        }
    }

    private void enterPressed() {
        if (text.length() < minChar)
            return;

        if (listener != null)
            listener.onClick();
        enterPressed = true;
    }

    public String getText() {
        return text.toString();
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }
}
