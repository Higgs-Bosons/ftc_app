package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Created by Higgs Bosons on 12/3/2016.
 */

@Autonomous(name = "test", group = "auto")
@Disabled
public class AutoTest extends LinearOpMode{
    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor rightFront;
    DcMotor rightBack;

    @Override
    public void runOpMode() throws InterruptedException {
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftRear");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightRear");

        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        //leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        leftFront.setPower(0.2d);
        leftBack.setPower(0.2d);
        rightBack.setPower(0.2d);
        rightFront.setPower(0.2d);

        while(opModeIsActive()) {
            Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
            idle();
        }
    }
}
