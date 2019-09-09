/** Interface TrafficInterface
 * 
 */

package trafficlight;

/** Interface TrafficInterface
 * @author mikec
 * 
 * Standard java Interface where we assign all our static/final variables used through the program.
 * Each class would "implement" this interface in order to gain access to these variables, otherwise
 * they would be required to fully qualify.
 */
public interface TrafficInterface 
{
    public static final int RED = 0;
    public static final int GREEN = 1;
    public static final int YELLOW = 2;
    
    public static final int RED_ON = 0;
    public static final int RED_OFF = 1;
    public static final int GREEN_ON = 2;
    public static final int GREEN_OFF = 3;
    public static final int YELLOW_ON = 4;
    public static final int YELLOW_OFF = 5;
}
