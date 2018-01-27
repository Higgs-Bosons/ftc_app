package org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.officialcode.Constants;

public class Servos extends Constants{
    private Servo RMLServo;
    private Servo ClampyServo;
    private Servo FishTailLifterServo;
    private Servo FishTailSwingerServo;
    private Servo GrabberOneServo;
    private Servo GrabberTwoServo;
    private Servo LifterServo;
    Servos(Servo FishTailLifter,  Servo FishTailSwinger, Servo GrabberOne, Servo GrabberTwo, Servo Clampy, Servo RML, Servo Lifter){
        this.FishTailSwingerServo = FishTailSwinger;
        this.FishTailLifterServo = FishTailLifter;
        this.GrabberOneServo = GrabberOne;
        this.GrabberTwoServo = GrabberTwo;
        this.LifterServo = Lifter;
        this.ClampyServo = Clampy;
        this.RMLServo = RML;
    }
    public Servo getServo(String Name){
        switch (Name){
            case FishTailLifter:
                return FishTailLifterServo;
            case FishTailSwinger:
                return FishTailSwingerServo;
            case GrabberOne:
                return GrabberOneServo;
            case GrabberTwo:
                return GrabberTwoServo;
            case RML:
                return RMLServo;
            case Clampy:
                return ClampyServo;
            case Lifter:
                return LifterServo;
        }
        return this.FishTailLifterServo;
    }
}
