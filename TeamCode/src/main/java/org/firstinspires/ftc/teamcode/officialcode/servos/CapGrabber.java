package org.firstinspires.ftc.teamcode.officialcode.servos;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.teleop.MyMessageQueue;
import org.firstinspires.ftc.teamcode.officialcode.teleop.TeleopMessages;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class CapGrabber implements IServos{
    Servo top;
    Servo bottom;
    BlockingQueue<TeleopMessages> queue;

    public CapGrabber(Servo top, Servo bottom){
        this.top = top;
        this.bottom = bottom;
        this.queue = MyMessageQueue.getInstance();
    }

    @Override
    public void handleMessage() throws InterruptedException {

    }

    @Override
    public void setState(Constants state) {

    }
}
