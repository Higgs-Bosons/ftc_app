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
public class CapGrabber implements IServos{
    private Servo top;
    private Servo bottomLeft;
    private Servo bottomRight;
    private Constants.CapGrabberState state = Constants.CapGrabberState.CLOSED;
    //private Constants.CapGrabberState lastState = Constants.CapGrabberState.CLOSED;
    private BlockingQueue<TeleopMessages> queue;

    public CapGrabber(Servo top, Servo bottomLeft, Servo bottomRight){
        this.top = top;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
        this.queue = MyMessageQueue.getInstance();
    }

    public void closeGrabber() throws InterruptedException {
        moveCGrabber(Constants.CapGrabberState.CLOSED);
        Thread.sleep(1000);
    }

    private void moveCGrabber(Constants.CapGrabberState state){
        switch (state) {
            case HOLDING:
                this.top.setPosition(0.4d);
                this.bottomRight.setPosition(0.6d);
                this.bottomLeft.setPosition(0.4d);
                break;
            case READY:
                this.top.setPosition(0.0d);
                this.bottomRight.setPosition(0.6d);
                this.bottomLeft.setPosition(0.4d);
                break;
            case CLOSED:
                this.top.setPosition(1.0d);
                this.bottomRight.setPosition(0.0d);
                this.bottomLeft.setPosition(1.0d);
                break;
            default:
                throw new IllegalStateException("Unknown State: " + state);
        }
    }

    private void handleServo(TeleopMessages message){
        HashMap<String, Object> metadata = message.getMetadata();

        switch (message.getRobotComponentAction()){
            case STOP:
                this.setState(Constants.CapGrabberState.CLOSED);
                break;
            case START:
                if(metadata.containsKey(Constants.CapGrabberState.READY.name())){
                    this.setState(Constants.CapGrabberState.READY);
                } else if(metadata.containsKey(Constants.CapGrabberState.HOLDING.name())) {
                    this.setState(Constants.CapGrabberState.HOLDING);
                }else{
                    this.setState(Constants.CapGrabberState.CLOSED);
                }
                break;
            default:
                //if an invalid action is found, throw an exception
                throw new IllegalStateException("Cannot Handle: " + message.getRobotComponentAction());
        }//switch
    }

    public Constants.CapGrabberState getState(){
        return state;
    }

    @Override
    public void handleMessage() throws InterruptedException {
        TeleopMessages msg = this.queue.peek();

        if(msg != null && Constants.RobotComponent.C_GRABBER.equals(msg.getRobotComponent())){
            msg = this.queue.take();
            this.handleServo(msg);

            //if(!this.getState().equals(lastState)){
                moveCGrabber(this.getState());
            //}
        }
    }

    @Override
    public void setState(Object state) {
        if(state instanceof Constants.CapGrabberState){
            this.state = (Constants.CapGrabberState)state;
        }else{
            throw new IllegalArgumentException("Invalid state type: " + state +
                    ". Expected object type of Constants.CapGrabberState");
        }
    }
}
