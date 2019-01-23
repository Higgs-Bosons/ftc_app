package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

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
    private PineappleChunks pineappleChunks;
    private boolean foundACube = false;
    private Thread telemetryThread;
    private int cubePosition;

    public void runOpMode() throws InterruptedException {
        try{
            initializeRobot();
            initializeTelemetryThread();

            waitForStart();

            telemetryThread.start();

            dropFromLander();
            unhookFromTheLander();
            positionForSampling();
            sample();
            driveToDepo();
            dropOffMarker();

            while(opModeIsActive());
            requestOpModeStop();

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
        Bubbles.addSensor(DistanceL, DISTANCE_SENSOR);
        Bubbles.addSensor(DistanceR, DISTANCE_SENSOR);
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

        telemetry.addData("SETTING UP SERVOS","");
        telemetry.addData("----------|-"," 80%");
        telemetry.update();
        Bubbles.moveServo(XThing, 0.25);
        Bubbles.moveServo(YThing, 0.685);
        Bubbles.moveServo(Grabby, 0.6);
        Bubbles.moveServo(WeightLifter, 0.7);

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

    private void dropFromLander()      throws InterruptedException{
        Bubbles.resetMotorTickCount(PowerDown, false);
        Bubbles.resetMotorTickCount(PowerUp, false);
        Bubbles.moveMotor(PowerUp, -1.0);
        Bubbles.moveMotor(PowerDown, -1.0);

        Bubbles.resetIMUGyro(Gyro);

        float[] gyroReadings = Bubbles.readIMUGyro(Gyro);

        while((gyroReadings[1] > 280 || gyroReadings[1] < 90) && (Bubbles.getMotorTickCount(PowerUp) > -12000)){
            gyroReadings = Bubbles.readIMUGyro(Gyro);
            Thread.sleep(0,50);
        }

        Bubbles.stopMotor(PowerDown);
        Bubbles.stopMotor(PowerUp);

        Bubbles.moveServo(Holder, 1.0);
    }
    private void unhookFromTheLander() throws InterruptedException{
        Bubbles.moveRobot(NORTH, 3, 0.3, 0.05, 4);
        Bubbles.moveRobot(EAST, 3, 0.3, 0.05, 4);

        Bubbles.moveMotor(PowerDown, 0.4);
        Bubbles.moveMotor(PowerUp, 0.4);
    }
    private void positionForSampling() throws InterruptedException{
        Bubbles.resetIMUGyro(Gyro);
        Bubbles.gyroTurn(0, Bubbles.getIMU(Gyro));
        Bubbles.moveRobot(45, 10, 0.5, 0.05, 4);

        Bubbles.stopMotor(PowerDown);
        Bubbles.stopMotor(PowerUp);

        Bubbles.gyroTurn(277, Bubbles.getIMU(Gyro));
        Bubbles.moveServo(ArmY, 0.0);
    }
    private void sample()              throws InterruptedException{
        double power;
        float gyroReading;
        cubePosition = findCubePosition();

        Tools.showToast("cubePosition = " + cubePosition);
        if(cubePosition == 3) Bubbles.moveRobot(EAST, 17, 0.6, 0.05, 4);

        do{
            gyroReading = Bubbles.readIMUGyro(Gyro)[0];
            power = (gyroReading) / 100.0;
            power = (power > 0.7) ? 0.7 : (power < 0.1) ? 0.1 : power;
            Bubbles.driveAtHeader(NORTH, power, 0.32);
        } while(gyroReading > 231);

        Bubbles.stopRobot();
    }
    private int  findCubePosition()    throws InterruptedException{
        pineappleStrainer = new PineappleStrainer(canOfPineapple);

        Thread.sleep(500);
        Bitmap picture = canOfPineapple.getBitmap();
        pineappleChunks = pineappleStrainer
                .findShadedObject(80, 85, picture, Color.rgb(250, 200, 0), 130);

        if(pineappleChunks.doesChunkExist()){
            foundACube = (pineappleChunks.getBiggestChunk()[PineappleChunks.SIZE] > 30);
            if(foundACube) return 1;
        }

        Bubbles.moveRobot(EAST, 16, 0.6, 0.05, 4);

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
    private void driveToDepo()         throws InterruptedException{
         while(Bubbles.readSensor(DistanceL, DISTANCE_IN_CENTIMETERS) > 7.0
                 && Bubbles.readSensor(DistanceR, DISTANCE_IN_CENTIMETERS) > 7.0)
             Bubbles.driveAtHeader(NORTH, 0.4);

         if(cubePosition != 1)
             Bubbles.moveRobot(WEST, (cubePosition == 2) ? 10 : 20, 0.6, 0.1, 5);

    }
    private void dropOffMarker()       throws InterruptedException{
        Bubbles.moveServo(WeightLifter, 0.25);
        Thread.sleep(400);
        Bubbles.moveServo(Grabby, 1.0);
        Thread.sleep(400);
        Bubbles.moveServo(WeightLifter, 0.7);
        Thread.sleep(50);
        Bubbles.moveServo(Grabby, 0.6);
    }
    private void parkOnCrater()        throws InterruptedException{
        // TODO    Next motions: pivot and turn 90 degrees, back into wall, turn, drive to right crater.
    }
}
