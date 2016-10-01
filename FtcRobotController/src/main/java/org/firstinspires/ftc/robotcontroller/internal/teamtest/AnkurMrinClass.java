package org.firstinspires.ftc.robotcontroller.internal.teamtest;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


/**
 * Created by Higgs Bosons on 10/1/2016.
 */
@Autonomous (name="AnkurMrinClass", group ="Tests")
public class AnkurMrinClass extends LinearOpMode {
    DcMotor leftB;
    DcMotor leftF;
    DcMotor rightB;
    DcMotor rightF;

    @Override
    public void runOpMode() throws InterruptedException {
        leftB = hardwareMap.dcMotor.get("leftBack");
        leftF = hardwareMap.dcMotor.get("leftFront");
        rightB = hardwareMap.dcMotor.get("rightBack");
        rightF = hardwareMap.dcMotor.get("rightFront");
        rightB.setDirection(DcMotor.Direction.REVERSE);
        rightB.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        leftF.setPower(1.0d);
        rightF.setPower(-1.0d);
        leftB.setPower(1.0d);
        rightB.setPower(-1.0d);

        Thread.sleep(2000);

        leftF.setPower(0.0d);
        rightF.setPower(0.3d);
        leftB.setPower(0.0d);
        rightB.setPower(0.3d);

        Thread.sleep(2000);
    }
}
