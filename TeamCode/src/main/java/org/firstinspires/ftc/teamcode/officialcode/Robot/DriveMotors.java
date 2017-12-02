package org.firstinspires.ftc.teamcode.officialcode.Robot;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.officialcode.Autonomus.AutonomousCode;
import org.firstinspires.ftc.teamcode.officialcode.Constants;
import org.firstinspires.ftc.teamcode.officialcode.IntStringDefInterfaces.*;

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
    public void Move(@rection int Direction, int Duration, double Power){
        int Distance = (int) (Duration / (Math.PI * Constants.Wheel_Diameter) * Constants.Motor_Tick_Per_Rotation );
        ResetEncoders();
        this.RF.setPower(Power * Direction);
        this.RB.setPower(Power * Direction);
        this.LF.setPower(Power * Direction);
        this.LB.setPower(Power * Direction);

        boolean isRightSideDone = false;
        boolean isLeftSideDone = false;

        while(!isLeftSideDone || !isRightSideDone){
            if(Math.abs(this.RF.getCurrentPosition())>=Distance){this.RF.setPower(0.0);this.RB.setPower(0.0);isRightSideDone = true;}
            if(Math.abs(this.LF.getCurrentPosition())>=Distance){this.LF.setPower(0.0);this.LB.setPower(0.0);isLeftSideDone = true;}
        }
    }
    public void Turn(int whereToTurnTo){
        boolean WhichWay = WhichWayToTurn(whereToTurnTo, (int) readGyro());
        while (Math.abs(whereToTurnTo - readGyro()) >= 2.5) {
            double power = Math.abs((whereToTurnTo - readGyro()) / 100);
            if(power <= 0.1){power = 0.1;}
            if(WhichWay){this.TurnMotorsOn(power,-power,power,-power);
            }else{       this.TurnMotorsOn(-power,power,-power,power);}
        }
        STOP();
    }
    public void TurnMotorsOn(double PowerToLeftFront, double PowerToRightFront, double PowerToLeftBack, double PowerToRightBack){
        this.LF.setPower(PowerToLeftFront);
        this.RF.setPower(PowerToRightFront);
        this.LB.setPower(PowerToLeftBack);
        this.RB.setPower(PowerToRightBack);
    }
    public void STOP(){
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
        return AutonomousCode.Crabby.sensors.ReadGyro();
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
