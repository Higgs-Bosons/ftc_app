package org.firstinspires.ftc.teamcode.officialcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class MyGamepadController implements Runnable {
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


    public MyGamepadController(OpMode opMode){
        this.queue = MyMessageQueue.getInstance();
        this.opMode = opMode;
    }

    private void handleGamepad(){
        this.handleDrive();
        this.handleLauncher();
        this.handleLift();
        this.handlePusher();
        this.handleBGrabber();
        this.handleLoader();
        this.handleCGrabber();
    }

    private boolean exceedsThreshold(Float power){
        return (Math.abs(power) > 0.2);
    }

    private void handleDrive(){
        HashMap<String, Object> drivetrainPower = new HashMap<String, Object>();

        float lp = opMode.gamepad1.left_stick_y * -0.6f;
        float rp = opMode.gamepad1.right_stick_y * 0.6f;

        Float leftPower = (-1) * (exceedsThreshold(lp) ? new Float(lp) : 0.0f);
        Float rightPower = exceedsThreshold(rp) ? new Float(rp) : 0.0f;

        drivetrainPower.put(Constants.DrivetrainPower.LEFT.name(), leftPower);
        drivetrainPower.put(Constants.DrivetrainPower.RIGHT.name(), rightPower);

        TeleopMessages message = new TeleopMessages(Constants.RobotComponent.DRIVE_TRAIN, null, drivetrainPower);
        this.queue.offer(message);
    }

    private void handleLift(){
        HashMap<String, Object> liftDirection = new HashMap<String, Object>();

        boolean rise = opMode.gamepad2.dpad_up;
        boolean drop = opMode.gamepad2.dpad_down;

        TeleopMessages message;

        if (rise && !drop){
            currentLiftState = Constants.LiftState.ASCENDING;
        }else if (drop && !rise){
            currentLiftState = Constants.LiftState.DESCENDING;
        }else{
            currentLiftState = Constants.LiftState.STOPPED;
        }

        if (currentLiftState != lastLiftState){
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
            }
            this.queue.offer(message);
            lastLiftState = currentLiftState;
        }
    }

    private void handleLauncher(){
        HashMap<String, Object> launcherActivity = new HashMap<String, Object>();

        boolean enable = (opMode.gamepad2.right_trigger > 0.3 ? true : false);

        TeleopMessages message;

        if(enable){
            currentLauncherState = Constants.LauncherState.FIRING;
        }else{
            currentLauncherState = Constants.LauncherState.STOPPED;
        }

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
            }
            this.queue.offer(message);
            lastLauncherState = currentLauncherState;
        }
    }

    private void handlePusher(){
        HashMap<String, Object> pusherMovement = new HashMap<String, Object>();

        boolean moveLeft = opMode.gamepad1.left_bumper;
        boolean moveRight = opMode.gamepad1.right_bumper;

        TeleopMessages message;

        if(moveLeft && !moveRight){
            currentPusherState = Constants.PusherState.L_MOVING;
        }else if(moveRight && !moveLeft){
            currentPusherState = Constants.PusherState.R_MOVING;
        }else{
            currentPusherState = Constants.PusherState.STOPPED;
        }

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
            }

            this.queue.offer(message);
            lastPusherState = currentPusherState;
        }
    }

    private void handleBGrabber(){
        HashMap<String, Object> grabberMovement = new HashMap<String, Object>();

        boolean grabbing = opMode.gamepad2.x;
        boolean opening = opMode.gamepad2.b;

        TeleopMessages message;

        if(grabbing){
            currentBGrabState = Constants.BallGrabberState.CLOSED;
        }else if(opening){
            currentBGrabState = Constants.BallGrabberState.OPEN;
        }

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
            }
            this.queue.offer(message);
            lastBGrabState = currentBGrabState;
        }
    }

    private void handleLoader(){
        HashMap<String, Object> loaderMovement = new HashMap<String, Object>();

        boolean accepting = opMode.gamepad2.a;
        boolean loading = opMode.gamepad2.y;

        TeleopMessages message;

        if(loading){
            currentLoaderState = Constants.BallLoaderState.UP;
        }else if(accepting){
            currentLoaderState = Constants.BallLoaderState.DOWN;
        }

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
            }
            this.queue.offer(message);
            lastLoaderState = currentLoaderState;
        }
    }

    private void handleCGrabber(){
        HashMap<String, Object> cGrabMovement = new HashMap<String, Object>();

        boolean ready = opMode.gamepad2.left_bumper;
        boolean grab = opMode.gamepad2.right_bumper;
        boolean close = opMode.gamepad2.dpad_left;

        TeleopMessages message;

        if(ready){
            currentCGrabState = Constants.CapGrabberState.READY;
        }else if(grab){
            currentCGrabState = Constants.CapGrabberState.HOLDING;
        }else if(close){
            currentCGrabState = Constants.CapGrabberState.CLOSED;
        }

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
            }
            this.queue.offer(message);
            lastCGrabState = currentCGrabState;
        }
    }

    @Override
    public void run() {
        while(true){
            try{
                this.handleGamepad();
                Thread.sleep(Constants.THREAD_WAIT_TIME_MS);
            }catch (InterruptedException e){
                e.printStackTrace();
                break;
            }
        }
    }
}
