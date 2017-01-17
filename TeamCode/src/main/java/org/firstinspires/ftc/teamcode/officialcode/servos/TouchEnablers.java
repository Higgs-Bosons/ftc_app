package org.firstinspires.ftc.teamcode.officialcode.servos;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.launcher.ILauncher;
import org.firstinspires.ftc.teamcode.officialcode.teleop.TeleopMessages;

import java.util.concurrent.BlockingQueue;

/**
 * Class for controlling touch sensors
 */
public class TouchEnablers{
	//declare servos touch sensors
    private Servo right;
    private Servo left;
	//position constants for servos
    private static final double READY_POS = 0.5d;
    private static final double UP_POS = 0.0d;

	/** 
	 * Constructor initializes servos
	 * @param right
	 * @param left
	 */
    public TouchEnablers(Servo right, Servo left){
        this.right = right;
        this.left = left;
    }//constructor

	/**
	 * Method for retracting sensors
	 */
    public void retract() throws InterruptedException {
        this.right.setPosition(1.0d - UP_POS);
        this.left.setPosition(UP_POS);
        Thread.sleep(500);
    }//retract

	/**
	 * Method for positioning sensors
	 */
    public void activate() throws InterruptedException {
        this.right.setPosition(1.0d - READY_POS);
        this.left.setPosition(READY_POS);
        Thread.sleep(500);
    }//activate
}//class
