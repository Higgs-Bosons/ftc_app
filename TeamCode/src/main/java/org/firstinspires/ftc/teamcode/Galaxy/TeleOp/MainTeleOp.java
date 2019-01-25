package org.firstinspires.ftc.teamcode.Galaxy.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot.MecanumWheelRobot;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;
import static org.firstinspires.ftc.teamcode.Galaxy.Names.*;

@TeleOp(name = "TeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode{

    private boolean isArmUp = false;
    private boolean tanked = false;
    private MecanumWheelRobot BubbleTheRobo;
    private String aiControls = ON;
    private int speed = 80;
    private int rampPosition = 2;
    private int bucketPosition = 1;
    private int counterOfLiftingRobot = 0;
    private int counterOfLiftingVSlide = 0;
    private int counterOfLoweringVSlide = 0;
    private String mode = "JOYSTICK DRIVE";
    private int screenPos = 1;

    private Thread driveControlThread, lifterThread, gatherAndDumpThread, telemetryThread;


    public void runOpMode(){
        initializeTheRobot();
        setUpThreads();

        telemetry.setCaptionValueSeparator("");
        telemetry.addData("Ready to Rumble!", "");
        telemetry.update();

        waitForStart();

        telemetryThread.start();
        lifterThread.start();
        driveControlThread.start();
        gatherAndDumpThread.start();

        while (opModeIsActive()) {
            checkAIControls();
            checkSettings();
            checkForReset();
        }

        BubbleTheRobo.stopRobot();
    }
    private void setUpThreads(){

        driveControlThread = new Thread(new Runnable() {
            public void run(){
                while(opModeIsActive()){
                    checkDriveMotors();
                }
                BubbleTheRobo.stopRobot();
            }
        });
        lifterThread = new Thread(new Runnable() {
            public void run() {
                while(opModeIsActive()){
                    checkLifter();
                }
            }
        });
        telemetryThread = new Thread(new Runnable() {
            public void run() {
                while(opModeIsActive()){
                    updateTelemetry();
                    try{Thread.sleep(10);}catch (InterruptedException ignore){}
                }
            }
        });
        gatherAndDumpThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(opModeIsActive()){
                    checkDumper();
                    checkGatherer();
                }
            }
        });
    }

    private void initializeTheRobot(){
        BubbleTheRobo = new MecanumWheelRobot(hardwareMap, FIRST_LETTER_NO_SPACE_UPPERCASE);
        BubbleTheRobo.addAMotor(PowerUp, NO_TAG);
        BubbleTheRobo.addAMotor(PowerDown, NO_TAG);
        BubbleTheRobo.addAMotor(VSlide, NO_TAG);
        BubbleTheRobo.addAMotor(HSlide, NO_TAG);

        BubbleTheRobo.addServo(Holder);
        BubbleTheRobo.addServo(Grabby);
        BubbleTheRobo.addServo(WeightLifter);
        BubbleTheRobo.addServo(Spinny);
        BubbleTheRobo.addServo(ArmY);
        BubbleTheRobo.addServo(Rampy);
        BubbleTheRobo.addServo(BucketDumper);

        BubbleTheRobo.addSensor(DistanceL, DISTANCE_SENSOR);
        BubbleTheRobo.addSensor(DistanceR, DISTANCE_SENSOR);
        BubbleTheRobo.addSensor(TouchyL, TOUCH_SENSOR);
        BubbleTheRobo.addSensor(TouchyR, TOUCH_SENSOR);
        BubbleTheRobo.addSensor(TopTouchy, TOUCH_SENSOR);
        BubbleTheRobo.addSensor(Gyro, IMU);


        BubbleTheRobo.setMotorZeroPowerMode(PowerUp, DcMotor.ZeroPowerBehavior.BRAKE);
        BubbleTheRobo.setMotorZeroPowerMode(PowerDown, DcMotor.ZeroPowerBehavior.BRAKE);
        BubbleTheRobo.setMotorZeroPowerMode(VSlide, DcMotor.ZeroPowerBehavior.BRAKE);
        BubbleTheRobo.setMotorZeroPowerMode(HSlide, DcMotor.ZeroPowerBehavior.BRAKE);


        BubbleTheRobo.setMotorDirection(FORWARDS, REVERSE, REVERSE, FORWARDS);
        BubbleTheRobo.setBreakOrCoast(DcMotor.ZeroPowerBehavior.FLOAT);

        BubbleTheRobo.moveServo(Grabby, 0.6);
        BubbleTheRobo.moveServo(WeightLifter, 0.7);
        BubbleTheRobo.moveServo(BucketDumper, 0);
        BubbleTheRobo.moveServo(Rampy, .44);
        BubbleTheRobo.moveServo(Holder, 1.0);
    }

    private void updateTelemetry(){
        if (gamepad1.left_bumper || gamepad2.left_bumper) {
            screenPos = (screenPos == 1) ? 3 : --screenPos;
            while (gamepad1.left_bumper || gamepad2.left_bumper);
        }
        if (gamepad1.right_bumper || gamepad2.right_bumper) {
            screenPos = (screenPos == 3) ? 1 : ++screenPos;
            while (gamepad1.right_bumper || gamepad2.right_bumper);
        }

        telemetry.addData("Movement Mode : ", mode);
        telemetry.addData("Speed : ", speed + "/100");
        telemetry.addData("AI Controls: ", aiControls);

        if(screenPos == 1){
            telemetry.addData("   [data]     [CONTROLS]   [sensors]", "");
            telemetry.addLine();
            telemetry.addData("--{Game Pad 1 (A)}------------","");
            telemetry.addData(" Left Joystick:  Movement","");
            telemetry.addData(" Right Joystick X: Turning","");
            telemetry.addData(" DPad Up & Down: Lifter","");
            telemetry.addData(" X Button: Toggle Holder","");
            telemetry.addData(" A Button: Press to change Speed","");
            telemetry.addData(" B Button: Toggle Drive Mode","");
            telemetry.addData(" Y Button: Change AI Mode","");
            telemetry.addData(" Bumpers: Change Screen","");
            telemetry.addData(" Both Triggers: Reset Stuff","");
            telemetry.addLine();
            telemetry.addData("--{Game Pad 2 (B)}------------","");
            telemetry.addData(" Left Joystick Y:  Spinner","");
            telemetry.addData(" Left Joystick X:  H-Slide","");
            telemetry.addData(" Right Joystick Y: Arm-Y (3 pos)","");
            telemetry.addData(" DPad Up & Down: V-Slide","");
            telemetry.addData(" X Button: Move Dumper","");
            telemetry.addData(" A Button: Move Ramp","");
            telemetry.addData(" Y Button: Change AI Mode","");
            telemetry.addData(" Bumpers: Change Screen","");
            telemetry.addData(" Both Triggers: Reset Stuff","");
        }else if(screenPos == 2){
            telemetry.addData(" [controls]    [SENSORS]     [data] ", "");
            telemetry.addLine();
            telemetry.addData("DistanceR CM: ", BubbleTheRobo.readSensor(DistanceL, DISTANCE_IN_CENTIMETERS));
            telemetry.addData("DistanceL CM: ", BubbleTheRobo.readSensor(DistanceR, DISTANCE_IN_CENTIMETERS));
            telemetry.addData("TouchyL:      ", (BubbleTheRobo.readSensor(TouchyL, TOUCH_VALUE)== 1) ? "PRESSED" : ". . ." );
            telemetry.addData("TouchyR:      ", (BubbleTheRobo.readSensor(TouchyR, TOUCH_VALUE) == 1) ? "PRESSED" : ". . .");
            telemetry.addData("TopTouchy:    ", (BubbleTheRobo.readSensor(TopTouchy, TOUCH_VALUE) == 1) ? "PRESSED" : ". . .");
            telemetry.addData("IMU Axis 0:   ", BubbleTheRobo.readIMUGyro(Gyro)[0]);
            telemetry.addData("IMU Axis 1:   ", BubbleTheRobo.readIMUGyro(Gyro)[1]);
            telemetry.addData("IMU Axis 2:   ", BubbleTheRobo.readIMUGyro(Gyro)[2]);
        }else if(screenPos == 3){
            telemetry.addData("  [sensors]      [DATA]   [controls]", "");
            telemetry.addLine();
            telemetry.addData("Ramp Position: ", rampPosition);
            telemetry.addData("Bucket Position: ", bucketPosition);
            telemetry.addData("Counter Lifter", counterOfLiftingRobot);
            telemetry.addData("Counter for Dumper (UP): ", counterOfLiftingVSlide);
            telemetry.addData("Counter for Dumper (DOWN): ", counterOfLoweringVSlide);
            telemetry.addData("V-Slide Count: ", BubbleTheRobo.getMotorTickCount(VSlide));
            telemetry.addData("H-Slide Count: ", BubbleTheRobo.getMotorTickCount(HSlide));
        }
        telemetry.update();
    }

    private void checkDriveMotors(){
        if (!tanked) 
             BubbleTheRobo.driveByJoystick(gamepad1, (speed/100.0));
         else
             BubbleTheRobo.driveByTank(gamepad1, (speed/100.0));
    }
    private void checkLifter(){
        if(!gamepad1.a){
            if(gamepad1.dpad_down){
                BubbleTheRobo.moveMotor(PowerUp, -1.0);
                BubbleTheRobo.moveMotor(PowerDown, -1.0);
            }else if(gamepad1.dpad_up){
                BubbleTheRobo.moveMotor(PowerUp, 1.0);
                BubbleTheRobo.moveMotor(PowerDown, 1.0);
            }else{
                counterOfLiftingRobot = 0;
                BubbleTheRobo.stopMotor(PowerUp);
                BubbleTheRobo.stopMotor(PowerDown);
            }
        }
        if (gamepad1.x) {
            isArmUp = !isArmUp;
            BubbleTheRobo.moveServo(Holder, (isArmUp) ? 1.0 : 0.0);
            while (gamepad1.x);

        }

    }

    private void checkDumper(){
        if(gamepad2.dpad_up){
            if (BubbleTheRobo.getMotorTickCount(VSlide) < -1460 && !aiControls.equals(OFF))
                BubbleTheRobo.stopMotor(VSlide);
            else
                BubbleTheRobo.moveMotor(VSlide, -0.7);
        }else if(gamepad2.dpad_down){
            if (BubbleTheRobo.getMotorTickCount(VSlide) > 0 && !aiControls.equals(OFF))
                BubbleTheRobo.stopMotor(VSlide);
            else
                BubbleTheRobo.moveMotor(VSlide, 0.7);
        }else{
            BubbleTheRobo.stopMotor(VSlide);
        }

        if (gamepad2.x) {
            bucketPosition = (bucketPosition == 3) ? 1 : ++bucketPosition;
            while (gamepad2.x);
        }

        switch (bucketPosition) {
            case 1: BubbleTheRobo.moveServo(BucketDumper, 0); break;
            case 2: BubbleTheRobo.moveServo(BucketDumper, 0.45); break;
            case 3: BubbleTheRobo.moveServo(BucketDumper, 0.85); break;
        }
    }
    private void checkGatherer(){
        BubbleTheRobo.moveServo(Spinny, ((-gamepad2.left_stick_y + 1) / 2.0));

        if(gamepad2.right_stick_y > 0.1){
            BubbleTheRobo.moveServo(ArmY, 1.00);
        }else if(gamepad2.right_stick_y < -0.1){
            BubbleTheRobo.moveServo(ArmY, 0.0);
        }else {
            BubbleTheRobo.moveServo(ArmY, 0.5);
        }

        if(gamepad2.left_stick_x < -0.1){
            if(BubbleTheRobo.getMotorTickCount(HSlide) > 1440 && !aiControls.equals(OFF)){
                BubbleTheRobo.stopMotor(HSlide);
            }else{
                BubbleTheRobo.moveMotor(HSlide, -gamepad2.left_stick_x);
            }
        }else if(gamepad2.left_stick_x > 0.1){
            if(BubbleTheRobo.getMotorTickCount(HSlide) < 0 && !aiControls.equals(OFF)){
                BubbleTheRobo.stopMotor(HSlide);
            }else{
                BubbleTheRobo.moveMotor(HSlide, -gamepad2.left_stick_x);
            }
        }else{
            BubbleTheRobo.stopMotor(HSlide);
        }


        if(gamepad2.a){
            rampPosition++;
            rampPosition = (rampPosition == 4) ? 1 : rampPosition;

            if(rampPosition == 1){
                BubbleTheRobo.moveServo(Rampy, .44);
            }else if(rampPosition == 2){
                BubbleTheRobo.moveServo(Rampy, .495);
            }else{
                BubbleTheRobo.moveServo(Rampy, .53);
            }

            while (gamepad2.a);
        }
    }

    private void checkAIControls(){
        if(aiControls.equals(ON) || aiControls.equals(LIMITED)){
            if(!gamepad1.a && gamepad1.dpad_up){
                counterOfLiftingRobot ++;
                if(counterOfLiftingRobot == 20)
                    BubbleTheRobo.moveServo(Holder, 0);
            }else counterOfLiftingRobot = 0;

            if(gamepad2.dpad_up){
                counterOfLiftingVSlide++;
                if(counterOfLiftingVSlide == 10)
                    bucketPosition = 2;
                if(BubbleTheRobo.getMotorTickCount(VSlide) < -1400 && counterOfLiftingVSlide > 20 && aiControls.equals(ON))
                    bucketPosition = 3;
            }else counterOfLiftingVSlide = 0;

            if(gamepad2.dpad_down){
                counterOfLoweringVSlide++;
                if(counterOfLoweringVSlide == 20)
                    bucketPosition = 1;
            }else counterOfLoweringVSlide = 0;

            if(gamepad2.left_stick_x > 0.1 && !aiControls.equals(ON) && BubbleTheRobo.getMotorTickCount(HSlide) < 5)
                rampPosition = 1;
        }
    }
    private void checkSettings(){
        if(gamepad1.a){
            if(gamepad1.dpad_up){
                long startTime = System.currentTimeMillis(), currentTime;
                speed += (speed > 100) ? (100 - speed) : 5;
                while (gamepad1.dpad_up){
                    currentTime = System.currentTimeMillis();
                    if(startTime < currentTime - 750){
                        speed += (speed > 100) ? (100 - speed) : 5;
                        startTime += 250L;
                    }
                }
            }else if(gamepad1.dpad_down){
                long startTime = System.currentTimeMillis(), currentTime;
                speed -= (speed < 0) ? speed : 5;
                while (gamepad1.dpad_up){
                    currentTime = System.currentTimeMillis();
                    if(startTime < currentTime - 750){
                        speed -= (speed < 0) ? speed : 5;
                        startTime += 250L;
                    }
                }
            }

        }
        if(gamepad1.b){
            mode = (mode.equals("JOYSTICK DRIVE")) ? "TANK DRIVE" : "JOYSTICK DRIVE";
            tanked = !tanked;
            while (gamepad1.b);
        }

        if(gamepad1.y || gamepad2.y){
            aiControls = (aiControls.equals(OFF)) ? LIMITED : ((aiControls.equals(LIMITED)) ? ON : OFF);
            while(gamepad1.y || gamepad2.y);
        }

    }
    private void checkForReset(){
        if((gamepad1.left_trigger > 0.1 && gamepad1.right_trigger > 0.1)
                || (gamepad2.left_trigger > 0.1 && gamepad2.right_trigger > 0.1)){
            BubbleTheRobo.resetEncoders();
            BubbleTheRobo.resetMotorTickCount(VSlide, false);
            BubbleTheRobo.resetMotorTickCount(HSlide, false);
            BubbleTheRobo.resetIMUGyro(Gyro);
        }
    }
}
