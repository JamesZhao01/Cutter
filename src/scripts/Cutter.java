package scripts;
import java.awt.Color;
import java.awt.Graphics;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Constants;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.PathNavigator;
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
    //TODO: bankAreas
    private final int[] GEAR = {1351, 1352};
    private final int[] TREE_ID = {678, 1276, 1277, 1278, 1280};
    private final int[] LOGS = {1511, 1512};
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

    public RSObject findNearestObj() {
	RSObject[] nearbyObjs = Objects.findNearest(100, TREE_ID);
	for(RSObject obj : nearbyObjs) {
	    if(obj != null) {
		return obj;
	    }
	}
	return null;
    }

    public RSNPC findNearestNPC(String description)
    {
	RSNPC[] nearbyNPCs = NPCs.findNearest(description);
	for(RSNPC obj : nearbyNPCs) {
	    if(obj != null) {
		return obj;
	    }
	}
	return null;

    }

    public void loop() {
	if(Inventory.isFull()) {
	    RSTile currentPosition = Player.getPosition();
	    bank();
	    //	    walkToTile(currentPosition);
	}
	else if(Player.getRSPlayer().getAnimation() == -1)
	{
	    RSObject tree = findNearestObj();
	    if(tree != null) {
		if(tree.isOnScreen()) {
		    tree.click("Chop Down Tree");
		}
	    }
	}
	try {
	    Thread.sleep(1000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    public void bank() {
	if(WebWalking.walkToBank())
	{
	    
	    if(!Banking.isBankScreenOpen()) {
		Banking.openBankBooth();
	    }
	    else
	    {
		Banking.deposit(28, LOGS);
		Banking.close();
	    }
	}
    }

    public void walkToTile(int x, int y) {
	PathNavigator p = new PathNavigator();
	p.traverse(new RSTile(x, y));
    }

    public void walkToTile(RSTile tile) {
	PathNavigator p = new PathNavigator();
	p.traverse(tile);
    }

}
