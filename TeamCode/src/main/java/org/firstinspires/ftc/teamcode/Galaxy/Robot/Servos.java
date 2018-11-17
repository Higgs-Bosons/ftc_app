package org.firstinspires.ftc.teamcode.Galaxy.Robot;

import com.qualcomm.robotcore.hardware.*;

import java.util.Hashtable;
import java.util.Map;

public class Servos extends  Motors{
    private Map<String, Servo> servos;
    private HardwareMap hardwareMap;

    public Servos(HardwareMap hardwareMap){
        super(hardwareMap);
        this.hardwareMap = hardwareMap;
        this.servos = new Hashtable<>();
    }
    public void addServo(String name) throws RuntimeException{
        if (servos.containsKey(name)) {
            throw new customErrors.DuplicateNameException();
        }
        servos.put(name, hardwareMap.servo.get(name));
    }
    public void moveServo(String name, double toMoveTo){
        this.servos.get(name).setPosition(toMoveTo);
    }
}
