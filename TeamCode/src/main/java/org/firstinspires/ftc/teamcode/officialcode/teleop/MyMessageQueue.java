package org.firstinspires.ftc.teamcode.officialcode.teleop;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Class for creating message queue
 */
public class MyMessageQueue {
    //define queue and messages along with queue capacity
    private static final int CAPACITY = 25;
    private static BlockingQueue<TeleopMessages> teleopMessages;

    /**
     * private construct in order to prevent external object creation
     */
    private MyMessageQueue(){

    }//constructor

    /**
     * get instance of teleop message if it doesn't have one
     * @return teleopMessages
     */
    public static synchronized BlockingQueue<TeleopMessages> getInstance(){
        if(teleopMessages == null){
            teleopMessages = new ArrayBlockingQueue<TeleopMessages>(CAPACITY);
        }
        return teleopMessages;
    }//getInstance
}//class
