package org.firstinspires.ftc.teamcode.officialcode.teleop;

import org.firstinspires.ftc.teamcode.officialcode.drivetrain.IDrivetrain;
import org.firstinspires.ftc.teamcode.officialcode.pusher.IPusher;
import org.firstinspires.ftc.teamcode.officialcode.servos.IServos;
import org.firstinspires.ftc.teamcode.officialcode.servos.MyServos;

/**
 * Class for running drivetrain and pusher
 */
public class DrivetrainPusherController implements Runnable {
    //declare drivetrain and pusher interfaces
    private IDrivetrain drivetrain;
    private IPusher pusher;

    /**
     * Initialize variables
     * @param drivetrain
     * @param pusher
     */
    public DrivetrainPusherController(IDrivetrain drivetrain, IPusher pusher){
        this.drivetrain = drivetrain;
        this.pusher = pusher;

    }//constructor

    /**
     * Runnable's run method
     */
    @Override
    public void run() {
        //run components until interruption
        try{
            while (true){
                this.drivetrain.handleMessage();
                this.pusher.handleMessage();
            }//while
        }catch (InterruptedException e){
            e.printStackTrace();
        }//catch
    }//run
}//class
