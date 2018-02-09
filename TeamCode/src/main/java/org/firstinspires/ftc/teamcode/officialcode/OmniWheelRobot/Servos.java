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
    private Servo GrabberSpinOneServo;
    private Servo GrabberSpinTwoServo;
    Servos(Servo FishTailLifter,  Servo FishTailSwinger, Servo GrabberOne, Servo GrabberTwo,
           Servo Clampy, Servo RML, Servo Lifter, Servo Spin1, Servo Spin2){
        this.FishTailSwingerServo = FishTailSwinger;
        this.FishTailLifterServo = FishTailLifter;
        this.GrabberOneServo = GrabberOne;
        this.GrabberTwoServo = GrabberTwo;
        this.LifterServo = Lifter;
        this.ClampyServo = Clampy;
        this.RMLServo = RML;
        this.GrabberSpinOneServo = Spin1;
        this.GrabberSpinTwoServo = Spin2;
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
            case GrabberSpinOne:
                return GrabberSpinOneServo;
            case GrabberSpinTwo:
                return GrabberSpinTwoServo;
        }
        return this.FishTailLifterServo;
    }
}
