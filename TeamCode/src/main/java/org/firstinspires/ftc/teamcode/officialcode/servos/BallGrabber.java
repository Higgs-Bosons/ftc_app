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
public class BallGrabber implements IServos{
    private Servo left;
    private Servo right;
    private Constants.BallGrabberState state = Constants.BallGrabberState.CLOSED;
    private Constants.BallGrabberState lastState = Constants.BallGrabberState.CLOSED;
    private BlockingQueue<TeleopMessages> queue;

    public BallGrabber(Servo left, Servo right){
        this.left = left;
        this.right = right;
        this.queue = MyMessageQueue.getInstance();
    }

    private void openGates(){
        left.setPosition(0.25);
        right.setPosition(0.25);
    }

    private void closeGates(){
        left.setPosition(0.0);
        right.setPosition(0.0);

    }

    private void handleServo(TeleopMessages message){
        HashMap<String, Object> metadata = message.getMetadata();

        switch (message.getRobotComponentAction()){
            case STOP:
                //System.out.println("***************Stopping People Dropper*************");
                this.setState(Constants.BallGrabberState.CLOSED);
                break;
            case START:
                //if action is to start, get the key of hashmap in order to see how to start
                //System.out.println("***************Starting People Dropper*************");
                if(metadata.containsKey(Constants.BallGrabberState.OPEN.name())){
                    //System.out.println("****Message Handler Setting Servo State to Drop");
                    this.setState(Constants.BallGrabberState.OPEN);
                } else {
                    //if left or right isn't in key, stop the dispenser
                    //System.out.println("****Message Handler Setting Debris Collection State to STOP");
                    this.setState(Constants.BallGrabberState.CLOSED);
                }//if-else
                break;
            default:
                //if an invalid action is found, throw an exception
                throw new IllegalStateException("Cannot Handle: " + message.getRobotComponentAction());
        }//switch
    }

    public Constants.BallGrabberState getState(){
        return state;
    }

    @Override
    public void handleMessage() throws InterruptedException {
        TeleopMessages msg = this.queue.peek();

        if(msg != null && Constants.RobotComponent.B_GRABBER.equals(msg.getRobotComponent())){
            msg = this.queue.take();
            this.handleServo(msg);

            if(!this.getState().equals(lastState)){
                switch (this.getState()){
                    case OPEN:
                        break;
                    case CLOSED:
                        break;
                    default:
                        throw new IllegalStateException("Unknown State: " + this.getState());
                }
            }
        }

    }

    @Override
    public void setState(Object state) {
        if(state instanceof Constants.BallGrabberState){
            this.state = (Constants.BallGrabberState)state;
        }else{
            throw new IllegalArgumentException("Invalid state type: " + state +
                ". Expected object type of Constants.BallGrabberState");
        }
    }


}
