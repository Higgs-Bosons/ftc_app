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

    private void handlePusherMessage(TeleopMessages message){
        HashMap<String, Object> metadata = message.getMetadata();

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
                }
                break;
            default:
                throw new IllegalStateException("Cannot Handle: " + message.getRobotComponentAction());
        }
    }

    @Override
    public void pusherMovement(double power) {
        this.pusher.getPusher().setPower(power);
    }

    @Override
    public Constants.PusherState getState() {
        return state;
    }

    @Override
    public void setState(Constants.PusherState state) {
        this.state = state;
    }

    @Override
    public void handleMessage() throws InterruptedException {
        TeleopMessages msg = this.queue.peek();

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
            }
        }
    }
}
