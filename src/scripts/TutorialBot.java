/**
 * TutorialBot.java
 * James Zhao
 * Jun 29, 2017
 **/
/**
 * 
 */
package scripts;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.rs3.Player;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.ext.Doors;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSInterface;
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
@ScriptManifest(authors="jzhao", category="Other",name="Tutorial Island")
public class TutorialBot extends Script implements Painting  {
    public static final RSTile DOOR1 = new RSTile(3098, 3107, 0);
    public static final RSArea FISH_AREA = new RSArea(new RSTile(3101, 3097, 0), new RSTile(3105, 3095, 0));
    public static final RSArea POND_AREA = new RSArea(new RSTile(3099, 3094, 0), new RSTile(3101, 3093, 0));

    public static final RSInterface SETTINGS = Interfaces.get(548, 41);
    public static final RSInterface INVENTORY = Interfaces.get(548, 50);
    public static final RSInterface SKILLS = Interfaces.get(548, 48);
    @Override
    public void onPaint(Graphics arg0) {

    }

    @Override
    public void run() {
	//	General.useAntiBanCompliance(true);
	//	ABCUtil util = new ABCUtil();
	//	Mouse.click (259, 282, 1);
	//	General.sleep(500, 2300);
	//	Camera.setCameraAngle(100);
	//	General.sleep(500, 2000);
	//	Camera.setCameraRotation(0);
	//	General.sleep(500, 2000);
	//	findNearestNPC(3308).click("Talk-to RuneScape Guide"); // RSGuide
	//	General.sleep(500, 1300);
	//	clickContinue(7);
	//	NPCChat.selectOption(NPCChat.getOptions()[2], false);
	//	General.sleep(General.random(1000, 1400), General.random(1401, 1900));
	//	clickContinue(3);
	//	SETTINGS.click();
	//General.sleep(General.random(1000, 1400), General.random(1401, 1900));
	//	findNearestNPC(3308).click("Talk-to RuneScape Guide"); // RSGuide
	//	
	//	clickContinue(2);
	//	Doors.getDoorAt(DOOR1).click();
	//	WebWalking.walkTo(FISH_AREA.getRandomTile());
	//	gSleep();
	//	findNearestNPC(3306).click("Talk-to Survival Expert"); 
	//	gSleep();
	//	clickContinue(2);
	//	INVENTORY.click();
	//	gSleep();
	//	findNearestObj(5, new int[] {9730}).click();
	//	Timing.waitCondition(new Condition() {
	//
	//	    @Override
	//	    public boolean active() {
	//		General.sleep(800, 1300);
	//		if(Player.getAnimation() == -1) {
	//		    if(Inventory.getCount("Logs") >= 1) {
	//			return true;
	//		    } else {
	//			findNearestObj(5, new int[] {9730}).click();
	//		    }
	//		} 
	//		return false;
	//	    }
	//	    
	//	}, 25000);
	//	clickContinue(1);
	//	Inventory.find("Tinderbox")[0].click("Use Tinderbox");
	//	General.sleep(300, 500);
	//	Inventory.find("Logs")[0].click();
	//	gSleep();
	//	SKILLS.click();
	//	findNearestNPC(3306).click("Talk-to Survival Expert"); 
	//	clickContinue(2);
	//	INVENTORY.click();
	// TODO implement cooking + firemaking loop
//	WebWalking.walkTo(POND_AREA.getRandomTile());
//	findNearestNPC(3317).click();
//	Timing.waitCondition(new Condition() {
//
//	    @Override
//	    public boolean active() {
//		General.sleep(800, 1300);
//		if(Player.getAnimation() == -1) {
//		    if(Inventory.getCount("Raw shrimps") >= 1) {
//			return true;
//		    } else {
//			findNearestNPC(3317).click();
//		    }
//		} 
//		return false;
//	    }
//
//	}, 25000);
//	WebWalking.walkTo(POND_AREA.getRandomTile());
//	Inventory.find("Raw shrimps")[0].click();
//	findNearestObj(10, new int[] {26185}).click();
    }

    public void clickContinue(int x) {
	for(int i = 0; i < x; i++) {
	    NPCChat.clickContinue(false);
	    General.sleep(1200, 1700);
	}
    }

    public void gSleep() {
	General.sleep(General.random(200, 1400), General.random(1401, 1900));
    }

    // Helper Methods

    public RSObject findNearestObj(int dist, int[] ID) {
	RSObject[] nearbyObjs = Objects.findNearest(dist, ID);
	for(RSObject obj : nearbyObjs) {
	    if(obj != null) {
		return obj;
	    }
	}
	return null;
    }

    public RSNPC findNearestNPC(int id)
    {
	RSNPC[] nearbyNPCs = NPCs.findNearest(id);
	println(nearbyNPCs.length);
	for(RSNPC obj : nearbyNPCs) {
	    if(obj != null) {
		return obj;
	    }
	}
	return null;

    }

    public Point randomPoint(Rectangle r) {
	return new Point(General.random((int)r.getX(), (int)r.getX() + (int)r.getWidth()), General.random((int)r.getY(), (int)r.getY() + (int)r.getHeight()));
    }

}
