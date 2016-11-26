package org.firstinspires.ftc.teamcode.officialcode.pusher;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.teleop.MyMessageQueue;
import org.firstinspires.ftc.teamcode.officialcode.teleop.TeleopMessages;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;


/**
 * Created by Higgs Bosons on 11/17/2016.
 */
public class Pusher implements IPusher {
    private PusherMotor pusher;
    private Constants.PusherState currentState = Constants.PusherState.STOPPED;
    private Constants.PusherState lastState = Constants.PusherState.STOPPED;
    BlockingQueue<TeleopMessages> queue;

    public Pusher(PusherMotor pusher){
        this.pusher = pusher;
        this.queue = MyMessageQueue.getInstance();
    }

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
    public void pressButton(double power) throws InterruptedException {
        this.pusher.getPusher().setPower(power);
        Thread.sleep(1000);
        this.pusher.getPusher().setPower(0.0d);
    }

    @Override
    public Constants.PusherState getState() {
        return currentState;
    }

    @Override
    public void setState(Constants.PusherState state) {
        this.currentState = state;
    }

    @Override
    public void handleMessage() throws InterruptedException {
        TeleopMessages msg = this.queue.peek();

        if (msg != null && Constants.RobotComponent.PUSHER.equals(msg.getRobotComponent())){
            msg = this.queue.take();

            this.handlePusherMessage(msg);

            if (!this.getState().equals(lastState)){
                switch (this.getState()){
                    case STOPPED:
                        this.pusher.getPusher().setPower(0.0f);
                        break;
                    case L_MOVING:
                        this.pressButton(1.0f);
                        break;
                    case R_MOVING:
                        this.pressButton(-1.0f);
                        break;
                    default:
                        throw new IllegalStateException("Unknown State: " + this.getState());
                }
            }
            lastState = currentState;
        }
    }
}
