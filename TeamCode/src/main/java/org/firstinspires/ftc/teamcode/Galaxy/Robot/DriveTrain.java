package org.firstinspires.ftc.teamcode.Galaxy.Robot;

import android.util.Log;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Galaxy.Tools;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

public class DriveTrain {
    private DcMotor LeftFront, LeftBack, RightFront, RightBack;

//-------{INITIALIZATION}---------------------------------------------------------------------------
    public DriveTrain(){}
    public DriveTrain(DcMotor[] driveMotors){
        LeftFront = driveMotors[0];
        RightFront = driveMotors[1];
        RightBack = driveMotors[2];
        LeftBack = driveMotors[3];
        resetEncoders();
    }
    void addMotors(DcMotor[] driveMotors){
        LeftFront = driveMotors[0];
        RightFront = driveMotors[1];
        RightBack = driveMotors[2];
        LeftBack = driveMotors[3];
        resetEncoders();
    }


//-------{SETTING STUFF}----------------------------------------------------------------------------
    public void setBreakOrCoast(DcMotor.ZeroPowerBehavior zeroPowerBehavior){
        LeftBack.setZeroPowerBehavior(zeroPowerBehavior);
        LeftFront.setZeroPowerBehavior(zeroPowerBehavior);
        RightBack.setZeroPowerBehavior(zeroPowerBehavior);
        RightFront.setZeroPowerBehavior(zeroPowerBehavior);
    }
    public void setMotorDirection(@MotorDirections int LeftFrontDirection, @MotorDirections int RightFrontDirection,
                                  @MotorDirections int RightBackDirection, @MotorDirections int LeftBackDirection ){

        if(LeftFrontDirection  == REVERSE){LeftFront.setDirection(DcMotorSimple.Direction.REVERSE);}
        else{LeftFront.setDirection(DcMotorSimple.Direction.FORWARD);}

        if(RightFrontDirection == REVERSE){RightFront.setDirection(DcMotorSimple.Direction.REVERSE);}
        else{RightFront.setDirection(DcMotorSimple.Direction.FORWARD);}

        if(RightBackDirection  == REVERSE){RightBack.setDirection(DcMotorSimple.Direction.REVERSE);}
        else{RightBack.setDirection(DcMotorSimple.Direction.FORWARD);}

        if(LeftBackDirection   == REVERSE){LeftBack.setDirection(DcMotorSimple.Direction.REVERSE);}
        else{LeftBack.setDirection(DcMotorSimple.Direction.FORWARD);}
    }

    public int[] getMotorValues(){
        int[] values = new int[4];
        values[0] = LeftFront.getCurrentPosition();
        values[1] = LeftBack.getCurrentPosition();
        values[2] = RightFront.getCurrentPosition();
        values[3] = RightBack.getCurrentPosition();
        return values;
    }

//-------{DRIVING}----------------------------------------------------------------------------------
    public void stopRobot(){
        LeftFront.setPower(0);
        LeftBack.setPower(0);
        RightFront.setPower(0);
        RightBack.setPower(0);
    }
    public void driveByTank(Gamepad gamepad, double speed){
        // Speed must be between 0-1, examples: 0.4, 0.6, 1.0, etc.

        double left = gamepad.left_stick_y * speed;
        double right = gamepad.right_stick_y * speed;

        LeftFront.setPower(left);
        LeftBack.setPower(left);
        RightFront.setPower(right);
        RightBack.setPower(right);
    }
    public void driveByJoystick(Gamepad gamepad, double speed){
        double RFPower, RBPower, LFPower, LBPower;
        double stick1X, stick1Y, stick2X;

        stick1X = gamepad.left_stick_x  * speed;
        stick1Y = gamepad.left_stick_y  * speed;
        stick2X = gamepad.right_stick_x * speed;

        RFPower = (((stick1Y + stick1X) / 2)+stick2X)*2;
        RBPower = (((stick1Y - stick1X) / 2)+stick2X)*2;
        LFPower = (((stick1Y - stick1X) / 2)-stick2X)*2;
        LBPower = (((stick1Y + stick1X) / 2)-stick2X)*2;

        RightFront.setPower(RFPower);
        RightBack.setPower(RBPower);
        LeftFront.setPower(LFPower);
        LeftBack.setPower(LBPower);
    }
    public void moveDriveMotor(int motorTag, double power){
        if(motorTag == RIGHT_FRONT){
            RightFront.setPower(power);
        }else if(motorTag == LEFT_FRONT){
            LeftFront.setPower(power);
        }else if(motorTag == LEFT_BACK){
            LeftBack.setPower(power);
        }else if(motorTag == RIGHT_BACK){
            RightBack.setPower(power);
        }else{
            Tools.showToast("Motor Tag Invalid. (Robots.DriveTrain.moveMotor");
        }
    }

//-------{AUTONOMOUS}----------------------------------------------------------------------------------
    //--{MOVEMENT}---------------------------------------------------
    public void driveAtHeader(double degrees, double power){
        double LFPower = 0, RFPower = 0, RBPower = 0, LBPower = 0;

        while(degrees < 0)
            degrees += 360;

        degrees = (degrees % 360);

        if(0 <= degrees && degrees < 90){
            RFPower = power;
            LBPower = power;
            LFPower = power - ((degrees / 90)*2*power);
            RBPower = power - ((degrees / 90)*2*power);
        }else if(90 <= degrees && degrees < 180){
            LFPower = -power;
            RBPower = -power;
            RFPower = power - (((degrees-90) / 90)*2*power);
            LBPower = power - (((degrees-90) / 90)*2*power);
        }else if(180 <= degrees && degrees < 270){
            RFPower = -power;
            LBPower = -power;
            LFPower = -(power - (((degrees-180) / 90)*2*power));
            RBPower = -(power - (((degrees-180) / 90)*2*power));
        }else if(270 <= degrees && degrees < 360){
            LFPower = power;
            RBPower = power;
            RFPower = -(power - (((degrees-90) / 90)*2*power));
            LBPower = -(power - (((degrees-90) / 90)*2*power));
        }

        RightFront.setPower(-RFPower);
        RightBack.setPower(-RBPower);
        LeftFront.setPower(-LFPower);
        LeftBack.setPower(-LBPower);
        Log.d("LF", LFPower +", RF: " + RFPower + ", RB: " + RBPower + "LB: " + LBPower);
    }
    public void driveAtHeader(double degrees, double power, double spinPower){
        double LFPower = 0, RFPower = 0, RBPower = 0, LBPower = 0;

        while(degrees < 0)
            degrees = 360 + degrees;

        if(degrees != 0)
            degrees = (degrees % 360);

        if(0 <= degrees && degrees < 90){
            RFPower = power;
            LBPower = power;
            LFPower = power - ((degrees / 90)*2*power);
            RBPower = power - ((degrees / 90)*2*power);
        }else if(90 <= degrees && degrees < 180){
            LFPower = -power;
            RBPower = -power;
            RFPower = power - (((degrees-90) / 90)*2*power);
            LBPower = power - (((degrees-90) / 90)*2*power);
        }else if(180 <= degrees && degrees < 270){
            RFPower = -power;
            LBPower = -power;
            LFPower = -(power - (((degrees-180) / 90)*2*power));
            RBPower = -(power - (((degrees-180) / 90)*2*power));
        }else if(270 <= degrees && degrees < 360){
            LFPower = power;
            RBPower = power;
            RFPower = -(power - (((degrees-90) / 90)*2*power));
            LBPower = -(power - (((degrees-90) / 90)*2*power));
        }

        LFPower = -(LFPower + spinPower);
        RFPower = -(RFPower - spinPower);
        RBPower = -(RBPower - spinPower);
        LBPower = -(LBPower + spinPower);

        RightFront.setPower(RFPower);
        RightBack.setPower( RBPower);
        LeftFront.setPower( LFPower);
        LeftBack.setPower(  LBPower);
    }

