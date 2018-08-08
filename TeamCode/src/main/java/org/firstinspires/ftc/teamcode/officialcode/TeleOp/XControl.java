package org.firstinspires.ftc.teamcode.officialcode.TeleOp;
import com.qualcomm.hardware.bosch.*;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot.*;
import static org.firstinspires.ftc.teamcode.officialcode.Constants.*;
@TeleOp(name = "X Control", group = "TeleOp")
public class XControl extends LinearOpMode{
    //VARIABLES:
    private OmniWheelRobot Omni;
    private boolean GrabbingGlyph = false;
    private boolean GrabbingRelic = false;
    private int InstructionPage = 0;
    private boolean Slow = false;
    private boolean LimitedMovement = false;
    private double x = 0;
    private double y = 0;

    //INITIALIZATION:
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
        Omni.GiveServos(FishTailLifter, FishTailSwinger, GrabberOne, GrabberTwo,
                Clampy, RML, Lifter,GrabberSpinOne, GrabberSpinTwo);
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

    //SET-UP AND LOOPS:
    public void runOpMode() throws InterruptedException{
        initialize();
        waitForStart();
        while(!gamepad1.a && !gamepad2.a){
            telemetry.addData("READY TO GO ","-)   PRESS \"A\" TO START");
            telemetry.update();
        }
        Thread ControlThread = new Thread(new CheckControls());
        ControlThread.start();
        new Thread(new Servo_Setup()).start();
        while(opModeIsActive()){displayInfo();}
        ControlThread.interrupt();
    }
    private void displayInfo(){
        if(gamepad1.right_stick_button || gamepad1.left_stick_button || gamepad2.right_stick_button){
            InstructionPage ++;
            while((gamepad1.right_stick_button || gamepad1.left_stick_button|| gamepad2.right_stick_button)){
                if(InstructionPage == 6){InstructionPage = 0;}
            }
        }
        telemetry.clearAll();
        telemetry.addData("CONTROLS SLOWED ", (Slow+"").toUpperCase());
        telemetry.addData("CONTROLS LIMITED ", (LimitedMovement+"").toUpperCase());
        if(InstructionPage == 0){
            telemetry.addData("Drive Controls -", "- Driver");
            telemetry.addData("Turn", " Right Joystick X");
            telemetry.addData("Movement", " Left Joystick");
            telemetry.addData("Slow Down", " A Button");
            telemetry.addData("Limit Controls", " X Button");
        }
        if(InstructionPage == 1){
            telemetry.addData("Conveyor Stuff -", "- Attachment");
            telemetry.addData("Run Belt"," Forwards: Right Trigger  Backwards: Right Bumper");
            telemetry.addData("Glyph Lifter", " Up: DPad Up, Down: DPad Down");
        }
        if(InstructionPage == 2){
            telemetry.addData("Gripper Stuff -", "- Attachment");
            telemetry.addData("Set Wheels to perfect distance", " Wiggle Left Joystick");
            telemetry.addData("Grab/Release Glyph", " A Button");
            telemetry.addData("Pull in Glyph", " Left Trigger");
            telemetry.addData("Push out Glyph", " Left Bumper");
        }
        if(InstructionPage == 3){
            telemetry.addData("Slide Movement -", "- Driver");
            telemetry.addData("Extend", " DPad Left");
            telemetry.addData("Extend", " (Attachment) Left stick button");
            telemetry.addData("Retract (just motor)", " DPad Right");

        }
        if(InstructionPage == 4){
            telemetry.addData("Relic Movement -", "- Driver");
            telemetry.addData("Grab/Release Relic", " B Button");
            telemetry.addData("Lift Relic", " DPad Up");
            telemetry.addData("Lower Relic", " DPad Down");
            telemetry.addData("Drop of Relic", " Either Bumper");
        }
        if(InstructionPage == 5){
            telemetry.addData("Move Servos -", "- Attachment");
            telemetry.addData("Move FishTail out of the yay", " Y Button");
            telemetry.addData("Move Grabbers out of the way", " Right Joystick");
            telemetry.addData("Lower Linear Servo", " B Button");
            telemetry.addData("Raise Linear Servo", " X Button");

        }
        if(InstructionPage == 6){
            telemetry.addData("Other -", "- Driver");
            telemetry.addData("Reset Gyro", " Both Triggers");
        }
        telemetry.update();

    }
    public class Servo_Setup implements Runnable{
        public void run(){
            Omni.servos.getServo(Servos.Lifter).setPosition(0.3);
            Omni.servos.getServo(Servos.GrabberOne).setPosition(0.45);
            Omni.servos.getServo(Servos.GrabberTwo).setPosition(0.55);
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
                Check_X_Joystick();
                Check_Conveyor();
                Check_Grabber();
                Check_Slide();
                Check_Other();
            }
        }
    }

    //ROBOT MOVEMENTS:
    private void Check_X_Joystick(){
        if(gamepad1.a){while(gamepad1.a){}Slow = !Slow;}                       //Here we check for slowing down the controls
        if(gamepad1.x){while(gamepad1.x){}LimitedMovement = !LimitedMovement;} //Here we check for limited controls (when enabled, the robot
                                                                               // will only move orthogonally, and not diagonally)
        if(!isRobotMoving()){                                                  //If the robot is moving, which is when the controls are moved from the
            double speed;                                                      //origin. The variables speed, stores the power to the robot.
            ReadJoysticks();                                                   //We call ReadJoysticks() which handles the orientation of the robot.
            if(Slow){speed = 0.4;}else{speed = 0.8;}                           //If Slow is enabled, it slows the robot, else, it moves faster.
            float RFPower = (float) ((y + x)*speed);                           //The x and y are the joysticks' x an y. The joysticks report values from
            float RBPower = (float) ((y - x)*speed);                           // -1 to 1. Moving a motor at a power of 1, the fastest is can go, will
            float LFPower = (float) ((y - x)*speed);                           // lead to inconsistencies. When slowed, the result of y +- x is multiplied
            float LBPower = (float) ((y + x)*speed);                           // by 0.4. the max speed is 0.4, when not slowed, it is multiplied by 0.8.
            if(Math.abs(gamepad1.right_stick_x) >= 0.1) {                      //If the other joystick is moved past 0.1 or -0.1, we then turn.
                LBPower = (float)  ((gamepad1.right_stick_x)* -speed);         // The speed at which we move the robot is still affected by the speed
                LFPower = (float)  ((gamepad1.right_stick_x)* -speed);         // variable, and the speed is also affected by the amount the joystick
                RBPower = (float) ((-gamepad1.right_stick_x)* -speed);         // is moved.
                RFPower = (float) ((-gamepad1.right_stick_x)* -speed);
            }
            Omni.driveMotors.TurnMotorsOn(LFPower,RFPower, LBPower, RBPower);  //We then set the motor's power to the variables we just set.
        }else{
           Omni.driveMotors.STOP();                                            //If the robot is not moving, we stop all the motors.
        }
    }
    private void ReadJoysticks(){                                              //ReadJoystick is the method that we use to handle the robot's orientation.
        x = gamepad1.left_stick_x;                                             // x is set to the joystick x reading,
        y = gamepad1.left_stick_y;                                             // and y is set to the joystick y reading.
        if(inRange((int) Omni.sensors.ReadGyro(), 135, 45)){        //Here we decide if the robot needs to rotate its orientation. If the robot's
            double oldX = x;                                                   // gyro reads from 135 to 45, we set x to y and -y to x. We have to make the
            x=-y;                                                              // variable oldX because if we set X to -y, we will then set y to -y if we reuse
            y=oldX;                                                            // the variable x.
        }else if(inRange((int) Omni.sensors.ReadGyro(), 225, 135)){ //If the gyro reading is within 225 to 135, we set -x to x and -y to y.
            x=-x;                                                              //Here the robot is facing the opposite direction the driver is facing.
            y=-y;                                                              // By reversing the controls, the robot moves according to the driver perspective.
        }else if(inRange((int) Omni.sensors.ReadGyro(), 315, 225)){ //If the gyro reads for 315 to 255, we set y to x, and -x to y.
            double oldX = x;
            x=y;
            y=-oldX;
        }
        if(LimitedMovement){                                                   //If LimitedMovement is activated, if x it greater than y, y is set to 0.
            if(Math.abs(x)>Math.abs(y)){y = 0;}                                // by doing this, the robot only moves side to side, not moving diagonally.
            if(Math.abs(x)<Math.abs(y)){x = 0;}                                // If y is greater than x, x is set to 0. this restricts the robot's
        }                                                                      // movement to only the y-axis of motion (Only moves up and down).
    }
    private boolean isRobotMoving(){                                           //isRobotMoving is a method we wrote that returns a boolean value
        return  0.1 >= (Math.abs(gamepad1.left_stick_x) +                      // based on whether any of the joysticks are moved past the dead zone.
                Math.abs(gamepad1.left_stick_y) +
                Math.abs(gamepad1.right_stick_x));
    }
    private boolean inRange(int ValueToTest, int Max, int Min){                // inRange is a tools that we wrote to simply code. It checks if the
        return ((ValueToTest >= Min) && (ValueToTest <= Max));                 // ValueToTest is greater than the minimum value an less than the max.
    }

    //CHECKING OTHER CONTROLS:
    private void Check_Grabber(){
        if(this.gamepad2.a){
            if(GrabbingGlyph){
                while (gamepad2.a){
                    GrabbingGlyph = false;
                    Omni.servos.getServo(Servos.GrabberOne).setPosition(0.55);
                    Omni.servos.getServo(Servos.GrabberTwo).setPosition(0.45);
                }
            }else{
                while (gamepad2.a) {
                    GrabbingGlyph = true;
                    Omni.servos.getServo(Servos.GrabberOne).setPosition(0.4);
                    Omni.servos.getServo(Servos.GrabberTwo).setPosition(0.6);
                }
            }
        }
        if(Math.abs(gamepad2.left_stick_y)>0.1 || Math.abs(gamepad2.left_stick_x)>0.1){
            GrabbingGlyph = false;
            Omni.servos.getServo(Servos.GrabberOne).setPosition(0.45);
            Omni.servos.getServo(Servos.GrabberTwo).setPosition(0.55);
        }
        if(this.gamepad2.left_bumper){
            Omni.servos.getServo(Servos.GrabberSpinOne).setPosition(1.0);
            Omni.servos.getServo(Servos.GrabberSpinTwo).setPosition(0);
        }else if(this.gamepad2.left_trigger > 0.1){
            Omni.servos.getServo(Servos.GrabberSpinOne).setPosition(0);
            Omni.servos.getServo(Servos.GrabberSpinTwo).setPosition(1.0);
        }else{
            Omni.servos.getServo(Servos.GrabberSpinOne).setPosition(0.5);
            Omni.servos.getServo(Servos.GrabberSpinTwo).setPosition(0.5);
        }
    }
    private void Check_Conveyor(){
        if(this.gamepad2.right_trigger > 0.1){
            Omni.attachmentMotors.getMotor(Conveyor).setPower(-0.7);
        }else if(this.gamepad2.right_bumper){
            Omni.attachmentMotors.getMotor(Conveyor).setPower(0.7);
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
                    Omni.servos.getServo(Servos.Clampy).setPosition(0);
                }
            }else{
                while(this.gamepad1.b) {
                    GrabbingRelic = true;
                    Omni.servos.getServo(Servos.Clampy).setPosition(1);
                }
            }
        }
        if(this.gamepad1.dpad_up){
            Omni.servos.getServo(Servos.Clampy).setPosition(1);
            Omni.servos.getServo(RML).setPosition(1);
            GrabbingRelic = true;
        }else if(this.gamepad1.dpad_down){
            Omni.servos.getServo(RML).setPosition(0.14);      //Was 0.12
        }
        if(this.gamepad1.dpad_right){
            Omni.attachmentMotors.getMotor(SlideExtender).setPower(-0.7);
            Omni.attachmentMotors.getMotor(SlideRetracter).setPower(0.7);
        }else if(this.gamepad1.dpad_left || gamepad2.left_stick_button ){
            Omni.attachmentMotors.getMotor(SlideExtender).setPower(0.7);
            Omni.attachmentMotors.getMotor(SlideRetracter).setPower(-0.7);
        }else{
            Omni.attachmentMotors.getMotor(SlideExtender).setPower(0);
            Omni.attachmentMotors.getMotor(SlideRetracter).setPower(0);
        }
        if(this.gamepad1.right_bumper || this.gamepad1.left_bumper){
            new Thread(new Runnable() {public void run() {
                    Omni.servos.getServo(RML).setPosition(0.14);   // Was 0.12
                    Omni.Pause(1000);
                    Omni.servos.getServo(Servos.Clampy).setPosition(0);
                    Omni.Pause(500);
                    Omni.servos.getServo(RML).setPosition(0);
                }}).start();
        }
    }
    private void Check_Other(){
        if(Math.abs(this.gamepad2.right_stick_y)>0.1||Math.abs(this.gamepad2.right_stick_x)>0.1){
            Omni.servos.getServo(Servos.GrabberOne).setPosition(1);
            Omni.servos.getServo(Servos.GrabberTwo).setPosition(0);
        }
        if(this.gamepad2.y){
            new Thread(new Runnable(){public void run() {
                Omni.servos.getServo(Servos.FishTailLifter).setPosition(0.95);
                Omni.Pause(1000);
                Omni.servos.getServo(Servos.FishTailSwinger).setPosition(0.1);
                Omni.Pause(1000);
                Omni.servos.getServo(Servos.FishTailLifter).setPosition(0.9);
            }}).start();
        }
        if(this.gamepad2.b){
            Omni.servos.getServo(Servos.Lifter).setPosition(0.3);
        }
        if(this.gamepad2.x){
            Omni.servos.getServo(Servos.Lifter).setPosition(0.7);
        }
        if(this.gamepad1.left_trigger>0.1 && this.gamepad1.right_trigger>0.1){
            Omni.sensors.ResetGyro();
        }
    }
}


