import java.awt.Color;
import java.awt.Graphics;

import org.tribot.api.input.Mouse;
import org.tribot.api2007.Constants;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;

/**
	* Fisher.java
	* James Zhao
	* Jun 19, 2017
**/
/**
 * 
 */

/**
 * @author jameszhao
 *
 */
@ScriptManifest(authors="jzhao", category="Woodcutting",name="James's Woodcutter")
public class Cutter extends Script implements Painting {

    private final int[] GEAR = {1351, 1352};
    private final int[] TREE_ID = {678, 1276, 1277, 1278, 1280};
    
    @Override
    public void onPaint(Graphics g) {
	g.setColor(Color.WHITE);
	g.drawString("James's Cutter", 50, 100);
	
    }
    @Override
    public void run() {
	Mouse.setSpeed(230);
	println("Starting Cutter");
	while(true) {
	    loop();
	}
	
    }
    
    public RSObject findNearest()
    {
	RSObject[] nearbyObjs = Objects.findNearest(100, TREE_ID);
	for(RSObject obj : nearbyObjs) {
	    if(obj != null) {
		return obj;
	    }
	}
	return null;
    }
    
    public void loop() {
	if(Inventory.isFull()) {
	    Inventory.dropAllExcept(GEAR);
	}
	if(Player.getRSPlayer().getAnimation() == -1)
	{
	    println("I am Still");
	    RSObject tree = findNearest();
	    println(tree);
	    if(tree != null) {
		if(tree.isOnScreen()) {
		    tree.click("Chop Down Tree");
		}
		try {
		    Thread.sleep(100);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	    
	}
    }

}
