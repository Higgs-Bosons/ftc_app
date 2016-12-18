package org.firstinspires.ftc.teamcode.officialcode.teleop;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class MyMessageQueue {
    private static final int CAPACITY = 25;
    private static BlockingQueue<TeleopMessages> teleopMessages;

    private MyMessageQueue(){

    }

    public static synchronized BlockingQueue<TeleopMessages> getInstance(){
        if(teleopMessages == null){
            teleopMessages = new ArrayBlockingQueue<TeleopMessages>(CAPACITY);
        }
        return teleopMessages;
    }
}
