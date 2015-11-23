package darkapi.gui.javafx;

import com.runemate.game.api.client.ClientUI;
import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.Stylesheet;
import darkapi.gui.javafx.component.FXGUIComponent;
import darkapi.gui.javafx.component.impl.*;
import darkapi.script.DarkScript;
import darkapi.script.utils.Logger;
import darkapi.script.utils.Settings;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class FXScriptGUI extends Stage {

    private double xOffset, yOffset;

    public FXScriptGUI(DarkScript script) {
        super(StageStyle.UNDECORATED);
        JFrame frame = ClientUI.getFrame();
        initStyle(StageStyle.TRANSPARENT);

        setOnCloseRequest(event -> script.stop());

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    String fileName = this.getClass().getResource("/darkapi/resources/style.css").toExternalForm();
                    Logger.log("Loading CSS file: " + fileName);
                    //StyleManager.getInstance().addUserAgentStylesheet(fileName);
                } catch (Exception e) {
                    System.out.println("Caught Exception: [" + e.getClass().getName() + "]\n PLEASE SCREENSHOT THIS AND SEND TO THE AUTHOR");
                    e.printStackTrace();
                }
            }
        });

        Platform.runLater(() -> {
            getIcons().add(SwingFXUtils.toFXImage((BufferedImage) frame.getIconImage(), null));
            setScene(init(script));
        });

    }

    public Scene init(DarkScript script) {
        final BorderPane borderPane = new BorderPane();

        final BorderPane topPane = new BorderPane();
        final Label titleLabel = new Label(script.getMetaData().getName());
        final ToggleButton runButton = new ToggleButton("Run");
        final Button stopButton = new Button("Stop");

        final Accordion accordion = new Accordion();

        titleLabel.setFont(new Font("Segoe UI Semibold", 34));
        titleLabel.setPadding(new Insets(5));


        runButton.setMaxWidth(Double.MAX_VALUE);
        runButton.setFont(new Font(runButton.getFont().getName(), 28));
        HBox.setHgrow(runButton, Priority.ALWAYS);
        runButton.setPadding(new Insets(5));
        runButton.setOnAction(event -> script.setIsRunning(runButton.isSelected()));

        stopButton.setFont(new Font(stopButton.getFont().getName(), 28));
        stopButton.setPadding(new Insets(5));
        stopButton.setOnAction(event -> script.stop());

        accordion.setCache(true);
        accordion.setCacheHint(CacheHint.SPEED);

        topPane.setCenter(titleLabel);

        borderPane.setTop(topPane);
        borderPane.setCenter(accordion);
        borderPane.setBottom(new HBox(runButton, stopButton));

        addItem(accordion, new FXFrameworkSettings(script));
        addItem(accordion, new FXScriptSettings(script));
        addItem(accordion, new FXInfoTracker(script));


        Scene scene = createScene(borderPane);
        for (String style : ClientUI.getScene().getStylesheets())
            scene.getStylesheets().addAll(style);

        Settings.setFxConsoleComponent(new FXConsoleComponent(script));

        return scene;

    }

    private Scene createScene(Pane root) {
        Scene scene = new Scene(root);
        scene.setFill(null);
        root.setMinWidth(600);
        root.setMinHeight(700);

        FadeTransition ft = new FadeTransition(Duration.millis(500), root);
        ft.setFromValue(0.0f);
        ft.setToValue(1.0f);
        ft.play();

        scene.setOnMousePressed(event -> {
            xOffset = getX() - event.getScreenX();
            yOffset = getY() - event.getScreenY();
        });

        scene.setOnMouseDragged(event -> {
            setX(event.getScreenX() + xOffset);
            setY(event.getScreenY() + yOffset);
        });
        return scene;
    }

    private static void addItem(Accordion accordion, String title, FXGUIComponent pane) {

        VBox container = new VBox(pane);
        container.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        BorderPane borderPane = new BorderPane(scrollPane);

        TitledPane titledPane = new TitledPane(title != null ? title : pane.getName(), borderPane);
        titledPane.setAlignment(Pos.TOP_CENTER);

        accordion.getPanes().add(titledPane);
    }

    private static void addItem(Accordion accordion, FXGUIComponent pane) {
        addItem(accordion, null, pane);
    }

}
