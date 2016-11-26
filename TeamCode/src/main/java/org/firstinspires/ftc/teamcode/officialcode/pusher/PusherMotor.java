package org.firstinspires.ftc.teamcode.officialcode.pusher;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Higgs Bosons on 11/26/2016.
 */
public class PusherMotor {
    private DcMotor pusher;

    public PusherMotor(DcMotor pusher){
        this.pusher = pusher;
    }

    public DcMotor getPusher(){
        return pusher;
    }
}
