package org.firstinspires.ftc.teamcode.Galaxy.MecanumWheelRobot;

import com.qualcomm.robotcore.hardware.*;

import java.util.Hashtable;
import java.util.Map;

public class Servos extends  Motors{
    private Map<String, Servo> servos;
    private HardwareMap hardwareMap;
    private Map<String, double[]> servoBounds;

    public Servos(HardwareMap hardwareMap){
        super(hardwareMap);
        this.hardwareMap = hardwareMap;
        this.servos = new Hashtable<>();
        this.servoBounds = new Hashtable<>();
    }
    public void addServo(String name) throws RuntimeException{
        if (servos.containsKey(name)) {
            throw new customErrors.DuplicateNameException();
        }
        servos.put(name, hardwareMap.servo.get(name));
        double[] array = {0,1};
        servoBounds.put(name, array);
    }
    public void addServo(String name, double minRange, double maxRange) throws RuntimeException{
        if (servos.containsKey(name)) {
            throw new customErrors.DuplicateNameException();
        }
        servos.put(name, hardwareMap.servo.get(name));
        double[] array = {minRange,maxRange};
        servoBounds.put(name, array);
    }
    public void moveServo(String name, double toMoveTo) {
        if (toMoveTo < servoBounds.get(name)[0])
            toMoveTo = servoBounds.get(name)[0];

        if (toMoveTo > servoBounds.get(name)[1])
            toMoveTo = servoBounds.get(name)[1];

        this.servos.get(name).setPosition(toMoveTo);
    }
}
