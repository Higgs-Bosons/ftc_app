package org.firstinspires.ftc.teamcode.officialcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.officialcode.Robot.AttachmentMotors;
import org.firstinspires.ftc.teamcode.officialcode.Robot.CrabbingRobot;
import org.firstinspires.ftc.teamcode.officialcode.Robot.Servos;

@TeleOp(name = "TeleOp For Cam Wheels", group = "Beacon")
public class CAMrenCode extends LinearOpMode {
    private CrabbingRobot Crabby;

    private void initialize() throws InterruptedException{
        Crabby = new CrabbingRobot();
        DcMotor Left_Front = hardwareMap.dcMotor.get("LF");
        DcMotor Right_Front = hardwareMap.dcMotor.get("RF");
        DcMotor Left_Back = hardwareMap.dcMotor.get("LB");
        DcMotor Right_Back = hardwareMap.dcMotor.get("RB");
        DcMotor  ARMy = hardwareMap.dcMotor.get("ARM-Y");

        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);

        Right_Front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Right_Back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Left_Front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Left_Back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ARMy.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Servo JackSmith = hardwareMap.servo.get("Jack Smith");
        Servo Grabby = hardwareMap.servo.get("Grabby");
        Servo FishTail = hardwareMap.servo.get("FishTail");
        JackSmith.setPosition(0.3);
        Grabby.setPosition(0);

        Crabby.GiveDriveMotors(Left_Front, Right_Front,Left_Back,Right_Back);
        Crabby.GiveServos(FishTail, JackSmith, Grabby);
        Crabby.GiveAttachmentMotors(ARMy);
    }
    public void runOpMode() throws InterruptedException{
        initialize();
        waitForStart();
        while (opModeIsActive()) {
            Check_Joystick_Control();
            Check_Slide();
            Check_Jack();
            Check_Arm_Stuff();
        }
    }

    private void Check_Joystick_Control(){
        Crabby.driveMotors.TurnMotorsOn(-gamepad1.left_stick_y,-gamepad1.left_stick_y,-gamepad1.right_stick_y,-gamepad1.right_stick_y);
    }
    private void Check_Slide(){
        if (gamepad1.dpad_right) {
            while (gamepad1.dpad_right) {
                Crabby.driveMotors.TurnMotorsOn(0.8,-0.8,-0.8,0.8);
            }
            Crabby.driveMotors.STOP();
        }

        if (gamepad1.dpad_left) {
            while (gamepad1.dpad_left) {
                Crabby.driveMotors.TurnMotorsOn(-0.8,0.8,0.8,-0.8);
            }
            Crabby.driveMotors.STOP();
        }
    }
    private void Check_Jack(){
        if (gamepad1.left_trigger >= 0.1 || gamepad1.right_trigger >= 0.1) {
            Crabby.servos.getServo(Servos.JackSmith).setPosition(0.3);
        }
        if (gamepad1.left_bumper || gamepad1.right_bumper) {
            Crabby.servos.getServo(Servos.JackSmith).setPosition(0.5);
        }
    }
    private void Check_Arm_Stuff(){
        boolean isMoving = false;
        if (gamepad2.left_trigger > 0.1) {
            isMoving = true;
            Crabby.attachmentMotors.getMotor(AttachmentMotors.ArmLifter).setPower(0.45);
        } else if (gamepad2.right_trigger > 0.1) {
            isMoving = true;
            Crabby.attachmentMotors.getMotor(AttachmentMotors.ArmLifter).setPower(-0.5);
        }
        if (!isMoving) {
            Crabby.attachmentMotors.getMotor(AttachmentMotors.ArmLifter).setPower(0);
        }

        if (gamepad2.left_bumper) {
            Crabby.servos.getServo(Servos.Grabby).setPosition(0.6);
        } else if (gamepad2.right_bumper) {
            Crabby.servos.getServo(Servos.Grabby).setPosition(0.75);
        }
    }

}

