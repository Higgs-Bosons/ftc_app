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
import org.firstinspires.ftc.teamcode.officialcode.TeleOp.XControl;
import static org.firstinspires.ftc.teamcode.officialcode.Constants.*;
@Autonomous(name = "Autonomous Omni", group = "Program")
public class OmniAutonomous extends LinearOpMode{
    //-------{VARIABLES}--------------------------------------------------------------------------------
    public static OmniWheelRobot Crabby;
    private String COLOR ="NULL";
    private String PositionOnField = "NULL";
    private String KeyColumn = "RIGHT";
    private double[][] PROGRAM;
    private VuforiaTrackables relicTrackables;
    private VuforiaTrackable relicTemplate;

    //-------{STARTING OF THE PROGRAM}------------------------------------------------------------------
    public void runOpMode() throws InterruptedException {
        initialize();
        telemetry.addData("INITIALIZED ","-)");telemetry.update();
        waitForStart();
        relicTrackables.activate();
        Runnable PROGRAM = new runProgram();
        Thread programedMissionThread = new Thread(PROGRAM);
        programedMissionThread.start();
        while(opModeIsActive()) {
           telemetry.addData("Vuforia",readPictograph(false, false));telemetry.update();
        }
        programedMissionThread.interrupt();
    }

    //-------{INITIALIZE PHASE}-------------------------------------------------------------------------
    private void initialize(){
        GatherInfo();
        telemetry.clearAll();
        telemetry.addData("STATUS "," INITIALIZING");telemetry.update();
        Crabby = new OmniWheelRobot();
        initializeSensors();
        initializeServos();
        initializeMotors();
        initializeVuforia();
        WriteProgram();
        telemetry.clearAll();
        telemetry.addData("STATUS "," READY TO RUN! :-)");telemetry.update();
    }
    private void initializeVuforia(){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = Constants.VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
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

        DcMotor ArmLifter = hardwareMap.dcMotor.get("ArmLifter");
        DcMotor HorizontalLift = hardwareMap.dcMotor.get("LinearSlide");
        DcMotor ConveyorLower = hardwareMap.dcMotor.get("ConveyorLower");
        DcMotor ConveyorUpper = hardwareMap.dcMotor.get("ConveyorUpper");

        ArmLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        HorizontalLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ConveyorLower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ConveyorUpper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Crabby.GiveAttachmentMotors(ArmLifter, HorizontalLift, ConveyorLower, ConveyorUpper);


    }
    private void initializeServos(){
        Servo FishTailLifter = hardwareMap.servo.get("FishTailLifter");
        Servo FishTailSwinger = hardwareMap.servo.get("FishTailSwinger");
        Servo GrabberOne = hardwareMap.servo.get("GrabberOne");
        Servo GrabberTwo = hardwareMap.servo.get("GrabberTwo");
        Servo Clampy = hardwareMap.servo.get("Clampy");
        Servo RML = hardwareMap.servo.get("RML");
        Servo Lifter = hardwareMap.servo.get("Lifter");
        Crabby.GiveServos(FishTailLifter, FishTailSwinger, GrabberOne, GrabberTwo, Clampy, RML, Lifter);


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

        Crabby.GiveSensors(SuperNitron9000, imu, LIGHT);
    }

