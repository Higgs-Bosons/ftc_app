package org.firstinspires.ftc.teamcode.officialcode.lift;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class LiftMotor {
    DcMotor lift;

    public LiftMotor(DcMotor lift){
        this.lift = lift;
    }

    public DcMotor getLift(){
        return lift;
    }
}
