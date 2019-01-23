package org.firstinspires.ftc.teamcode.Galaxy.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import static org.firstinspires.ftc.teamcode.Galaxy.Names.*;


@Autonomous(name = "Reset Lifter", group = "Tool")
public class ResetLifter extends LinearOpMode {

    public void runOpMode(){
        Servo holder = hardwareMap.servo.get(Holder);
        TouchSensor topTouchy = hardwareMap.touchSensor.get(TopTouchy);
        DcMotor powerUp = hardwareMap.dcMotor.get(PowerUp), powerDown = hardwareMap.dcMotor.get(PowerDown);

        waitForStart();
        holder.setPosition(0);

        powerUp.setPower(0.7);
        powerDown.setPower(0.7);
        while (opModeIsActive()){
            telemetry.addData("TopTouchy ", (topTouchy.isPressed()) ? " PRESSED!" : " . . .");
            telemetry.update();
            if(gamepad1.dpad_down){
                powerDown.setPower(-1.0);
                powerUp.setPower(-1.0);
            }else if(gamepad1.dpad_up){
                powerDown.setPower(1.0);
                powerUp.setPower(1.0);
            }else{
                powerDown.setPower(0);
                powerUp.setPower(0);
            }
        }
        powerUp.setPower(0);
        powerDown.setPower(0);
    }
}
