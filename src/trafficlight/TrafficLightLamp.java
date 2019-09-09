/** Class TrafficLightLamp
 * 
 */

package trafficlight;

import javafx.scene.control.Button;

/** TrafficLightLamp() 
 * @author mikec
 * 
 * class to represent a "lamp" inside a traffic light "face"... extending the button class allows us to shape the image and 
 * assign a color... the lamp object has six possible colors and is either on or off
 */
public class TrafficLightLamp extends Button implements TrafficInterface
{   
    private boolean isOn;
    private int lampColor;
    
    /** TrafficLightLamp() - main constructor that creates the lamp and gives it color
     * @param mode 
     */
    public TrafficLightLamp(int mode) 
    { 
        init(mode);
        isOn = true;
        
        switch (mode)
        {
            case RED_ON:        lampColor = RED;    break;
            case RED_OFF:       lampColor = RED;    break;           
            case GREEN_ON:      lampColor = GREEN;  break;
            case GREEN_OFF:     lampColor = GREEN;  break;
            case YELLOW_ON:     lampColor = YELLOW;  break;
            case YELLOW_OFF:    lampColor = YELLOW;  break;
        }
    }
    
    /** init() - lamp initialization, called from constructor
     * @param mode 
     */
    private void init(int mode) { setLight(mode); }
    
    /** setLight() - public routine used to change the color of the light when in the ON or OFF state based on initial color
     * @param mode 
     */
    public void setLight(int mode)
    {
        switch(mode)
        {
            case RED_ON:
                // set the style attributes for each of the light options, 40x40 px button with a 40 px radius gets us a circle
                // this could be done in a CSS file and included, then we only reference the style name assigned... strictly programmer preference here
                setStyle("-fx-background-color: red; "
                       + "-fx-background-radius: 40px; "
                       + "-fx-min-width: 40px; "
                       + "-fx-min-height: 40px; "
                       + "-fx-max-width: 40px; "
                       + "-fx-max-height: 40px;");
                isOn = true;                            // background color RED means the light is ON
            break;
                
            case RED_OFF:   
                setStyle("-fx-background-color: darkred; -fx-background-radius: 40px; -fx-min-width: 40px; -fx-min-height: 40px; -fx-max-width: 40px; -fx-max-height: 40px;");
                isOn = false;                           // background color DARKRED means the light is OFF 
            break;
            
            case GREEN_ON:  
                setStyle("-fx-background-color: lime; -fx-background-radius: 40px; -fx-min-width: 40px; -fx-min-height: 40px; -fx-max-width: 40px; -fx-max-height: 40px;");
                isOn = true;                            // background color LIME means the light is ON
            break;
                
            case GREEN_OFF: 
                setStyle("-fx-background-color: green; -fx-background-radius: 40px; -fx-min-width: 40px; -fx-min-height: 40px; -fx-max-width: 40px; -fx-max-height: 40px;");
                isOn = false;                           // background color GREEN means the light is OFF
            break;
                
            case YELLOW_ON: 
                setStyle("-fx-background-color: yellow; -fx-background-radius: 40px; -fx-min-width: 40px; -fx-min-height: 40px; -fx-max-width: 40px; -fx-max-height: 40px;");
                isOn = true;                            // background color YELLOW means the light is ON
            break;
                
            case YELLOW_OFF: 
                setStyle("-fx-background-color: darkgoldenrod; -fx-background-radius: 40px; -fx-min-width: 40px; -fx-min-height: 40px; -fx-max-width: 40px; -fx-max-height: 40px;");
                isOn = false;                           // background color DARKGOLDENROD means the light is OFF
            break;
        }
    }
    
    /** isLampOn() and getLampColor() - general unit modules used to pass back lamp state and color
     * @return 
     */
    public boolean isLampOn() { return isOn; }
    public int getLampColor() { return lampColor; }
   
}
