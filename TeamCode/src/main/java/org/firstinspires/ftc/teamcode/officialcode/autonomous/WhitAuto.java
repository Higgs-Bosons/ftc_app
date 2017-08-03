package org.firstinspires.ftc.teamcode.officialcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.annotation.Annotation;
@Autonomous(name = "CrabAuto", group = "Beacon")
public class WhitAuto extends LinearOpMode implements Autonomous {
//-----{ NEEDED INTERFACES }------------------------------------------------------------------------
    public Class<? extends Annotation> annotationType() {
        return null;
    }
    public String name() {
        return null;
    }public String group() {
        return null;
    }

//----{ MOVEMENT & PROGRAM }------------------------------------------------------------------------
    public void initialize() throws InterruptedException {

    }
    private void runAutonomous() throws InterruptedException {
        Robot Crab = new Robot();

        Crab.Move(direction.North, 2000, moveTypes.Seconds, Robot.BREAK);
        Crab.stop(500);
        Crab.Move(direction.East, 2000, moveTypes.Seconds, Robot.BREAK);
        Crab.stop(500);
        Crab.Move(direction.South, 2000, moveTypes.Seconds, Robot.BREAK);
        Crab.stop(500);
        Crab.Move(direction.West, 2000, moveTypes.Seconds, Robot.BREAK);
        Crab.stop(500);
        Crab.turn(direction.clockwise,moveTypes.Seconds,4800, Robot.BREAK);
        Crab.stop(1000);
        Crab.Move(direction.North, 360, moveTypes.Degrees ,Robot.BREAK);
    }
    public void runOpMode() throws InterruptedException {
        waitForStart();
        try{
            initialize();
        }catch (Exception e){e.printStackTrace();}
        runAutonomous();
    }

//----{ ROBOT CLASS }-------------------------------------------------------------------------------
    class Robot{
    static final boolean COAST = false;
    static final boolean BREAK = true;
    int CurrentSpotN = 0;
    int CurrentSpotE = 0;
    int CurrentSpotS = 0;
    int CurrentSpotW = 0;
        DcMotor North;
        DcMotor East;
        DcMotor South;
        DcMotor West;

