import javax.swing.*;
import java.awt.GridBagLayout;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
public class gui extends Application{
    final int DIM = 800;
    Node[][] grid = new Node[20][20];
    Node start;
    Node end;
    public static void main (String args[]){
            launch(args);
        }
        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("A*");            
            GridPane root = new GridPane();
            ArrayList<Button> buttons = new ArrayList<Button>();
            HashMap<Button, Node> map = new HashMap<Button, Node>();
            for(int i = 0; i < 20; i++){
                for(int j = 0; j < 20; j++){
                    Button b = new Button();
                    b.setMaxWidth(DIM / 20);
                    b.setMaxHeight(DIM / 20);
                    b.setMinWidth(DIM / 20);
                    b.setMinHeight(DIM / 20);
                    root.setRowIndex(b, i);
                    root.setColumnIndex(b, j);
                    root.getChildren().add(b);
                    buttons.add(b);
                    Node n = new Node(b, i, j);
                    map.put(b, n);
                    grid[i][j] = n;
                }
            }
            root.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                if(key.getCode()==  KeyCode.ENTER) {
                    AStar alg = new AStar(grid, buttons, map, start, end);
                    Thread t = new Thread(alg);
                    t.start();
                }
          });
            primaryStage.setScene(new Scene(root, DIM, DIM));
            primaryStage.show();
            final EventHandler<ActionEvent> startHandler = new EventHandler<ActionEvent>(){
                int status = 0;
                @Override
                public void handle(final ActionEvent event) {
                    Button x = (Button) event.getSource();
                    if(status == 0){ //setting start node
                        x.setStyle("-fx-background-color: #00ff00");
                        map.get(x).setStart();
                        start = map.get(x);
                    } else if (status == 1){ //setting end node
                        x.setStyle("-fx-background-color: #ff0000");
                        map.get(x).setEnd();
                        end = map.get(x);
                    } else { //setting obstacle nodes
                        x.setStyle("-fx-background-color: #000000");
                        map.get(x).setObstacle();
                    }
                    status++;
                     
                }
            };
            buttons.stream().forEach(e -> e.setOnAction(startHandler));
        }
}