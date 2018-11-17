package org.firstinspires.ftc.teamcode.Testers;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.Constants.*;
import org.firstinspires.ftc.teamcode.Robot.MecanumWheelRobot;

@TeleOp(name = "DriveTrain Tester", group = "Tester")
public class DriveTrainTester extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        MecanumWheelRobot Robo = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        waitForStart();
        while (opModeIsActive()){
            //double direction, int degrees, double spin, double maxPower, double minPower, double precision
            Robo.useDriveTrain().moveDegrees(0, 720, 0, 0.5, 0.1, 5);
            Robo.useDriveTrain().moveDegrees(180, 720, 0, 0.5, 0.1, 5);
            Robo.useDriveTrain().moveDegrees(0, 720, 0.3, 0.5, 0.1, 5);
            break;
        }
        Robo.useDriveTrain().stopRobot();
    }
}
