package org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.officialcode.Autonomus.OmniAutonomous;
import org.firstinspires.ftc.teamcode.officialcode.Constants;

public class DriveMotors {
    private DcMotor RF;
    private DcMotor RB;
    private DcMotor LF;
    private DcMotor LB;

//-------{CALLS FOR ROBOT MOVEMENT}-----------------------------------------------------------------
    DriveMotors(DcMotor LeftFront, DcMotor RightFront, DcMotor LeftBack, DcMotor RightBack){
        this.LB = LeftBack;
        this.LF = LeftFront;
        this.RB = RightBack;
        this.RF = RightFront;

        this.RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.RB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.LB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    public void Move(Constants.RobotDirection Direction, int DistanceInches, double Power) {
        int DistanceTicks = (int) (DistanceInches / (Math.PI * Constants.Wheel_Diameter) * Constants.Motor_Tick_Per_Rotation);
        double X = Direction.getX();
        double Y = Direction.getY();
        boolean keepLooping = true;
        int averageTicks;
        ResetEncoders();
        float RFPower = (float) ((X+Y)*Power);
        float RBPower = (float) ((Y-X)*Power);
        float LFPower = (float) ((Y-X)*Power);
        float LBPower = (float) ((X+Y)*Power);
        TurnMotorsOn(LFPower,RFPower, LBPower, RBPower);
        while (keepLooping){
            averageTicks = ((RF.getCurrentPosition() + RB.getCurrentPosition() + LF.getCurrentPosition() + LB.getCurrentPosition())/4);
            keepLooping = ((Math.abs(averageTicks)) <= DistanceTicks);
        }
        STOP();

    }

    public void Turn(int whereToTurnTo){
        boolean WhichWay = WhichWayToTurn(whereToTurnTo, (int) readGyro());
        while (Math.abs(whereToTurnTo - readGyro()) >= 2.5) {
        double power = Math.abs((whereToTurnTo - readGyro()) / 100);
        if(power <= 0.1){power = 0.1;}
        if(WhichWay){this.TurnMotorsOn(power,power,power,power);
        }else{       this.TurnMotorsOn(power,power,power,power);}
        }
        STOP();
    }
    public void TurnMotorsOn(double PowerToLeftFront, double PowerToRightFront, double PowerToLeftBack, double PowerToRightBack){
        this.LF.setPower(PowerToLeftFront);
        this.RF.setPower(PowerToRightFront);
        this.LB.setPower(PowerToLeftBack);
        this.RB.setPower(PowerToRightBack);
        }
    private void STOP(){
        this.LB.setPower(0.0);
        this.RB.setPower(0.0);
        this.LF.setPower(0.0);
        this.RF.setPower(0.0);
        }

//-------{TOOLS}------------------------------------------------------------------------------------
    private void ResetEncoders(){
        this.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.RB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.LB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.RB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.LB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    private float readGyro(){
        return OmniAutonomous.Crabby.sensors.ReadGyro();
    }
    private boolean WhichWayToTurn(int Target, int Gyro){
        int  VirtualDegrees = Gyro;
        int counterOne = 0;
        int counterTwo = 0;
        boolean LOOP = true;
        while(LOOP){
        VirtualDegrees ++;
        counterOne ++;
        if(VirtualDegrees == 361){VirtualDegrees = 0;}
        if(VirtualDegrees == -1) {VirtualDegrees = 360;}
        LOOP = !(Math.abs(VirtualDegrees - Target) <= 2.5);
        }
        LOOP = true;
        VirtualDegrees = Gyro;
        while(LOOP){
        VirtualDegrees --;
        counterTwo ++;
        if(VirtualDegrees == 361){VirtualDegrees = 0;}
        if(VirtualDegrees == -1) {VirtualDegrees = 360;}
        LOOP = !(Math.abs(VirtualDegrees - Target) <= 2.5);
        }
        return counterOne > counterTwo;
        }
}
