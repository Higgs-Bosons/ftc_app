package org.firstinspires.ftc.teamcode.officialcode.launcher;


import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.teleop.MyMessageQueue;
import org.firstinspires.ftc.teamcode.officialcode.teleop.TeleopMessages;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

/**
 * Class for launcher functionality
 */
public class Launcher implements ILauncher {
    //declare variables for launcher motor, launcher state, and gamepad message queue
    private LauncherMotor launcher;
    private Constants.LauncherState currentState = Constants.LauncherState.STOPPED;
    private BlockingQueue<TeleopMessages> queue;

    /**
     * initialize launcher and message queue
     * @param launcher
     */
    public Launcher(LauncherMotor launcher) {
        this.launcher = launcher;
        this.queue = MyMessageQueue.getInstance();
    }//constructor

    /**
     * handle launcher instructions from teleop messages
     * @param message
     */
    private void handleLauncher(TeleopMessages message) {
        //declare and initialize variable to get metadate from teleop message argument
        HashMap<String, Object> metadata = message.getMetadata();

        //based on the teleop message action, set the appropriate state
        switch (message.getRobotComponentAction()){
            case STOP:
                this.setState(Constants.LauncherState.STOPPED);
                break;
            case START:
                //based on different possible paths from start action, do the name of the passed
                // message
                if(metadata.containsKey(Constants.LauncherState.FIRING.name())){
                    this.setState(Constants.LauncherState.FIRING);
                }else{
                    this.setState(Constants.LauncherState.STOPPED);
                }
                break;
            default:
                //default action is to throw illegal state exception
                throw new IllegalStateException("Cannot Handle: " + message.getRobotComponentAction());
        }//switch
    }//handleLauncher

    /**
     * firing method from interface; sets power to full speed
     */
    @Override
    public void fire(){
        launcher.getLauncher().setPower(-0.80f);
    }//fire

    /**
     * stopping method from interface; sets power to full zero
     */
    @Override
    public void cease(){
        launcher.getLauncher().setPower(0.0f);
    }//cease

    /**
     * getter method from interface; gets state
     * @return
     */
    @Override
    public Constants.LauncherState getState(){
        return this.currentState;
    }//getState

    /**
     * gamepad message handler from interface
     * @throws InterruptedException
     */
    @Override
    public void handleMessage() throws InterruptedException {
        //peek at the message queue
        TeleopMessages msg = this.queue.peek();

        //if the message is not null and is meant for the launcher get its state and take the
        // appropriate course of action
        if (msg != null && Constants.RobotComponent.LAUNCHER.equals(msg.getRobotComponent())){
            msg = this.queue.take();

            this.handleLauncher(msg);

            switch (this.getState()){
                case STOPPED:
                    this.cease();
                    break;
                case FIRING:
                    this.fire();
                    break;
                default:
                    throw new IllegalStateException("Unknown State: " + this.getState());
            }//switch
        }//if
    }//handleMessage

    /**
     * set state method from interface
     * @param launcherState
     */
    @Override
    public void setState(Constants.LauncherState launcherState) {
        this.currentState = launcherState;
    }//setState
}//class
