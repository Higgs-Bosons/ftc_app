package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import org.firstinspires.ftc.teamcode.Galaxy.Constants.*;
import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class AutonomousCode extends AutonomousMethods {
    MecanumWheelRobot Bubbles;
    private Map<String, Object[]> programToRun;
    private ArrayList<String> tagsNIndex;
    int numOfActions;
    private Thread program;

    public AutonomousCode(MecanumWheelRobot Robot){
        this.Bubbles = Robot;
        programToRun = new LinkedHashMap<>();
        tagsNIndex = new ArrayList<>();
        numOfActions = 0;
    }

    public Object[] getAction(int index){
        return programToRun.get(tagsNIndex.get(index));
    }
    public String getActionName(int index){
        return tagsNIndex.get(index);
    }

    public void addAction(String actionName, Object valueOne){
        Object[] array = {1f, valueOne};
        tagsNIndex.add(actionName);
        programToRun.put(actionName, array);
        numOfActions++;
    }
    public void addAction(String actionName, Object valueOne, Object valueTwo){
        Object[] array = {2f, valueOne, valueTwo};
        tagsNIndex.add(actionName);
        programToRun.put(actionName, array);
        numOfActions++;
    }
    public void addAction(String actionName, Object valueOne, Object valueTwo, Object valueThree){
        Object[] array = {3f, valueOne, valueTwo, valueThree};
        tagsNIndex.add(actionName);
        programToRun.put(actionName, array);
        numOfActions++;
    }
    public void addAction(String actionName, Object valueOne, Object valueTwo, Object valueThree, Object valueFour){
        Object[] array = {4f, valueOne, valueTwo, valueThree, valueFour};
        tagsNIndex.add(actionName);
        programToRun.put(actionName, array);
        numOfActions++;
    }
    public void addAction(String actionName, Object valueOne, Object valueTwo, Object valueThree, Object valueFour, Object valueFive){
        Object[] array = {5f, valueOne, valueTwo, valueThree, valueFour, valueFive};
        tagsNIndex.add(actionName);
        programToRun.put(actionName, array);
        numOfActions++;
    }

    public void runProgram() {
        program = new Thread(new Runnable() {
            @Override
            public void run() {
                AutonomousMethods.runProgram(AutonomousCode.this);
            }
        });
        program.start();

    }
    public void stopProgram(){
        program.interrupt();
    }

}
