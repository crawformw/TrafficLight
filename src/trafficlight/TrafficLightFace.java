package trafficlight;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/** TrafficLightFace()
 * @author mikec
 */
public class TrafficLightFace extends VBox implements TrafficInterface
{
    public TrafficLightLamp redLamp;
    public TrafficLightLamp yellowLamp;
    public TrafficLightLamp greenLamp;
    
    /** TrafficLightFace() - main constructor */
    public TrafficLightFace()
    {
        super(4);
        // create the three lamps for our face
        initLamps();
        
        // set the face attributes and add our lamps
        initFace();
    }
    
    /** initFace() - set all the visual attributes of the face and add the lamps */
    private void initFace()
    {
        setMaxHeight(170);
        setMaxWidth(50);
        setAlignment(Pos.CENTER);
        setStyle("-fx-border-style: solid inside; "
                + "-fx-border-width: 4; "
                + "-fx-border-color: black; "
                + "-fx-bofder-insets: 5; -fx-border-radius: 10px;"
                + "-fx-background-color: gray;"
                + "-fx-background-radius: 10px; ");
        setPadding( new Insets(3, 3, 3, 3) );
        
        getChildren().addAll(redLamp, yellowLamp, greenLamp);
    }
    
    /** initLamps() - create all three lamps, RED, YELLOW, GREEN and turn them on by default */
    private void initLamps()
    {
        redLamp = new TrafficLightLamp(RED_ON);
        yellowLamp = new TrafficLightLamp(YELLOW_ON);
        greenLamp = new TrafficLightLamp(GREEN_ON);
    }
    
    /** setLights() - pass in the light mode and auto set the three lights
     * @param mode
     */
    public void setFace(int mode)
    {
        switch(mode)
        {
            case RED: 
                redLamp.setLight(RED_ON);
                yellowLamp.setLight(YELLOW_OFF);
                greenLamp.setLight(GREEN_OFF);
                break;
                
            case YELLOW: 
                redLamp.setLight(RED_OFF);
                yellowLamp.setLight(YELLOW_ON);
                greenLamp.setLight(GREEN_OFF);
                break;
            
            case GREEN: 
                redLamp.setLight(RED_OFF);
                yellowLamp.setLight(YELLOW_OFF);
                greenLamp.setLight(GREEN_ON);
                break;
        }
    }
    
}
