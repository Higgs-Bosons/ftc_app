package org.firstinspires.ftc.teamcode.officialcode.servos;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.teleop.MyMessageQueue;
import org.firstinspires.ftc.teamcode.officialcode.teleop.TeleopMessages;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class BallGrabber implements IServos{
    Servo left;
    Servo right;
    BlockingQueue<TeleopMessages> queue;

    public BallGrabber(Servo left, Servo right){
        this.left = left;
        this.right = right;
        this.queue = MyMessageQueue.getInstance();
    }

    @Override
    public void handleMessage() throws InterruptedException {

    }

    @Override
    public void setState(Constants state) {

    }
}
