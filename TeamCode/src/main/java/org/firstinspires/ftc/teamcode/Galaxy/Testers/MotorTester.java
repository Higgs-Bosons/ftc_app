package org.firstinspires.ftc.teamcode.Galaxy.Testers;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Galaxy.Robot.DriveTrain;
import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;
import org.firstinspires.ftc.teamcode.Galaxy.Robot.Motors;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

@Autonomous(name = "Motor Tester", group = "Tester")
public class MotorTester extends LinearOpMode {
    @Override
    public void runOpMode(){
        MecanumWheelRobot mwr = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        waitForStart();
        while (opModeIsActive()) {
            while (gamepad1.a) {
                mwr.moveDriveMotor(LEFT_FRONT, 1.0);
            }
            while (gamepad1.b) {
                mwr.moveDriveMotor(RIGHT_FRONT, 1.0);
            }
            while (gamepad1.x) {
                mwr.moveDriveMotor(LEFT_BACK, 1.0);
            }
            while (gamepad1.y) {
                mwr.moveDriveMotor(RIGHT_BACK, 1.0);
            }
            mwr.stopRobot();
            telemetry.addData("A", "LF");
            telemetry.addData("B", "RF");
            telemetry.addData("X", "LB");
            telemetry.addData("Y", "RB");

            int[] values = mwr.getMotorValues();
            telemetry.addData("LF", values[0]);
            telemetry.addData("LB", values[1]);
            telemetry.addData("RF", values[2]);
            telemetry.addData("RB", values[3]);
            telemetry.update();

        }
    }
}
