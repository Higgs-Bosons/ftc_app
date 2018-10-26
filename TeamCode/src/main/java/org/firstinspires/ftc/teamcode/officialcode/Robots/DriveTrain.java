package org.firstinspires.ftc.teamcode.officialcode.Robots;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

public class DriveTrain extends Robot{
    private DcMotor LeftFront, LeftBack, RightFront, RightBack;
    public DriveTrain(){}
    public DriveTrain(DcMotor[] driveMotors){
        LeftFront = driveMotors[0];
        RightFront = driveMotors[1];
        RightBack = driveMotors[2];
        LeftBack = driveMotors[3];
    }

    public void addDriveMotors(DcMotor[] driveMotors){
       LeftFront = driveMotors[0];
       RightFront = driveMotors[1];
       RightBack = driveMotors[2];
       LeftBack = driveMotors[3];
    }

    public void setBreakOrCoast(DcMotor.ZeroPowerBehavior zeroPowerBehavior){
        LeftBack.setZeroPowerBehavior(zeroPowerBehavior);
        LeftFront.setZeroPowerBehavior(zeroPowerBehavior);
        RightBack.setZeroPowerBehavior(zeroPowerBehavior);
        RightFront.setZeroPowerBehavior(zeroPowerBehavior);
    }
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
        double stick1X;
        double stick1Y;

        stick1X = gamepad.left_stick_x * speed;
        stick1Y = gamepad.left_stick_y * speed;

        double RFpower = ((((stick1Y + stick1X) / 2)+  stick1X) * 2);
        double RBpower = ((((stick1Y - stick1X) / 2)+  stick1X) * 2);
        double LFpower = ((-((stick1Y - stick1X) / 2)+ stick1X) * 2);
        double LBpower = ((-((stick1Y + stick1X) / 2)+ stick1X) * 2);


        RightFront.setPower(RFpower);
        RightBack.setPower(RBpower);
        LeftFront.setPower(LFpower);
        LeftBack.setPower(LBpower);
    }

}
