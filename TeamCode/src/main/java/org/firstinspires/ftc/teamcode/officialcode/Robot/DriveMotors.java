package org.firstinspires.ftc.teamcode.officialcode.Robot;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.officialcode.IntStringDefInterfaces.*;

public class DriveMotors {
    private DcMotor RF;
    private DcMotor RB;
    private DcMotor LF;
    private DcMotor LB;
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
        if(Direction == Constants.Forwards){
            RF.setTargetPosition(RF.getCurrentPosition()+Duration);
            RB.setTargetPosition(RB.getCurrentPosition()+Duration);
            LF.setTargetPosition(LF.getCurrentPosition()+Duration);
            LB.setTargetPosition(LB.getCurrentPosition()+Duration);

            RF.setPower(Power);
            RB.setPower(Power);
            LF.setPower(Power);
            LB.setPower(Power);

            boolean isRFDone = false;
            boolean isRBDone = false;
            boolean isLFDone = false;
            boolean isLBDone = false;
            while(!isLBDone || !isLFDone || !isRBDone || !isRFDone){
                if(RF.getCurrentPosition()>=RF.getTargetPosition()){RF.setPower(0.0);isRFDone = true;}
                if(LF.getCurrentPosition()>=LF.getTargetPosition()){LF.setPower(0.0);isLFDone = true;}
                if(RB.getCurrentPosition()>=RB.getTargetPosition()){RB.setPower(0.0);isRBDone = true;}
                if(LB.getCurrentPosition()>=LB.getTargetPosition()){LB.setPower(0.0);isLBDone = true;}
            }
        }else if(Direction == Constants.Backwards){
            RF.setTargetPosition(RF.getCurrentPosition()+Duration);
            RB.setTargetPosition(RB.getCurrentPosition()+Duration);
            LF.setTargetPosition(LF.getCurrentPosition()-Duration);
            LB.setTargetPosition(LB.getCurrentPosition()-Duration);

            RF.setPower(-Power);
            RB.setPower(-Power);
            LF.setPower(-Power);
            LB.setPower(-Power);

            boolean isRFDone = false;
            boolean isRBDone = false;
            boolean isLFDone = false;
            boolean isLBDone = false;
            while(!isLBDone || !isLFDone || !isRBDone || !isRFDone){
                if(RF.getCurrentPosition()>=RF.getTargetPosition()){RF.setPower(0.0);isRFDone = true;}
                if(LF.getCurrentPosition()<=LF.getTargetPosition()){LF.setPower(0.0);isLFDone = true;}
                if(RB.getCurrentPosition()>=RB.getTargetPosition()){RB.setPower(0.0);isRBDone = true;}
                if(LB.getCurrentPosition()<=LB.getTargetPosition()){LB.setPower(0.0);isLBDone = true;}
            }
        }
    }
    public void Move(@rection int Direction, int Degree, int Duration, double Power){

    }
    public void Turn(@rection int Direction, int Duration, double Power){

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

    
}
