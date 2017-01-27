package org.firstinspires.ftc.teamcode.officialcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * Class for controlling gamepad
 */
public class MyGamepadController implements Runnable {
    //define opmode, message queue, and states
    private OpMode opMode;
    private BlockingQueue<TeleopMessages> queue;

    private Constants.LauncherState currentLauncherState = Constants.LauncherState.STOPPED;
    private Constants.LauncherState lastLauncherState = Constants.LauncherState.STOPPED;
    private Constants.LiftState currentLiftState = Constants.LiftState.STOPPED;
    private Constants.LiftState lastLiftState = Constants.LiftState.STOPPED;
    private Constants.PusherState currentPusherState = Constants.PusherState.STOPPED;
    private Constants.PusherState lastPusherState = Constants.PusherState.STOPPED;
    private Constants.BallGrabberState currentBGrabState = Constants.BallGrabberState.CLOSED;
    private Constants.BallGrabberState lastBGrabState = Constants.BallGrabberState.OPEN;
    private Constants.BallLoaderState currentLoaderState = Constants.BallLoaderState.DOWN;
    private Constants.BallLoaderState lastLoaderState = Constants.BallLoaderState.UP;
    private Constants.CapGrabberState currentCGrabState = Constants.CapGrabberState.CLOSED;
    private Constants.CapGrabberState lastCGrabState = Constants.CapGrabberState.READY;

    /**
     * initialize queue and opmode
     * @param opMode
     */
    public MyGamepadController(OpMode opMode){
        this.queue = MyMessageQueue.getInstance();
        this.opMode = opMode;
    }//constructor

    /**
     * handle all gamepad actions
     */
    private void handleGamepad(){
        this.handleDrive();
        this.handleLauncher();
        this.handleLift();
        this.handlePusher();
        this.handleBGrabber();
        this.handleLoader();
        this.handleCGrabber();
    }//handleGamepad

    /**
     * only move a motor if it is above provided threshold
     * @param power
     * @return
     */
    private boolean exceedsThreshold(Float power){
        return (Math.abs(power) > 0.2);
    }//exceedsThreshold

    /**
     * handle the drivetrain controls
     */
    private void handleDrive(){
        //hashmap for drivetrain power
        HashMap<String, Object> drivetrainPower = new HashMap<String, Object>();

        //based on stick input, move robot
        float lp = opMode.gamepad1.left_stick_y * -0.6f;
        float rp = opMode.gamepad1.right_stick_y * 0.6f;

        //create Float objects for gamepad input values
        Float leftPower = (-1) * (exceedsThreshold(lp) ? new Float(lp) : 0.0f);
        Float rightPower = exceedsThreshold(rp) ? new Float(rp) : 0.0f;

        //put the powers and their names into the hashmap
        drivetrainPower.put(Constants.DrivetrainPower.LEFT.name(), leftPower);
        drivetrainPower.put(Constants.DrivetrainPower.RIGHT.name(), rightPower);

        //post teleop message and offer message
        TeleopMessages message = new TeleopMessages(Constants.RobotComponent.DRIVE_TRAIN, null, drivetrainPower);
        this.queue.offer(message);
    }//handleDrive

    /**
     * handle lift controls
     */
    private void handleLift(){
        //create hashmap for lift direction
        HashMap<String, Object> liftDirection = new HashMap<String, Object>();

        //rise or drop based on controller input
        boolean rise = opMode.gamepad2.dpad_up;
        boolean drop = opMode.gamepad2.dpad_down;

        //define message for lift
        TeleopMessages message;

        //if one of the two buttons is pressed commence specified action
        if (rise && !drop){
            currentLiftState = Constants.LiftState.ASCENDING;
        }else if (drop && !rise){
            currentLiftState = Constants.LiftState.DESCENDING;
        }else{
            currentLiftState = Constants.LiftState.STOPPED;
        }//if-elseif-else

        //if the state hasn't changed from the previous one
        if (currentLiftState != lastLiftState){
            //based on state action, do appropriate action
            switch (currentLiftState){
                case ASCENDING:
                    liftDirection.put(Constants.LiftState.ASCENDING.name(), true);

                    message = new TeleopMessages(Constants.RobotComponent.LIFT,
                            Constants.RobotComponentAction.START, liftDirection);

//                    System.out.println("Ascending at Controller");

                    break;
                case DESCENDING:
                    liftDirection.put(Constants.LiftState.DESCENDING.name(), true);

                    message = new TeleopMessages(Constants.RobotComponent.LIFT,
                            Constants.RobotComponentAction.START, liftDirection);

//                    System.out.println("Descending at Controller");

                    break;
                case STOPPED:
                    message = new TeleopMessages(Constants.RobotComponent.LIFT,
                            Constants.RobotComponentAction.STOP, null);

                    break;
                default:
                    System.out.println("********Illegal State: " + currentLiftState);

                    message = new TeleopMessages(Constants.RobotComponent.LIFT,
                            Constants.RobotComponentAction.STOP, null);
            }//switch
            //offer message and update current state as previous state
            this.queue.offer(message);
            lastLiftState = currentLiftState;
        }//if
    }//handleLift

