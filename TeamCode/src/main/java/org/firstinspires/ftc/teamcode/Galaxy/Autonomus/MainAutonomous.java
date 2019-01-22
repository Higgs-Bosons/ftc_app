package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.CanOfPineapple;
import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.PineappleChunks;
import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.PineappleStrainer;
import org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot.MecanumWheelRobot;
import org.firstinspires.ftc.teamcode.Galaxy.Tools;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.*;

@Autonomous(name = "Autonomous", group = "Autonomous")
public class MainAutonomous extends LinearOpMode {
    private MecanumWheelRobot Bubbles;
    private CanOfPineapple canOfPineapple;
    private PineappleStrainer pineappleStrainer;

    //Up Ticks: 30
    //Down Ticks: -11195
    public void runOpMode() throws InterruptedException {
        try{
            initializeRobot();

            waitForStart();

            dropFromLander();
            unhookFromTheLander();
            positionForSampling();
            sample();
            while (opModeIsActive());
        }finally{
            Bubbles.stopRobot();
            canOfPineapple.closeCanOfPineapple();
        }
    }

    private void initializeRobot(){
        telemetry.setCaptionValueSeparator("");
        telemetry.addData("INITIALIZING MOTORS","");
        telemetry.addData("-|--------","");
        telemetry.update();

        Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.addAMotor(PowerUp, NO_TAG);
        Bubbles.addAMotor(PowerDown, NO_TAG);

        telemetry.addData("SETTING UP MOTORS","");
        telemetry.addData("--|-------"," 20%");
        telemetry.update();
        Bubbles.setBreakOrCoast(DcMotor.ZeroPowerBehavior.BRAKE);
        Bubbles.setMotorDirection(FORWARDS,REVERSE,REVERSE,FORWARDS);
        Bubbles.stopMotor(PowerUp);
        Bubbles.stopMotor(PowerDown);

        Bubbles.resetEncoders();

        telemetry.addData("INITIALIZING SENSORS","");
        telemetry.addData("----|-----"," 40%");
        telemetry.update();
        canOfPineapple = new CanOfPineapple(UPSIDE_DOWN);
        pineappleStrainer = new PineappleStrainer(canOfPineapple);
        Bubbles.addSensor(Gyro, IMU);
        Bubbles.resetIMUGyro(Gyro);

        telemetry.addData("INITIALIZING SERVOS","");
        telemetry.addData("--------|---"," 60%");
        telemetry.update();
        Bubbles.addServo(XThing);
        Bubbles.addServo(YThing);
        Bubbles.addServo(Holder);
        Bubbles.addServo(Grabby);
        Bubbles.addServo(WeightLifter);

        telemetry.addData("SETTING UP SERVOS","");
        telemetry.addData("----------|-"," 80%");
        telemetry.update();
        Bubbles.moveServo(XThing, 0.365);
        Bubbles.moveServo(YThing, 0.685);
        Bubbles.moveServo(Grabby, 0.6);
        Bubbles.moveServo(WeightLifter, 0.7);

        telemetry.addData("READY :-)","");
        telemetry.addData("-----------|"," 100%");
        telemetry.update();
    }


    private void dropFromLander() throws InterruptedException{
        Bubbles.resetMotorTickCount(PowerDown, false);
        Bubbles.resetMotorTickCount(PowerUp, false);
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
        Bubbles.moveRobot(NORTH, 3, 0.3, 0.05, 3);
        Bubbles.moveRobot(EAST, 3, 0.3, 0.05, 3);
        Thread.sleep(100);
        Bubbles.resetIMUGyro(Gyro);
        Bubbles.moveMotor(PowerDown, 0.4);
        Bubbles.moveMotor(PowerUp, 0.4);
    }
    private void positionForSampling() throws InterruptedException{
        Bubbles.gyroTurn(277, Bubbles.getIMU(Gyro), UPSIDE_DOWN);
        Bubbles.stopMotor(PowerDown);
        Bubbles.stopMotor(PowerUp);
    }
    private void sample()throws InterruptedException{
        int cubePosition = findCubePosition();


        Bubbles.moveRobot(NORTH, 8.0, 0.3, 0.1, 10);
        if(cubePosition == 1)
            Bubbles.moveRobot(WEST, 15.0, 0.5, 0.1, 4);
        else if(cubePosition == 2)
            Bubbles.moveRobot(EAST, 3.0,  0.5, 0.1, 4);
        else
            Bubbles.moveRobot(EAST, 18.0, 0.5, 0.1, 4);

        Bubbles.moveRobot(NORTH, 30.0,  0.9, 0.1, 4.0);
    }
    private int findCubePosition(){
        int xValue = -1;
        pineappleStrainer = new PineappleStrainer(canOfPineapple);
        Bitmap picture = canOfPineapple.getBitmap();
        PineappleChunks pineappleChunks = pineappleStrainer
                .findShadedObject(85, 95, picture, Color.rgb(250, 200, 0), 130);

        if(pineappleChunks.doesChunkExist()){
            for(int count = 0; count < pineappleChunks.getNumberOfChunks(); count++){
                telemetry.addData("CUBE " + (count + 1) +" X: " , pineappleChunks.getChunk(count)[PineappleChunks.X]);
                telemetry.addData("       Y: " , pineappleChunks.getChunk(count)[PineappleChunks.Y]);
                telemetry.addData("    SIZE: " , pineappleChunks.getChunk(count)[PineappleChunks.SIZE]);

                if(pineappleChunks.getChunk(count)[PineappleChunks.Y]  < 60 &&
                        pineappleChunks.getChunk(count)[PineappleChunks.SIZE] > 30){
                    xValue = pineappleChunks.getChunk(count)[PineappleChunks.X];
                }
            }
            telemetry.update();
            return (xValue == -1) ? 3 : (xValue < 50) ? 1 : 2;
        }else{
            telemetry.update();
            return 3;
        }
    }
    private void driveToDepo() throws InterruptedException{

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
    private void parkOnCrater() throws InterruptedException{

    }
}
