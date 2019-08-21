
package trafficlight;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TrafficLight extends Application implements TrafficInterface
{
    private TrafficLightFace northFace;
    private TrafficLightFace southFace;
    private TrafficLightFace eastFace;
    private TrafficLightFace westFace;
    private VBox buttonBox;
    
    private Button startButton;
    private Button stopButton;
    private Label redTimerLabel;
    private Label redSecLabel;
    private Label yellowTimerLabel;
    private Label yellowSecLabel;
    private TextField redTextField;
    private TextField yellowTextField;
    
    private GridPane timeFields;
    
    private int redDelay = 5;
    private int yellowDelay = 2;
    private ArrayList<Integer> sequence;
    private int frameCnt;
    
    private Label frameCntLabel;
    private final StringProperty frameString = new SimpleStringProperty("frame");
    
    private AnimationTimer timer;
    
    @Override
    public void start(Stage primaryStage) 
    {
        // create a TrafficLightFace for each side of the traffic signal
        northFace = new TrafficLightFace();
        southFace = new TrafficLightFace();
        eastFace = new TrafficLightFace();
        westFace = new TrafficLightFace();
        
        redTimerLabel = new Label("Red Light Timer");
        GridPane.setHalignment(redTimerLabel, HPos.RIGHT);
        redSecLabel = new Label("Secs");
        redTextField = new TextField();
        redTextField.setText("5");
        redTextField.setMaxWidth(35);
        redTextField.setPrefWidth(35);
        
        yellowTimerLabel = new Label("Yellow Light Timer");
        GridPane.setHalignment(yellowTimerLabel, HPos.RIGHT);
        yellowSecLabel = new Label("Secs");
        yellowTextField = new TextField();
        yellowTextField.setText("2");
        yellowTextField.setMaxWidth(35);
        yellowTextField.setPrefWidth(35);
        
        // build gridPane
        timeFields = new GridPane();
        timeFields.setPadding(new Insets(3, 3, 3, 3));
        timeFields.setHgap(5);
        timeFields.setAlignment(Pos.CENTER);
        timeFields.add(redTimerLabel, 0, 0);
        timeFields.add(redTextField, 1, 0);
        timeFields.add(redSecLabel, 2, 0);
        timeFields.add(yellowTimerLabel, 0, 1);
        timeFields.add(yellowTextField, 1, 1);
        timeFields.add(yellowSecLabel, 2, 1);
        
        frameCntLabel = new Label();
        frameCntLabel.textProperty().bind(frameString);
        
        // start button to activate the signal sequence
        startButton = new Button();
        startButton.setText("Start");
        startButton.setOnAction(ae-> { signalTimer(true); });
        
        // stop button to deactivate the signal sequence
        stopButton = new Button();
        stopButton.setText("Stop");
        stopButton.setDisable(true);
        stopButton.setOnAction(ae-> { signalTimer(false); });
        
        // this holds our start and stop buttons to activate the signal        
        buttonBox = new VBox(4);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setStyle("-fx-background-color: gray;");
        buttonBox.setPadding( new Insets(3, 3, 3, 3) );
        buttonBox.getChildren().addAll(frameCntLabel, startButton, stopButton, timeFields);
        
        // create the timer here, call the updateSignal() routine for each frame count (60 times a second)
        // this happens in a separate thread so the main thread doesn't get locked up
        timer = new AnimationTimer() 
                {
                    @Override
                    public void handle(long now) { updateSignal(); }
                };
        
        // create the root pane as a border pane
        BorderPane root = new BorderPane();
        BorderPane.setMargin(buttonBox, new Insets(5, 5, 5, 5));
        BorderPane.setAlignment(northFace, Pos.CENTER);
        BorderPane.setAlignment(southFace, Pos.CENTER);
        BorderPane.setAlignment(eastFace, Pos.CENTER);
        BorderPane.setAlignment(westFace, Pos.CENTER);
        
        // add our traffic light faces to the root pane
        root.setPadding( new Insets(10, 10, 10, 10) );
        root.setTop(northFace);
        root.setLeft(westFace);
        root.setRight(eastFace);
        root.setCenter(buttonBox);
        root.setBottom(southFace);
        
        // create our scene object with the root borderpane set as the content
        Scene scene = new Scene(root, 550, 450);
        
        // set the stage title, set the scene and show the stage
        primaryStage.setTitle("Traffic Light Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /** updateSignal() - called inside our Animation timer once every cycle, or roughly 60 times a second */
    void updateSignal()
    {
        frameCnt++;                     // update our frameCnt value each time
        
        frameString.set(String.valueOf(frameCnt));
        
        if (frameCnt == sequence.get(0)) { trafficSequence(eastFace, YELLOW_ON); }          // cycle moves to Yellow on the EW signals - 300
        if (frameCnt == sequence.get(1)) { trafficSequence(eastFace, RED_ON); }             // cycle moves to Red on the EW signals - 520
        if (frameCnt == sequence.get(2)) { trafficSequence(northFace, YELLOW_ON); }         // cycle moves to Yellow on the NS signals - 820
        if (frameCnt == sequence.get(3)) { trafficSequence(northFace, RED_ON); frameCnt = 0;}            // cycle moves to Red on the NS signals - 940
        //if (frameCnt == sequence.get(4)) { frameCnt = 0; }                                 // cycle complete, set frameCnt back to 0 and start over - 1180                           
    }
    
    /** turnTrafficSignalOn() - called by our updateSignal() routine when the frameCnt hits a certain place, signaling a change in our traffic lights
     * @param light 
     */
    void trafficSequence(TrafficLightFace face, int mode)
    {
        switch( mode )
        {
            case RED_ON:
                if (face == northFace)
                {
                    face.setFace(TrafficLightFace.RED);
                    southFace.setFace(TrafficLightFace.RED);
                    eastFace.setFace(TrafficLightFace.GREEN);
                    westFace.setFace(TrafficLightFace.GREEN);
                }
                
                if (face == eastFace)
                {
                    northFace.setFace(TrafficLightFace.GREEN);
                    southFace.setFace(TrafficLightFace.GREEN);
                    face.setFace(TrafficLightFace.RED);
                    westFace.setFace(TrafficLightFace.RED);
                }
                break;
                
            case YELLOW_ON:
                if (face == northFace)
                {
                    face.setFace(TrafficLightFace.YELLOW);
                    southFace.setFace(TrafficLightFace.YELLOW);
                    eastFace.setFace(TrafficLightFace.RED);
                    westFace.setFace(TrafficLightFace.RED);
                }
                
                if (face == eastFace)
                {
                    northFace.setFace(TrafficLightFace.RED);
                    southFace.setFace(TrafficLightFace.RED);
                    face.setFace(TrafficLightFace.YELLOW);
                    westFace.setFace(TrafficLightFace.YELLOW);
                }
                break;
        }
    }
    
    /** signalTimer() - pass TRUE to start our signal timer and FALSE to stop it
     * @param in 
     */
    void signalTimer(boolean flag)            
    {   
        if (flag)
        {
            redDelay = Integer.valueOf(redTextField.getText()) * 60;
            yellowDelay = Integer.valueOf(yellowTextField.getText()) * 60;

            frameCnt = 0;
            
            sequence = new ArrayList<>();
            sequence.add(redDelay);
            sequence.add(redDelay + yellowDelay);
            sequence.add( (2*redDelay) + yellowDelay);
            sequence.add( (2*redDelay) + (2*yellowDelay));
            
            startButton.setDisable(true);
            stopButton.setDisable(false);
            
            trafficSequence(northFace, RED_ON);
            timer.start();
        }
        else 
        { 
            timer.stop();
            startButton.setDisable(false);
            stopButton.setDisable(true);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
}
