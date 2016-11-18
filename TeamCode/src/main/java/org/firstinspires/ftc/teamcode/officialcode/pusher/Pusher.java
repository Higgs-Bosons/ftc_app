package org.firstinspires.ftc.teamcode.officialcode.pusher;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Created by Higgs Bosons on 11/17/2016.
 */
public class Pusher implements IPusher, Runnable {
    private DcMotor pusher;

    public Pusher(DcMotor pusher){
        this.pusher = pusher;
    }

    @Override
    public void pressButton(double power) throws InterruptedException {
        this.pusher.setPower(power);
        Thread.sleep(1000);
        this.pusher.setPower(0.0d);
    }

    @Override
    public void run() {

    }
}