    //------{WRITING THE PROGRAM}-----------------------------------------------------------------------
    private void GatherInfo(){
        telemetry.addData("Alliance Color",COLOR);
        telemetry.addData("POSITION ON FIELD", PositionOnField);telemetry.update();
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
            telemetry.addData("Alliance Color",COLOR);
            telemetry.addData("POSITION ON FIELD", PositionOnField);telemetry.update();
            telemetry.addData("WHEN FINISHED ", "PRESS Y");
            if(HasPosition && HasColor && !ALL_INFO){
                AppUtil.getInstance().showToast(UILocation.BOTH,"Ready To Run! :-)");
                ALL_INFO = true;
            }
        }
        XControl.COLOR = COLOR;
        XControl.PositionOnField = PositionOnField;
    }
    private void WriteProgram(){
        if(PositionOnField.equals("LEFT")){
            if(COLOR.equals(RED)){
                PROGRAM = new double[][]{
                        {RobotActions.Read_Pictograph, RobotActions.NULL},
                        {RobotActions.KnockOffJewel, RobotActions.NULL},
                        {RobotActions.MoveS, 20, 0.4},
                        {RobotActions.Turn, 270},
                        {RobotActions.MoveS, 7, 0.1},
                        {RobotActions.Turn, 270},
                        {RobotActions.AlineToRow, RobotActions.NULL}};
            }else{
                PROGRAM = new double[][]{
                        {RobotActions.Read_Pictograph, RobotActions.NULL},
                        {RobotActions.KnockOffJewel, RobotActions.NULL},
                        {RobotActions.MoveN, 20, 0.6},
                        {RobotActions.Turn, 180},
                        {RobotActions.MoveW, 2.2, 0.3},
                        {RobotActions.MoveS, 6, 0.4},
                        {RobotActions.MoveN, 0.3, 0.2},
                        {RobotActions.AlineToRow, RobotActions.NULL},
                        {RobotActions.MoveN, 3, 0.7},
                        {RobotActions.MoveS, 5, 0.2},
                        {RobotActions.MoveN, 5, 0.3},
                        {RobotActions.Turn, 90}};
            }
        }
        if(PositionOnField.equals("RIGHT")){
            if(COLOR.equals(RED)){
                PROGRAM = new double[][]{
                        {RobotActions.Read_Pictograph, RobotActions.NULL},
                        {RobotActions.KnockOffJewel, RobotActions.NULL},
                        {RobotActions.MoveS, 22, 0.4},
                        {RobotActions.MoveE, 7, 0.4},
                        {RobotActions.MoveS, 6, 0.2},
                        {RobotActions.Turn, 0},
                        {RobotActions.AlineToRow, RobotActions.NULL},
                        {RobotActions.MoveN, 2, 0.5},
                        {RobotActions.MoveS, 3, 0.2},
                        {RobotActions.MoveN, 4, 0.5},
                        {RobotActions.Turn, 270}};
            }else{
                PROGRAM = new double[][]{
                        {RobotActions.KnockOffJewel, RobotActions.NULL},
                        {RobotActions.MoveN, 25, 0.5},
                        {RobotActions.MoveW, 2, 0.5}};
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
            }else if(PROGRAM[LineInProgram][0] == RobotActions.MoveN) {
                Crabby.driveMotors.Move(N, PROGRAM[LineInProgram][1], PROGRAM[LineInProgram][2]);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.MoveS) {
                Crabby.driveMotors.Move(S, PROGRAM[LineInProgram][1], PROGRAM[LineInProgram][2]);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.MoveE) {
                Crabby.driveMotors.Move(E, PROGRAM[LineInProgram][1], PROGRAM[LineInProgram][2]);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.MoveW) {
                Crabby.driveMotors.Move(W, PROGRAM[LineInProgram][1], PROGRAM[LineInProgram][2]);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.Turn){
                Crabby.driveMotors.Turn((int) PROGRAM[LineInProgram][1]);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.MoveNW) {
                Crabby.driveMotors.Move(NW, PROGRAM[LineInProgram][1], PROGRAM[LineInProgram][2]);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.MoveSW) {
                Crabby.driveMotors.Move(SW, PROGRAM[LineInProgram][1], PROGRAM[LineInProgram][2]);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.MoveNE) {
                Crabby.driveMotors.Move(NE, PROGRAM[LineInProgram][1], PROGRAM[LineInProgram][2]);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.MoveSE) {
                Crabby.driveMotors.Move(SE, PROGRAM[LineInProgram][1], PROGRAM[LineInProgram][2]);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.Turn){
                Crabby.driveMotors.Turn((int)PROGRAM[LineInProgram][1]);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.Read_Pictograph){
                KeyColumn = readPictograph(true, true);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.AlineToRow){
                Crabby.ScoreAGlyph(KeyColumn, COLOR, PositionOnField);
            }
        }
    }

    //------{TOOLS}-------------------------------------------------------------------------------------
    private String readPictograph(boolean MakeAToast, boolean LOOP){
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        if(LOOP){
            while(vuMark.toString().equals("UNKNOWN")){vuMark = RelicRecoveryVuMark.from(relicTemplate);}
        }
        if(MakeAToast){
            String ToastText = "KEY COLUMN: " + vuMark.toString();
            AppUtil.getInstance().showToast(UILocation.BOTH,ToastText);
        }
        return vuMark.toString();
    }
}
