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
import org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot.*;
import static org.firstinspires.ftc.teamcode.officialcode.Constants.*;

@Autonomous(name = "Autonomous X (DO NOT RUN)", group = "Program")
public class OmniGlyph extends LinearOpMode{                                     // This is our Autonomous program
    //-------{VARIABLES}--------------------------------------------------------------------------------
    public static OmniWheelRobot Omni;                                                //This is our robot object that controls everything.
    private String COLOR ="NULL";                                                     //This stores the Alliance Color
    private String PositionOnField = "NULL";                                          //This stores the Position on the field
    private String KeyColumn = "RIGHT";                                               //This stores the Key Column.
    private double[][] PROGRAM;                                                       //We use the double array to create a "program" that we later read.
    private VuforiaTrackables relicTrackables;                                        //We uses these to rea the pictograph.
    private VuforiaTrackable relicTemplate;
    public static Thread programedMissionThread;

    //-------{STARTING OF THE PROGRAM}------------------------------------------------------------------
    public void runOpMode() throws InterruptedException {                             //When we start our program, it runs this code first>
        initialize();                                                                 //First we initialize the robot.
        waitForStart();                                                               //We wait for the start button to be pressed.
        relicTrackables.activate();                                                   //We activate the relicTrackables, causing the robot to start
        Runnable PROGRAM = new runProgram();                                          // searching for the pictograph. We then make a Runnable then
        programedMissionThread = new Thread(PROGRAM);                          // turn it into a thread, and start it. We make a thread object
        programedMissionThread.start();                                               // so later we can stop it.
        while(opModeIsActive()) {                                                     //While the opMode is running, we display this info:
            telemetry.addData("Vuforia",KeyColumn);                           //    Vuforia: 'the key column'
            telemetry.addData("Red    ",Omni.sensors.ReadColor(RED));         //    Red    : 'the light sensor's red reading'
            telemetry.addData("Blue   ",Omni.sensors.ReadColor(BLUE));        //    Blue   : 'the light sensor's blue reading'
            telemetry.addData("Gyro   ",Omni.sensors.ReadGyro());             //    Gyro   : 'the gyro reading'
            telemetry.addData("Light  ",Omni.sensors.getReflectedLight());    //    Light  : 'the optical distance sensors reading'
            telemetry.addData("RF     ",Omni.driveMotors.                     //    RF     : 'the right front motor's encoder'
                    GetMotor(Constants.RightFront).getCurrentPosition());             //    RB     : 'the right back motor's encoder'
            telemetry.addData("RB     ",Omni.driveMotors.                     //    LF     : 'the left front motor's encoder'
                    GetMotor(Constants.RightBack).getCurrentPosition());              //    LB     : 'the left back motor's encoder'
            telemetry.addData("LF     ",Omni.driveMotors.
                    GetMotor(Constants.LeftFront).getCurrentPosition());
            telemetry.addData("LB     ",Omni.driveMotors.
                    GetMotor(Constants.LeftBack).getCurrentPosition());
            telemetry.update();
        }
        programedMissionThread.interrupt();                                           //If you ever stop the opMode, it stops the thread, stopping the robot.
    }

