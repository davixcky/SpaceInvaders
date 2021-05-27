package com.uninorte.base.game.states;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Text;
import com.uninorte.base.game.ui.StaticElements;
import com.uninorte.base.game.ui.UIButton;
import com.uninorte.base.game.ui.UIObject;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Random;

public class MainState extends State {

    private int red, green, blue;
    private int opacity = 0;
    private boolean increase = true;
    private Random random;

    private int y;

    public MainState(Handler handler) {
        super(handler);

        random = new Random();
        y = handler.boardDimensions().height / 2;
        red = green = blue = 255;
    }

    @Override
    protected void initComponents() {
        int x = (int) (handler.boardDimensions().width * 0.5f - 128 / 2);
        int y = handler.boardDimensions().height / 2 + 101;

        UIButton singlePlayerBtn = StaticElements.singleplayerBtnn(this, handler, x, y);
        singlePlayerBtn.setSize(new Dimension(105, 40));

        UIButton multiplayerBtn = StaticElements.multiplayerBtn(this, handler, x, UIButton.getHeightRelative(singlePlayerBtn));
        multiplayerBtn.setSize(new Dimension(105, 40));

        UIButton settingsBtn = StaticElements.settingsBtn(this, handler, x, UIButton.getHeightRelative(multiplayerBtn));

        UIButton sourceCodeBtn = new UIButton(this, x, UIObject.getHeightRelative(settingsBtn), UIButton.btnImage, () -> {
            try {
                Desktop.getDesktop().browse(URI.create("https://github.com/Norte-invaders/MenuSonido"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sourceCodeBtn.setText("CODEBASE");
        sourceCodeBtn.setHover(UIButton.btnHoverImager, "OPEN GITHUB");

        UIButton exitBtn = StaticElements.exitBtn(this, handler, x, UIButton.getHeightRelative(sourceCodeBtn));

        uiManager.addObjects(singlePlayerBtn, multiplayerBtn, settingsBtn, sourceCodeBtn, exitBtn);
    }

    @Override
    public void update() {
        opacity += increase ? 1 : -1;
        if (opacity >= 255) {
            increase = false;
            red = random.nextInt(256);
            green = random.nextInt(256);
            blue = random.nextInt(256);
            opacity = 240;
        } else if (opacity <= 0) {
            increase = true;
            red = random.nextInt(256);
            green = random.nextInt(256);
            blue = random.nextInt(256);
            opacity = 10;
        }
    }

    @Override
    public void render(Graphics g) {
        Color textColor = new Color(red, green, blue, opacity);

        Dimension titleSize = Text.drawString(g, "SPACE INVADERS",
                handler.boardDimensions().width / 2,
                y,
                true,
                textColor,
                Assets.getFont(Assets.FontsName.SPACE_MISSION, 100));

        Text.drawString(g, "Tabata Llach, Alan Florez & David Orozco",
                handler.boardDimensions().width / 2,
                y + titleSize.height - 60,
                true,
                Color.white,
                Assets.getFont(Assets.FontsName.SLKSCR, 30));

        uiManager.render(g);
    }
}
