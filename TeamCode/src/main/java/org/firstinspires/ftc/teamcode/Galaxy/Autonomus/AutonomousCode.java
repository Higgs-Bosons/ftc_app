package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

import org.firstinspires.ftc.teamcode.Galaxy.Robot.MecanumWheelRobot;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class AutonomousCode extends AutonomousMethods {
    MecanumWheelRobot Bubbles;
    private Map<String, float[]> programToRun;
    private ArrayList<String> tagsNIndex;
    int numOfActions;

    public AutonomousCode(MecanumWheelRobot Robot){
        this.Bubbles = Robot;
        programToRun = new LinkedHashMap<>();
        tagsNIndex = new ArrayList<>();
        numOfActions = 0;
    }

    public float[] getAction(int index){
        return programToRun.get(tagsNIndex.get(index));
    }

    public void addAction(String actionName, float valueOne){
        float[] array = {1f, valueOne};
        tagsNIndex.add(actionName);
        programToRun.put(actionName, array);
        numOfActions++;
    }
    public void addAction(String actionName, float valueOne, float valueTwo){
        float[] array = {2f, valueOne, valueTwo};
        tagsNIndex.add(actionName);
        programToRun.put(actionName, array);
        numOfActions++;
    }
    public void addAction(String actionName, float valueOne, float valueTwo, float valueThree){
        float[] array = {3f, valueOne, valueTwo, valueThree};
        tagsNIndex.add(actionName);
        programToRun.put(actionName, array);
        numOfActions++;
    }
    public void addAction(String actionName, float valueOne, float valueTwo, float valueThree, float valueFour){
        float[] array = {4f, valueOne, valueTwo, valueThree, valueFour};
        tagsNIndex.add(actionName);
        programToRun.put(actionName, array);
        numOfActions++;
    }
    public void addAction(String actionName, float valueOne, float valueTwo, float valueThree, float valueFour, float valueFive){
        float[] array = {5f, valueOne, valueTwo, valueThree, valueFour, valueFive};
        tagsNIndex.add(actionName);
        programToRun.put(actionName, array);
        numOfActions++;
    }

    public void runProgram() {
        super.runProgram(this);
    }
}
