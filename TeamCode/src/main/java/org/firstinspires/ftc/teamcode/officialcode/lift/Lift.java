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
    //declare lift motor, lift state, and messages queue
    private LiftMotor lift;
    private Constants.LiftState currentState = Constants.LiftState.STOPPED;
    private BlockingQueue<TeleopMessages> queue;

    /**
     * initialize lift and messages
     */
    public Lift(LiftMotor lift){
        this.lift = lift;
        this.queue = MyMessageQueue.getInstance();
    }//constructor

    /**
     * handle messages for lift state toggling
     * @param message
     */
    private void handleLift(TeleopMessages message){
        //create a hashmap to get metadata of teleop messages
        HashMap<String, Object> metadata = message.getMetadata();

        //based on message action set appropriate state
        switch (message.getRobotComponentAction()){
            case STOP:
                this.setState(Constants.LiftState.STOPPED);
                break;
            case START:
                //based on key name, either ascend or descend
                if(metadata.containsKey(Constants.LiftState.ASCENDING.name())){
                    this.setState(Constants.LiftState.ASCENDING);
//                    System.out.println("Ascending at lift");
                }else if(metadata.containsKey(Constants.LiftState.DESCENDING.name())){
                    this.setState(Constants.LiftState.DESCENDING);
//                    System.out.println("Descending at lift");
                }else{
                    this.setState(Constants.LiftState.STOPPED);
                }//if-elseif-else
                break;
            default:
                //If all else fails, throw IllegalStateException
                throw new IllegalStateException("Cannot Handle: " + message.getRobotComponentAction());
        }//switch
    }//handleLift

    /**
     * set max power to each lift to ascend
     */
    @Override
    public void ascend(){
        lift.getLeftLift().setPower(1.0f);
        lift.getRightLift().setPower(-1.0f);
    }//ascend

    /**
     * set max power to each lift to descend
     */
    @Override
    public void descend(){
        lift.getLeftLift().setPower(-1.0f);
        lift.getRightLift().setPower(1.0f);
    }//descend

    /**
     * stop lift motors
     */
    @Override
    public void stop(){
        lift.getLeftLift().setPower(0.0f);
        lift.getRightLift().setPower(0.0f);
    }//stop

    /**
     * get current state of robot
     * @return currentstate
     */
    @Override
    public Constants.LiftState getState(){
        return this.currentState;
    }//getState

    /**
     * set current state to pass in state
     * @param liftState
     */
    @Override
    public void setState(Constants.LiftState liftState) {
        this.currentState = liftState;
    }//setState

    /**
     * handle messages on message queue
     * @throws InterruptedException
     */
    @Override
    public void handleMessage() throws InterruptedException {
        //peek at messages and only use them if they are meant for the lift
        TeleopMessages msg = this.queue.peek();

        if (msg != null && Constants.RobotComponent.LIFT.equals(msg.getRobotComponent())){
            msg = this.queue.take();

            this.handleLift(msg);

            //based on state, execute appropriate action
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
            }//switch
        }//if
    }//handleMessage
}//class
