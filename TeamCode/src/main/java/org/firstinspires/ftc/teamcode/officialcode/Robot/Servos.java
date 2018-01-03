package org.firstinspires.ftc.teamcode.officialcode.Robot;

import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.officialcode.IntStringDefInterfaces;

public class Servos {
    public static final String FishTail = "Fish Tail";
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
            return this.FishTailServo;
        }else if(Name.equalsIgnoreCase(JackSmith)){
            return this.JackSmithServo;
        }else if(Name.equalsIgnoreCase(Grabby)){
            return this.GrabbyServo;
        }
        return this.FishTailServo;
    }
}
