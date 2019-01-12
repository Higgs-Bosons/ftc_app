package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.Galaxy.Names.*;

@Autonomous(name = "CameraServoTester", group = "Tester")
public class CameraServoTester extends LinearOpMode {
    public void runOpMode() {
        Servo x = hardwareMap.servo.get(XThing);
        Servo y = hardwareMap.servo.get(YThing);
        x.setPosition(0.5);
        y.setPosition(0.5);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_left){
                x.setPosition(x.getPosition() - 0.05);
                while (gamepad1.dpad_left);
            }
            if (gamepad1.dpad_right){
                x.setPosition(x.getPosition() + 0.05);
                while (gamepad1.dpad_right);
            }
            if (gamepad1.dpad_up){
                y.setPosition(y.getPosition() + 0.05);
                while (gamepad1.dpad_up);
            }
            if (gamepad1.dpad_down){
                y.setPosition(y.getPosition() - 0.05);
                while (gamepad1.dpad_down);
            }

            telemetry.addData("X-Thingy", x.getPosition());
            telemetry.addData("Y-Thingy", y.getPosition());
            telemetry.update();
            //X - 0.7 0.45 0.2
            //Y - 0.25 0.6 0.85
        }
    }
}
