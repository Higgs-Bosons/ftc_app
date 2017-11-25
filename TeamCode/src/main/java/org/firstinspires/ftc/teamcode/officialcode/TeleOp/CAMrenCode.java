package org.firstinspires.ftc.teamcode.officialcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleOp For Cam Wheels", group = "Beacon")
public class CAMrenCode extends LinearOpMode {
    private DcMotor Left_Front;
    private DcMotor Right_Front;
    private DcMotor Left_Back;
    private DcMotor Right_Back;
    private DcMotor ARMy;
    private Servo Arm;
    private Servo Grabby;

    private void initialize() throws InterruptedException {
        Left_Front = hardwareMap.dcMotor.get("LF");
        Right_Front = hardwareMap.dcMotor.get("RF");
        Left_Back = hardwareMap.dcMotor.get("LB");
        Right_Back = hardwareMap.dcMotor.get("RB");
        ARMy = hardwareMap.dcMotor.get("ARM-Y");

        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);

        Right_Front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Right_Back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Left_Front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Left_Back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ARMy.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Arm = hardwareMap.servo.get("Jack Smith");
        Grabby = hardwareMap.servo.get("Grabby");
        Arm.setPosition(0.3);
        Grabby.setPosition(0);
    }

    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        while (opModeIsActive()) {
            Check_Joystick_Control();
            Check_Slide();
            Check_Jack();
            Check_Arm_Stuff();
        }
    }

    private void Check_Joystick_Control() {
        Left_Front.setPower(-gamepad1.left_stick_y);
        Left_Back.setPower(-gamepad1.left_stick_y);
        Right_Front.setPower(-gamepad1.right_stick_y);
        Right_Back.setPower(-gamepad1.right_stick_y);
    }
    private void Check_Slide() {
        if (gamepad1.dpad_right) {
            while (gamepad1.dpad_right) {
                Left_Front.setPower(1.0);
                Right_Front.setPower(-1.0);
                Left_Back.setPower(-1.0);
                Right_Back.setPower(1.0);
            }
            Left_Front.setPower(0);
            Right_Front.setPower(0);
            Left_Back.setPower(0);
            Right_Back.setPower(0);
        }

        if (gamepad1.dpad_left) {
            while (gamepad1.dpad_left) {
                Left_Front.setPower(-1.0);
                Right_Front.setPower(1.0);
                Left_Back.setPower(1.0);
                Right_Back.setPower(-1.0);
            }

            Left_Front.setPower(0);
            Right_Front.setPower(0);
            Left_Back.setPower(0);
            Right_Back.setPower(0);
        }
    }
    private void Check_Jack() {
        if (gamepad1.left_trigger >= 0.1 || gamepad1.right_trigger >= 0.1) {
            Arm.setPosition(0.3);
        }
        if (gamepad1.left_bumper || gamepad1.right_bumper) {
            Arm.setPosition(0.50);
        }
    }
    private void Check_Arm_Stuff() {
        boolean isMoving = false;
        if (gamepad2.left_trigger > 0.1) {
            isMoving = true;
            ARMy.setPower(0.45);
        } else if (gamepad2.right_trigger > 0.1) {
            isMoving = true;
            ARMy.setPower(-0.5);
        }
        if (!isMoving) {
            ARMy.setPower(0);
        }

        if (gamepad2.left_bumper) {
            Grabby.setPosition(0.35);
        } else if (gamepad2.right_bumper) {
            Grabby.setPosition(0.5);
        }
    }

}

