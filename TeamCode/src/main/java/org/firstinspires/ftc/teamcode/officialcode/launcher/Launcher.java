package org.firstinspires.ftc.teamcode.officialcode.launcher;


import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;
import org.firstinspires.ftc.teamcode.officialcode.teleop.MyMessageQueue;
import org.firstinspires.ftc.teamcode.officialcode.teleop.TeleopMessages;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class Launcher implements ILauncher {
    private LauncherMotor launcher;
    private Constants.LauncherState currentState = Constants.LauncherState.STOPPED;
    //private Constants.LauncherState lastState = Constants.LauncherState.STOPPED;
    private BlockingQueue<TeleopMessages> queue;

    public Launcher(LauncherMotor launcher) {
        this.launcher = launcher;
        this.queue = MyMessageQueue.getInstance();
    }

    private void handleLauncher(TeleopMessages message){
        HashMap<String, Object> metadata = message.getMetadata();

        switch (message.getRobotComponentAction()){
            case STOP:
                this.setState(Constants.LauncherState.STOPPED);
                break;
            case START:
                if(metadata.containsKey(Constants.LauncherState.FIRING.name())){
                    this.setState(Constants.LauncherState.FIRING);
                }else{
                    this.setState(Constants.LauncherState.STOPPED);
                }
                break;
            default:
                throw new IllegalStateException("Cannot Handle: " + message.getRobotComponentAction());
        }
    }

    @Override
    public void fire(){
        launcher.getLauncher().setPower(-1.0f);
    }

    @Override
    public void cease(){
        launcher.getLauncher().setPower(0.0f);
    }

    @Override
    public Constants.LauncherState getState(){
        return this.currentState;
    }

    @Override
    public void handleMessage() throws InterruptedException {
        TeleopMessages msg = this.queue.peek();

        if (msg != null && Constants.RobotComponent.LAUNCHER.equals(msg.getRobotComponent())){
            msg = this.queue.take();

            this.handleLauncher(msg);

            //if (!this.getState().equals(lastState)){
                switch (this.getState()){
                    case STOPPED:
                        this.cease();
                        break;
                    case FIRING:
                        this.fire();
                        break;
                    default:
                        throw new IllegalStateException("Unknown State: " + this.getState());
                }
            //}

            //lastState = currentState;
        }
    }

    @Override
    public void setState(Constants.LauncherState launcherState) {
        this.currentState = launcherState;
    }
}
