package org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot;

import android.util.Log;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Galaxy.Tools;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

public class DriveTrain {
    private DcMotor LeftFront, LeftBack, RightFront, RightBack;
    private final double POWER_DECAY_RATIO = 600;
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
        double RFPower, RBPower, LFPower, LBPower;

        degrees = ((degrees % 360) - 90);
        if(degrees < 0){
            degrees = (360 + degrees);
        }

        double X = Math.cos((degrees / 180) * 3.14159265358979);
        double Y = Math.sin((degrees / 180) * 3.14159265358979);

        RFPower = (Y + X)*power;
        RBPower = (Y - X)*power;
        LFPower = (Y - X)*power;
        LBPower = (Y + X)*power;


        RightFront.setPower(RFPower);
        RightBack.setPower(RBPower);
        LeftFront.setPower(LFPower);
        LeftBack.setPower(LBPower);
    }
    public void driveAtHeader(double degrees, double power, double spinPower){
        double RFPower, RBPower, LFPower, LBPower;

        degrees = ((degrees % 360) - 90);
        if(degrees < 0){
            degrees = (360 + degrees);
        }

        double X = Math.cos((degrees / 180) * 3.14159265358979);
        double Y = Math.sin((degrees / 180) * 3.14159265358979);

        RFPower = (Y + X)*power;
        RBPower = (Y - X)*power;
        LFPower = (Y - X)*power;
        LBPower = (Y + X)*power;

        RFPower = (RFPower + spinPower);
        RBPower = (RBPower + spinPower);
        LFPower = (LFPower - spinPower);
        LBPower = (LBPower - spinPower);


        if(RFPower > 1.0)  LFPower = (LFPower - (1.0 - RFPower));
        if(RFPower < -1.0) LFPower = (LFPower + (-1.0 - RFPower));
        if(RBPower > 1.0)  LBPower = (LBPower - (1.0 - RBPower));
        if(RBPower < -1.0) LBPower = (LBPower + (-1.0 - RBPower));
        
        if(LFPower > 1.0)  RFPower = (RFPower - (1.0 - LFPower));
        if(LFPower < -1.0) RFPower = (RFPower + (-1.0 - LFPower));
        if(LBPower > 1.0)  RBPower = (RBPower - (1.0 - LBPower));
        if(LBPower < -1.0) RBPower = (RBPower + (-1.0 - LBPower));
            
        

        RightFront.setPower(RFPower);
        RightBack.setPower(RBPower);
        LeftFront.setPower(LFPower);
        LeftBack.setPower(LBPower);
    }
    private void spinRobot(double spinPower) {
        RightFront.setPower(-spinPower);
        RightBack.setPower(-spinPower);
        LeftFront.setPower(spinPower);
        LeftBack.setPower(spinPower);
    }

    public void moveRobot(double direction, double inches, double spin, double maxPower, double minPower, double precision) throws InterruptedException{
        int numberOfTicksMoved;
        double power = maxPower;
        double spinPower;
        int ticksToMove = (int) (inches * (1120 / (Math.PI * 4)));
        resetEncoders();
        numberOfTicksMoved = (Math.abs(LeftFront.getCurrentPosition()) + Math.abs(RightFront.getCurrentPosition())
                        + Math.abs(RightBack.getCurrentPosition()) + Math.abs(LeftBack.getCurrentPosition()))/4;
        try {

            while (Math.abs(Math.abs(numberOfTicksMoved) - Math.abs(ticksToMove)) > precision) {
                numberOfTicksMoved = (Math.abs(LeftFront.getCurrentPosition()) + Math.abs(RightFront.getCurrentPosition())
                        + Math.abs(RightBack.getCurrentPosition()) + Math.abs(LeftBack.getCurrentPosition())) / 4;

                power = Math.abs(Math.abs(ticksToMove) - Math.abs(numberOfTicksMoved)) / POWER_DECAY_RATIO;
                power = (power > maxPower) ? maxPower : (power < minPower) ? minPower : power;
                if (Math.abs(numberOfTicksMoved) > Math.abs(ticksToMove)) power = -power;

                spinPower = (spin * (power / maxPower));

                driveAtHeader(direction, power, spinPower);
                Thread.sleep(0,50);
            }
        }finally {
            stopRobot();
        }
    }
    public void moveRobot(double direction, double inches, double maxPower, double minPower, double precision) throws InterruptedException{
        int numberOfTicksMoved;
        double power = maxPower;
        int ticksToMove = (int) (inches * (1120 / (Math.PI * 4)));

        resetEncoders();

        numberOfTicksMoved = (Math.abs(LeftFront.getCurrentPosition()) + Math.abs(RightFront.getCurrentPosition())
                + Math.abs(RightBack.getCurrentPosition()) + Math.abs(LeftBack.getCurrentPosition()))/4;
        try {
            while (Math.abs(Math.abs(numberOfTicksMoved) - Math.abs(ticksToMove)) > precision) {

                driveAtHeader(direction, power);

                numberOfTicksMoved = (Math.abs(LeftFront.getCurrentPosition()) + Math.abs(RightFront.getCurrentPosition())
                        + Math.abs(RightBack.getCurrentPosition()) + Math.abs(LeftBack.getCurrentPosition())) / 4;

                power = Math.abs(Math.abs(ticksToMove) - Math.abs(numberOfTicksMoved)) / POWER_DECAY_RATIO;
                power = (power > maxPower) ? maxPower : (power < minPower) ? minPower : power;
                if (Math.abs(numberOfTicksMoved) > Math.abs(ticksToMove)) power = -power;

                Thread.sleep(0,50);
            }
        }finally {
            stopRobot();
        }
    }
    public void moveRobot(double direction, double inches, double power) throws InterruptedException{
        int numberOfTicksMoved;
        int ticksToMove = (int) (inches * (1120 / (Math.PI * 4)));

        resetEncoders();

        numberOfTicksMoved = (Math.abs(LeftFront.getCurrentPosition()) + Math.abs(RightFront.getCurrentPosition())
                + Math.abs(RightBack.getCurrentPosition()) + Math.abs(LeftBack.getCurrentPosition()))/4;
        try {
            driveAtHeader(direction, power);
            while (Math.abs(numberOfTicksMoved) < Math.abs(ticksToMove)) {
                Thread.sleep(0, 50);
                numberOfTicksMoved = (Math.abs(LeftFront.getCurrentPosition()) + Math.abs(RightFront.getCurrentPosition())
                        + Math.abs(RightBack.getCurrentPosition()) + Math.abs(LeftBack.getCurrentPosition()))/4;
            }
        }catch (InterruptedException e){
            stopRobot();
            throw e;
        }
    }

    public void gyroTurn(int toDegree, BNO055IMU IMU) throws InterruptedException{
        double power;
        boolean WhichWay = WhichWayToTurn(toDegree, (int) getGyroReading(IMU));

        while (HowFar(toDegree, (int) getGyroReading(IMU)) >= 2) {
            power = (HowFar(toDegree, (int) getGyroReading(IMU))) / 60.0;
            power = (power < 0.1) ? 0.1 : power;
            power = (power > 1.0) ? 1.0 : power;

            if (WhichWay) {
                spinRobot(-power);
            } else {
                spinRobot(power);
            }
            WhichWay = WhichWayToTurn(toDegree, (int) getGyroReading(IMU));

            Thread.sleep(0,50);
        }
        stopRobot();

    }

    //--{TOOLS}------------------------------------------------------
    private float getGyroReading(BNO055IMU IMU){
        float degree = IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        if(degree<0){
            degree = (180+(181 - Math.abs(degree)));
        }
        return degree;
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
