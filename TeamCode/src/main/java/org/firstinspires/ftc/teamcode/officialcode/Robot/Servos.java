package org.firstinspires.ftc.teamcode.officialcode.Robot;

import com.qualcomm.robotcore.hardware.Servo;

public class Servos {
    static final String FishTail = "Fish Tail";
    public static final String JackSmith = "JackSmith";
    public static final String Grabby = "Grabby";
    private Servo FishTailServo;
    private Servo JackSmithServo;
    private Servo GrabbyServo;
    Servos(Servo FishTail, Servo JackSmith, Servo Grabby){
        this.FishTailServo = FishTail;
        this.JackSmithServo = JackSmith;
        this.GrabbyServo = Grabby;
    }
    public Servo getServo(String Name){
        if(Name.equalsIgnoreCase(FishTail)){
            return FishTailServo;
        }else if(Name.equalsIgnoreCase(JackSmith)){
            return JackSmithServo;
        }else if(Name.equalsIgnoreCase(Grabby)){
            return GrabbyServo;
        }
        return FishTailServo;
    }
}
