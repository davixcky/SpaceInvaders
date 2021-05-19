package com.uninorte.base.game.modes.multiplayer;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

public class Multiplayer  {

    private Stage stage;
    private JFXPanel jfxPanel;
    private JButton swingButton;
    private JPanel panel;
    private JSBridge[] jsBridges;
    public WebEngine engine;

    public Multiplayer() {

        panel = new JPanel();
        jsBridges = new JSBridge[3];
    }

    public void start() {
        final JFrame frame = new JFrame();

        // TODO: Close the browser when the state change

        initComponents();

        frame.getContentPane().add(panel);

        frame.setMinimumSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void initComponents(){

        jfxPanel = new JFXPanel();
        createScene();

        panel.setLayout(new BorderLayout());
        panel.add(jfxPanel, BorderLayout.CENTER);

        swingButton = new JButton();
        swingButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        System.out.println("hi");
                    }
                });
            }
        });
        swingButton.setText("Reload");

        panel.add(swingButton, BorderLayout.SOUTH);
    }

    private Accordion prepareAccordion(final Map<String, String> sites) {
        Accordion accordion = new Accordion();
        int index = 0;
        for (Map.Entry<String, String> webMap : sites.entrySet())
            accordion.getPanes().add(new TitledPane(webMap.getKey(), buildWebView(webMap.getValue(), index++)));
        return accordion;
    }


    private WebView buildWebView(final String url, int index) {
        WebView webView = new WebView();
        engine = webView.getEngine();

        // Add JSBridge as 'window.enigma' in JavaScript
        JSObject jsobj = (JSObject) engine.executeScript("window");
        jsBridges[index] = new JSBridge(engine);
        jsobj.setMember("enigma", jsBridges[index]);
        jsobj.setMember("testvalue", "12");

        engine.load(url);
        return webView;
    }

    private String fromLocalFile(String fpath) {
        String path = System.getProperty("user.dir");
        path.replace("\\\\", "/");
        path += ('/' + fpath);
        System.out.println("file:///" + path);
        return "file:///home/davidorozco/dev/space-invader/test_game/src/main/java/com/uninorte/base/game/modes/multiplayer/element.html";
    }

    /**
     * createScene
     *
     * Note: Key is that Scene needs to be created and run on "FX user thread"
     *       NOT on the AWT-EventQueue Thread
     *
     */
    private void createScene() {
        PlatformImpl.startup(new Runnable() {
            @Override
            public void run() {

                stage = new Stage();

                stage.setTitle("Hello Java FX");
                stage.setResizable(true);

                BorderPane root = new BorderPane();
                // Populate sites map, TreeMap is sorted
                Map<String, String> sites = new TreeMap<String, String>() {{
                    put("A Question!", "https://norte-invaders.github.io/MenuSonido/");
                }};


                Accordion sa = prepareAccordion(sites);

                // Set focus on first
                sa.setExpandedPane(sa.getPanes().get(0));

                //root.getChildren().add(prepareAccordion(sites));
                root.setCenter(sa);

                Scene scene = new Scene(root, 1000, 600, Color.WHITE);
                stage.setScene(scene);

                jfxPanel.setScene(scene);
            }
        });
    }

}

