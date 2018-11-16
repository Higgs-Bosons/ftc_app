package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.*;

import java.util.Hashtable;
import java.util.Map;

public class Servos {
    private Map<String, Servo> servos;
    private HardwareMap hardwareMap;

    public Servos(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        this.servos = new Hashtable<>();
    }

    public void addServo(String name) throws RuntimeException{
        if (servos.containsKey(name)) {
            throw new customErrors.DuplicateNameException();
        }
        servos.put(name, hardwareMap.servo.get(name));
    }

    public Servo getServer(String name) {
        return this.servos.get(name);
    }
}
