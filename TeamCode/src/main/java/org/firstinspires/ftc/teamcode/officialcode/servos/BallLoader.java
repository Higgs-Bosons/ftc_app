package org.firstinspires.ftc.teamcode.officialcode.servos;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.teleop.MyMessageQueue;
import org.firstinspires.ftc.teamcode.officialcode.teleop.TeleopMessages;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

/**
 * IServos implementation of the particle loader
 */
public class BallLoader implements IServos{
    //declare servo for loader
    private Servo loader;
    //declare and initialize state
    private Constants.BallLoaderState state = Constants.BallLoaderState.DOWN;
    //declare message queue
    private BlockingQueue<TeleopMessages> queue;

    //constants for loader position
    private static final double LOADING = 0.60d;
    private static final double RESTING = 0.20d;

    /**
     * constructor to initialize servo and message queue
     * @param loader
     */
    public BallLoader(Servo loader){
        this.loader = loader;
        this.queue = MyMessageQueue.getInstance();
    }//constructor

    /**
     * move loader to down position
     */
    public void downLoader(){
        this.loader.setPosition(RESTING);
    }//downLoader

    /**
     * move loader to raised position
     */
    public void raiseLoader(){
        this.loader.setPosition(LOADING);
    }//raiseLoader

    /**
     * move loader to passed position
     * @param position
     */
    private void moveLoader(double position){
        this.loader.setPosition(position);
    }//moveLoader

    /**
     * handle servo actions based on a given message
     * @param message
     */
    private void handleServo(TeleopMessages message){
        //get metadata for message
        HashMap<String, Object> metadata = message.getMetadata();

        //based on action, take appropriate course of work
        switch (message.getRobotComponentAction()){
            case STOP:
                this.setState(Constants.BallLoaderState.DOWN);
                break;
            case START:
                if(metadata.containsKey(Constants.BallLoaderState.UP.name())){
                    this.setState(Constants.BallLoaderState.UP);
                } else {
                    this.setState(Constants.BallLoaderState.DOWN);
                }//if-else
                break;
            default:
                //if an invalid action is found, throw an exception
                throw new IllegalStateException("Cannot Handle: " + message.getRobotComponentAction());
        }//switch
    }//handleServo

    /**
     * get loader state
     * @return state
     */
    public Constants.BallLoaderState getState(){
        return state;
    }//getState

    /**
     * interface method for handling messages
     * @throws InterruptedException
     */
    @Override
    public void handleMessage() throws InterruptedException {
        //peek at message queue
        TeleopMessages msg = this.queue.peek();

        //if the message is for loader, take it and set it to the appropriate state
        if(msg != null && Constants.RobotComponent.LOADER.equals(msg.getRobotComponent())){
            msg = this.queue.take();
            this.handleServo(msg);

			switch (this.getState()){
				case DOWN:
					this.moveLoader(RESTING);
					break;
				case UP:
					this.moveLoader(LOADING);
					break;
				default:
					throw new IllegalStateException("Unknown State: " + this.getState());
			}//switch
        }//if
    }//handleMessage

    /**
     * interface method for setting state
     * @param state
     */
    @Override
    public void setState(Object state) {
        if(state instanceof Constants.BallLoaderState){
            this.state = (Constants.BallLoaderState)state;
        }else{
            throw new IllegalArgumentException("Invalid state type: " + state +
                    ". Expected object type of Constants.BallGrabberState");
        }//if-else
    }//setState
}//class
