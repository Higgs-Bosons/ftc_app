package org.firstinspires.ftc.teamcode.officialcode.Autonomus;

//--------{IMPORTS}---------------------------------------------------------------------------------
import com.qualcomm.hardware.bosch.*;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.*;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;
import org.firstinspires.ftc.teamcode.officialcode.Constants;
import org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot.OmniWheelRobot;
import org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot.*;

import static org.firstinspires.ftc.teamcode.officialcode.Constants.*;
@Autonomous(name = "Autonomous Omni", group = "Program")
public class OmniAutonomous extends LinearOpMode{

    //-------{VARIABLES}--------------------------------------------------------------------------------
    public static OmniWheelRobot Crabby;
    private String COLOR ="NULL";
    private String PositionOnField = "NULL";
    private String KeyColumn = "NULL";
    private int[][] PROGRAM;
    private VuforiaTrackable Tracker;

    //-------{STARTING OF THE PROGRAM}------------------------------------------------------------------
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        Runnable programedMission = new runProgram();
        Thread programedMissionThread = new Thread(programedMission);
        programedMissionThread.run();
        while (opModeIsActive()) {
            telemetry.addData("Vuforia",readPictograph(false));telemetry.update();
        }
        readPictograph(true);
        programedMissionThread.interrupt();
    }

    //-------{INITIALIZE PHASE}-------------------------------------------------------------------------
    private void initialize(){
        Crabby = new OmniWheelRobot();
        initializeSensors();
        initializeServos();
        initializeMotors();
        initializeVuforia();
        GatherInfo();
        WriteProgram();
        telemetry.clearAll();
        telemetry.addData("STATUS:","READY TO RUN! :-)");telemetry.update();
    }
    private void initializeVuforia(){
        int cameraMonitor = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId","id",
                hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitor);
        parameters.vuforiaLicenseKey = Constants.VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables trackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        Tracker = trackables.get(0);
        Tracker.setName("trackableTemplet");
        trackables.activate();
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

        Crabby.GiveDriveMotors(Left_Front, Right_Front, Left_Back, Right_Back);
        /*
        DcMotor ArmLifter = hardwareMap.dcMotor.get("ArmLifter");
        DcMotor HorizontalLift = hardwareMap.dcMotor.get("HorizontalLift");
        DcMotor ConveyorLower = hardwareMap.dcMotor.get("ConveyorLower");
        DcMotor ConveyorUpper = hardwareMap.dcMotor.get("ConveyorUpper");

        ArmLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        HorizontalLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ConveyorLower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ConveyorUpper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Crabby.GiveAttachmentMotors(ArmLifter, HorizontalLift, ConveyorLower, ConveyorUpper);
        */

    }
    private void initializeServos(){
        Servo FishTailLifter = hardwareMap.servo.get("FishTailLifter");
        Servo FishTailSwinger = hardwareMap.servo.get("FishTailSwinger");
        Servo Grabby = null; //= hardwareMap.servo.get("Grabby");
        Crabby.GiveServos(FishTailLifter, FishTailSwinger, Grabby);


    }
    private void initializeSensors(){
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

        Crabby.GiveSensors(SuperNitron9000, imu);
    }

    //------{WRITING THE PROGRAM}-----------------------------------------------------------------------
    private void GatherInfo(){
        telemetry.addData("Alliance Color:",COLOR);
        telemetry.addData("POSITION ON FIELD:", PositionOnField);telemetry.update();
        telemetry.addData("WHEN FINISHED ", "PRESS Y");
        boolean HasPosition = false;
        boolean HasColor = false;
        boolean ALL_INFO = false;
        while(!gamepad1.y){
            if(gamepad1.x){
                COLOR = BLUE;
                HasColor = true;
            }else if(gamepad1.b){
                HasColor = true;
                COLOR = RED;
            }

            if(gamepad1.dpad_right){
                PositionOnField = "RIGHT";
                HasPosition = true;
            }else if(gamepad1.dpad_left){
                PositionOnField = "LEFT";
                HasPosition = true;
            }
            telemetry.addData("Alliance Color:",COLOR);
            telemetry.addData("POSITION ON FIELD:", PositionOnField);telemetry.update();
            telemetry.addData("WHEN FINISHED ", "PRESS Y");
            if(HasPosition && HasColor && !ALL_INFO){
                AppUtil.getInstance().showToast(UILocation.BOTH,"Ready To Run! :-)");
                ALL_INFO = true;
            }
        }
    }
    private void WriteProgram(){
        if(PositionOnField.equals("LEFT")){
            if(COLOR.equals(RED)){
                PROGRAM = new int[][]{
                        {RobotActions.Read_Pictograph, RobotActions.NULL},
                        {RobotActions.KnockOffJewel, RobotActions.NULL},};
            }else{
                PROGRAM = new int[][]{
                        {RobotActions.Read_Pictograph, RobotActions.NULL},
                        {RobotActions.KnockOffJewel, RobotActions.NULL},};
            }
        }
        if(PositionOnField.equals("RIGHT")){
            if(COLOR.equals(RED)){
                PROGRAM = new int[][]{
                        {RobotActions.Read_Pictograph, RobotActions.NULL},
                        {RobotActions.KnockOffJewel, RobotActions.NULL},};
            }else{
                PROGRAM = new int[][]{
                        {RobotActions.Read_Pictograph, RobotActions.NULL},
                        {RobotActions.KnockOffJewel, RobotActions.NULL},};
            }
        }
    }

    //------{READING THE PROGRAM}-----------------------------------------------------------------------
    private class runProgram implements Runnable{
        public void run(){
            ReadProgram();
            requestOpModeStop();
        }
    }
    private void ReadProgram(){
        for(int LineInProgram = 0; LineInProgram <= PROGRAM.length-1;LineInProgram++){
            if(PROGRAM[LineInProgram][0] == RobotActions.KnockOffJewel){
                Crabby.KnockOffJewel(COLOR);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.Move) {
                Crabby.driveMotors.Move(N, PROGRAM[LineInProgram][1], 0.8);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.Turn){
                Crabby.driveMotors.Turn(PROGRAM[LineInProgram][1]);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.Read_Pictograph){
                KeyColumn = readPictograph(true);
            }
        }
    }

    //------{TOOLS}-------------------------------------------------------------------------------------
    private String readPictograph(boolean MakeAToast){
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(Tracker);
        if(MakeAToast){
            String ToastText = "KEY COLUMN: " + vuMark.toString();
            AppUtil.getInstance().showToast(UILocation.BOTH,ToastText);
        }
        return vuMark.toString();
    }
}
