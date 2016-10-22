package org.firstinspires.ftc.teamcode.officialcode.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Created by Higgs Bosons on 10/22/2016.
 */
public class DriveMotors {
    private final static double GEAR_RATIO = 1;
    private final static double WHEEL_DIAMETER = 4;
    private final static double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;

    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;

    public DriveMotors(DcMotor leftFront, DcMotor leftBack, DcMotor rightFront,DcMotor rightBack){
        this.leftFront = leftFront;
        this.leftBack = leftBack;
        this.rightFront = rightFront;
        this.rightBack = rightBack;
    }

    private double getRotations(double distance) {
        return distance / CIRCUMFERENCE;
    }

    public int getCounts(double distance) {
        return (int)(Constants.ENCODER_CPR * this.getRotations(distance) * GEAR_RATIO);
    }
}
