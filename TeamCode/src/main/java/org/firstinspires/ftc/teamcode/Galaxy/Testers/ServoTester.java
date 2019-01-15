package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Servo Tester", group = "Tester")
public class ServoTester extends LinearOpMode {
    public void runOpMode() {
        Servo grabby = hardwareMap.servo.get("Grabby");
        Servo weightLifter = hardwareMap.servo.get("WeightLifter");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_left) {
                grabby.setPosition(grabby.getPosition() - 0.05);
                while (gamepad1.dpad_left);
            }
            if (gamepad1.dpad_right) {
                grabby.setPosition(grabby.getPosition() + 0.05);
                while (gamepad1.dpad_right);
            }
            if (gamepad1.dpad_up) {
                weightLifter.setPosition(weightLifter.getPosition() + 0.05);
                while (gamepad1.dpad_up);
            }
            if (gamepad1.dpad_down) {
                weightLifter.setPosition(weightLifter.getPosition() - 0.05);
                while (gamepad1.dpad_down);
            }

            telemetry.addData("Grabby", grabby.getPosition());
            telemetry.addData("WeightLifter", weightLifter.getPosition());
            telemetry.update();


            // Grab - 0.6 Lift - 0.7
            //        1          0.25
            //        0.65       0.4

        }
    }
}
