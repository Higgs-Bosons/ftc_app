package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

@Autonomous(name = "Autonomous", group = "Autonomous")
public class MainAutonomus extends LinearOpMode{
    MecanumWheelRobot Bubbles;
    public void runOpMode(){
        Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.resetEncoders();
        Bubbles.addServo("Gate");
        Bubbles.setMotorDirection(FORWARDS,REVERSE,REVERSE,FORWARDS);
        Bubbles.addAMotor("Grabby", NO_TAG);
        Bubbles.addAMotor("Lifter", NO_TAG);
        Bubbles.addServo("X-Thing");
        Bubbles.addServo("Y-Thing");
        Bubbles.setBreakOrCoast(DcMotor.ZeroPowerBehavior.FLOAT);

        AutonomousProgram program = new AutonomousProgram();
        waitForStart();

        program.runAutonomous();

        while (opModeIsActive()) {}
        program.stop();
        Bubbles.stopRobot();

    }

    class AutonomousProgram implements Runnable {
        private Thread thread;
        void runAutonomous() {
           thread =  new Thread(this);
           thread.start();
        }
        public void run() {
            Bubbles.moveDegrees(NORTH, 360, 0.8, 0.2, 10);
        }
        void stop(){
            thread.interrupt();
        }
    }
}


