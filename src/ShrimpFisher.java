import org.tribot.api.input.Mouse;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;

import java.awt.*;

/**
 * Created by billy on 6/18/17.
 */
@ScriptManifest(authors="billz12oz", category="Fishing",name="Billy's simple shrimp fisher")
public class ShrimpFisher extends Script implements Painting {

    Timer time = new Timer(150000);

    private final int[] FISHINGGEAR = {303,304};
    private final int[] SPOT_ID = {1530};
    @Override
    public void onPaint(Graphics g) {
	g.setColor(Color.WHITE);
	g.drawString("Billy's Fisher",10,70);
    }
    @Override
    public void run() {
	Mouse.setSpeed(230);
	println("Starting fisher");
	while(true) {

	    int x = loop();
	    sleep(x);
	}

    }
    public RSNPC findNearest(String desc) {
	RSNPC[] id = NPCs.findNearest(desc);
	for(RSNPC i : id) {
	    if(i != null) {
		return i;
	    }
	}
	return null;
    }
    public int loop() {
	if (Inventory.isFull()) {
	    Inventory.dropAllExcept(FISHINGGEAR);
	}
	if(Player.getRSPlayer().getAnimation() == -1) {
	    println("I am still");
	    RSNPC netspot = findNearest("Fishing spot");
	    println(netspot);
	    if (netspot != null) {
		if(netspot.isOnScreen()) {
		    netspot.click("Net Fishing Spot");
		}
		time.reset();
		while(Player.getRSPlayer().getAnimation()== -1 && time.isRunning()) {
		    sleep(10);
		}
	    }
	}

	return (int)(Math.random()*100000) + 10000;
    }

}
