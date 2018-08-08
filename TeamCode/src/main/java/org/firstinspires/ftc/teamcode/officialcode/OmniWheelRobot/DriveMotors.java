package org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.officialcode.Autonomus.OmniAutonomous;
import org.firstinspires.ftc.teamcode.officialcode.Autonomus.OmniGlyph;
import org.firstinspires.ftc.teamcode.officialcode.Constants;

public class DriveMotors extends Constants{
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
    public void Move(Constants.RobotDirection Direction, double DistanceInches, double Power) {
        int DistanceTicks = (int) (DistanceInches / (Math.PI * Constants.Wheel_Diameter) * Constants.Motor_Tick_Per_Rotation);
        double X = Direction.getX();
        double Y = Direction.getY();
        boolean keepLooping = true;
        int averageTicks;
        float RFPower = (float) ((X+Y)*Power);
        float RBPower = (float) ((Y-X)*Power);
        float LFPower = (float) ((Y-X)*Power);
        float LBPower = (float) ((X+Y)*Power);
        ResetEncoders();
        TurnMotorsOn(LFPower,RFPower, LBPower, RBPower);
        while (keepLooping){
            averageTicks = ((Math.abs(RF.getCurrentPosition()) + Math.abs(RB.getCurrentPosition())
                    + Math.abs(LF.getCurrentPosition()) + Math.abs(LB.getCurrentPosition()))/4);
            keepLooping = ((Math.abs(averageTicks)) <= (Math.abs(DistanceTicks)));
        }
        STOP();

    }
    public void Turn(int whereToTurnTo){
        int counter = 0;
        double power = 0.6;
        boolean WhichWay = WhichWayToTurn(whereToTurnTo, (int) readGyro());
        while(HowFar(whereToTurnTo, (int) readGyro()) >= 50) {
            if(!WhichWay){this.TurnMotorsOn(power,-power,power,-power);}else{this.TurnMotorsOn(-power,power,-power,power);}
        }
        WhichWay = WhichWayToTurn(whereToTurnTo, (int) readGyro());
        power = 0.5;
        while (HowFar(whereToTurnTo, (int) readGyro()) >= 25){
            if(!WhichWay){this.TurnMotorsOn(power,-power,power,-power);}else{this.TurnMotorsOn(-power,power,-power,power);}
        }
        power = 0.2;
        WhichWay = WhichWayToTurn(whereToTurnTo, (int) readGyro());
        while (HowFar(whereToTurnTo, (int) readGyro()) >= 2.5) {
            if(counter == 25){WhichWay = WhichWayToTurn(whereToTurnTo, (int) readGyro());counter = 0;}else{counter++;}
            if(!WhichWay){this.TurnMotorsOn(power,-power,power,-power);}else{this.TurnMotorsOn(-power,power,-power,power);}
        }
        STOP();
    }
    public void XTurn(int whereToTurnTo){
        double power = 0.6;
        for(int count = 1; count !=3; count++){
            boolean WhichWay = WhichWayToTurn(whereToTurnTo, (int) XReadGyro());
            while(HowFar(whereToTurnTo, (int) XReadGyro()) >= 50) {
                if(!WhichWay){this.TurnMotorsOn(power,-power,power,-power);}else{this.TurnMotorsOn(-power,power,-power,power);}
            }
            power-=0.2;
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
    public DcMotor GetMotor(String Motor){
        switch (Motor) {
            case RightBack:return RB;
            case RightFront:return RF;
            case LeftBack:return LB;
            default:return LF;
        }
    }
    public void XMove(Constants.RobotDirection Direction, double DistanceInches, double Power){
        int DistanceTicks = (int) (DistanceInches / (Math.PI * Constants.Wheel_Diameter) * Constants.Motor_Tick_Per_Rotation);
        double MaxPower = Power;
        double X = Direction.getX();
        double Y = Direction.getY();
        boolean keepLooping = true;
        int averageTicks;
        float RFPower;
        float RBPower;
        float LFPower;
        float LBPower;
        ResetEncoders();
        rampUp(Power, (int)(((DistanceTicks)/10000)*Power), Direction);
        while (keepLooping){
            Power = (DistanceTicks / 276000);
            if(Power > MaxPower){Power = MaxPower;}
            if(Power < 0.1){Power = 0.1;}
            RFPower = (float) ((X+Y)*Power);
            RBPower = (float) ((Y-X)*Power);
            LFPower = (float) ((Y-X)*Power);
            LBPower = (float) ((X+Y)*Power);
            TurnMotorsOn(LFPower,RFPower, LBPower, RBPower);
            averageTicks = ((Math.abs(RF.getCurrentPosition()) + Math.abs(RB.getCurrentPosition())
                    + Math.abs(LF.getCurrentPosition()) + Math.abs(LB.getCurrentPosition()))/4);
            keepLooping = ((Math.abs(averageTicks)) <= (Math.abs(DistanceTicks)));
        }
        STOP();

    }
    private void rampUp(double finalPower, int speed, RobotDirection Direction){
        double X = Direction.getX();
        double Y = Direction.getY();
        float RFPower;
        float RBPower;
        float LFPower;
        float LBPower;
        for (int Power = 0; Power < finalPower; Power+=speed/1000){
            RFPower = (float) ((X+Y)*Power);
            RBPower = (float) ((Y-X)*Power);
            LFPower = (float) ((Y-X)*Power);
            LBPower = (float) ((X+Y)*Power);
            TurnMotorsOn(LFPower,RFPower, LBPower, RBPower);
            try{Thread.sleep(50);}catch(Exception ignore){}
        }
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
    private float XReadGyro(){
        return OmniGlyph.Omni.sensors.ReadGyro();
    }
    private float readGyro(){
        return OmniAutonomous.Omni.sensors.ReadGyro();
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
    private int HowFar(int Target, int Gyro){
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
        if(counterOne < counterTwo){
            return counterOne;
        }
        return counterTwo;
    }
}
