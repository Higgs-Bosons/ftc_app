package org.firstinspires.ftc.teamcode.officialcode.pusher;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Factory for pusher
 */
public class PusherFactory {
    //declare static pusher interface variable
    private static IPusher pusher;

    /**
     * get an instance for the pusher variable if it is null
     * @param opMode
     * @return
     * @throws InterruptedException
     */
    public static synchronized IPusher getInstance(OpMode opMode) throws InterruptedException{
        if(pusher == null){
            PusherMotor pm = getPusherMotor(opMode);
            pusher = new Pusher(pm);
        }//if
        return pusher;
    }//getInstance

    /**
     * get motors and PusherMotors instance
     * @param opMode
     * @return
     */
    private static PusherMotor getPusherMotor(OpMode opMode){
        //hardware map the pusher
        DcMotor pusher = opMode.hardwareMap.dcMotor.get(Constants.PUSHER_MOTOR);

        //return a PusherMotor object
        return new PusherMotor(pusher);
    }//getPusherMotor
}//class
