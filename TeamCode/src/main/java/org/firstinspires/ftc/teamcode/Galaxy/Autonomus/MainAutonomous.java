package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.CanOfPineapple;
import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.PineappleChunks;
import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.PineappleStrainer;
import org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot.MecanumWheelRobot;
import org.firstinspires.ftc.teamcode.Galaxy.Tools;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.*;

@Autonomous(name = "Autonomous", group = "Autonomous")
public class MainAutonomous extends LinearOpMode{
    //---{VARIABLES}---------------------------------------
    private MecanumWheelRobot Bubbles;
    private CanOfPineapple canOfPineapple;
    private PineappleStrainer pineappleStrainer;
    private PineappleChunks pineappleChunks;
    private String craterToGoTo =  "  NULL";
    private String sideOfTheLander =  "  NULL";
    private int cubePosition;



//-------{runOpMode()}------------------------------------------------------------------------------
    public void runOpMode() {
        initializeTheRobot();
        getMenuChoices();

        final Thread thread = new Thread(new Runnable() {
            public void run() {
                if (sideOfTheLander.equals(LEFT_SIDE_OF_THE_LANDER)) {
                    Tools.showToast("LEFT");
                    sampleOnLeft();
                    Tools.showToast("TO DEPO");
                    driveToTheDepoFromLeft();
                } else {
                    Tools.showToast("RIGHT");
                    sampleOnRight();
                    Tools.showToast("TO DEPO");
                    driveToTheDepoFromRight();
                }

                canOfPineapple.closeCanOfPineapple();

                Tools.showToast("TO CRATER");
                dropOffStuffAndDriveToCrater();

                Tools.showToast("DONE");
                requestOpModeStop();
            }
        });

        waitForStart();



        thread.start();

        while(opModeIsActive()){

        }


        thread.interrupt();
        Tools.showToast(thread.isAlive()+"");
        Bubbles.stopRobot();
        telemetry.clearAll();
    }

//-------{INITIALIZATION}---------------------------------------------------------------------------
    private void initializeTheRobot(){
        telemetry.addData("INITIALIZING","ROBOT"); telemetry.update();
        Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.resetEncoders();

        telemetry.addData("INITIALIZING","SERVOS"); telemetry.update();
        Bubbles.addServo(Gate);
        Bubbles.addServo(Dumper);
        Bubbles.addServo(XThing);
        Bubbles.addServo(YThing);

        Bubbles.moveServo(XThing, 0.64);
        Bubbles.moveServo(YThing, 0.27);

        Bubbles.moveServo(Gate,0.55);
        Bubbles.moveServo(Dumper, 0);

        telemetry.addData("INITIALIZING","MOTORS"); telemetry.update();
        Bubbles.setBreakOrCoast(DcMotor.ZeroPowerBehavior.BRAKE);
        Bubbles.setMotorDirection(FORWARDS,REVERSE,REVERSE,FORWARDS);
        Bubbles.addAMotor(Grabby, NO_TAG);
        Bubbles.addAMotor(Lifter, NO_TAG);

        telemetry.addData("INITIALIZING","SENSORS"); telemetry.update();
        Bubbles.addSensor(TouchyLF,TOUCH_SENSOR);
        Bubbles.addSensor(TouchyRF,TOUCH_SENSOR);
        Bubbles.addSensor(TouchyLB,TOUCH_SENSOR);
        Bubbles.addSensor(TouchyRB,TOUCH_SENSOR);
        Bubbles.addSensor(Imu, IMU);
        Bubbles.ResetIMUGyro(Imu);

        telemetry.addData("INITIALIZING","CAN OF PINEAPPLE"); telemetry.update();
        canOfPineapple = new CanOfPineapple();
    }
    private void getMenuChoices(){
        while(!gamepad1.y || sideOfTheLander.equals("  NULL") || craterToGoTo.equals("  NULL")){
            telemetry.addData("WHICH CRATER TO GO TO    ", craterToGoTo);
            telemetry.addData("WHICH SIDE OF THE LANDER ", sideOfTheLander);
            telemetry.addLine("         PRESS Y TO CONTINUE  ");
            telemetry.update();
            if(gamepad1.dpad_left)
                sideOfTheLander = LEFT_SIDE_OF_THE_LANDER;
            else if(gamepad1.dpad_right)
                sideOfTheLander = RIGHT_SIDE_OF_THE_LANDER;

            if(gamepad1.x)
                craterToGoTo = CRATER_ON_THE_LEFT;
            else if(gamepad1.b)
                craterToGoTo = CRATER_ON_THE_RIGHT;
        }

        telemetry.addData("READY ", "-)");
        telemetry.update();
    }

//-------{LEFT SIDE}--------------------------------------------------------------------------------
    private void sampleOnLeft(){
        // 1 - 0.32, 0.61; 2 - 0.32, 0.84; 3 - 0.32, 0.42
        final int NO_CUBE = 0;
        cubePosition = findYellowCubePlacement();
        if(cubePosition == NO_CUBE){
            int biggestSize = 0;
            Bubbles.moveServo(YThing, 0.26);
            Bubbles.moveServo(XThing, 0.84);
            Bubbles.moveRobot(NORTH, 8.4, 1, 0.1, 15);

            pineappleStrainer = new PineappleStrainer(canOfPineapple);
            Bitmap picture = canOfPineapple.getBitmap();


            pineappleChunks = pineappleStrainer
                    .findShadedObject(85,90, picture, Color.rgb(250,200, 0), 130);

            if(pineappleChunks.doesChunkExist()){
                if(pineappleChunks.getBiggestChunk()[PineappleChunks.X] > 35
                        && pineappleChunks.getBiggestChunk()[PineappleChunks.X] < 65) {
                    cubePosition = 3;
                    biggestSize = pineappleChunks.getBiggestChunk()[PineappleChunks.SIZE];
                }
            }

            Bubbles.moveServo(XThing, 0.61);
            Tools.wait(333);

            picture = canOfPineapple.getBitmap();

            pineappleChunks = pineappleStrainer
                    .findShadedObject(85,90, picture, Color.rgb(250,200, 0), 130);

            if(pineappleChunks.doesChunkExist()){
                if(pineappleChunks.getBiggestChunk()[PineappleChunks.SIZE] > biggestSize &&
                        pineappleChunks.getBiggestChunk()[PineappleChunks.X] > 35 && pineappleChunks.getBiggestChunk()[PineappleChunks.X] < 65) {
                    cubePosition = 2;
                }else if(cubePosition == NO_CUBE){
                    cubePosition = 1;
                }

            }else if (cubePosition == NO_CUBE){
                cubePosition = 1;
            }

            telemetry.addData("CUBE POSITION", cubePosition);
            telemetry.update();

            if(cubePosition == 3){
                Bubbles.gyroTurn(330,Bubbles.getIMU(Imu));
            }else if (cubePosition == 1){
                Bubbles.gyroTurn(30,Bubbles.getIMU(Imu));
            }

            Bubbles.moveMotor(Grabby, -1);
            Bubbles.moveRobot(NORTH, 40, 1, 0.1, 15);


        }else{
            Bubbles.moveRobot(NORTH, 2.49, 1, 0.1, 15);
            if(cubePosition == 3){
                Bubbles.gyroTurn(330,Bubbles.getIMU(Imu));
            }else if (cubePosition == 1){
                Bubbles.gyroTurn(25,Bubbles.getIMU(Imu));
            }
            Bubbles.moveMotor(Grabby, -1);
            Bubbles.moveRobot(NORTH, 30, 1, 0.1, 15);
            Bubbles.moveRobot(SOUTH, 10, 1, 0.1, 15);
        }
    }
    private void driveToTheDepoFromLeft(){
        Bubbles.gyroTurn(90,Bubbles.getIMU(Imu));

        double movementInInches = 16 * cubePosition + 3.5;
        Bubbles.moveRobot(NORTH, movementInInches, 1, 0.1, 15);
        Bubbles.gyroTurn(45,Bubbles.getIMU(Imu));

        Bubbles.stopMotor(Grabby);
        ramIntoWall(FORWARDS);

        Bubbles.moveRobot(SOUTH, 2.4, 1, 0.1, 15);

        Bubbles.gyroTurn(315, Bubbles.getIMU(Imu));

        ramIntoWall(REVERSE);

        Bubbles.moveRobot(NORTH, 4, 0.2, 0.1, 15);
    }

//-------{RIGHT SIDE}-------------------------------------------------------------------------------
    private void sampleOnRight(){
        // 1 - 0.32, 0.61; 2 - 0.32, 0.84; 3 - 0.32, 0.42
        final int NO_CUBE = 0;
        cubePosition = findYellowCubePlacement();
        if(cubePosition == NO_CUBE){
            int biggestSize = 0;
            Bubbles.moveServo(YThing, 0.29);
            Bubbles.moveServo(XThing, 0.84);
            Bubbles.moveRobot(NORTH, 8.4, 1, 0.1, 15);

            pineappleStrainer = new PineappleStrainer(canOfPineapple);
            Bitmap picture = canOfPineapple.getBitmap();


            pineappleChunks = pineappleStrainer
                    .findShadedObject(85,90, picture, Color.rgb(250,200, 0), 130);

            if(pineappleChunks.doesChunkExist()){
                if(pineappleChunks.getBiggestChunk()[PineappleChunks.X] > 35
                        && pineappleChunks.getBiggestChunk()[PineappleChunks.X] < 65) {
                    cubePosition = 3;
                    biggestSize = pineappleChunks.getBiggestChunk()[PineappleChunks.SIZE];
                }
            }

            Bubbles.moveServo(XThing, 0.61);
            Tools.wait(333);

            picture = canOfPineapple.getBitmap();

            pineappleChunks = pineappleStrainer
                    .findShadedObject(85,90, picture, Color.rgb(250,200, 0), 130);

            if(pineappleChunks.doesChunkExist()){
                if(pineappleChunks.getBiggestChunk()[PineappleChunks.SIZE] > biggestSize &&
                        pineappleChunks.getBiggestChunk()[PineappleChunks.X] > 35 && pineappleChunks.getBiggestChunk()[PineappleChunks.X] < 65) {
                    cubePosition = 2;
                }else if(cubePosition == NO_CUBE){
                    cubePosition = 1;
                }

            }else if (cubePosition == NO_CUBE){
                cubePosition = 1;
            }

            telemetry.addData("CUBE POSITION", cubePosition);
            telemetry.update();

            if(cubePosition == 3){
                Bubbles.gyroTurn(330,Bubbles.getIMU(Imu));
            }else if (cubePosition == 1){
                Bubbles.gyroTurn(30,Bubbles.getIMU(Imu));
            }

            Bubbles.moveMotor(Grabby, -1);
            Bubbles.moveRobot(NORTH, 40, 1, 0.1, 15);


        }else{
            Bubbles.moveRobot(NORTH, 2.49, 1, 0.1, 15);
            if(cubePosition == 3){
                Bubbles.gyroTurn(330,Bubbles.getIMU(Imu));
            }else if (cubePosition == 1){
                Bubbles.gyroTurn(25,Bubbles.getIMU(Imu));
            }
            Bubbles.moveMotor(Grabby, -1);
            Bubbles.moveRobot(NORTH, 40, 1, 0.1, 15);
        }
    }
    private void driveToTheDepoFromRight(){

        Bubbles.gyroTurn(315,Bubbles.getIMU(Imu));

        ramIntoWall(FORWARDS);

        Bubbles.stopMotor(Grabby);
        Bubbles.moveRobot(SOUTH, 2.4, 1, 0.1, 15);

        Bubbles.gyroTurn(315, Bubbles.getIMU(Imu));
        Bubbles.gyroTurn(225, Bubbles.getIMU(Imu));

        ramIntoWall(REVERSE);

        Bubbles.moveRobot(NORTH, 4, 0.2, 0.1, 15);
    }

//-------{USED BY BOTH}-----------------------------------------------------------------------------
    private int findYellowCubePlacement(){
        pineappleStrainer = new PineappleStrainer(canOfPineapple);
        Bitmap picture = canOfPineapple.getBitmap();

        pineappleChunks = pineappleStrainer
                .findShadedObject(85,90, picture, Color.rgb(250,200, 0), 130);

        if(pineappleChunks.doesChunkExist()){
            return (int) (Math.floor((pineappleChunks.getBiggestChunk()[PineappleChunks.Y])/34)+1);
        }else{
            return 0;
        }
    }
    private void dropOffStuffAndDriveToCrater(){
        Bubbles.ResetIMUGyro(Imu);

        if (craterToGoTo.equals(CRATER_ON_THE_RIGHT))
            Bubbles.gyroTurn(270, Bubbles.getIMU(Imu));

        Bubbles.moveServo(Gate,0.4);
        Bubbles.moveServo(Dumper, 0.45);

        Bubbles.ResetIMUGyro(Imu);

        Bubbles.moveServo(Gate,0.55);
        Bubbles.moveServo(Dumper, 0);

        if(craterToGoTo.equals(CRATER_ON_THE_LEFT))
            Bubbles.driveAtHeader(NORTH, 1, -0.1);
        else
            Bubbles.driveAtHeader(NORTH, 1, 0.1);

        // Tools.wait(2000); // TODO Lets the robot get off of something if it is prompted up on something

        float[] imuDegrees = Bubbles.ReadIMUGyro(Imu);

        while (!((imuDegrees[1] > 3  && imuDegrees[1] < 357) ||
                (imuDegrees[2] > 3  && imuDegrees[2] < 357) ||
                (imuDegrees[0] > 30 && imuDegrees[0] < 330))) {
            imuDegrees = Bubbles.ReadIMUGyro(Imu);
        }
        Bubbles.stopRobot();
    }
    private void ramIntoWall(@MotorDirections int direction){
        int hittingAWall = 0;
        long startTime = System.currentTimeMillis();
        long currentTime;

        if (direction == FORWARDS)
            Bubbles.driveAtHeader(0, 1);
        else
            Bubbles.driveAtHeader(SOUTH, 1);


        while (hittingAWall < 25 && opModeIsActive()){
            sleep(20);

            if (direction == FORWARDS) {
                if ((Bubbles.readSensor(TouchyLF, TOUCH_VALUE) == 1) ^ (Bubbles.readSensor(TouchyRF, TOUCH_VALUE) == 1))
                    hittingAWall++;
                else
                    hittingAWall = 0;
                if (Bubbles.readSensor(TouchyLF, TOUCH_VALUE) == 1 && Bubbles.readSensor(TouchyRF, TOUCH_VALUE) == 1)
                    hittingAWall = 999999999;
            }
            else {
                if ((Bubbles.readSensor(TouchyLB, TOUCH_VALUE) == 1) ^ (Bubbles.readSensor(TouchyRB, TOUCH_VALUE) == 1))
                    hittingAWall++;
                else
                    hittingAWall = 0;
                if (Bubbles.readSensor(TouchyLB, TOUCH_VALUE) == 1 && Bubbles.readSensor(TouchyRB, TOUCH_VALUE) == 1)
                    hittingAWall = 999999999;
            }

            currentTime = System.currentTimeMillis();
            if(currentTime - startTime >= 10000){hittingAWall = 9999999;} // TODO CAN LOWER THE TIME LIMIT, AROUND 5000 milliseconds
        }
        Bubbles.stopRobot();

    }
}


