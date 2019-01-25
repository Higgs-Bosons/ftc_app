package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;

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
    private String craterToGoTo = "NOT YET INPUTTED";
    private String landerSide =   "NOT YET INPUTTED";
    private MecanumWheelRobot Bubbles;
    private CanOfPineapple canOfPineapple;
    private PineappleStrainer pineappleStrainer;
    private PineappleChunks pineappleChunks;
    private boolean foundACube = false;
    private Thread telemetryThread, setupForDumper, returnForDumper;

    public void runOpMode() throws InterruptedException {
        try{
            initializeRobot();
            initializeTelemetryThread();
            getMenuChoices();

            waitForStart();

            telemetryThread.start();

            if(landerSide.equals(RIGHT_SIDE_OF_THE_LANDER)){
                dropFromLander();
                unhookFromTheLander();
                positionForSampling_Right();
                sample_Right();
                driveToDepo_Right();
                dropOffMarker();
                parkOnCrater_Right();
            }else{
                dropFromLander();
                unhookFromTheLander();
                positionForSampling_Left();
                sample_Left();
                //driveToDepo_Left();
                //dropOffMarker();
                //parkOnCrater_Right();
            }


            while(opModeIsActive());
            requestOpModeStop();

        }finally{
            Bubbles.stopRobot();
            setupForDumper.interrupt();
            returnForDumper.interrupt();
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
        Bubbles.addAMotor(VSlide, NO_TAG);
        Bubbles.addAMotor(HSlide, NO_TAG);

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
        Bubbles.addSensor(DistanceL, DISTANCE_SENSOR);
        Bubbles.addSensor(DistanceR, DISTANCE_SENSOR);
        Bubbles.addSensor(TouchyL, TOUCH_SENSOR);
        Bubbles.addSensor(TouchyR, TOUCH_SENSOR);

        Bubbles.resetIMUGyro(Gyro);

        telemetry.addData("INITIALIZING SERVOS","");
        telemetry.addData("--------|---"," 60%");
        telemetry.update();
        Bubbles.addServo(XThing);
        Bubbles.addServo(YThing);
        Bubbles.addServo(Holder);
        Bubbles.addServo(Grabby);
        Bubbles.addServo(WeightLifter);
        Bubbles.addServo(ArmY);
        Bubbles.addServo(BucketDumper);
        Bubbles.addServo(Rampy);

        telemetry.addData("SETTING UP SERVOS","");
        telemetry.addData("----------|-"," 80%");
        telemetry.update();
        Bubbles.moveServo(XThing, 0.25);
        Bubbles.moveServo(YThing, 0.685);
        Bubbles.moveServo(Grabby, 0.6);
        Bubbles.moveServo(WeightLifter, 0.7);
        Bubbles.moveServo(Rampy, 0.6);

        telemetry.addData("READY :-)","");
        telemetry.addData("-----------|"," 100%");
        telemetry.update();
    }
    private void initializeTelemetryThread(){
        telemetryThread = new Thread(new Runnable() {
            public void run() {
                while (opModeIsActive()){
                    telemetry.addData("Gyro Tilt:    ", Bubbles.readIMUGyro(Gyro)[1]);
                    telemetry.addData("Gyro Turn:    ", Bubbles.readIMUGyro(Gyro)[0]);
                    telemetry.addData("DistanceR CM: ", Bubbles.readSensor(DistanceR, DISTANCE_IN_CENTIMETERS));
                    telemetry.addData("DistanceR MM: ", Bubbles.readSensor(DistanceR, DISTANCE_IN_MILLIMETERS));
                    telemetry.addData("DistanceL CM: ", Bubbles.readSensor(DistanceL, DISTANCE_IN_CENTIMETERS));
                    telemetry.addData("DistanceL MM: ", Bubbles.readSensor(DistanceL, DISTANCE_IN_MILLIMETERS));

                    if(foundACube){
                        for(int count = 0; count < pineappleChunks.getNumberOfChunks(); count++){
                            telemetry.addData("CUBE " + (count + 1) +" X: " , pineappleChunks.getChunk(count)[PineappleChunks.X]);
                            telemetry.addData("       Y: " , pineappleChunks.getChunk(count)[PineappleChunks.Y]);
                            telemetry.addData("    SIZE: " , pineappleChunks.getChunk(count)[PineappleChunks.SIZE]);
                        }
                    }
                    telemetry.update();

                    Tools.wait(500);
                }
            }
        });

    }
    private void getMenuChoices() throws InterruptedException{
        boolean allInfoPutIn = false;
        while(!gamepad1.y || !allInfoPutIn){
            if(gamepad1.dpad_left)
                craterToGoTo = CRATER_ON_THE_LEFT;
            else if(gamepad1.dpad_right)
                craterToGoTo = CRATER_ON_THE_RIGHT;

            if(gamepad1.x)
                landerSide = LEFT_SIDE_OF_THE_LANDER;
            else if(gamepad1.b)
                landerSide = RIGHT_SIDE_OF_THE_LANDER;

            allInfoPutIn = (!landerSide.equals("NOT YET INPUTTED") && !craterToGoTo.equals("NOT YET INPUTTED"));

            telemetry.addData(" Side of the Lander: (X/B) ", landerSide);
            telemetry.addData(" Crater to go to: (D-PAD)  ", craterToGoTo);
            telemetry.addData("        Press 'Y' to Continue!", "");
            telemetry.update();

            Thread.sleep(0, 50);
        }

        telemetry.addData("READY :-)","");
        telemetry.addData("-----------|"," 100%");
        telemetry.update();
    }

    private void startSetupForDumper(){
        setupForDumper = new Thread(new Runnable() {
            public void run() {
                try{
                    while(Bubbles.getMotorTickCount(VSlide)> -174) {
                        Bubbles.moveMotor(VSlide, -0.4);
                        Thread.sleep(0, 50);
                    }
                    Bubbles.stopMotor(VSlide);

                    Bubbles.moveServo(BucketDumper, 0.565);
                    Thread.sleep(2000);

                    while(Bubbles.getMotorTickCount(VSlide) < -100) {
                        Bubbles.moveMotor(VSlide, 0.3);
                        Thread.sleep(0, 50);
                    }
                    Bubbles.stopMotor(VSlide);
                }catch (InterruptedException ignore){
                    Bubbles.stopMotor(VSlide);
                }
            }
        });
        setupForDumper.start();
    }
    private void startReturnForDumper(){
        returnForDumper = new Thread(new Runnable() {
            public void run() {
                try{
                    Bubbles.moveServo(BucketDumper, 0.565);
                    Thread.sleep(2000);

                    while(Bubbles.getMotorTickCount(VSlide)> -174) {
                        Bubbles.moveMotor(VSlide, -0.4);
                        Thread.sleep(0, 50);
                    }
                    Bubbles.stopMotor(VSlide);

                    Bubbles.moveServo(BucketDumper, 0.395);
                    Thread.sleep(2000);

                    while(Bubbles.getMotorTickCount(VSlide) <= 0) {
                        Bubbles.moveMotor(VSlide, 0.3);
                        Thread.sleep(0, 50);
                    }

                    Bubbles.stopMotor(VSlide);
                }catch (InterruptedException ignore){
                    Bubbles.stopMotor(VSlide);
                }
            }
        });
        returnForDumper.start();
    }


    private void positionForSampling_Right() throws InterruptedException{
        startSetupForDumper();

        Bubbles.resetIMUGyro(Gyro);
        Bubbles.gyroTurn(0, Bubbles.getIMU(Gyro));
        Bubbles.moveRobot(45, 10, 0.9, 0.05, 4);

        Bubbles.moveMotor(PowerDown, 0.3);
        Bubbles.moveMotor(PowerUp, 0.3);

        Bubbles.gyroTurn(277, Bubbles.getIMU(Gyro));

        Bubbles.stopMotor(PowerDown);
        Bubbles.stopMotor(PowerUp);
        Bubbles.moveServo(ArmY, 0.0);
    }
    private void sample_Right()              throws InterruptedException{
        double power;
        float gyroReading;
        int cubePosition = findCubePosition_Right();

        Tools.showToast("cubePosition = " + cubePosition);
        if(cubePosition == 3) Bubbles.moveRobot(EAST, 17, 0.8, 0.05, 4);

        do{
            gyroReading = Bubbles.readIMUGyro(Gyro)[0];
            power = (gyroReading) / 100.0;
            power = (power > 0.7) ? 0.7 : (power < 0.1) ? 0.1 : power;
            Bubbles.driveAtHeader(NORTH, power, (cubePosition == 3) ? 0.45 : (cubePosition == 2) ? 0.3 : 0.2);
            Thread.sleep(0, 50);
        } while(gyroReading > 231);

        Bubbles.stopRobot();
    }
    private int  findCubePosition_Right()    throws InterruptedException{
        pineappleStrainer = new PineappleStrainer(canOfPineapple);

        Thread.sleep(500);
        Bitmap picture = canOfPineapple.getBitmap();
        pineappleChunks = pineappleStrainer
                .findShadedObject(75, 85, picture, Color.rgb(250, 200, 0), 130);

        if(pineappleChunks.doesChunkExist()){
            foundACube = (pineappleChunks.getBiggestChunk()[PineappleChunks.SIZE] > 30);
            if(foundACube){
                Bubbles.moveRobot(WEST, 8, 0.8, 0.1, 6);
                return 1;
            }
        }

        Bubbles.moveRobot(EAST, 16, 0.8, 0.1, 4);

        Thread.sleep(500);

        picture = canOfPineapple.getBitmap();
        pineappleChunks = pineappleStrainer
                .findShadedObject(80, 85, picture, Color.rgb(250, 200, 0), 130);
        if(pineappleChunks.doesChunkExist()){
            foundACube = (pineappleChunks.getBiggestChunk()[PineappleChunks.SIZE] > 30);
            if(foundACube) return 2;
        }

        return 3;
    }
    private void driveToDepo_Right()         throws InterruptedException{
        int hittingAWall = 0;
        long startTime, currentTime;
        boolean keepGoing = true;
        startTime = System.currentTimeMillis();

         while(keepGoing) {
             currentTime = System.currentTimeMillis();
             keepGoing = Bubbles.readSensor(DistanceL, DISTANCE_IN_CENTIMETERS) > 7.0 &&
                         Bubbles.readSensor(DistanceR, DISTANCE_IN_CENTIMETERS) > 7.0 &&
                         (currentTime - startTime <= 5000);
             Bubbles.driveAtHeader(NORTH, 0.5);
             Thread.sleep(0,50);
         }

        Bubbles.moveRobot(SOUTH, 1, 0.8, 0.1, 4);
        Bubbles.gyroTurn(225, Bubbles.getIMU(Gyro));
        Bubbles.driveAtHeader(WEST, 0.6);

        startTime = System.currentTimeMillis();
        while (hittingAWall < 25 && opModeIsActive()){
            Thread.sleep(0, 50);

            if ((Bubbles.readSensor(TouchyL, TOUCH_VALUE) == 1) ^ (Bubbles.readSensor(TouchyR, TOUCH_VALUE) == 1))
                hittingAWall++;
            else
                hittingAWall = 0;
            if (Bubbles.readSensor(TouchyL, TOUCH_VALUE) == 1 && Bubbles.readSensor(TouchyR, TOUCH_VALUE) == 1)
                hittingAWall = 999999999;

            currentTime = System.currentTimeMillis();
            if(currentTime - startTime >= 5000){hittingAWall = 9999999;}
        }
        Bubbles.moveRobot(EAST, 1, 0.5, 0.01, 4);
    }
    private void parkOnCrater_Right()        throws InterruptedException{
        Bubbles.moveRobot(NORTH, 65, -0.01, 1.0, 0.01, 5);

        Bubbles.moveServo(ArmY, 1.0);
        Log.d("LOOK, I MADE IT,", "LOOK IT MADE IT. HOW 'BOUT YOU?");

    }


    private void positionForSampling_Left() throws InterruptedException{
        Bubbles.resetIMUGyro(Gyro);
        Bubbles.gyroTurn(0, Bubbles.getIMU(Gyro));
        Bubbles.moveRobot(120, 10.2, 0.8, 0.05, 4);

        Bubbles.moveMotor(PowerDown, 0.3);
        Bubbles.moveMotor(PowerUp, 0.3);

        Bubbles.gyroTurn(277, Bubbles.getIMU(Gyro));

        Bubbles.stopMotor(PowerDown);
        Bubbles.stopMotor(PowerUp);

        Bubbles.moveRobot(SOUTH, 5, 0.5, 0.1, 5);
        Bubbles.moveRobot(EAST, 8, 0.5, 0.1, 5);

        Bubbles.moveServo(XThing, 0.45);
        Bubbles.moveServo(ArmY, 0.0);
    }
    private void sample_Left()              throws InterruptedException{
        int cubePosition = findCubePosition_Left();

        Tools.showToast("cubePosition = " + cubePosition);
        if(cubePosition == 3) Bubbles.moveRobot(WEST, 17, 0.7, 0.05, 4);

        Bubbles.moveRobot(NORTH, 15, 0.7, 0.1, 5);
        Bubbles.moveServo(ArmY, 1.0);
    }
    private int  findCubePosition_Left()    throws InterruptedException{
        pineappleStrainer = new PineappleStrainer(canOfPineapple);

        Thread.sleep(500);
        Bitmap picture = canOfPineapple.getBitmap();
        pineappleChunks = pineappleStrainer
                .findShadedObject(75, 85, picture, Color.rgb(250, 200, 0), 130);

        if(pineappleChunks.doesChunkExist()){
            foundACube = (pineappleChunks.getBiggestChunk()[PineappleChunks.SIZE] > 30);
            if(foundACube){
                Bubbles.moveRobot(EAST, 6, 0.8, 0.1, 6);
                return 1;
            }
        }

        Bubbles.moveRobot(WEST, 19, 0.7, 0.05, 4);

        Thread.sleep(500);

        picture = canOfPineapple.getBitmap();
        pineappleChunks = pineappleStrainer
                .findShadedObject(80, 85, picture, Color.rgb(250, 200, 0), 130);
        if(pineappleChunks.doesChunkExist()){
            foundACube = (pineappleChunks.getBiggestChunk()[PineappleChunks.SIZE] > 30);
            if(foundACube) return 2;
        }

        return 3;
    }
    private void driveToDepo_Left()         throws InterruptedException{
        int hittingAWall = 0;
        long startTime, currentTime;
        boolean keepGoing = true;
        startTime = System.currentTimeMillis();

        while(keepGoing) {
            currentTime = System.currentTimeMillis();
            keepGoing = Bubbles.readSensor(DistanceL, DISTANCE_IN_CENTIMETERS) > 7.0 &&
                    Bubbles.readSensor(DistanceR, DISTANCE_IN_CENTIMETERS) > 7.0 &&
                    (currentTime - startTime <= 5000);
            Bubbles.driveAtHeader(NORTH, 0.5);
            Thread.sleep(0,50);
        }

        Bubbles.moveRobot(SOUTH, 1, 0.4, 0.1, 4);
        Bubbles.gyroTurn(225, Bubbles.getIMU(Gyro));
        Bubbles.driveAtHeader(WEST, 0.6);

        startTime = System.currentTimeMillis();
        while (hittingAWall < 25 && opModeIsActive()){
            Thread.sleep(0, 50);

            if ((Bubbles.readSensor(TouchyL, TOUCH_VALUE) == 1) ^ (Bubbles.readSensor(TouchyR, TOUCH_VALUE) == 1))
                hittingAWall++;
            else
                hittingAWall = 0;
            if (Bubbles.readSensor(TouchyL, TOUCH_VALUE) == 1 && Bubbles.readSensor(TouchyR, TOUCH_VALUE) == 1)
                hittingAWall = 999999999;

            currentTime = System.currentTimeMillis();
            if(currentTime - startTime >= 5000){hittingAWall = 9999999;}
        }
        Bubbles.moveRobot(EAST, 1, 0.3, 0.01, 4);
    }
    private void parkOnCrater_Left()        throws InterruptedException{
        Bubbles.moveRobot(NORTH, 65, -0.01, 1.0, 0.01, 5);

        Bubbles.moveServo(ArmY, 1.0);
        Log.d("LOOK, I MADE IT,", "LOOK IT MADE IT. HOW 'BOUT YOU?");

    }


    private void dropFromLander()      throws InterruptedException{
        Bubbles.resetMotorTickCount(PowerDown, false);
        Bubbles.resetMotorTickCount(PowerUp, false);
        Bubbles.moveMotor(PowerUp, -1.0);
        Bubbles.moveMotor(PowerDown, -1.0);

        Bubbles.resetIMUGyro(Gyro);

        float[] gyroReadings = Bubbles.readIMUGyro(Gyro);

        while((gyroReadings[1] > 280 || gyroReadings[1] < 90) && (Bubbles.getMotorTickCount(PowerDown) > -12000)){
            gyroReadings = Bubbles.readIMUGyro(Gyro);
            Thread.sleep(0,50);
        }

        Bubbles.stopMotor(PowerDown);
        Bubbles.stopMotor(PowerUp);

        Bubbles.moveServo(Holder, 1.0);
    }
    private void unhookFromTheLander() throws InterruptedException{
        Bubbles.moveRobot(NORTH, 3, 0.5, 0.05, 4);
        Bubbles.moveRobot(EAST, 3, 0.5, 0.05, 4);
    }
    private void dropOffMarker()       throws InterruptedException{
        Bubbles.gyroTurn(55, Bubbles.getIMU(Gyro));

        Bubbles.moveServo(BucketDumper, 1.0);
        Thread.sleep(1000);

        startReturnForDumper();
    }
}