    //-------{INITIALIZE PHASE}-------------------------------------------------------------------------
    private void initialize(){
        GatherInfo();                                                                //We call GatherInfo(), receiving the robot's color an position.
        telemetry.clearAll();
        telemetry.addData("STATUS "," INITIALIZING");                  //Then displays: "STATUS : INITIALIZING"
        telemetry.update();
        Omni = new OmniWheelRobot();                                                 //Initializes Omni, and then initializes the rest.
        initializeSensors();                                                         //This initializes the sensors.
        initializeServos();                                                          //This initializes the servos.
        initializeMotors();                                                          //This initializes the motors.
        initializeVuforia();                                                         //This initializes Vuforia.
        WriteProgram();                                                              //Using the info gathered by GatherInfo, the robot writes a program
        telemetry.clearAll();                                                        // for that position/color.
        telemetry.addData("STATUS "," READY TO RUN! :-)");             //Then displays "STATUS : READY TO RUN :-)"
        telemetry.update();
    }
    private void initializeVuforia(){                                                 // This initializes everything that deals with Vuforia
        int cameraMonitorViewId = hardwareMap.appContext.getResources().
                getIdentifier("cameraMonitorViewId", "id",
                        hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = Constants.VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
    }
    private void initializeMotors(){                                                  //This initializes everything all the motors, and then passes
        DcMotor Left_Front = hardwareMap.dcMotor.get("LF");                           // to Omni.
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
    private void initializeServos(){                                                  // This initializes all the servos and passes them to Omni.
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
    private void initializeSensors(){                                                 // This initializes all the sensors and passes them to Omni.
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

    //------{WRITING THE PROGRAM}-----------------------------------------------------------------------
    private void GatherInfo(){                                                           //Gather info is when we put in the info about color and placement.
        telemetry.addData("Alliance Color",COLOR);                               //We display:   WHEN FINISHED:PRESS Y
        telemetry.addData("Position on Field", PositionOnField);                 //              Alliance Color:(the color)
        telemetry.addData("WHEN FINISHED ", "PRESS Y");                    //             Position on Field:(the position)
        telemetry.update();
        boolean HasPosition = false;                                                     //HasPosition is false until we put in a position.
        boolean HasColor = false;                                                        //HasColor is false until we put in a color.
        boolean ALL_INFO = false;                                                        //ALL_INFO is equal to (HasColor && HasPosition)
        while(!gamepad1.y){                                                              //We loop until we press the 'Y' button.
            if(gamepad1.x){                                                              // If we  press the 'X', which is blue, it sets the COLOR variable
                COLOR = BLUE;                                                            // to BLUE, and HasColor is set to true.
                HasColor = true;
            }else if(gamepad1.b){                                                        //If we press the 'B', which is red, it sets the COLOR variable
                COLOR = RED;                                                             // to RED, and HasColor is set to true.
                HasColor = true;
            }

            if(gamepad1.dpad_right){                                                     //If we press the right on the dpad, we set the PositionOnField
                PositionOnField = "RIGHT";                                               // to "RIGHT", and HasPosition to true;
                HasPosition = true;
            }else if(gamepad1.dpad_left){                                                //If we press the right on the dpad, we set the PositionOnField
                PositionOnField = "LEFT";                                                // to "LEFT", and HasPosition to true;
                HasPosition = true;
            }
            telemetry.addData("WHEN FINISHED ", "PRESS Y");                //We display:   WHEN FINISHED:PRESS Y
            telemetry.addData("Alliance Color",COLOR);                           //              Alliance Color:(the color)
            telemetry.addData("POSITION ON FIELD", PositionOnField);             //              Position on Field:(the position)
            telemetry.update();
            if(HasPosition && HasColor && !ALL_INFO){                                    //If both HasPosition and HasColor are true, and ALL_INFO is false,
                AppUtil.getInstance().showToast(UILocation.BOTH,                         // We display a Toast saying that it has all the information.
                        "Ready To Run! :-)");
                ALL_INFO = true;                                                         //It then sets ALL_INFO to true, so the if would be called again.
            }
        }                                                                                //Then the call returns.
    }
    private void WriteProgram(){                                                      //This is where we write the program for the autonomous.
        if(PositionOnField.equals("LEFT")){                                           //If we are on the left, it goes here.
            if(COLOR.equals(RED)){                                                    //If we are on the red alliance, this becomes our program.
                PROGRAM = new double[][]{                                             //PROGRAM is assigned value, which later the robot preforms.
                        {RobotActions.Read_Pictograph},                               //First we read the pictograph,
                        {RobotActions.KnockOffJewel},                                 // Next knockoff the Jewel of the opposing alliance.
                        {RobotActions.MoveS, 12, 0.5},                                // Move south 12 inches of the balancing stone at 0.5 power.
                        {RobotActions.Turn, 270},                                     // Turn to face 270 degrees,
                        {RobotActions.MoveS, 8, 0.6},                                 // Back up into cryptobox,
                        {RobotActions.MoveN, 0.4, 0.2},                               // and move forward 0.4 inches from the cryptobox.
                        {RobotActions.AlineToRow},                                    // Then we call the ScoreAGlyph method in the OmniWheelRobot class.
                        {RobotActions.MoveN, 1, 0.7}};                                // And move forward so we aren't touching the glyph.
            }else{
                PROGRAM = new double[][]{                                             //If we are on the left and on blue, we set up this program.
                        {RobotActions.Read_Pictograph},                               //First we read the pictograph,
                        {RobotActions.KnockOffJewel},                                 // Next knockoff the Jewel of the opposing alliance.
                        {RobotActions.MoveN, 15, 0.5},                                // Move north off the balance stone,
                        {RobotActions.Turn, 180},                                     // Turn around (180 degrees),
                        {RobotActions.MoveW, 0.5, 0.4},                               // Move west a little (0.5 inches),
                        {RobotActions.MoveS, 6, 0.6},                                 // Back up into the cryptobox, straighting ourselves,
                        {RobotActions.MoveN, 0.2, 0.2},                               // and move forward 0.2 inches away from the cryptobox.
                        {RobotActions.AlineToRow},                                    // Then we call the ScoreAGlyph method in the OmniWheelRobot class.
                        {RobotActions.MoveN, 1, 1.0},
                        {RobotActions.MoveS, 5, 1.0},
                        {RobotActions.MoveN, 1, 0.6},
                        {RobotActions.Turn, 90}};                                     // Lastly, we turn to face north according to the driver perspective.

            }
        }
        if(PositionOnField.equals("RIGHT")){                                          //If we are on the right, it goes here.
            if(COLOR.equals(RED)){                                                    // If on alliance red, we create this program:
                PROGRAM = new double[][]{
                        {RobotActions.Read_Pictograph},                               //First we read the pictograph,
                        {RobotActions.KnockOffJewel},                                 // Next knockoff the Jewel of the opposing alliance.
                        {RobotActions.MoveS, 14, 0.5},                                // Move south of the balancing stone at 0.5 power.
                        {RobotActions.MoveE, 6, 0.4},                                 // Then move east 6 inches,
                        {RobotActions.MoveS, 20, 0.6},                                // Back up into the cryptobox,
                        {RobotActions.MoveN, 0.2, 0.2},                               // And move forward off of it.
                        {RobotActions.AlineToRow},                                   // Then we call the ScoreAGlyph method in the OmniWheelRobot class.
                        {RobotActions.MoveN, 2, 0.5},                                 // And move forward so we aren't touching the glyph,
                        {RobotActions.MoveN, 1, 1.0},                                 // And move forward so we aren't touching the glyph,
                        {RobotActions.MoveS, 5, 1.0},
                        {RobotActions.MoveN, 1, 0.6},
                       {RobotActions.Turn, 270}};                                    // And turn to face north. according to the drivers perspective.
            }else{
                PROGRAM = new double[][]{                                             //If we are on the blue alliance, we run this program:
                        {RobotActions.Read_Pictograph},                               //First we read the pictograph,
                        {RobotActions.KnockOffJewel},                                 // Next knockoff the Jewel of the opposing alliance.
                        {RobotActions.MoveN, 12, 0.5},                                // Then we move north off the balancing stone,
                        {RobotActions.Turn, 270},                                     // Turn to 270 degrees,
                        {RobotActions.MoveS, 8, 0.6},                               // Back up into the cryptobox,
                        {RobotActions.MoveN, 0.2, 0.2},                               // And move forward off of it.
                        {RobotActions.AlineToRow},                                    // Then we call the ScoreAGlyph method in the OmniWheelRobot class.
                        {RobotActions.MoveN, 1, 0.7}};                                // And move forward so we aren't touching the glyph.
            }
        }
    }

    //------{READING THE PROGRAM}-----------------------------------------------------------------------
    private class runProgram implements Runnable{                                 //We call this program to run the program the robot just wrote.
        public void run(){
            // new ServoSetup().moveServos();                                        //First we call the Thread ServoSetup, moving servos into their correct
            Omni.servos.getServo(Lifter).setPosition(0.3);
            ReadProgram();                                                        //positions. The we call ReadProgram(), which reads the PROGRAM array.
            requestOpModeStop();                                                  // Finally, it stops the opMode.
        }
    }
    /**
     public class ServoSetup implements Runnable{                                      //We use this to move servos around to their correct position.
     public void run() {
     Omni.servos.getServo(Servos.Lifter).setPosition(0.3);                     //First we raise the linear servo a little,
     Omni.servos.getServo(Servos.GrabberOne).setPosition(0);                   // Move a grabber servo out of the way,
     Omni.Pause(2000);                                                // Wait for it to move,
     Omni.servos.getServo(RML).setPosition(1);                                 // Lifter up the relic grabber arm,
     Omni.Pause(2000);                                                // Wait fo it to move,
     Omni.servos.getServo(Servos.GrabberOne).setPosition(1);                   // And move the grabber servo back.
     }
     void moveServos(){
     new Thread(this).start();                                        //When we call this method, it starts the run() method.
     }
     }*/
    private void ReadProgram(){                                                                  //When we call this, the robot reads the PROGRAM array, and
        for(int LineInProgram = 0; LineInProgram <= PROGRAM.length-1;LineInProgram++){           // preforms the actions. It loops through the entire array.
            if(PROGRAM[LineInProgram][0] == RobotActions.KnockOffJewel){                         //If the first double equals KnockOffJewel, we knock
                Omni.KnockOffJewel(COLOR);                                                       // off the jewel.
            }else if(PROGRAM[LineInProgram][0] == RobotActions.MoveN) {                          //If it is MoveN, we pass info to the DriveMotor
                Omni.driveMotors.XMove(N, PROGRAM[LineInProgram][1], PROGRAM[LineInProgram][2]);  // class and it moves the robot north.
            }else if(PROGRAM[LineInProgram][0] == RobotActions.MoveS) {                          //If it is MoveS, we pass info to the DriveMotor
                Omni.driveMotors.XMove(S, PROGRAM[LineInProgram][1], PROGRAM[LineInProgram][2]);  // class and it moves the robot south.
            }else if(PROGRAM[LineInProgram][0] == RobotActions.MoveE) {                          //If it is MoveE, we pass info to the DriveMotor
                Omni.driveMotors.XMove(E, PROGRAM[LineInProgram][1], PROGRAM[LineInProgram][2]);  // class and it moves the robot east.
            }else if(PROGRAM[LineInProgram][0] == RobotActions.MoveW) {                          //If it is MoveW, we pass info to the DriveMotor
                Omni.driveMotors.XMove(W, PROGRAM[LineInProgram][1], PROGRAM[LineInProgram][2]);  // class and it moves the robot west.
            }else if(PROGRAM[LineInProgram][0] == RobotActions.Turn){                            //If it is Turn, we pass info to the DriveMotor
                Omni.driveMotors.XTurn((int) PROGRAM[LineInProgram][1]);                          // class and it turns the robot.
            }else if(PROGRAM[LineInProgram][0] == RobotActions.Read_Pictograph){                 //If it is Read_Pictograph, we start a Threa that loops until we
                new Thread(new Runnable(){public void run(){KeyColumn =                          // see the pictograph and stores the key column in the KeyColumn
                        readPictograph();}}).start();                // variable.
            }else if(PROGRAM[LineInProgram][0] == RobotActions.AlineToRow){                      //If it is AlineToRow, we call the ScoreAGlyph method in the
                Omni.ScoreAGlyph(KeyColumn, COLOR);                                              // OmniWheelRobot class.
            }else if(PROGRAM[LineInProgram][0] == RobotActions.ResetGyro){                       //If it is ResetGyro, we reset the gyro.
                Omni.sensors.ResetGyro();
            }
        }
    }

    //------{TOOLS}-------------------------------------------------------------------------------------
    private String readPictograph(){                                                  //This method we use to find out the key column.
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);         // First we initialize the vuMark.
        while(vuMark.toString().equals("UNKNOWN")){                                   // We keep repeating until the Robot sees something.
            vuMark = RelicRecoveryVuMark.from(relicTemplate);                         // If we don't see anything, we scan again.
        }
        String ToastText = "KEY COLUMN: " + vuMark.toString();                        //When then display the key column in a toast that says:
        AppUtil.getInstance().showToast(UILocation.BOTH,ToastText);                   //   KEY COLUMN: 'the key column.
        return vuMark.toString();                                                     //We then return the key column as a string.
    }
}
