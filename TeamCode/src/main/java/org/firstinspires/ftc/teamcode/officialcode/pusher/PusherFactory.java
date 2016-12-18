package org.firstinspires.ftc.teamcode.officialcode.pusher;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Created by Higgs Bosons on 11/22/2016.
 */
public class PusherFactory {
    private static IPusher pusher;

    public static synchronized IPusher getInstance(OpMode opMode) throws InterruptedException{
        if(pusher == null){
            PusherMotor pm = getPusherMotor(opMode);
            pusher = new Pusher(pm);
        }
        return pusher;
    }

    private static PusherMotor getPusherMotor(OpMode opMode){
        DcMotor pusher = opMode.hardwareMap.dcMotor.get(Constants.PUSHER_MOTOR);

        return new PusherMotor(pusher);
    }
}