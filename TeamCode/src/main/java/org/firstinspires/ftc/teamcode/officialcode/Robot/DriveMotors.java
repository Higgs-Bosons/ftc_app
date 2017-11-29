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
        RF.setPower(Power * Direction);
        RB.setPower(Power * Direction);
        LF.setPower(Power * Direction);
        LB.setPower(Power * Direction);

        boolean isRightSideDone = false;
        boolean isLeftSideDone = false;

        while(!isLeftSideDone || !isRightSideDone){
            if(Math.abs(RF.getCurrentPosition())>=Distance){RF.setPower(0.0);RB.setPower(0.0);isRightSideDone = true;}
            if(Math.abs(LF.getCurrentPosition())>=Distance){LF.setPower(0.0);LB.setPower(0.0);isLeftSideDone = true;}
        }
    }
    public void Turn(int whereToTurnTo){
        while (Math.abs(whereToTurnTo - readGyro()) >= 2.5) {
            double power = -((whereToTurnTo - readGyro()) / 100);
            if(Math.abs(power) <= 0.1){
                if(power <= 0){
                    power = -0.1;
                }else{
                    power = 0.1;
                }
            }
            if(Math.abs(( readGyro() - 180)) >= whereToTurnTo){
                this.TurnMotorsOn(power,-power,power,-power);
            }else{
                this.TurnMotorsOn(-power,power,-power,power);
            }
        }
        STOP();
    }
    void TurnMotorsOn(double PowerToLeftFront, double PowerToRightFront, double PowerToLeftBack, double PowerToRightBack){
        this.LF.setPower(PowerToLeftFront);
        this.RF.setPower(PowerToRightFront);
        this.LB.setPower(PowerToLeftBack);
        this.RB.setPower(PowerToRightBack);
    }
    void STOP(){
        this.LB.setPower(0.0);
        this.RB.setPower(0.0);
        this.LF.setPower(0.0);
        this.RF.setPower(0.0);
    }
    private void ResetEncoders(){
        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    private float readGyro(){
        return AutonomousCode.Crabby.sensors.ReadGyro();
    }


    public DcMotor getRF(){
        return  this.RF;
    }
    public DcMotor getLB(){
        return  this.LB;
    }
    public DcMotor getRB(){
        return  this.RB;
    }
    public DcMotor getLF(){
        return  this.LF;
    }
}
