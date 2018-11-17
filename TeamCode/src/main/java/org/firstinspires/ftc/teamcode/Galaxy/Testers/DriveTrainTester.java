package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;
import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;

@TeleOp(name = "DriveTrain Tester", group = "Tester")
public class DriveTrainTester extends LinearOpMode {
    public void runOpMode(){
        MecanumWheelRobot Robo = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        waitForStart();
        while (opModeIsActive()){
            Robo.moveDegrees(NORTH, 720, 0, 0.5, 0.1, 5);
            Robo.moveDegrees(SOUTH, 720, 0, 0.5, 0.1, 5);
            Robo.moveDegrees(NORTH, 720, 0.3, 0.5, 0.1, 5);
            break;
        }
        Robo.stopRobot();
    }
}
