package org.firstinspires.ftc.teamcode.officialcode.lift;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class LiftMotor {
    DcMotor leftLift;
    DcMotor rightLift;

    public LiftMotor(DcMotor leftLift, DcMotor rightLift){
        this.leftLift = leftLift;
        this.rightLift = rightLift;
    }

    public DcMotor getLeftLift(){
        return leftLift;
    }

    public DcMotor getRightLift(){
        return rightLift;
    }
}
