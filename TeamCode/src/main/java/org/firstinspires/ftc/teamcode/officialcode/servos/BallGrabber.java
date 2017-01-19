package org.firstinspires.ftc.teamcode.officialcode.servos;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.teleop.MyMessageQueue;
import org.firstinspires.ftc.teamcode.officialcode.teleop.TeleopMessages;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

/**
 * IServos implementation of the particle grabber
 */
public class BallGrabber implements IServos{
	//declare servos
    private Servo left;
    private Servo right;
	//declare and initialize state
    private Constants.BallGrabberState state = Constants.BallGrabberState.CLOSED;
	//delcare message queue
    private BlockingQueue<TeleopMessages> queue;

	//Constants for servo positions
    private static final double OPEN_GATES = 0.5d;
    private static final double CLOSE_GATES = 0.1d;
	private static final double FULL_CLOSE = 0.0d;

	/**
	 * initialize servos and queue
	 * @param left
	 * @param right
	 */
    public BallGrabber(Servo left, Servo right){
        this.left = left;
        this.right = right;
        this.queue = MyMessageQueue.getInstance();
    }//constructor

	/**
	 * close gates completely
	 */
    public void fullClose(){
        moveGates(FULL_CLOSE);
    }//fullClose

    /**
     * partially close gates
     */
    public void partialClose(){
        moveGates(CLOSE_GATES);
    }

	/**
	 * moving the gates to specific position
	 */
    private void moveGates(double position){
        left.setPosition(position);
        right.setPosition(1.0d - position);
        //System.out.println("Moving Gates Position: " + position);
    }//moveGates
	
	/**
	 * handle servo based on given message
	 */
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
	
	/**
	 *
	 */
    public Constants.BallGrabberState getState(){
        return state;
    }

	/**
	 *
	 */
    @Override
    public void handleMessage() throws InterruptedException {
        TeleopMessages msg = this.queue.peek();

        if(msg != null && Constants.RobotComponent.B_GRABBER.equals(msg.getRobotComponent())){
            msg = this.queue.take();
            this.handleServo(msg);

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
        }
    }

	/**
	 *
	 */
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
