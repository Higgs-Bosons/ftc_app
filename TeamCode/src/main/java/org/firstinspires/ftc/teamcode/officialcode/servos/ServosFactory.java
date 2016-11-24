package org.firstinspires.ftc.teamcode.officialcode.servos;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class ServosFactory {
    private static MyServos servos;

    public static synchronized MyServos getInstance(OpMode opMode){
        if(servos == null){
            servos = getMyServos(opMode);
        }
        return servos;
    }

    private static MyServos getMyServos(OpMode opMode){
        Servo leftBallGrabber = opMode.hardwareMap.servo.get(Constants.L_BALL_SERVO);
        Servo rightBallGrabber = opMode.hardwareMap.servo.get(Constants.R_BALL_SERVO);
        Servo topCapGrabber = opMode.hardwareMap.servo.get(Constants.T_CAP_SERVO);
        Servo bottomCapGrabber = opMode.hardwareMap.servo.get(Constants.B_CAP_SERVO);
        Servo ballLoader = opMode.hardwareMap.servo.get(Constants.LOADER_SERVO);

        BallGrabber bGrab = new BallGrabber(leftBallGrabber, rightBallGrabber);
        CapGrabber cGrab = new CapGrabber(topCapGrabber, bottomCapGrabber);
        BallLoader bLoad = new BallLoader(ballLoader);

        return new MyServos(bGrab, cGrab, bLoad);
    }
}
