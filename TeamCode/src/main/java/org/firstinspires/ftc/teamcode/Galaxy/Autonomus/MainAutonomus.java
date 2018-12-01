package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

@Autonomous(name = "Autonomus", group = "Autonomus")
public class MainAutonomus extends LinearOpMode{

    public void runOpMode(){
        MecanumWheelRobot Bubbles = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Bubbles.resetEncoders();
        Bubbles.addServo("Gate");
        Bubbles.setMotorDirection(FORWARDS,REVERSE,REVERSE,FORWARDS);
        Bubbles.addAMotor("Grabby", NO_TAG);
        Bubbles.addAMotor("Lifter", NO_TAG);
        Bubbles.addServo("X-Thing");
        Bubbles.addServo("Y-Thing");
        Bubbles.setBreakOrCoast(DcMotor.ZeroPowerBehavior.FLOAT);

        AutonomousCode autonomousCode = new AutonomousCode(Bubbles);

        autonomousCode.addAction(DRIVE_ROBOT, NORTH, 720, 0.7, 0.1, 10);
        waitForStart();

        autonomousCode.runProgram();

        while (opModeIsActive()){
            int[] values = Bubbles.getMotorValues();
            telemetry.addData("LF", values[0]);
            telemetry.addData("LB", values[1]);
            telemetry.addData("RF", values[2]);
            telemetry.addData("RB", values[3]);
            telemetry.update();
        }

        autonomousCode.stopProgram();
        Bubbles.stopRobot();
    }
}
