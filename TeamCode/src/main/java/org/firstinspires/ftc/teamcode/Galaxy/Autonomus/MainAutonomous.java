package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.CanOfPineapple;
import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.PineappleChunks;
import org.firstinspires.ftc.teamcode.Galaxy.ImageCapturing.PineappleStrainer;
import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;
import org.firstinspires.ftc.teamcode.Galaxy.Tools;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

@Autonomous(name = "Autonomous", group = "Autonomous")
public class MainAutonomous extends LinearOpMode{
    private MecanumWheelRobot Bubbles;
    private CanOfPineapple canOfPineapple;
    private int cubePosition;

    public void runOpMode(){
        initializeTheRobot();

        telemetry.addData("READY ", "-)");
        telemetry.update();

        waitForStart();

        sampleOnTheRight();
        driveToTheDepoOnTheRight();
        dropOffStuffAndDriveToCrater();



        Bubbles.stopRobot();
        canOfPineapple.closeCanOfPineapple();

    }

    private void initializeTheRobot(){
        Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.resetEncoders();
        Bubbles.addServo("Gate");
        Bubbles.addServo("Dumper");
        Bubbles.addSensor("imu", IMU);

        Bubbles.setMotorDirection(FORWARDS,REVERSE,REVERSE,FORWARDS);
        Bubbles.addAMotor("Grabby", NO_TAG);
        Bubbles.addAMotor("Lifter", NO_TAG);

        Bubbles.addSensor("TouchyL",TOUCH_SENSOR);
        Bubbles.addSensor("TouchyR",TOUCH_SENSOR);

        Bubbles.addServo("X-Thing");
        Bubbles.addServo("Y-Thing");

        Bubbles.moveServo("X-Thing", 0.64);
        Bubbles.moveServo("Y-Thing", 0.29);
        Bubbles.setBreakOrCoast(DcMotor.ZeroPowerBehavior.FLOAT);

        canOfPineapple = new CanOfPineapple();
    }

    private void LanderToSampling(){
        Bubbles.gyroTurn(90, Bubbles.getIMU("imu"));
        telemetry.addData("Cube is at position ", " " +findYellowCubePlacement());
        Tools.showToast("Cube is at position" + findYellowCubePlacement());
    }
    private int findYellowCubePlacement(){
        PineappleStrainer pineappleStrainer = new PineappleStrainer(canOfPineapple);
        PineappleChunks pineappleChunks;
        Bitmap picture = canOfPineapple.getBitmap();

        pineappleChunks = pineappleStrainer
                .findShadedObject(80,90, picture, Color.rgb(250,200, 0), 130);

        if(pineappleChunks.doesChunkExist()){
            return (int) (Math.floor((pineappleChunks.getBiggestChunk()[PineappleChunks.Y])/34)+1);
        }else{
            return 0;
        }
    }

    private void sampleOnTheRight(){
        final int NO_CUBE = 0;
        cubePosition = findYellowCubePlacement();
        if(cubePosition == NO_CUBE){
            Bubbles.moveRobot(NORTH, 400, 0.7, 0.1, 15);
        }else{
            Bubbles.moveRobot(NORTH, 2.49, 0,0.7, 0.1, 15);
            if(cubePosition == 3){
                Bubbles.gyroTurn(327,Bubbles.getIMU("imu"));
            }else if (cubePosition == 1){
                Bubbles.gyroTurn(25,Bubbles.getIMU("imu"));
            }
            Bubbles.moveMotor("Grabby", -1);
            Bubbles.moveRobot(NORTH, 37.37, 0.7, 0.1, 15);
        }
    }

    private void driveToTheDepoOnTheRight(){
        if (cubePosition == 2 || cubePosition == 3)
            Bubbles.gyroTurn(315,Bubbles.getIMU("imu"));
        else
            Bubbles.gyroTurn(45, Bubbles.getIMU("imu"));


        Bubbles.stopMotor("Grabby");

        driveUntilItHitsAWall();

        Bubbles.moveRobot(SOUTH, 3.36, 0.7, 0.1, 15);

        Bubbles.ResetIMUGyro("imu");

        if (cubePosition == 3 || cubePosition == 2)
            Bubbles.gyroTurn(90, Bubbles.getIMU("imu"));
        else
            Bubbles.gyroTurn(270, Bubbles.getIMU("imu"));

        driveUntilItHitsAWall();

        Bubbles.moveRobot(NORTH, 4.98, 0.7, 0.4, 15);
    }
    private void dropOffStuffAndDriveToCrater(){
        Bubbles.ResetIMUGyro("imu");
        Bubbles.moveServo("Gate",0.4);
        Bubbles.moveServo("Dumper", 0.5);
        Bubbles.pause(1000);
        Bubbles.moveServo("Gate",0.55);
        Bubbles.moveServo("Dumper", 0);
        Bubbles.pause(1000);
    }
    private void driveUntilItHitsAWall(){
        int hittingAWall = 0;
        Bubbles.driveAtHeader(0, 0.5);
        while (hittingAWall < 100){
            if((Bubbles.readSensor("TouchyL", TOUCH_VALUE) == 1) ^ (Bubbles.readSensor("TouchyR", TOUCH_VALUE) == 1))
                hittingAWall++;
            else
                hittingAWall = 0;
            if(Bubbles.readSensor("TouchyL", TOUCH_VALUE) == 1 && Bubbles.readSensor("TouchyR", TOUCH_VALUE) == 1)
                hittingAWall = 999999999;
        }
        Bubbles.stopRobot();

    }
}


