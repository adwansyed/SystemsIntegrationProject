package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 * Created by 100525709 on 3/14/2017.
 */
public class TodoListTabController {

    private final DataFormat buttonFormat = new DataFormat(" ");
    @FXML
    private AnchorPane textField;

    @FXML
    private Button testButton, draggingButton;

    @FXML
    private FlowPane mPane, tPane, wPane, thPane, fPane, satPane, sunPane;

    @FXML
    void testAction(ActionEvent event) {
        mPane.getChildren().add(createButton("task1"));
        mPane.getChildren().add(createButton("task2"));
        mPane.getChildren().add(createButton("task3"));
        tPane.getChildren().add(createBlueButton("hello1"));
        tPane.getChildren().add(createBlueButton("hello1"));
        tPane.getChildren().add(createBlueButton("hello1"));
        tPane.getChildren().add(createBlueButton("hello1"));
        addDropHandling(mPane);
        addDropHandling(tPane);
        addDropHandling(wPane);
        addDropHandling(thPane);
        addDropHandling(fPane);
        addDropHandling(satPane);
        addDropHandling(sunPane);
        for (Node component : mPane.getChildren()){
            System.out.println(component.toString());
        }

    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: seagreen; -fx-text-fill: white");
        button.setShape(new Circle(30));
        button.setMinSize(60,60);
        button.setMaxSize(60,60);
        //button.setMinWidth(120);
        button.setOnDragDetected(e -> {
            Dragboard db = button.startDragAndDrop(TransferMode.MOVE);
            db.setDragView(button.snapshot(null, null));
            ClipboardContent cc = new ClipboardContent();
            cc.put(buttonFormat, "button");
            db.setContent(cc);
            draggingButton = button ;
        });
        button.setOnDragDone(e -> draggingButton = null);
        button.setOnAction(event -> mPane.getChildren().remove(0,1));
        return button ;
    }

    private Button createBlueButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: blue; -fx-text-fill: white");
        button.setMinWidth(120);
        button.setOnDragDetected(e -> {
            Dragboard db = button.startDragAndDrop(TransferMode.MOVE);
            db.setDragView(button.snapshot(null, null));
            ClipboardContent cc = new ClipboardContent();
            cc.put(buttonFormat, "button");
            db.setContent(cc);
            draggingButton = button ;
        });
        button.setOnDragDone(e -> draggingButton = null);
        button.setOnAction(event -> System.out.println("Hi you pressed me"));
        return button ;
    }

    private void addDropHandling(Pane pane) {
        pane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(buttonFormat)
                    && draggingButton != null
                    && draggingButton.getParent() != pane) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });

        pane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(buttonFormat)) {
                ((Pane)draggingButton.getParent()).getChildren().remove(draggingButton);
                pane.getChildren().add(draggingButton);
                e.setDropCompleted(true);
            }
        });
    }
}
