package org.firstinspires.ftc.teamcode.Testers;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.Motors;
import org.firstinspires.ftc.teamcode.Robots.customErrors;

@TeleOp(name = "Motor Tester", group = "Tester")
public class MotorTester extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Motors motors = new Motors();
        motors.addAMotor("LF", Motors.NO_TAG);
        motors.addAMotor("RF", Motors.NO_TAG);
        motors.addAMotor("LB", Motors.NO_TAG);
        motors.addAMotor("RB", Motors.NO_TAG);

        waitForStart();
        while (opModeIsActive()) {
            while (gamepad1.a) {
                try {
                    motors.getMotorByName("LF").setPower(1);
                } catch (customErrors.motorNotFoundException e) {
                    e.printStackTrace();
                }
            }
            while (gamepad1.b) {
                try {
                    motors.getMotorByName("RF").setPower(1);
                } catch (customErrors.motorNotFoundException e) {
                    e.printStackTrace();
                }
            }
            while (gamepad1.x) {
                try {
                    motors.getMotorByName("LB").setPower(1);
                } catch (customErrors.motorNotFoundException e) {
                    e.printStackTrace();
                }
            }
            while (gamepad1.y) {
                try {
                    motors.getMotorByName("RB").setPower(1);
                } catch (customErrors.motorNotFoundException e) {
                    e.printStackTrace();
                }
            }
            telemetry.addData("A", "LF");
            telemetry.addData("B", "RF");
            telemetry.addData("X", "LB");
            telemetry.addData("Y", "RB");
            telemetry.update();

        }
    }
}
