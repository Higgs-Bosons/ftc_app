package org.firstinspires.ftc.teamcode.officialcode.TeleOp;

import com.qualcomm.hardware.bosch.*;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.teamcode.officialcode.Constants;
import org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot.*;

import static org.firstinspires.ftc.teamcode.officialcode.Constants.*;
import static org.firstinspires.ftc.teamcode.officialcode.Constants.RML;

//Open Controls:
//DRIVER:
//X, Y, ALL TRIGGERS + BUMPERS


@TeleOp(name = "TeleOp", group = "Beacon")
public class OmniTeleOp extends LinearOpMode {
    private OmniWheelRobot Robot;
    private boolean GrabbingGlyph = false;
    private boolean GrabbingRelic = false;
    private int InstructionPage = 0;
    private boolean Slow = false;
    private boolean LimitedMovement = false;
    private void initialize(){
        Robot = new OmniWheelRobot();
        initializeSensors();
        initializeServos();
        initializeMotors();
    }
    private void initializeMotors(){
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

        DcMotor ArmLifter = hardwareMap.dcMotor.get("ArmLifter");
        DcMotor HorizontalLift = hardwareMap.dcMotor.get("LinearSlide");
        DcMotor ConveyorLower = hardwareMap.dcMotor.get("ConveyorLower");
        DcMotor ConveyorUpper = hardwareMap.dcMotor.get("ConveyorUpper");
        ArmLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        HorizontalLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ConveyorLower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ConveyorUpper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Robot.GiveAttachmentMotors(ArmLifter, HorizontalLift, ConveyorLower, ConveyorUpper);
        Robot.attachmentMotors.getMotor(Constants.ArmLifter).setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    private void initializeServos(){
        Servo FishTailLifter = hardwareMap.servo.get("FishTailLifter");
        Servo FishTailSwinger = hardwareMap.servo.get("FishTailSwinger");
        Servo GrabberOne = hardwareMap.servo.get("GrabberOne");
        Servo GrabberTwo = hardwareMap.servo.get("GrabberTwo");
        Servo Clampy = hardwareMap.servo.get("Clampy");
        Servo RML = hardwareMap.servo.get("RML");
        Servo Lifter = hardwareMap.servo.get("Lifter");
        Robot.GiveServos(FishTailLifter, FishTailSwinger, GrabberOne, GrabberTwo, Clampy, RML, Lifter);
    }
    private void initializeSensors(){
        ColorSensor SuperNitron9000 = hardwareMap.colorSensor.get("SuperNitron9000");
        OpticalDistanceSensor LIGHT = hardwareMap.opticalDistanceSensor.get("Light");
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
        Robot.GiveSensors(SuperNitron9000, imu, LIGHT);
    }

    public void runOpMode() throws InterruptedException{
        initialize();
        waitForStart();
        while(!gamepad1.a && !gamepad2.a){
            telemetry.addData("READY TO GO ","-)   PRESS \"A\" TO START");telemetry.update();
        }
        Runnable ControlRunnable = new CheckControls();
        Thread ControlThread = new Thread(ControlRunnable);
        ControlThread.start();
        new Thread(new Servo_Setup()).run();
        while(opModeIsActive()){}
        ControlThread.interrupt();
    }
    private void displayInfo(){
        if(gamepad1.right_stick_button || gamepad1.left_stick_button||
                gamepad2.right_stick_button || gamepad2.left_stick_button){
            InstructionPage ++;
            while((gamepad1.right_stick_button || gamepad1.left_stick_button||
                    gamepad2.right_stick_button || gamepad2.left_stick_button)){
                if(InstructionPage == 4){
                    InstructionPage = 0;
                }
            }

        }
        telemetry.clearAll();
        telemetry.addData("CONTROLS SLOWED ", (Slow+"").toUpperCase());
        telemetry.addData("CONTROLS LIMITED ", (LimitedMovement+"").toUpperCase());
        telemetry.addData("----------", "--------------");
        if(InstructionPage == 0){
            telemetry.addData("Drive Controls", "--------------------");
            telemetry.addData("Controller", " DRIVER");
            telemetry.addData("Turn", " Right Joystick X");
            telemetry.addData("Movement", " Left Joystick");
            telemetry.addData("Slow Down", " A Button");
            telemetry.addData("Limit Controls", " X Button");
            telemetry.update();
        }
        if(InstructionPage == 1){
            telemetry.addData("Conveyor Stuff", "--------------------");
            telemetry.addData("Controller", " ATTACHMENT");
            telemetry.addData("Run Upper Belt", " Forwards: Right Trigger  Backwards: Right Bumper");
            telemetry.addData("Run Lower Belt", " Forwards: Left Trigger  Backwards: Left Bumper");
            telemetry.addData("Glyph Lifter", " Up: DPad Up, Down: DPad Down");
            telemetry.addData("Grab/Release Glyph", " A Button");
            telemetry.update();
        }
        if(InstructionPage == 2){
            telemetry.addData("Horizontal Slide", "--------------------");
            telemetry.addData("Controller", " DRIVER");
            telemetry.addData("Extend", " DPad Left");
            telemetry.addData("Retract (just motor)", " DPad Right");
            telemetry.addData("Grab/Release Relic", " B Button");
            telemetry.addData("Lift Relic", " DPad Up");
            telemetry.addData("Lower Relic", " DPad Down");
            telemetry.update();
        }
        if(InstructionPage == 3){
            telemetry.addData("Other", "--------------------");
            telemetry.addData("Controller", " ATTACHMENT");
            telemetry.addData("Move FishTail out of the yay", " Y Button");
            telemetry.addData("Move Grabbers out of the way", " DPad Left or Right");
            telemetry.addData("Lower Linear Servo", " B Button");
            telemetry.addData("Raise Linear Servo", " X Button");
            telemetry.update();
        }

    }
    public class Servo_Setup implements Runnable{
        public void run(){
            Robot.servos.getServo(Servos.GrabberOne).setPosition(1);
            Robot.servos.getServo(Servos.GrabberTwo).setPosition(0);
            Robot.servos.getServo(Servos.Lifter).setPosition(0.25);
            Robot.servos.getServo(Servos.RML).setPosition(0.25);
            Robot.servos.getServo(Servos.Clampy).setPosition(1);
            Robot.Pause(1000);
            Robot.servos.getServo(Servos.GrabberOne).setPosition(0.15);
            Robot.servos.getServo(Servos.GrabberTwo).setPosition(0.45);
            Robot.servos.getServo(Servos.FishTailLifter).setPosition(0.95);
            Robot.Pause(1000);
            Robot.servos.getServo(Servos.FishTailSwinger).setPosition(0.1);
            Robot.Pause(1000);
            Robot.servos.getServo(Servos.FishTailLifter).setPosition(0.9);
        }
    }
    public class CheckControls implements Runnable{
        public void run() {
            while(opModeIsActive()){
                Check_Joystick_Control();
                Check_Conveyor();
                Check_Slide();
                Check_Other();
                displayInfo();
            }

        }
    }

    private void Check_Joystick_Control(){
        double ALL = (Math.abs(gamepad1.left_stick_x) + Math.abs(gamepad1.left_stick_y) + Math.abs(gamepad1.right_stick_x));
        if(this.gamepad1.a) {
            while(this.gamepad1.a){}
            Slow = !Slow;
        }
        if(this.gamepad1.x) {
            while(this.gamepad1.x){}
            LimitedMovement = !LimitedMovement;
        }
        if(LimitedMovement){
            if(ALL >= 0.6){
                double speed;
                double x = gamepad1.left_stick_x;
                double y = gamepad1.left_stick_y;
                if(Math.abs(x) >= Math.abs(y)){
                    y = 0;
                }
                if(Math.abs(x) <= Math.abs(y)){
                    x = 0;
                }
                if(!Slow){speed = 0.8;}else{speed = 0.4;}
                float RFPower = (float) ((x + y)*speed);
                float RBPower = (float) ((y - x)*speed);
                float LFPower = (float) ((y - x)*speed);
                float LBPower = (float) ((x + y)*speed);
                if(Math.abs(gamepad1.right_stick_x) >= 0.1) {
                    LBPower = (float)  ((gamepad1.right_stick_x)* -speed);
                    LFPower = (float)  ((gamepad1.right_stick_x)* -speed);
                    RBPower = (float) ((-gamepad1.right_stick_x)* -speed);
                    RFPower = (float) ((-gamepad1.right_stick_x)* -speed);
                }
                Robot.driveMotors.TurnMotorsOn(LFPower,RFPower, LBPower, RBPower);
            }else{
                Robot.driveMotors.TurnMotorsOn(0,0,0,0);
            }
        }else {
            if(ALL >= 0.6){
                double speed;

                if(!Slow){speed = 0.8;}else{speed = 0.4;}
                float RFPower = (float) ((gamepad1.left_stick_x + gamepad1.left_stick_y)*speed);
                float RBPower = (float) ((gamepad1.left_stick_y - gamepad1.left_stick_x)*speed);
                float LFPower = (float) ((gamepad1.left_stick_y - gamepad1.left_stick_x)*speed);
                float LBPower = (float) ((gamepad1.left_stick_x + gamepad1.left_stick_y)*speed);
                if(Math.abs(gamepad1.right_stick_x) >= 0.1) {
                    LBPower = (float)  ((gamepad1.right_stick_x)* -speed);
                    LFPower = (float)  ((gamepad1.right_stick_x)* -speed);
                    RBPower = (float) ((-gamepad1.right_stick_x)* -speed);
                    RFPower = (float) ((-gamepad1.right_stick_x)* -speed);
                }
                Robot.driveMotors.TurnMotorsOn(LFPower,RFPower, LBPower, RBPower);
            }else{
                Robot.driveMotors.TurnMotorsOn(0,0,0,0);
            }
        }

    }
    private void Check_Conveyor(){
        if(this.gamepad2.right_trigger > 0.1){
            Robot.attachmentMotors.getMotor(ConveyorUpper).setPower(-0.5);
        }else if(this.gamepad2.right_bumper){
            Robot.attachmentMotors.getMotor(ConveyorUpper).setPower(0.5);
        }else{
            Robot.attachmentMotors.getMotor(ConveyorUpper).setPower(0);
        }

        if(this.gamepad2.left_trigger > 0.1){
            Robot.attachmentMotors.getMotor(ConveyorLower).setPower(-0.5);
        }else if(this.gamepad2.left_bumper){
            Robot.attachmentMotors.getMotor(ConveyorLower).setPower(0.5);
        }else{
            Robot.attachmentMotors.getMotor(ConveyorLower).setPower(0);
        }



        if(this.gamepad2.dpad_down) {
            Robot.attachmentMotors.getMotor(ArmLifter).setPower(-0.5);
        }else if(this.gamepad2.dpad_up) {
            Robot.attachmentMotors.getMotor(ArmLifter).setPower(0.5);
        }else {
            Robot.attachmentMotors.getMotor(ArmLifter).setPower(0);
        }

        if(this.gamepad2.a){
            if(GrabbingGlyph){
                while (this.gamepad2.a){
                    GrabbingGlyph = false;
                    Robot.servos.getServo(Servos.GrabberOne).setPosition(0.25);
                    Robot.servos.getServo(Servos.GrabberTwo).setPosition(0.45);
                }
            }else{
                while (this.gamepad2.a) {
                    GrabbingGlyph = true;
                    Robot.servos.getServo(Servos.GrabberOne).setPosition(0.50);
                    Robot.servos.getServo(Servos.GrabberTwo).setPosition(0.26);
                }
            }
        }
    }
    private void Check_Slide(){
        if(this.gamepad1.b){
            if(GrabbingRelic){
                while(this.gamepad1.b){
                    GrabbingRelic = false;
                    Robot.servos.getServo(Servos.Clampy).setPosition(1);
                }
            }else{
                while(this.gamepad1.b) {
                    GrabbingRelic = true;
                    Robot.servos.getServo(Servos.Clampy).setPosition(0);
                }
            }
        }
        if(this.gamepad1.dpad_up){
            Robot.servos.getServo(RML).setPosition(0.70);
        }else if(this.gamepad1.dpad_down){
            Robot.servos.getServo(RML).setPosition(0.25);
        }
        if(this.gamepad1.dpad_right){
            Robot.attachmentMotors.getMotor(HorizontalLift).setPower(0.5);
        }else if(this.gamepad1.dpad_left){
            Robot.attachmentMotors.getMotor(HorizontalLift).setPower(-0.5);
        }else{
            Robot.attachmentMotors.getMotor(HorizontalLift).setPower(0);
        }
    }
    private void Check_Other(){
        if(this.gamepad2.dpad_left || this.gamepad2.dpad_right){
            Robot.servos.getServo(Servos.GrabberOne).setPosition(1);
            Robot.servos.getServo(Servos.GrabberTwo).setPosition(0);
        }
        if(this.gamepad2.y){
            Robot.servos.getServo(Servos.FishTailLifter).setPosition(0.95);
            Robot.Pause(1000);
            Robot.servos.getServo(Servos.FishTailSwinger).setPosition(0.1);
            Robot.Pause(1000);
            Robot.servos.getServo(Servos.FishTailLifter).setPosition(0.9);
        }
        if(this.gamepad2.b){
            Robot.servos.getServo(Servos.Lifter).setPosition(0.25);
        }
        if(this.gamepad2.x){
            Robot.servos.getServo(Servos.Lifter).setPosition(0.5);
        }
    }
}

