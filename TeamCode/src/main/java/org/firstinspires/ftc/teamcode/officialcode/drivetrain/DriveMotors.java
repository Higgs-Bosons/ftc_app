package org.firstinspires.ftc.teamcode.officialcode.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

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

        this.getLeftFront().setDirection(DcMotor.Direction.REVERSE);
        this.getLeftBack().setDirection(DcMotor.Direction.REVERSE);
    }

    private double getRotations(double distance) {
        return distance / CIRCUMFERENCE;
    }

    public int getCounts(double distance) {
        return (int)(Constants.ENCODER_CPR * this.getRotations(distance) * GEAR_RATIO);
    }

    public void  resetControllers(){
        this.getLeftFront().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.getRightFront().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void moveControllers(){
        this.getLeftFront().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.getRightFront().setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public boolean isBusy(){
        return this.getLeftFront().isBusy();
    }

    public void stopMotors() {
        this.getLeftFront().setPower(0);
        this.getLeftBack().setPower(0);
        this.getRightFront().setPower(0);
        this.getRightBack().setPower(0);
    }

    public void setPowerAll(double powerAll){
        this.getLeftFront().setPower(powerAll);
        this.getLeftBack().setPower(powerAll);
        this.getRightFront().setPower(powerAll);
        this.getRightBack().setPower(powerAll);
    }

    public void encodeInitialize(){
        this.getLeftBack().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.getRightBack().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.getLeftFront().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.getRightFront().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public DcMotor getLeftBack() {
        return leftBack;
    }

    public DcMotor getLeftFront() {
        return leftFront;
    }

    public DcMotor getRightFront() {
        return rightFront;
    }

    public DcMotor getRightBack() {
        return rightBack;
    }
}
