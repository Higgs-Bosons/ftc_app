package org.firstinspires.ftc.teamcode.officialcode.Robot;

import com.qualcomm.robotcore.hardware.Servo;

public class Servos {
    public static final String FishTail = "Fish Tail";
    public static final String JackSmith = "JackSmith";
    private Servo FishTailServo;
    private Servo JackSmithServo;
    public Servos(Servo FishTail, Servo JackSmith){
        this.FishTailServo = FishTail;
        this.JackSmithServo = JackSmith;
    }
    public Servo getServo(String Name){
        if(Name.equalsIgnoreCase(FishTail)){
            return FishTailServo;
        }else if(Name.equalsIgnoreCase(JackSmith)){
            return JackSmithServo;
        }
        return FishTailServo;
    }
}
