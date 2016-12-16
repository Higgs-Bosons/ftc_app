package org.firstinspires.ftc.teamcode.officialcode.teleop;

import org.firstinspires.ftc.teamcode.officialcode.drivetrain.IDrivetrain;
import org.firstinspires.ftc.teamcode.officialcode.pusher.IPusher;
import org.firstinspires.ftc.teamcode.officialcode.servos.IServos;
import org.firstinspires.ftc.teamcode.officialcode.servos.MyServos;

/**
 * Created by Higgs Bosons on 11/30/2016.
 */
public class DrivetrainPusherController implements Runnable {
    private IDrivetrain drivetrain;
    private IPusher pusher;

    public DrivetrainPusherController(IDrivetrain drivetrain, IPusher pusher){
        this.drivetrain = drivetrain;
        this.pusher = pusher;

    }

    @Override
    public void run() {
        try{
            while (true){
                this.drivetrain.handleMessage();
                this.pusher.handleMessage();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