        Robot(){
            DcMotor north = hardwareMap.dcMotor.get("MOTORNW");
            DcMotor east = hardwareMap.dcMotor.get("MOTORSW");
            DcMotor south = hardwareMap.dcMotor.get("MOTORNE");
            DcMotor west = hardwareMap.dcMotor.get("MOTORSE");
            this.East = east;
            this.North = north;
            this.South = south;
            this.West = west;
            this.North.setDirection(DcMotor.Direction.REVERSE);
            this.East.setDirection(DcMotor.Direction.REVERSE);
        }
        private void turn(int direction, int whenToStop, int duration, boolean Break){
            if (Break){
                this.North.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                this.South.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                this.East.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                this.West.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }else {
                this.North.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                this.South.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                this.East.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                this.West.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }
            if(direction==1){
                switch (whenToStop){
                    case 1:
                        this.North.setPower(1);
                        this.South.setPower(-1);
                        this.East.setPower(1);
                        this.West.setPower(-1);
                        try {Thread.sleep(duration);} catch (InterruptedException e) {e.printStackTrace();}
                        this.North.setPower(0);
                        this.South.setPower(0);
                        this.East.setPower(0);
                        this.West.setPower(0);
                }
            }
            else if(direction==2){
                switch (whenToStop){
                    case 1:
                        this.North.setPower(-1);
                        this.South.setPower(1);
                        this.East.setPower(-1);
                        this.West.setPower(1);
                        try {Thread.sleep(duration);} catch (InterruptedException e) {e.printStackTrace();}
                        this.North.setPower(0);
                        this.South.setPower(0);
                        this.East.setPower(0);
                        this.West.setPower(0);
                }
            }
        }
        private void Move(char direction, int duration, int moveType, boolean Break){
            if (Break){
                this.North.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                this.South.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                this.East.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                this.West.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }else {
                this.North.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                this.South.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                this.East.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                this.West.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }
            if(moveType == 1){Second(direction,duration);}
            if(moveType == 2){Degree(direction,duration);}
            if(moveType == 3){rotations(direction,duration);}
        }
                private void Second( char direction,int duration){
            if(direction == 'N'){
                this.East.setPower(-1);
                this.West.setPower(-1);
                this.South.setPower(-1);
                this.North.setPower(-1);
                try{Thread.sleep(duration); }catch (Exception e){e.printStackTrace();}
                this.East.setPower(0);
                this.West.setPower(0);
                this.South.setPower(0);
                this.North.setPower(0);
            }else if(direction == 'W'){
                this.East.setPower(-1);
                this.West.setPower(-1);
                this.South.setPower(1);
                this.North.setPower(1);
                try{Thread.sleep(duration); }catch (Exception e){e.printStackTrace();}
                this.East.setPower(0);
                this.West.setPower(0);
                this.South.setPower(0);
                this.North.setPower(0);
            }
            else if(direction == 'S'){
                this.North.setPower(1);
                this.South.setPower(1);
                this.East.setPower(1);
                this.West.setPower(1);
                try{Thread.sleep(duration); }catch (Exception e){e.printStackTrace();}
                this.East.setPower(0);
                this.West.setPower(0);
                this.South.setPower(0);
                this.North.setPower(0);
            }
            else if(direction == 'E'){
                this.East.setPower(1);
                this.West.setPower(1);
                this.South.setPower(-1);
                this.North.setPower(-1);
                try{Thread.sleep(duration); }catch (Exception e){e.printStackTrace();}
                this.East.setPower(0);
                this.West.setPower(0);
                this.South.setPower(0);
                this.North.setPower(0);
            }
        }
                private void Degree( char direction,int duration){
            if(direction == 'N'){
                ec(direction, duration);
            }else if(direction == 'W'){
                this.East.setPower(-1);
                this.West.setPower(-1);
                this.South.setPower(1);
                this.North.setPower(1);
                try{Thread.sleep(duration); }catch (Exception e){e.printStackTrace();}
                this.East.setPower(0);
                this.West.setPower(0);
                this.South.setPower(0);
                this.North.setPower(0);
            }
            else if(direction == 'S'){
                this.North.setPower(1);
                this.South.setPower(1);
                this.East.setPower(1);
                this.West.setPower(1);
                try{Thread.sleep(duration); }catch (Exception e){e.printStackTrace();}
                this.East.setPower(0);
                this.West.setPower(0);
                this.South.setPower(0);
                this.North.setPower(0);
            }
            else if(direction == 'E'){
                this.East.setPower(1);
                this.West.setPower(1);
                this.South.setPower(-1);
                this.North.setPower(-1);
                try{Thread.sleep(duration); }catch (Exception e){e.printStackTrace();}
                this.East.setPower(0);
                this.West.setPower(0);
                this.South.setPower(0);
                this.North.setPower(0);
            }
        }
                private void rotations( char direction,int duration){
            if(direction == 'N'){
                this.East.setPower(-1);
                this.West.setPower(-1);
                this.South.setPower(-1);
                this.North.setPower(-1);
                try{Thread.sleep(duration); }catch (Exception e){e.printStackTrace();}
                this.East.setPower(0);
                this.West.setPower(0);
                this.South.setPower(0);
                this.North.setPower(0);
            }else if(direction == 'W'){
                this.East.setPower(-1);
                this.West.setPower(-1);
                this.South.setPower(1);
                this.North.setPower(1);
                try{Thread.sleep(duration); }catch (Exception e){e.printStackTrace();}
                this.East.setPower(0);
                this.West.setPower(0);
                this.South.setPower(0);
                this.North.setPower(0);
            }
            else if(direction == 'S'){
                this.North.setPower(1);
                this.South.setPower(1);
                this.East.setPower(1);
                this.West.setPower(1);
                try{Thread.sleep(duration); }catch (Exception e){e.printStackTrace();}
                this.East.setPower(0);
                this.West.setPower(0);
                this.South.setPower(0);
                this.North.setPower(0);
            }
            else if(direction == 'E'){
                this.East.setPower(1);
                this.West.setPower(1);
                this.South.setPower(-1);
                this.North.setPower(-1);
                try{Thread.sleep(duration); }catch (Exception e){e.printStackTrace();}
                this.East.setPower(0);
                this.West.setPower(0);
                this.South.setPower(0);
                this.North.setPower(0);
            }
        }
        private  void ec(int direction, int duration){
            getEncoder('N',getEncoderModes.RESET);
            getEncoder('S',getEncoderModes.RESET);
            getEncoder('W',getEncoderModes.RESET);
            getEncoder('E',getEncoderModes.RESET);
            int deg = 0;
            if (direction == 'N'){
                while (deg >= duration){
                    deg = getEncoder('N', getEncoderModes.READ) + getEncoder('S', getEncoderModes.READ);
                    deg = deg + getEncoder('W', getEncoderModes.READ) + getEncoder('E', getEncoderModes.READ);
                    deg = deg/4;
                    this.East.setPower(-1);
                    this.West.setPower(-1);
                    this.South.setPower(-1);
                    this.North.setPower(-1);
                }
                this.East.setPower(0);
                this.West.setPower(0);
                this.South.setPower(0);
                this.North.setPower(0);
            }

        }
        private int getEncoder(char Motor,int Mode){
            if(Mode == 0){
                if (Motor == 'N'){CurrentSpotN = North.getCurrentPosition(); return 1;}
                else if (Motor == 'E') {CurrentSpotE = East.getCurrentPosition(); return 1;}
                else if (Motor == 'S'){CurrentSpotS = South.getCurrentPosition(); return 1;}
                else if (Motor == 'W') {CurrentSpotW = West.getCurrentPosition(); return 1;}
            }else if (Mode ==1){
                if (Motor == 'N'){return North.getTargetPosition() - CurrentSpotN;}
                else if (Motor == 'E') {return East.getTargetPosition() - CurrentSpotE;}
                else if (Motor == 'S'){return South.getTargetPosition() - CurrentSpotS;}
                else if (Motor == 'W') {return West.getTargetPosition() - CurrentSpotW;}
            }
            return 1;
        }
        private void stop(int miliseconds){
            try{Thread.sleep(miliseconds);}catch (Exception e){e.printStackTrace();}
        }

    }

//----{ HELPFUL VARIABLES (constants) }--------------------------------------------------------------
    private class direction{
        final static char North = 'N';
        final static char East = 'E';
        final static char West = 'W';
        final static char South = 'S';
        final static int clockwise = 1;
        final static int counterClockwise = 0;
    }
    private class moveTypes {
        final static int Seconds = 1;
        final static int Degrees = 2;
        final static int Rotations = 3;
    }
    private class getEncoderModes{
        final static int RESET = 0;
        final static int READ = 1;
    }
}
