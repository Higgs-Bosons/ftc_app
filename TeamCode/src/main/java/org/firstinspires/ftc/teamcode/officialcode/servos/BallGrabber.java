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
    //private Constants.BallGrabberState lastState = Constants.BallGrabberState.CLOSED;
    private BlockingQueue<TeleopMessages> queue;

    private static final double OPEN_GATES = 0.70d;
    private static final double CLOSE_GATES = 0.30d;

    public BallGrabber(Servo left, Servo right){
        this.left = left;
        this.right = right;
        this.queue = MyMessageQueue.getInstance();
    }

    public void fullClose(){
        left.setPosition(0.0d);
        right.setPosition(1.0d);
    }

    private void moveGates(double position){
        left.setPosition(position);
        right.setPosition(1.0d - position);
        //System.out.println("Moving Gates Position: " + position);
    }

    private void handleServo(TeleopMessages message){
        HashMap<String, Object> metadata = message.getMetadata();

        //System.out.println("Grabber action: " + message.getRobotComponentAction());
        switch (message.getRobotComponentAction()){
            case STOP:
                this.setState(Constants.BallGrabberState.CLOSED);
                //System.out.println("Setting state of ball grabber to closed");
                break;
            case START:
                if(metadata.containsKey(Constants.BallGrabberState.OPEN.name())){
                    this.setState(Constants.BallGrabberState.OPEN);
                } else {
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

        //    if(!this.getState().equals(lastState)){
                switch (this.getState()){
                    case OPEN:
                        this.moveGates(OPEN_GATES);
                        break;
                    case CLOSED:
                        this.moveGates(CLOSE_GATES);
                        break;
                    default:
                        throw new IllegalStateException("Unknown State: " + this.getState());
                }
          //  }
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