    public void moveDegrees(double direction, int degrees, double spin, double maxPower, double minPower, double precision){
        final int RATIO_BILLY = 5000;
        int averageDegrees;
        double power = maxPower;
        double spinPower = spin;
        degrees = (int) ((degrees / 360.0) * 1150.0);
        resetEncoders();
        averageDegrees = (Math.abs(LeftFront.getCurrentPosition()) + Math.abs(RightFront.getCurrentPosition())
                        + Math.abs(RightBack.getCurrentPosition()) + Math.abs(LeftBack.getCurrentPosition()))/4;

        while(Math.abs(Math.abs(averageDegrees) - Math.abs(degrees)) > precision){
            driveAtHeader(direction,power, spinPower);

            averageDegrees = (Math.abs(LeftFront.getCurrentPosition()) + Math.abs(RightFront.getCurrentPosition())
                    + Math.abs(RightBack.getCurrentPosition()) + Math.abs(LeftBack.getCurrentPosition()))/4;

            power = (Math.abs(degrees - averageDegrees)/RATIO_BILLY);
            power = (power > maxPower) ? maxPower : power;
            power = (power < minPower) ? minPower : power;

            spinPower = (spin * (power / maxPower));
        }
    }
    public void moveDegrees(double direction, int degrees, double maxPower, double minPower, double precision){
        final int RATIO_BILLY = 5000;
        int averageDegrees;
        double power = maxPower;
        degrees = (int) ((degrees / 360.0) * 1150.0);

        resetEncoders();

        averageDegrees = (Math.abs(LeftFront.getCurrentPosition()) + Math.abs(RightFront.getCurrentPosition())
                + Math.abs(RightBack.getCurrentPosition()) + Math.abs(LeftBack.getCurrentPosition()))/4;
        while(Math.abs(Math.abs(averageDegrees) - Math.abs(degrees)) > precision){
            driveAtHeader(direction,power);

            averageDegrees = (Math.abs(LeftFront.getCurrentPosition()) + Math.abs(RightFront.getCurrentPosition())
                    + Math.abs(RightBack.getCurrentPosition()) + Math.abs(LeftBack.getCurrentPosition()))/4;
            power = ((degrees - averageDegrees)/RATIO_BILLY);
            power = (power > maxPower) ? maxPower : power;
            power = (power < minPower) ? minPower : power;
        }
        stopRobot();
    }

    public void spinRobot(double spinPower) {
        RightFront.setPower(-spinPower);
        RightBack.setPower(-spinPower);
        LeftFront.setPower(spinPower);
        LeftBack.setPower(spinPower);
    }

    public void turnGyro(int toDegree, BNO055IMU IMU){
        double power = 0.6;
        float degrees = getGyroReading(IMU);
        for(int count = 1; count !=3; count++){
            boolean WhichWay = WhichWayToTurn(toDegree, (int) degrees);
            while(HowFar(toDegree, (int) degrees) >= 50) {
                if(!WhichWay)
                    spinRobot(power);
                else
                    spinRobot(-power);
            }
            power -= 0.2;
        }
        stopRobot();
    }

    //--{TOOLS}------------------------------------------------------
    private float getGyroReading(BNO055IMU IMU){
        return IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
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
    public void resetEncoders(){
        RightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        RightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
