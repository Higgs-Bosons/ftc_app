package org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.officialcode.IntStringDefInterfaces;

public class Servos {
    public static final String FishTailLifter = "Fish Tail Lifter";
    public static final String FishTailSwinger = "Fish Tail Swinger";
    public static final String Grabby = "Grabby";
    private Servo FishTailLifterServo;
    private Servo FishTailSwingerServo;
    private Servo GrabbyServo;
    Servos(Servo FishTailLifter,  Servo FishTailSwinger, Servo Grabby){
        this.FishTailSwingerServo = FishTailSwinger;
        this.FishTailLifterServo = FishTailLifter;
        this.GrabbyServo = Grabby;
    }
    public Servo getServo(String Name){
        switch (Name){
            case FishTailLifter:
                return FishTailLifterServo;
            case FishTailSwinger:
                return FishTailSwingerServo;
            case Grabby:
                return GrabbyServo;
        }
        return this.FishTailLifterServo;
    }
}
