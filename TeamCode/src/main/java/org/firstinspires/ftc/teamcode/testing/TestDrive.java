package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Higgs Bosons on 10/29/2016.
 */

@TeleOp (name = "Dankliop", group="Memes")
@Disabled
public class TestDrive extends OpMode {
    DcMotor LeftB;
    DcMotor LeftF;
    DcMotor RightB;
    DcMotor RightF;
    double left;
    double right;

    @Override
    public void init() {
        LeftF = hardwareMap.dcMotor.get("LeftF");
        LeftB = hardwareMap.dcMotor.get("LeftB");
        RightF = hardwareMap.dcMotor.get("RightF");
        RightB = hardwareMap.dcMotor.get("RightB");
        LeftF.setDirection(DcMotor.Direction.REVERSE);
        LeftB.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        left = gamepad1.left_stick_y;
        right = gamepad1.right_stick_y;
        LeftB.setPower(left);
        LeftF.setPower(left);
        RightB.setPower(right);
        RightF.setPower(right);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
