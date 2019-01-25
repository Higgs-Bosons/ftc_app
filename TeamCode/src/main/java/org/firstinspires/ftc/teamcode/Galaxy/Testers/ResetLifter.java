package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import static org.firstinspires.ftc.teamcode.Galaxy.Names.*;


@Autonomous(name = "Auto Reset Lifter", group = "Tool")
public class ResetLifter extends LinearOpMode {

    public void runOpMode() throws InterruptedException{
        DcMotor powerUp = hardwareMap.dcMotor.get(PowerUp), powerDown = hardwareMap.dcMotor.get(PowerDown);
        try{
            Servo holder = hardwareMap.servo.get(Holder);
            TouchSensor topTouchy = hardwareMap.touchSensor.get(TopTouchy);

            waitForStart();
            holder.setPosition(0);

            Thread.sleep(1000);

            powerUp.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            powerDown.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            powerDown.setPower(0.7);
            powerUp.setPower(0.7);

            while(!topTouchy.isPressed());
        }finally {
            powerUp.setPower(0);
            powerDown.setPower(0);
        }
    }
}