    /**
     * handling launcher controls
     */
    private void handleLauncher(){
        //Hashmap for launcher activity
        HashMap<String, Object> launcherActivity = new HashMap<String, Object>();

        //gamepad for enabling launcher
        boolean enable = (opMode.gamepad2.right_trigger > 0.3 ? true : false);

        //defining message queue
        TeleopMessages message;

        //if statement for enabling launcher
        if(enable){
            currentLauncherState = Constants.LauncherState.FIRING;
        }else{
            currentLauncherState = Constants.LauncherState.STOPPED;
        }//if-else

        //if current state is different from last state, put current state action hashmap into queue
        if(currentLauncherState != lastLauncherState){
            switch (currentLauncherState){
                case FIRING:
                    launcherActivity.put(Constants.LauncherState.FIRING.name(), true);

                    message = new TeleopMessages(Constants.RobotComponent.LAUNCHER,
                            Constants.RobotComponentAction.START, launcherActivity);

                    break;
                case STOPPED:
                    message = new TeleopMessages(Constants.RobotComponent.LAUNCHER,
                            Constants.RobotComponentAction.STOP, null);

                    break;
                default:
                    System.out.println("********Illegal State: " + currentLauncherState);

                    message = new TeleopMessages(Constants.RobotComponent.LAUNCHER,
                            Constants.RobotComponentAction.STOP, null);
            }//switch
            //offer message to queue and change last launcher state to current launcher state
            this.queue.offer(message);
            lastLauncherState = currentLauncherState;
        }//if
    }//handleLauncher

    /**
     * Handling pusher gamepad controls
     */
    private void handlePusher(){
        //Hashmap for pusher movement
        HashMap<String, Object> pusherMovement = new HashMap<String, Object>();

        //move pusher left or right based on gamepad button bumper pushed
        boolean moveLeft = opMode.gamepad1.left_bumper;
        boolean moveRight = opMode.gamepad1.right_bumper;

        //define message queue
        TeleopMessages message;

        //if one of the buttons is pressed, do its appropriate action
        if(moveLeft && !moveRight){
            currentPusherState = Constants.PusherState.L_MOVING;
        }else if(moveRight && !moveLeft){
            currentPusherState = Constants.PusherState.R_MOVING;
        }else{
            currentPusherState = Constants.PusherState.STOPPED;
        }//if-elseif-else

        //if currentState is not last state, put current state hashmap into que
        if(currentPusherState != lastPusherState){
            switch (currentPusherState){
                case L_MOVING:
                    pusherMovement.put(Constants.PusherState.L_MOVING.name(), true);
                    message = new TeleopMessages(Constants.RobotComponent.PUSHER, Constants.RobotComponentAction.START, pusherMovement);
                    break;
                case R_MOVING:
                    pusherMovement.put(Constants.PusherState.R_MOVING.name(), true);
                    message = new TeleopMessages(Constants.RobotComponent.PUSHER, Constants.RobotComponentAction.START, pusherMovement);
                    break;
                case STOPPED:
                    message = new TeleopMessages(Constants.RobotComponent.PUSHER, Constants.RobotComponentAction.STOP, null);
                    break;
                default:
                    System.out.println("*************Illegal State: " + currentPusherState);
                    message = new TeleopMessages(Constants.RobotComponent.PUSHER, Constants.RobotComponentAction.STOP, null);
            }//switch
            //offer message to queue, and change last pusher state to current pusher state
            this.queue.offer(message);
            lastPusherState = currentPusherState;
        }//if
    }//handlePusher

