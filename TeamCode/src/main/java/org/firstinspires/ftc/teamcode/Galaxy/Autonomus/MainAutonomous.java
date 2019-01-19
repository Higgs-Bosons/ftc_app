package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.CanOfPineapple;
import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.PineappleChunks;
import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.PineappleStrainer;
import org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot.MecanumWheelRobot;
import org.firstinspires.ftc.teamcode.Galaxy.Tools;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.*;

@Autonomous(name = "New Autonomous", group = "Autonomous")
public class MainAutonomous extends LinearOpMode {
    private MecanumWheelRobot Bubbles;
    private CanOfPineapple canOfPineapple;
    private PineappleStrainer pineappleStrainer;
    private PineappleChunks pineappleChunks;

    public void runOpMode() throws InterruptedException {

        try{
            initializeRobot();

            waitForStart();

           //dropFromLander();
           // unhookFromTheLander();
            positionForSampling();

        }finally{
            Bubbles.stopRobot();
            canOfPineapple.closeCanOfPineapple();
        }
    }

    private void initializeRobot(){
        Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.addSensor(Gyro, IMU);
        Bubbles.addAMotor(PowerUp, NO_TAG);
        Bubbles.addAMotor(PowerDown, NO_TAG);

        Bubbles.addServo(XThing);
        Bubbles.addServo(YThing);
        Bubbles.addServo(Holder);
        Bubbles.addServo(Grabby);
        Bubbles.addServo(WeightLifter);

        Bubbles.setBreakOrCoast(DcMotor.ZeroPowerBehavior.BRAKE);
        Bubbles.setMotorDirection(FORWARDS,REVERSE,REVERSE,FORWARDS);

        canOfPineapple = new CanOfPineapple(UPSIDE_DOWN);
        pineappleStrainer = new PineappleStrainer(canOfPineapple);

        Bubbles.resetIMUGyro(Gyro);

        Bubbles.moveServo(XThing, .45);
        Bubbles.moveServo(YThing, .6);
        Bubbles.moveServo(Grabby, 0.6);
        Bubbles.moveServo(WeightLifter, 0.7);
        Bubbles.stopMotor(PowerUp);
        Bubbles.stopMotor(PowerDown);

        Bubbles.resetEncoders();
    }

    private void dropFromLander() throws InterruptedException{

        Bubbles.moveMotor(PowerUp, -1.0);
        Bubbles.moveMotor(PowerDown, -1.0);
        float[] gyroReadings = Bubbles.readIMUGyro(Gyro);

        while(gyroReadings[1] > 280 || gyroReadings[1] < 90){
            gyroReadings = Bubbles.readIMUGyro(Gyro);
            Thread.sleep(0,50);
        }

        Bubbles.stopMotor(PowerDown);
        Bubbles.stopMotor(PowerUp);
        Bubbles.moveServo(Holder, 1.0);
    }
    private void unhookFromTheLander()throws InterruptedException{
        Bubbles.moveRobot(NORTH, 2.5, 0.5, 0.1, 2);
        Bubbles.moveRobot(EAST, 2.5, 0.5, 0.1, 2);
        Bubbles.moveMotor(PowerDown, 0.3);
        Bubbles.moveMotor(PowerUp, 0.3);
        Thread.sleep(500);
        Bubbles.stopMotor(PowerDown);
        Bubbles.stopMotor(PowerUp);
    }
    private void positionForSampling() throws InterruptedException{
        Bubbles.gyroTurn(90, Bubbles.getIMU(Gyro), UPSIDE_DOWN);
    }
    private void dropOffMarker() {
        Bubbles.moveServo(WeightLifter, 0.25);
        Tools.wait(400);
        Bubbles.moveServo(Grabby, 1.0);
        Tools.wait(400);
        Bubbles.moveServo(WeightLifter, 0.7);
        Tools.wait(50);
        Bubbles.moveServo(Grabby, 0.6);
    }
}
