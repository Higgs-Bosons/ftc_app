package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

import org.firstinspires.ftc.teamcode.Galaxy.Autonomus.AutonomousCode;
import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;

@Autonomous(name = "DriveTrain Tester", group = "Tester")
public class DriveTrainTester extends LinearOpMode {
    public void runOpMode(){
        MecanumWheelRobot Robo = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        Robo.setMotorDirection(FORWARDS,REVERSE,REVERSE,FORWARDS);
        AutonomousCode code = new AutonomousCode(Robo);

        code.addAction(DRIVE_ROBOT, 0, 180, 0.5f, 0.1f, 5);
        waitForStart();
        code.runProgram();

        while (opModeIsActive()){}

        Robo.stopRobot();
    }
}