    /**
     * handle little ball grabber
     */
    private void handleBGrabber(){
        //Hashmap for grabber movement
        HashMap<String, Object> grabberMovement = new HashMap<String, Object>();

        //button booleans for using grabber
        boolean grabbing = opMode.gamepad2.x;
        boolean opening = opMode.gamepad2.b;

        //teleop message queue
        TeleopMessages message;

        //based on button push, set appropriate state
        if(grabbing){
            currentBGrabState = Constants.BallGrabberState.CLOSED;
        }else if(opening){
            currentBGrabState = Constants.BallGrabberState.OPEN;
        }//if-elseif

        //if current state is not last state, then do appropriate action based on current state
        if(currentBGrabState != lastBGrabState){
            switch (currentBGrabState){
                case OPEN:
                    grabberMovement.put(Constants.BallGrabberState.OPEN.name(), true);
                    message = new TeleopMessages(Constants.RobotComponent.B_GRABBER, Constants.RobotComponentAction.START, grabberMovement);
                    //System.out.println("Opening Gates");
                    break;
                case CLOSED:
                    message = new TeleopMessages(Constants.RobotComponent.B_GRABBER, Constants.RobotComponentAction.STOP, null);
                    //System.out.println("Closing Grabber");
                    break;
                default:
                    System.out.println("*********Illegal State: " + currentBGrabState);
                    message = new TeleopMessages(Constants.RobotComponent.B_GRABBER, Constants.RobotComponentAction.STOP, null);
            }//switch
            //offer message to queue, and change last state to current state
            this.queue.offer(message);
            lastBGrabState = currentBGrabState;
        }//if
    }//handleBGrabber

    /**
     * handle loader controls
     */
    private void handleLoader(){
        //hashmap for loader movement
        HashMap<String, Object> loaderMovement = new HashMap<String, Object>();

        //booleans for loading buttons
        boolean accepting = opMode.gamepad2.a;
        boolean loading = opMode.gamepad2.y;

        //declare message queue
        TeleopMessages message;

        //based on booleans, set appropriate state
        if(loading){
            currentLoaderState = Constants.BallLoaderState.UP;
        }else if(accepting){
            currentLoaderState = Constants.BallLoaderState.DOWN;
        }//if-elseif

        //if current state is not last state, then do appropriate action based on current state
        if(currentLoaderState != lastLoaderState){
            switch (currentLoaderState){
                case UP:
                    loaderMovement.put(Constants.BallLoaderState.UP.name(), true);
                    message = new TeleopMessages(Constants.RobotComponent.LOADER, Constants.RobotComponentAction.START, loaderMovement);
                    break;
                case DOWN:
                    message = new TeleopMessages(Constants.RobotComponent.LOADER, Constants.RobotComponentAction.STOP, null);
                    break;
                default:
                    System.out.println("****************Illegal State: " + currentLoaderState);
                    message = new TeleopMessages(Constants.RobotComponent.LOADER, Constants.RobotComponentAction.STOP, null);
            }//switch
            //offer message to queue, and change last state to current state
            this.queue.offer(message);
            lastLoaderState = currentLoaderState;
        }//if
    }//handleLoader

    /**
     * handle cap ball grabber
     */
    private void handleCGrabber(){
        //hashmap for cap grabber movement
        HashMap<String, Object> cGrabMovement = new HashMap<String, Object>();

        //booleans grabber movements
        boolean ready = opMode.gamepad2.left_bumper;
        boolean grab = opMode.gamepad2.right_bumper;
        boolean close = opMode.gamepad2.dpad_left;

        //declare message queue
        TeleopMessages message;

        //based on booleans, set appropriate state
        if(ready){
            currentCGrabState = Constants.CapGrabberState.READY;
        }else if(grab){
            currentCGrabState = Constants.CapGrabberState.HOLDING;
        }else if(close){
            currentCGrabState = Constants.CapGrabberState.CLOSED;
        }//if-elseif-elseif

        //if current state is not last state, then do appropriate action based on current state
        if(currentCGrabState != lastCGrabState){
            switch (currentCGrabState){
                case HOLDING:
                    cGrabMovement.put(Constants.CapGrabberState.HOLDING.name(), true);
                    message = new TeleopMessages(Constants.RobotComponent.C_GRABBER, Constants.RobotComponentAction.START, cGrabMovement);
                    break;
                case READY:
                    cGrabMovement.put(Constants.CapGrabberState.READY.name(), true);
                    message = new TeleopMessages(Constants.RobotComponent.C_GRABBER, Constants.RobotComponentAction.START, cGrabMovement);
                    break;
                case CLOSED:
                    message = new TeleopMessages(Constants.RobotComponent.C_GRABBER, Constants.RobotComponentAction.STOP, null);
                    break;
                default:
                    System.out.println("****************Illegal State: " + currentCGrabState);
                    message = new TeleopMessages(Constants.RobotComponent.C_GRABBER, Constants.RobotComponentAction.STOP, null);
            }//switch
            //offer message to queue, and change last state to current state
            this.queue.offer(message);
            lastCGrabState = currentCGrabState;
        }//if
    }//handleCGrabber

    /**
     * run() from Runnable interface
     */
    @Override
    public void run() {
        //keep handling gamepad until interrupted
        try{
            while(true) {
                this.handleGamepad();
                Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
            }//while
        }catch (InterruptedException e){
            e.printStackTrace();
        }//try-catch
    }//run
}//class
