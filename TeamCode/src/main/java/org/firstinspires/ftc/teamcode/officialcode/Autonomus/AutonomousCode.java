package org.firstinspires.ftc.teamcode.officialcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.internal.*;
import org.firstinspires.ftc.teamcode.officialcode.Robot.*;

import static org.firstinspires.ftc.teamcode.Constants.*;

@Autonomous(name = "Autonomous", group = "Program")
public class AutonomousCode extends LinearOpMode{
    private CrabbingRobot Crabby;
    private String COLOR ="NULL";
    private String PositionOnField = "NULL";
    private int[][] PROGRAM;
    public void initialize(){
        DcMotor lf = hardwareMap.dcMotor.get("LF");
        DcMotor lb = hardwareMap.dcMotor.get("LB");
        DcMotor rf = hardwareMap.dcMotor.get("RF");
        DcMotor rb = hardwareMap.dcMotor.get("RB");
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);
        Servo FishTail = hardwareMap.servo.get("FishTail");
       // Servo JackSmith = hardwareMap.servo.get("JackSmith");
        ColorSensor SuperNitron9000 = hardwareMap.colorSensor.get("SuperNitron9000");
        Crabby = new CrabbingRobot();
        Crabby.GiveServos(FishTail, null);
        Crabby.GiveDriveMotors(lf,rf,lb,rb);
        Crabby.GiveSensors(SuperNitron9000);
        Crabby.GiveAttachmentMotors();
        GatherInfo();
    } // Makes the Robot

    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        Runnable runProgram = new runProgram();
        Thread billy = new Thread(runProgram);
        billy.start();
        while(opModeIsActive()){}
        billy.interrupt();

    }
    private class runProgram implements Runnable{
        public void run(){
            ReadProgram();
            requestOpModeStop();
        }
    }
    private void GatherInfo(){
        telemetry.addData("Alliance Color:",COLOR);
        telemetry.addData("POSITION ON FIELD:", PositionOnField);telemetry.update();
        boolean LOOP = true;
        AppUtil.getInstance().showToast(UILocation.BOTH, "COLOR:");
        while(LOOP){
            if(gamepad1.x){
                COLOR = BLUE;
                LOOP = false;
            }else if(gamepad1.b){
                COLOR = RED;
                LOOP = false;
            }
        }
        telemetry.addData("Alliance Color:",COLOR);
        telemetry.addData("POSITION ON FIELD:", PositionOnField);telemetry.update();
        LOOP = true;
        AppUtil.getInstance().showToast(UILocation.BOTH, "POSITION ON FIELD:");
        while (LOOP){
            if(gamepad1.dpad_left){
                LOOP = false;
                if(COLOR.equals(RED)){
                    PROGRAM = new int[][]{{RobotActions.KnockOffJewel, RobotActions.NULL}};
                }
                else if(COLOR.equals(BLUE)){
                    PROGRAM = new int[][]{{RobotActions.KnockOffJewel, RobotActions.NULL}};
                }
                PositionOnField = "LEFT";
            }else if(gamepad1.dpad_right){
                LOOP = false;
                if(COLOR.equals(RED)){
                    PROGRAM = new int[][]{{RobotActions.KnockOffJewel, RobotActions.NULL}};
                }
                else if(COLOR.equals(BLUE)){
                    PROGRAM = new int[][]{{RobotActions.KnockOffJewel, RobotActions.NULL}};
                }
                PositionOnField = "RIGHT";
            }
        }
        telemetry.addData("Alliance Color:",COLOR);
        telemetry.addData("POSITION ON FIELD:", PositionOnField);telemetry.update();
        AppUtil.getInstance().showToast(UILocation.BOTH,"All Needed Info Given");
    }
    private void ReadProgram(){
        for(int LineInProgram = 0; LineInProgram <= PROGRAM.length-1;LineInProgram++){
            if(PROGRAM[LineInProgram][0] == RobotActions.KnockOffJewel){
               Crabby.driveMotors.Move(Forwards,360,0.2);
            }
        }
    }






}
