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
    public void runOpMode(){
        initializeTheRobot();

        AutonomousProgram program = new AutonomousProgram();
        telemetry.addData("READY ", "-)");
        telemetry.update();

        waitForStart();
        Bubbles.gyroTurn(90, Bubbles.getIMU("imu"));
       // program.runAutonomous();

        telemetry.addData("Cube is at position ", " " +findYellowCubePlacement());
        telemetry.update();

        sample();


        while (opModeIsActive()) {}
        program.stop();
        Bubbles.stopRobot();

    }

    private void initializeTheRobot(){
        Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.resetEncoders();
        Bubbles.addServo("Gate");
        Bubbles.addSensor("imu", IMU);
        Bubbles.setMotorDirection(FORWARDS,REVERSE,REVERSE,FORWARDS);
        Bubbles.addAMotor("Grabby", NO_TAG);
        Bubbles.addAMotor("Lifter", NO_TAG);
        Bubbles.addServo("X-Thing");
        Bubbles.addServo("Y-Thing");

        Bubbles.moveServo("X-Thing", 0.64);
        Bubbles.moveServo("Y-Thing", 0.29);
        Bubbles.setBreakOrCoast(DcMotor.ZeroPowerBehavior.FLOAT);

        canOfPineapple = new CanOfPineapple();
    }

    class AutonomousProgram implements Runnable {
        private Thread thread;
        void runAutonomous() {
           thread =  new Thread(this);
           thread.start();
        }
        public void run() {
            LanderToSampling();
        }
        void stop(){
            thread.interrupt();
        }
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

    private void sample(){
        final int NO_CUBE = 0;
        int cubePosition = findYellowCubePlacement();
        if(cubePosition == NO_CUBE){
            Bubbles.moveRobot(NORTH, 400, 0.7, 0.1, 1);
        }else{
            if(cubePosition == 1){
                
            }
        }
    }
}


