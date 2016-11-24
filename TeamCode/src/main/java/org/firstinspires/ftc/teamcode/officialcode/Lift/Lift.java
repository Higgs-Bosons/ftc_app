package org.firstinspires.ftc.teamcode.officialcode.Lift;

import org.firstinspires.ftc.teamcode.officialcode.teleop.MyMessageQueue;
import org.firstinspires.ftc.teamcode.officialcode.teleop.TeleopMessages;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class Lift implements ILift, Runnable {
    private LiftMotor lift;
    private BlockingQueue<TeleopMessages> queue;

    public Lift(LiftMotor lift){
        this.lift = lift;
        this.queue = MyMessageQueue.getInstance();
    }

    @Override
    public void run() {

    }
}
