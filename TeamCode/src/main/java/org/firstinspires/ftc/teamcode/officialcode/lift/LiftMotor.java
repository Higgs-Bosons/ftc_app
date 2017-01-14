package org.firstinspires.ftc.teamcode.officialcode.lift;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Class for lifting motors
 */
public class LiftMotor {
    //Declare motors for lift, use a constructor to initialize it, and make their getter methods
    DcMotor leftLift;
    DcMotor rightLift;

    /**
     * initialize lift motors
     * @param leftLift
     * @param rightLift
     */
    public LiftMotor(DcMotor leftLift, DcMotor rightLift){
        this.leftLift = leftLift;
        this.rightLift = rightLift;
    }//constructor

    /**
     * getter for left lift
     * @return
     */
    public DcMotor getLeftLift(){
        return leftLift;
    }//getLeftLift

    /**
     * getter for right lift
     * @return
     */
    public DcMotor getRightLift(){
        return rightLift;
    }//getRightLift
}//class
