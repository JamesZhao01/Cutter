/**
 * Miner.java
 * James Zhao
 * Jun 28, 2017
 **/
/**
 * 
 */
package scripts;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Options;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;

/**
 * @author jameszhao
 *
 */
@ScriptManifest(authors="jzhao", category="Mining",name="James's Miner")
public class Miner extends Script implements Painting {

    private static final int[] COPPER = {7484, 7453}; 
    private static final int[] COPPER_ORE = {436};
    private static final int[] EMPTY_ROCKS = {7468, 7469};
    private static RSObject ROCK_OBS = null;
    private static boolean mine = true;

    @Override
    public void onPaint(Graphics g) {
	g.setColor(Color.WHITE);
	g.drawString("James's Cutter", 50, 100);
    }

    @Override
    public void run() {
	class Spammer implements Runnable {
	    @Override
	    public void run() {
		while(true)
		{
		    if(ROCK_OBS != null)
			println(ROCK_OBS.getID());
		    try {
			Thread.sleep(500);
		    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}
	    }
	}
	Spammer s = new Spammer();
	Mouse.setSpeed(130);
	WebWalking.setUseAStar(true);
	WebWalking.setUseRun(true);
	ROCK_OBS = findNearestObj();

	println("Starting Cutter");
	while(loop()) {
	    General.sleep(100, 200);
	}
	println(Timing.msToString(Timing.currentTimeMillis()));
    }

    public boolean loop() {
	if(ROCK_OBS != null)
	    println(ROCK_OBS.getID());
	if(Inventory.isFull()) {
	    println("Inventory Is Full");
	    if(bank()) {
		if(!walkToTile(Banks.MINING_TILE, 0, 60000))
		    return false;
	    } else {
		return false;
	    }
	} else {
	    // Walk to the location of Mining Tile if it's not on screen
	    if(!Banks.MINING_TILE.isOnScreen())
		walkToTile(Banks.MINING_TILE, 4, 60000);
	    // Finding Ores
	    RSObject ore = findNearestObj();
	    if(ore != null && ore.isOnScreen()) {
		RSTile position = ore.getPosition();
		int ID = ore.getID();
		mine = true;

		ore.click();
		println("Ore Clicked");
//		Timing.waitCondition(new Condition() {
//
//		    @Override
//		    public boolean active() {
//			General.sleep(30, 50);
//			if(Objects.isAt(position, COPPER)) {
//			    mine = false;
//			}
//			return Player.getAnimation() == 626;
//		    }
//
//		}, 20000);
//		println("Begin Mining");
//		Timing.waitCondition(new Condition() {
//
//		    @Override
//		    public boolean active() {
//			General.sleep(50, 80);
//			if(Objects.isAt(position, COPPER)) {
//			    mine = false;
//			}
//			if(!mine)
//			    return true;
//			return Player.getAnimation() == -1;
//		    }
//
//		}, 20000);
//		println("Stopped Mining");
		Timing.waitCondition(new Condition() {

		    @Override
		    public boolean active() {
			return !Objects.isAt(position, COPPER);
		    }
		    
		}, 20000);
	    }
	}
	return true;
    }

    public boolean bank() {
	println("Banking");
	Options.setRunOn(true);
	walkToTile(Banks.VARROCK_E_BANK.getRandomTile(), 0, 60000);
	General.sleep(500, 1000);
	println("Arrived at Bank");
	if(!Banking.isBankScreenOpen()) {
	    Timing.waitCondition(new Condition() {
		@Override
		public boolean active() {
		    General.sleep(200, 500);
		    Banking.openBankBooth();
		    return Banking.isBankScreenOpen();
		}
		
	    }, General.randomLong(12000, 15000));
	    Banking.depositAllExcept(new int[]{1273});
	    General.sleep(750, 900);
	    Banking.close();
	}
	General.sleep(1000, 1400);
	if(Inventory.find(COPPER_ORE).length == 0) {
	    println("Bank Successful");
	    return true;
	} else {
	    println("Banking Failed. Copper ore still in inventory");
	    return false;
	}
    }

    public boolean walkToTile(int x, int y, int z, int precision, long timeout) {
	return walkToTile(new RSTile(x, y, z), precision, timeout);
    }

    public boolean walkToTile(RSTile tile, int precision, long timeout) {
	if(WebWalking.walkTo(tile, new Condition() {

	    @Override
	    public boolean active() {
		return distance(Player.getPosition(), tile) <= precision;
	    }

	}, timeout)) {
	    return true;
	} else {
	    return false;
	}
    }

    // Helper Methods

    public int distance(RSTile t1, RSTile t2) {
	return (Math.abs(t1.getX() - t2.getX()) + Math.abs(t1.getY() - t2.getY()));
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

    public RSObject findNearestObj() {
	RSObject[] nearbyOres = Objects.findNearest(7, COPPER);
	println("Copper Ores: " + nearbyOres.length);
	for(RSObject obj : nearbyOres) {
	    if(obj != null) {
		return obj;
	    }
	}
	return null;
    }

    public RSNPC findNearestNPC(String description)
    {
	RSNPC[] nearbyNPCs = NPCs.findNearest(description);
	println(nearbyNPCs.length);
	for(RSNPC obj : nearbyNPCs) {
	    if(obj != null) {
		return obj;
	    }
	}
	return null;

    }


}
