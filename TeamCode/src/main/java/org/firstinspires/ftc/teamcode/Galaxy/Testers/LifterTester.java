package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "LifterTester", group = "Tester")
public class LifterTester extends LinearOpMode {


    public void runOpMode() {
        DcMotor PowerUp, PowerDown;
        PowerUp = hardwareMap.dcMotor.get("PowerUp");
        PowerDown = hardwareMap.dcMotor.get("PowerDown");
        waitForStart();
        while (opModeIsActive()){
            if(gamepad1.dpad_up) {
                PowerUp.setPower(-0.8);
                PowerDown.setPower(-0.8);
            }
            else if (gamepad1.dpad_down) {
                PowerUp.setPower(0.8);
                PowerDown.setPower(0.8);
            }else{
                PowerUp.setPower(0);
                PowerDown.setPower(0);
            }
        }      
    }
}
