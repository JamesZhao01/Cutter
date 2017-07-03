package scripts;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Constants;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Options;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;
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
    private final int[] TREE_ID = {678, 1276, 1277, 1278, 1280};
    private final int[] OAK_TREE_ID = {1751};
    @Override
    public void onPaint(Graphics g) {
	g.setColor(Color.WHITE);
	g.drawString("James's Cutter", 50, 100);

    }
    @Override
    public void run() {
	Mouse.setSpeed(230);
	WebWalking.setUseAStar(true);
	println("Starting Cutter");
	while(true) {
	    loop();
	}

    }

    public void loop() {
	if(Inventory.isFull()) {
	    bank();
	    General.sleep(300);
	    Options.setRunOn(true);
	    walkToTile(Banks.CHOP_TILE, true, 60000);
	}
	else if(Player.getRSPlayer().getAnimation() == -1)
	{
	    if(distance(Player.getPosition(), Banks.CHOP_TILE) >= 7)
		walkToTile(Banks.CHOP_TILE, false, 60000);
	    
	    Options.setRunOn(false);
	    RSObject tree = findNearestObj();
	    if(tree != null) {
		if(!tree.isOnScreen()) {
		    walkToTile(tree.getPosition(), false, 5000);
		}
		tree.click();
		Timing.waitCondition(new Condition(){
		    @Override
		    public boolean active() {
			General.sleep(150, 200);
			return Player.getAnimation() == 875;
		    }

		}, 20000);
		Timing.waitCondition(new Condition(){
		    @Override
		    public boolean active() {
			General.sleep(100, 150);
			return Player.getAnimation() == -1;
		    }
		}, 45000);
		println("Tree is Chopped");

	    }
	}
	General.sleep(300);
    } 

    public void bank() {
	println("Banking");
	Options.setRunOn(true);
	walkToTile(Banks.VARROCK_W_BANK.getRandomTile(), true, 60000);
	General.sleep(500);
	if(!Banking.isBankScreenOpen()) {
	    Banking.openBankBooth();
//	    Timing.waitCondition(new Condition(){
//		@Override
//		public boolean active() {
//		    General.sleep(150, 200);
//		    return Banking.isBankScreenOpen();
//		}
//		
//	    }, 5000);
	    Banking.depositAllExcept(new int[]{1357, 1358});
	    Banking.close();
	}

    }

    public void walkToTile(int x, int y, boolean precise, long timeout) {
	walkToTile(new RSTile(x, y, 0), precise, timeout);
    }

    public void walkToTile(RSTile tile, boolean precise, long timeout) {
	println("Walking. | Target: " + tile.toString());
	PathFinding.aStarWalk(tile);
//	Timing.waitCondition(new Condition() {
//	    @Override
//	    public boolean active() {
//		General.sleep(200, 300);
//		println("Dist to target: " + distance(Player.getPosition(), tile));
//		if(precise)
//		    return distance(Player.getPosition(), tile) <= 4;
//		else
//		    return tile.isOnScreen();
//	    }
//	}, timeout);

    }

    // Helper Methods
    public RSObject findNearestObj() {
	RSObject[] nearbyOaks = Objects.findNearest(9, OAK_TREE_ID);
	RSObject[] nearbyTrees = Objects.findNearest(9, TREE_ID);
	println("Oaks: " + nearbyOaks.length);
	println("Trees: " + nearbyTrees.length);
	RSObject[] nearbyObjs = concatenateArray(nearbyOaks, nearbyTrees);
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

    public int distance(RSTile t1, RSTile t2) {
	int returnValue = ((int)(Math.abs(t1.getX() - t2.getX())) + (int)(Math.abs(t1.getY() - t2.getY())));
	int xDist = (int)(Math.abs(t1.getX() - t2.getX()));
	int yDist = (int)(Math.abs(t1.getY() - t2.getY()));
	println("xDist: " + xDist + " yDist: " + yDist);
	return returnValue;
    }

    public RSObject[] concatenateArray(RSObject[]... array) {
	int length = 0;
	for(RSObject[] a : array) {
	    for(int i = 0; i < a.length; i++) {
		length++;
	    }
	}
	RSObject[] finalArray = new RSObject[length];
	int index = 0;
	for(RSObject[] b : array) {
	    for(int i = 0; i < b.length; i++) {
		finalArray[index] = b[i];
		index++;
	    }
	}
	return finalArray;
    }




}
