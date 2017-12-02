package org.firstinspires.ftc.teamcode.officialcode.TeleOp;

import com.qualcomm.hardware.bosch.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.teamcode.officialcode.Robot.*;

@TeleOp(name = "TeleOp For Cam Wheels", group = "Beacon")
public class CAMraCode extends LinearOpMode {
    private CrabbingRobot Crabby;
    public static String COLOR = "NULL";
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

        ColorSensor SuperNitron9000 = hardwareMap.colorSensor.get("SuperNitron9000");
        BNO055IMU imu;
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "imu";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        Crabby.GiveDriveMotors(Left_Front, Right_Front,Left_Back,Right_Back);
        Crabby.GiveServos(FishTail, JackSmith, Grabby);
        Crabby.GiveAttachmentMotors(ARMy);
        Crabby.GiveSensors(SuperNitron9000,imu);
    }
    public void runOpMode() throws InterruptedException{
        initialize();
        waitForStart();
        while (opModeIsActive()) {
            Check_Joystick_Control();
            Check_Slide();
            Check_Jack();
            Check_Arm_Stuff();
            Check_Other();
            telemetry.addData("GYRO",Crabby.sensors.ReadGyro());
            telemetry.addData("COLOR", COLOR);
            telemetry.update();
        }
    }

    private void Check_Joystick_Control(){
        Crabby.driveMotors.TurnMotorsOn(-gamepad1.left_stick_y,-gamepad1.right_stick_y,-gamepad1.left_stick_y,-gamepad1.right_stick_y);
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
            Crabby.servos.getServo(Servos.JackSmith).setPosition(0.5);
        }
        if (gamepad1.left_bumper || gamepad1.right_bumper) {
            Crabby.servos.getServo(Servos.JackSmith).setPosition(0.3);
        }
    }
    private void Check_Arm_Stuff(){
        boolean isMoving = false;
        if (gamepad2.right_trigger > 0.1) {
            isMoving = true;
            Crabby.attachmentMotors.getMotor(AttachmentMotors.ArmLifter).setPower(0.1);
        } else if (gamepad2.left_trigger > 0.1) {
            isMoving = true;
            Crabby.attachmentMotors.getMotor(AttachmentMotors.ArmLifter).setPower(-0.6);
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
    private void Check_Other(){
        if(gamepad2.y){
            Crabby.servos.getServo(Servos.FishTail).setPosition(0.16);
        }
    }

}

