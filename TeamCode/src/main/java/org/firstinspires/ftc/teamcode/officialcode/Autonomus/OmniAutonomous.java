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
import org.firstinspires.ftc.teamcode.officialcode.Robot.*;
import org.firstinspires.ftc.teamcode.officialcode.TeleOp.CAMraCode;
import static org.firstinspires.ftc.teamcode.officialcode.Constants.*;
//Down: 0.3 Up: 1.0 Up/Down: 0.42
// Left 0.32 Right: 0.49 Center 0.41
@Autonomous(name = "Autonomous Omni", group = "Program")
public class OmniAutonomous extends LinearOpMode{

    //-------{VARIABLES}--------------------------------------------------------------------------------
    public static CrabbingRobot Crabby;
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
        Crabby = new CrabbingRobot();
        initializeSensors();
        initializeServos();
       // initializeMotors();
        initializeVuforia();
        GatherInfo();
        WriteProgram();
        CAMraCode.COLOR = COLOR;
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
        Crabby.GiveSensors(SuperNitron9000, imu);
    }
    private void initializeServos(){
        Servo FishTail = hardwareMap.servo.get("FishTail");
        Servo Grabby = hardwareMap.servo.get("Grabby");
        Servo Jack = hardwareMap.servo.get("Jack Smith");Jack.setPosition(0.2);
        Crabby.GiveServos(FishTail, Jack, Grabby);
    }
    private void initializeMotors(){
        DcMotor lf = hardwareMap.dcMotor.get("LF");
        DcMotor lb = hardwareMap.dcMotor.get("LB");
        DcMotor rf = hardwareMap.dcMotor.get("RF");
        DcMotor rb = hardwareMap.dcMotor.get("RB");
        DcMotor ARMy = hardwareMap.dcMotor.get("ARM-Y");
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);
        Crabby.GiveDriveMotors(lf,rf,lb,rb);
        Crabby.GiveAttachmentMotors(ARMy);
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
                        {RobotActions.KnockOffJewel, RobotActions.NULL},
                        {RobotActions.Turn, 359},
                        {RobotActions.Move_Forward, 20},
                        {RobotActions.Move_Backwards,2}};
            }else{
                PROGRAM = new int[][]{
                        {RobotActions.Read_Pictograph, RobotActions.NULL},
                        {RobotActions.KnockOffJewel, RobotActions.NULL},
                        {RobotActions.Turn, 340},
                        {RobotActions.Move_Backwards, 30},
                        {RobotActions.Move_Forward,2}};
            }
        }
        if(PositionOnField.equals("RIGHT")){
            if(COLOR.equals(RED)){
                PROGRAM = new int[][]{
                        {RobotActions.Read_Pictograph, RobotActions.NULL},
                        {RobotActions.KnockOffJewel, RobotActions.NULL},
                        {RobotActions.Turn, 20},
                        {RobotActions.Move_Forward, 30},
                        {RobotActions.Move_Backwards,2}};
            }else{
                PROGRAM = new int[][]{
                        {RobotActions.Read_Pictograph, RobotActions.NULL},
                        {RobotActions.KnockOffJewel, RobotActions.NULL},
                        {RobotActions.Turn, 359},
                        {RobotActions.Move_Backwards, 30},
                        {RobotActions.Move_Forward,2}};
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
            }else if(PROGRAM[LineInProgram][0] == RobotActions.Move_Forward){
                Crabby.driveMotors.Move(Forwards, PROGRAM[LineInProgram][1], 0.8);
            }else if(PROGRAM[LineInProgram][0] == RobotActions.Move_Backwards){
                Crabby.driveMotors.Move(Backwards, PROGRAM[LineInProgram][1], 0.8);
            }
            else if(PROGRAM[LineInProgram][0] == RobotActions.Turn){
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
