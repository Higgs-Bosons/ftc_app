package org.firstinspires.ftc.teamcode.officialcode.servos;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.teleop.MyMessageQueue;
import org.firstinspires.ftc.teamcode.officialcode.teleop.TeleopMessages;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

/**
 * IServos implementation of the cap ball grabber
 */
public class CapGrabber implements IServos{
    //declare servos
    private Servo top;
    private Servo bottomLeft;
    private Servo bottomRight;
    //declare and initialize state
    private Constants.CapGrabberState state = Constants.CapGrabberState.CLOSED;
    //declare message queue
    private BlockingQueue<TeleopMessages> queue;

    /**
     * initialize queue and servos
     * @param top
     * @param bottomLeft
     * @param bottomRight
     */
    public CapGrabber(Servo top, Servo bottomLeft, Servo bottomRight){
        this.top = top;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
        this.queue = MyMessageQueue.getInstance();
    }//constructor

    /**
     * close cap grabber
     * @throws InterruptedException
     */
    public void closeGrabber() throws InterruptedException {
        moveCGrabber(Constants.CapGrabberState.CLOSED);
        Thread.sleep(1000);
    }//closeGrabber

    /**
     * move cap grabber to passed in position
     * @param state
     */
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
        }//switch
    }//moveCGrabber

    /**
     * handle servo behavior based on passed in message
     * @param message
     */
    private void handleServo(TeleopMessages message){
        //get message metadata
        HashMap<String, Object> metadata = message.getMetadata();

        //based on action, take appropriate course of work
        switch (message.getRobotComponentAction()){
            case STOP:
                this.setState(Constants.CapGrabberState.CLOSED);
                break;
            case START:
                if(metadata.containsKey(Constants.CapGrabberState.READY.name())){
                    this.setState(Constants.CapGrabberState.READY);
                }else if(metadata.containsKey(Constants.CapGrabberState.HOLDING.name())) {
                    this.setState(Constants.CapGrabberState.HOLDING);
                }else{
                    this.setState(Constants.CapGrabberState.CLOSED);
                }//if-elseif-else
                break;
            default:
                //if an invalid action is found, throw an exception
                throw new IllegalStateException("Cannot Handle: " + message.getRobotComponentAction());
        }//switch
    }//handleServo

    /**
     * return current grabber state
     * @return state
     */
    public Constants.CapGrabberState getState(){
        return state;
    }//getState

    /**
     * interface method for sifting through message queue to find a message for itself
     * @throws InterruptedException
     */
    @Override
    public void handleMessage() throws InterruptedException {
        //peek at message queue
        TeleopMessages msg = this.queue.peek();

        //if component is meant for it grabber, take it and pass it to external method in order to
        // take appropriate course of action
        if(msg != null && Constants.RobotComponent.C_GRABBER.equals(msg.getRobotComponent())){
            msg = this.queue.take();
            this.handleServo(msg);

			moveCGrabber(this.getState());
        }//if
    }//handleMessage

    /**
     * interface method for setting state
     * @param state
     */
    @Override
    public void setState(Object state) {
        if(state instanceof Constants.CapGrabberState){
            this.state = (Constants.CapGrabberState)state;
        }else{
            throw new IllegalArgumentException("Invalid state type: " + state +
                    ". Expected object type of Constants.CapGrabberState");
        }//if-else
    }//setState
}//class
