package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.*;

class AutonomousMethods {
    private static MecanumWheelRobot Bubbles;
    static void runProgram(AutonomousCode code){
        String actionName;
        Bubbles = code.Bubbles;
        for(int counter = 0; counter < code.numOfActions; counter++){
            actionName = code.getActionName(counter);
            switch(actionName){
                case DRIVE_ROBOT:
                    float[] array = code.getAction(counter);
                    moveRobot(array[1],array[2],array[3],array[4],array[5]);
            }
        }

    }
    private static void moveRobot(float direction, float degrees, float maxPower, float minPower, float precision){
        Bubbles.moveDegrees(direction, (int) degrees, maxPower, minPower, precision);
    }

}
