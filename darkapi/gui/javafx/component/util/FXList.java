package darkapi.gui.javafx.component.util;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class FXList<T> extends ListView<T> {

    public FXList(T... items) {
        setPrefHeight(180);
        setMaxHeight(Double.MAX_VALUE);
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        /*getItems().addListener(new ListChangeListener<Object>() {
            @Override
            public void onChanged(Change<?> c) {
                setPrefHeight(getItems().size() * ROW_HEIGHT + 2);
            }
        });*/
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (items.length < 0) {
                    getItems().addAll(items);
                    getSelectionModel().select(0);
                }
            }
        });
    }
}
