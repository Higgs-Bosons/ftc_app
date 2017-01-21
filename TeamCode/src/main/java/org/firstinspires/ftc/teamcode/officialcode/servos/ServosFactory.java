package org.firstinspires.ftc.teamcode.officialcode.servos;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Class for servos factory
 */
public class ServosFactory {
	//declare MyServos variable
    private static MyServos servos;

	/**
	 * get an instance of MyServos and assign it to the variable if it is null
	 * @param opMode
	 */
    public static synchronized MyServos getInstance(OpMode opMode){
        if(servos == null){
            servos = getMyServos(opMode);
        }//if
        return servos;
    }//getInstance

    private static MyServos getMyServos(OpMode opMode){
		//hardware map the servos
        Servo leftBallGrabber = opMode.hardwareMap.servo.get(Constants.L_BALL_SERVO);
        Servo rightBallGrabber = opMode.hardwareMap.servo.get(Constants.R_BALL_SERVO);
        Servo topCapGrabber = opMode.hardwareMap.servo.get(Constants.T_CAP_SERVO);
        Servo bottomLeftCapGrabber = opMode.hardwareMap.servo.get(Constants.BL_CAP_SERVO);
        Servo bottomRightCapGrabber = opMode.hardwareMap.servo.get(Constants.BR_CAP_SERVO);
        Servo ballLoader = opMode.hardwareMap.servo.get(Constants.LOADER_SERVO);

		//create objects out of different groups of servos
        BallGrabber bGrab = new BallGrabber(leftBallGrabber, rightBallGrabber);
        CapGrabber cGrab = new CapGrabber(topCapGrabber, bottomLeftCapGrabber, bottomRightCapGrabber);
        BallLoader bLoad = new BallLoader(ballLoader);

		//Create object out of various servo groups
        return new MyServos(bGrab, cGrab, bLoad);
    }//getMyServos
}//class
