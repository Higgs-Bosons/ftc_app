package org.firstinspires.ftc.teamcode.officialcode.lift;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.teleop.MyMessageQueue;
import org.firstinspires.ftc.teamcode.officialcode.teleop.TeleopMessages;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class Lift implements ILift {
    private LiftMotor lift;
    private Constants.LiftState currentState = Constants.LiftState.STOPPED;
    //private Constants.LiftState lastState = Constants.LiftState.STOPPED;
    private BlockingQueue<TeleopMessages> queue;

    public Lift(LiftMotor lift){
        this.lift = lift;
        this.queue = MyMessageQueue.getInstance();
    }

    private void handleLift(TeleopMessages message){
        HashMap<String, Object> metadata = message.getMetadata();

        switch (message.getRobotComponentAction()){
            case STOP:
                this.setState(Constants.LiftState.STOPPED);
                break;
            case START:
                if(metadata.containsKey(Constants.LiftState.ASCENDING.name())){
                    this.setState(Constants.LiftState.ASCENDING);
//                    System.out.println("Ascending at lift");
                }else if(metadata.containsKey(Constants.LiftState.DESCENDING.name())){
                    this.setState(Constants.LiftState.DESCENDING);
//                    System.out.println("Descending at lift");
                }else{
                    this.setState(Constants.LiftState.STOPPED);
                }
                break;
            default:
                throw new IllegalStateException("Cannot Handle: " + message.getRobotComponentAction());
        }
    }

    @Override
    public void ascend(){
        lift.getLift().setPower(1.0f);
    }

    @Override
    public void descend(){
        lift.getLift().setPower(-1.0f);
    }

    @Override
    public void stop(){
        lift.getLift().setPower(0.0f);
    }

    @Override
    public Constants.LiftState getState(){
        return this.currentState;
    }

    @Override
    public void setState(Constants.LiftState liftState) {
        this.currentState = liftState;
    }

    @Override
    public void handleMessage() throws InterruptedException {
        TeleopMessages msg = this.queue.peek();

        if (msg != null && Constants.RobotComponent.LIFT.equals(msg.getRobotComponent())){
            msg = this.queue.take();

            this.handleLift(msg);

            //if (!this.getState().equals(lastState)){
                switch (this.getState()){
                    case STOPPED:
                        this.stop();
                        break;
                    case ASCENDING:
                        this.ascend();
//                        System.out.println("Ascending");
                        break;
                    case DESCENDING:
                        this.descend();
//                        System.out.println("Descending");
                        break;
                    default:
                        throw new IllegalStateException("Unknown State: " + this.getState());
                }
            //}

           // lastState = currentState;
        }
    }
}
