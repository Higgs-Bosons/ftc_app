package org.firstinspires.ftc.teamcode.officialcode.teleop;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

import java.util.HashMap;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */

public class TeleopMessages {
    private Constants.RobotComponent robotComponent;
    private Constants.RobotComponentAction robotComponentAction;
    private HashMap<String, Object> metadata;

    public TeleopMessages(Constants.RobotComponent robotComponent,
                          Constants.RobotComponentAction robotComponentAction,
                          HashMap<String, Object> metadata){
        this.robotComponent = robotComponent;
        this.robotComponentAction = robotComponentAction;
        this.metadata = metadata;
    }

    public Constants.RobotComponent getRobotComponent(){
        return robotComponent;
    }

    public Constants.RobotComponentAction getRobotComponentAction(){
        return robotComponentAction;
    }

    public HashMap<String, Object> getMetadata(){
        return metadata;
    }
}
