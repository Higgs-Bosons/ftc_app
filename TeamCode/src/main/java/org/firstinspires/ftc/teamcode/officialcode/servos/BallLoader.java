package org.firstinspires.ftc.teamcode.officialcode.servos;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.teleop.MyMessageQueue;
import org.firstinspires.ftc.teamcode.officialcode.teleop.TeleopMessages;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class BallLoader implements IServos{
    private Servo loader;
    private Constants.BallLoaderState state = Constants.BallLoaderState.DOWN;
    private Constants.BallLoaderState lastState = Constants.BallLoaderState.DOWN;
    private BlockingQueue<TeleopMessages> queue;

    private static final double LOADING = 0.25d;
    private static final double RESTING = 0.0d;

    public BallLoader(Servo loader){
        this.loader = loader;
        this.queue = MyMessageQueue.getInstance();
    }

    private void moveLoader(double position){
        this.loader.setPosition(position);
    }

    private void handleServo(TeleopMessages message){
        HashMap<String, Object> metadata = message.getMetadata();

        switch (message.getRobotComponentAction()){
            case STOP:
                //System.out.println("***************Stopping People Dropper*************");
                this.setState(Constants.BallLoaderState.DOWN);
                break;
            case START:
                //if action is to start, get the key of hashmap in order to see how to start
                //System.out.println("***************Starting People Dropper*************");
                if(metadata.containsKey(Constants.BallLoaderState.UP.name())){
                    //System.out.println("****Message Handler Setting Servo State to Drop");
                    this.setState(Constants.BallLoaderState.UP);
                } else {
                    //if left or right isn't in key, stop the dispenser
                    //System.out.println("****Message Handler Setting Debris Collection State to STOP");
                    this.setState(Constants.BallLoaderState.DOWN);
                }//if-else
                break;
            default:
                //if an invalid action is found, throw an exception
                throw new IllegalStateException("Cannot Handle: " + message.getRobotComponentAction());
        }//switch
    }

    public Constants.BallLoaderState getState(){
        return state;
    }

    @Override
    public void handleMessage() throws InterruptedException {
        TeleopMessages msg = this.queue.peek();

        if(msg != null && Constants.RobotComponent.LOADER.equals(msg.getRobotComponent())){
            msg = this.queue.take();
            this.handleServo(msg);

            if(!this.getState().equals(lastState)){
                switch (this.getState()){
                    case DOWN:
                        this.moveLoader(RESTING);
                        break;
                    case UP:
                        this.moveLoader(LOADING);
                        break;
                    default:
                        throw new IllegalStateException("Unknown State: " + this.getState());
                }
            }
        }

    }

    @Override
    public void setState(Object state) {
        if(state instanceof Constants.BallLoaderState){
            this.state = (Constants.BallLoaderState)state;
        }else{
            throw new IllegalArgumentException("Invalid state type: " + state +
                    ". Expected object type of Constants.BallGrabberState");
        }

    }
}
