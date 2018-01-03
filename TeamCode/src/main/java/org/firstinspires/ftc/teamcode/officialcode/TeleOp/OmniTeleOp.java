package org.firstinspires.ftc.teamcode.officialcode.TeleOp;

import com.qualcomm.hardware.bosch.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.*;
import org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot.*;

@TeleOp(name = "TeleOp For Omni Wheels", group = "Beacon")
public class OmniTeleOp extends LinearOpMode {
    private OmniWheelRobot Robot;
    private void initialize() throws InterruptedException {
        Robot = new OmniWheelRobot();
        InitializeMotors();
        InitializeSensors();
        InitializeServos();
    }
    private void InitializeMotors(){
        DcMotor Left_Front = hardwareMap.dcMotor.get("LF");
        DcMotor Right_Front = hardwareMap.dcMotor.get("RF");
        DcMotor Left_Back = hardwareMap.dcMotor.get("LB");
        DcMotor Right_Back = hardwareMap.dcMotor.get("RB");

        Right_Front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Right_Back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Left_Front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Left_Back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);

        Robot.GiveDriveMotors(Left_Front, Right_Front, Left_Back, Right_Back);
        /*
        DcMotor ArmLifter = hardwareMap.dcMotor.get("ArmLifter");
        DcMotor HorizontalLift = hardwareMap.dcMotor.get("HorizontalLift");
        DcMotor ConveyorLower = hardwareMap.dcMotor.get("ConveyorLower");
        DcMotor ConveyorUpper = hardwareMap.dcMotor.get("ConveyorUpper");

        ArmLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        HorizontalLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ConveyorLower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ConveyorUpper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Robot.GiveAttachmentMotors(ArmLifter, HorizontalLift, ConveyorLower, ConveyorUpper);
        */

    }
    private void InitializeServos(){
        Servo FishTailLifter = hardwareMap.servo.get("FishTailLifter");
        Servo FishTailSwinger = hardwareMap.servo.get("FishTailSwinger");
        Servo Grabby = null; //= hardwareMap.servo.get("Grabby");
        Robot.GiveServos(FishTailLifter, FishTailSwinger, Grabby);


    }
    private void InitializeSensors(){
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

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id",
                hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parametersView = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parametersView.vuforiaLicenseKey = "AV0wWub/////AAAAGSKQHdIYCUOUg23YaF6tD9iJGTKb6AvM5+agdRdqaxaB" +
                "KUaNM6IktQg+50ag4j03QdDbsGNhBZwjWpdsU+kQA7EG+aaAhgKqWpzVQlvuC0320Hy8aQZTgVegtu3el9r" +
                "ly5X2CeDuM3fzhdeVOmOCwUWviYbH+6GtFlXCWOrX3i09Roe4GOTLeG7sBR7Br28I0hLTRKiwalhFtkr/IR" +
                "jJTKvdL3CQGWhY+8Q30BTEhbYxA18d88OtgZMO712LNfRnD2btkxQjEFKdND+sGo+AovdwCsVCQY/6xmyZSAh" +
                "i4FvanKtdgHFbdOrUp7MCkoA0CVh2kQfvulGLQGp/Zx3WivkYZ3+euoVTbzjcYo6721C1";
        parametersView.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(parametersView);

        VuforiaTrackables relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        relicTrackables.activate();

        Robot.GiveSensors(SuperNitron9000, imu);
    }

    public void runOpMode() throws InterruptedException{
        initialize();
        waitForStart();
        while (opModeIsActive()) {
           Check_Joystick_Control();
        }
    }

    private void Check_Joystick_Control(){
        float RFPower = (float) ((gamepad1.left_stick_x + gamepad1.left_stick_y)*0.8);
        float RBPower = (float) ((gamepad1.left_stick_y - gamepad1.left_stick_x)*0.8);
        float LFPower = (float) ((gamepad1.left_stick_y - gamepad1.left_stick_x)*0.8);
        float LBPower = (float) ((gamepad1.left_stick_x + gamepad1.left_stick_y)*0.8);
        if(Math.abs(gamepad1.right_stick_x) >= 0.1) {
            LBPower = (float)  ((gamepad1.right_stick_x)*0.2);
            LFPower = (float)  ((gamepad1.right_stick_x)*0.2);
            RBPower = (float) ((-gamepad1.right_stick_x)*0.2);
            RFPower = (float) ((-gamepad1.right_stick_x)*0.2);
        }
        Robot.driveMotors.TurnMotorsOn(LFPower,RFPower, LBPower, RBPower);
    }
    private void Check_Horizontal_Slide(){
        if (gamepad2.dpad_right) {
            Robot.attachmentMotors.getMotor(org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot.AttachmentMotors.HorizontalLift).setPower(0.8);
        }else if(gamepad2.dpad_left){
            Robot.attachmentMotors.getMotor(org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot.AttachmentMotors.HorizontalLift).setPower(-0.8);
        }else{
            Robot.attachmentMotors.getMotor(org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot.AttachmentMotors.HorizontalLift).setPower(0);
        }
    }
}

