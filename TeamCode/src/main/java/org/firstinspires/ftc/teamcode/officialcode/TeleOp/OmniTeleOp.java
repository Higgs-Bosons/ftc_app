package org.firstinspires.ftc.teamcode.officialcode.TeleOp;

import com.qualcomm.hardware.bosch.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot.*;

import static org.firstinspires.ftc.teamcode.officialcode.Constants.*;
import static org.firstinspires.ftc.teamcode.officialcode.Constants.RML;

@TeleOp(name = "TeleOp", group = "Beacon")
public class OmniTeleOp extends LinearOpMode {
    private OmniWheelRobot Omni;
    private boolean GrabbingGlyph = false;
    private boolean GrabbingRelic = false;
    private int InstructionPage = 0;
    private boolean Slow = false;
    private boolean LimitedMovement = false;
    private void initialize(){
        Omni = new OmniWheelRobot();
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

        Omni.GiveDriveMotors(Left_Front, Right_Front, Left_Back, Right_Back);

        DcMotor ArmLifter = hardwareMap.dcMotor.get("ArmLifter");
        DcMotor SlideExtender = hardwareMap.dcMotor.get("LinearSlide");
        DcMotor SlideRetracter = hardwareMap.dcMotor.get("SlideRetractor");
        DcMotor Conveyor = hardwareMap.dcMotor.get("Conveyor");

        ArmLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SlideExtender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SlideRetracter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Conveyor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Omni.GiveAttachmentMotors(ArmLifter, Conveyor, SlideExtender, SlideRetracter);
    }
    private void initializeServos(){
        Servo FishTailLifter = hardwareMap.servo.get("FishTailLifter");
        Servo FishTailSwinger = hardwareMap.servo.get("FishTailSwinger");
        Servo GrabberOne = hardwareMap.servo.get("GrabberOne");
        Servo GrabberTwo = hardwareMap.servo.get("GrabberTwo");
        Servo GrabberSpinOne = hardwareMap.servo.get("Grabber1");
        Servo GrabberSpinTwo = hardwareMap.servo.get("Grabber2");
        Servo Clampy = hardwareMap.servo.get("Clampy");
        Servo RML = hardwareMap.servo.get("RML");
        Servo Lifter = hardwareMap.servo.get("Lifter");
        Omni.GiveServos(FishTailLifter, FishTailSwinger, GrabberOne, GrabberTwo, Clampy, RML, Lifter,GrabberSpinOne, GrabberSpinTwo);
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
        Omni.GiveSensors(SuperNitron9000, imu, LIGHT);
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
            Omni.servos.getServo(Servos.GrabberOne).setPosition(1);
            Omni.servos.getServo(Servos.GrabberTwo).setPosition(0);
            Omni.servos.getServo(Servos.Lifter).setPosition(0.25);
            Omni.servos.getServo(Servos.RML).setPosition(0.25);
            Omni.servos.getServo(Servos.Clampy).setPosition(1);
            Omni.Pause(1000);
            Omni.servos.getServo(Servos.GrabberOne).setPosition(0.15);
            Omni.servos.getServo(Servos.GrabberTwo).setPosition(0.45);
            Omni.servos.getServo(Servos.FishTailLifter).setPosition(0.95);
            Omni.Pause(1000);
            Omni.servos.getServo(Servos.FishTailSwinger).setPosition(0.1);
            Omni.Pause(1000);
            Omni.servos.getServo(Servos.FishTailLifter).setPosition(0.9);
        }
    }
    public class CheckControls implements Runnable{
        public void run() {
            while(opModeIsActive()){
                Check_Joystick_Control();
                Check_Conveyor();
                Check_Grabber();
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
                Omni.driveMotors.TurnMotorsOn(LFPower,RFPower, LBPower, RBPower);
            }else{
                Omni.driveMotors.TurnMotorsOn(0,0,0,0);
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
                Omni.driveMotors.TurnMotorsOn(LFPower,RFPower, LBPower, RBPower);
            }else{
                Omni.driveMotors.TurnMotorsOn(0,0,0,0);
            }
        }

    }
    private void Check_Grabber(){
        if(this.gamepad2.a){
            if(GrabbingGlyph){
                while (this.gamepad2.a){
                    GrabbingGlyph = false;
                    Omni.servos.getServo(Servos.GrabberOne).setPosition(0.53);
                    Omni.servos.getServo(Servos.GrabberTwo).setPosition(0.43);
                }
            }else{
                while (this.gamepad2.a) {
                    GrabbingGlyph = true;
                    Omni.servos.getServo(Servos.GrabberOne).setPosition(0.43);
                    Omni.servos.getServo(Servos.GrabberTwo).setPosition(0.53);
                }
            }
        }
        if(this.gamepad2.left_bumper){
            Omni.servos.getServo(Servos.GrabberSpinOne).setPosition(1.0);
            Omni.servos.getServo(Servos.GrabberSpinOne).setPosition(0);
        }else if(this.gamepad2.left_trigger > 0.1){
            Omni.servos.getServo(Servos.GrabberSpinOne).setPosition(0);
            Omni.servos.getServo(Servos.GrabberSpinOne).setPosition(1.0);
        }else{
            Omni.servos.getServo(Servos.GrabberSpinOne).setPosition(0.5);
            Omni.servos.getServo(Servos.GrabberSpinOne).setPosition(0.5);
        }
    }
    private void Check_Conveyor(){
        if(this.gamepad2.right_trigger > 0.1){
            Omni.attachmentMotors.getMotor(Conveyor).setPower(-0.5);
        }else if(this.gamepad2.right_bumper){
            Omni.attachmentMotors.getMotor(Conveyor).setPower(0.5);
        }else{
            Omni.attachmentMotors.getMotor(Conveyor).setPower(0);
        }

        if(this.gamepad2.dpad_down) {
            Omni.attachmentMotors.getMotor(ArmLifter).setPower(0.5);
        }else if(this.gamepad2.dpad_up) {
            Omni.attachmentMotors.getMotor(ArmLifter).setPower(-0.5);
        }else {
            Omni.attachmentMotors.getMotor(ArmLifter).setPower(0);
        }
    }
    private void Check_Slide(){
        if(this.gamepad1.b){
            if(GrabbingRelic){
                while(this.gamepad1.b){
                    GrabbingRelic = false;
                    Omni.servos.getServo(Servos.Clampy).setPosition(1);
                }
            }else{
                while(this.gamepad1.b) {
                    GrabbingRelic = true;
                    Omni.servos.getServo(Servos.Clampy).setPosition(0);
                }
            }
        }
        if(this.gamepad1.dpad_up){
            Omni.servos.getServo(RML).setPosition(0.70);
        }else if(this.gamepad1.dpad_down){
            Omni.servos.getServo(RML).setPosition(0.25);
        }
        if(this.gamepad1.dpad_right){
            Omni.attachmentMotors.getMotor(SlideExtender).setPower(0.5);
        }else if(this.gamepad1.dpad_left){
            Omni.attachmentMotors.getMotor(SlideExtender).setPower(-0.5);
        }else{
            Omni.attachmentMotors.getMotor(SlideExtender).setPower(0);
        }

        if(this.gamepad1.right_bumper || this.gamepad1.left_bumper){
            new Thread(new Runnable() {
                public void run() {
                    Omni.servos.getServo(RML).setPosition(.25);
                    Omni.Pause(500);
                    Omni.servos.getServo(Servos.Clampy).setPosition(0);
                    Omni.servos.getServo(RML).setPosition(0);
                }
            }).start();
        }
    }
    private void Check_Other(){
        if(this.gamepad2.dpad_left || this.gamepad2.dpad_right){
            Omni.servos.getServo(Servos.GrabberOne).setPosition(1);
            Omni.servos.getServo(Servos.GrabberTwo).setPosition(0);
        }
        if(this.gamepad2.y){
            Omni.servos.getServo(Servos.FishTailLifter).setPosition(0.95);
            Omni.Pause(1000);
            Omni.servos.getServo(Servos.FishTailSwinger).setPosition(0.1);
            Omni.Pause(1000);
            Omni.servos.getServo(Servos.FishTailLifter).setPosition(0.9);
        }
        if(this.gamepad2.b){
            Omni.servos.getServo(Servos.Lifter).setPosition(0.25);
        }
        if(this.gamepad2.x){
            Omni.servos.getServo(Servos.Lifter).setPosition(0.5);
        }
    }
}

