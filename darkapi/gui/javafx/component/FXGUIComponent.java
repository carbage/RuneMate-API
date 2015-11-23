package darkapi.gui.javafx.component;

import darkapi.script.DarkScript;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class FXGUIComponent extends VBox {

    private final DarkScript script;
    private final String name;

    public FXGUIComponent(DarkScript script, String name) {
        this.script = script;
        this.name = name;
        setPadding(new Insets(5));
        setAlignment(Pos.TOP_CENTER);
        maxWidth(Double.MAX_VALUE);
        HBox.setHgrow(this, Priority.ALWAYS);
    }

    public void addComponents(String name, Node... nodes) {
        if(nodes != null && nodes.length > 0) {
            VBox container = new VBox();
            container.setPadding(new Insets(10));
            container.setSpacing(10);
            container.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(container, Priority.ALWAYS);
            container.setAlignment(Pos.TOP_LEFT);

            for (Node node : nodes) {
                if (node instanceof Control) {
                    Control control = (Control) node;
                    control.setMaxWidth(Double.MAX_VALUE);
                    HBox.setHgrow(control, Priority.ALWAYS);
                }
                container.getChildren().add(node);
            }

            if (name != null) {
                Label label = new Label(name);
                label.setFont(new Font(24));
                getChildren().addAll(label);
            }
            getChildren().add(container);
        }
    }

    public void addComponents(Node... nodes) {
        addComponents(null, nodes);
    }

    public String getName() {
        return name;
    }

}
