package org.firstinspires.ftc.teamcode.officialcode.teleop;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

import java.util.HashMap;

/**
 * Class for defining behaviour of teleop messages
 */
public class TeleopMessages {
    //define variable for component action, and metadata
    private Constants.RobotComponent robotComponent;
    private Constants.RobotComponentAction robotComponentAction;
    private HashMap<String, Object> metadata;

    /**
     * initialize variable previously defined
     * @param robotComponent
     * @param robotComponentAction
     * @param metadata
     */
    public TeleopMessages(Constants.RobotComponent robotComponent,
                          Constants.RobotComponentAction robotComponentAction,
                          HashMap<String, Object> metadata){
        this.robotComponent = robotComponent;
        this.robotComponentAction = robotComponentAction;
        this.metadata = metadata;
    }//constructor

    /**
     * getter method for robot component
     * @return robotComponent
     */
    public Constants.RobotComponent getRobotComponent(){
        return robotComponent;
    }//getRobotComponent

    /**
     * getter method for component action
     * @return robotComponentAction
     */
    public Constants.RobotComponentAction getRobotComponentAction(){
        return robotComponentAction;
    }//getRobotComponentAction

    /**
     * getter method for metadata
     * @return metadata
     */
    public HashMap<String, Object> getMetadata(){
        return metadata;
    }//getMetadata
}//class
