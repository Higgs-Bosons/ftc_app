package org.firstinspires.ftc.teamcode.officialcode.pusher;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.teleop.MyMessageQueue;
import org.firstinspires.ftc.teamcode.officialcode.teleop.TeleopMessages;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;


/**
 * class for controlling pusher
 */
public class Pusher implements IPusher {
    //declare pusher
    private PusherMotor pusher;
    //declare and initialize current state
    private Constants.PusherState state = Constants.PusherState.STOPPED;
    //declare queue
    BlockingQueue<TeleopMessages> queue;

    public Pusher(PusherMotor pusher){
        this.pusher = pusher;
        this.queue = MyMessageQueue.getInstance();
    }//constructor

    /**
     * handle the messages meant for the pusher
     * @param message
     */
    private void handlePusherMessage(TeleopMessages message){
        //get the message metadata
        HashMap<String, Object> metadata = message.getMetadata();

        //based on component action, set appropriate state
        switch (message.getRobotComponentAction()){
            case STOP:
                this.setState(Constants.PusherState.STOPPED);
                break;
            case START:
                if (metadata.containsKey(Constants.PusherState.L_MOVING.name())){
                    this.setState(Constants.PusherState.L_MOVING);
                }else if (metadata.containsKey(Constants.PusherState.R_MOVING.name())){
                    this.setState(Constants.PusherState.R_MOVING);
                }else{
                    this.setState(Constants.PusherState.STOPPED);
                }//if-elseif-else
                break;
            default:
                throw new IllegalStateException("Cannot Handle: " + message.getRobotComponentAction());
        }//switch
    }//handlePusherMessage

    /**
     * move pusher at power passed to it
     * @param power
     */
    @Override
    public void pusherMovement(double power) {
        this.pusher.getPusher().setPower(power);
    }//pusherMovement

    /**
     * implemented method for returning state
     * @return
     */
    @Override
    public Constants.PusherState getState() {
        return state;
    }//getState

    /**
     * implemented method for setting state
     * @param state
     */
    @Override
    public void setState(Constants.PusherState state) {
        this.state = state;
    }//setState

    /**
     * implemented method for getting appropriate message for component
     * @throws InterruptedException
     */
    @Override
    public void handleMessage() throws InterruptedException {
        //look at message queue
        TeleopMessages msg = this.queue.peek();

        //if the message is for the robot component, take it and set the appropriate state for it
        if (msg != null && Constants.RobotComponent.PUSHER.equals(msg.getRobotComponent())){
            msg = this.queue.take();

            this.handlePusherMessage(msg);

            switch (this.getState()){
                case STOPPED:
                    this.pusher.getPusher().setPower(0.0f);
                    break;
                case L_MOVING:
                    this.pusherMovement(1.0f);
                    break;
                case R_MOVING:
                    this.pusherMovement(-1.0f);
                    break;
                default:
                    throw new IllegalStateException("Unknown State: " + this.getState());
            }//switch
        }//if
    }//handleMessage
}//class
