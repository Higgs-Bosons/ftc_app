package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Galaxy.Names;

@Autonomous(name = "Servo Tester", group = "Tester")
public class ServoTester extends LinearOpMode {
    public void runOpMode() {
        Servo grabby = hardwareMap.servo.get(Names.BucketDumper);
        grabby.setPosition(0.5);
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                grabby.setPosition(grabby.getPosition() - 0.05);
                while (gamepad1.dpad_up);
            }
            if (gamepad1.dpad_down) {
                grabby.setPosition(grabby.getPosition() + 0.05);
                while (gamepad1.dpad_down);
            }

            telemetry.addData("Bucket Dumper", grabby.getPosition());

            telemetry.update();
        }
    }
}
