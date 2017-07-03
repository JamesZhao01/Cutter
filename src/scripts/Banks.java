/**
	* Banks.java
	* James Zhao
	* Jun 25, 2017
**/
/**
 * 
 */
package scripts;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

/**
 * @author jameszhao
 *
 */
public class Banks {
    public static final RSTile CHOP_TILE = new RSTile(3145, 3403, 0);
    public static final RSArea VARROCK_W_BANK = new RSArea(new RSTile(3181, 3446, 0), new RSTile(3185, 3437, 0));
    public static final RSArea VARROCK_E_BANK = new RSArea(new RSTile(3251, 3421, 0), new RSTile(3254, 3420, 0));
    public static final RSTile MINING_TILE = new RSTile(3286, 3363, 0);
}
