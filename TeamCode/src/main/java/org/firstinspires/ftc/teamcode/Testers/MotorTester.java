package org.firstinspires.ftc.teamcode.Testers;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.DriveTrain;
import org.firstinspires.ftc.teamcode.Robots.Motors;
import org.firstinspires.ftc.teamcode.Robots.customErrors;

import static org.firstinspires.ftc.teamcode.Constants.*;

@TeleOp(name = "Motor Tester", group = "Tester")
public class MotorTester extends LinearOpMode {
    @Override
    public void runOpMode(){
        Motors motors = new Motors(hardwareMap);
        DriveTrain dt = new DriveTrain(motors.getAutoDriveTrain(FIRST_LETTER_NO_SPACE_UPPERCASE));
        waitForStart();
        while (opModeIsActive()) {
            while (gamepad1.a) {
                dt.moveMotor(LEFT_FRONT, 1.0);
            }
            while (gamepad1.b) {
                dt.moveMotor(RIGHT_FRONT, 1.0);
            }
            while (gamepad1.x) {
               dt.moveMotor(LEFT_BACK, 1.0);
            }
            while (gamepad1.y) {
                dt.moveMotor(RIGHT_BACK, 1.0);
            }
            dt.stopRobot();
            telemetry.addData("A", "LF");
            telemetry.addData("B", "RF");
            telemetry.addData("X", "LB");
            telemetry.addData("Y", "RB");
            telemetry.update();

        }
    }
}
