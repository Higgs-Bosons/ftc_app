package org.firstinspires.ftc.teamcode.officialcode.lift;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class LiftFactory {
    //declare lift interface
    private static ILift lift;

    /**
     * method for getting instance of lift motors for lift interface, it will get an instance only
     * if the lift interface is null
     * @param opMode
     * @return lift
     */
    public static synchronized ILift getInstance(OpMode opMode){
        if(lift == null){
            LiftMotor lm = getLiftMotor(opMode);
            lift = new Lift(lm);
        }
        return lift;
    }//getInstance

    /**
     * hardwaremap lift motors and create LiftMotors object
     * @param opMode
     * @return LiftMotor
     */
    private static LiftMotor getLiftMotor(OpMode opMode){
        DcMotor leftLift = opMode.hardwareMap.dcMotor.get(Constants.LEFT_LIFT_MOTOR);
        DcMotor rightLift = opMode.hardwareMap.dcMotor.get(Constants.RIGHT_LIFT_MOTOR);

        return new LiftMotor(leftLift, rightLift);
    }//getLiftMotor
}//class
