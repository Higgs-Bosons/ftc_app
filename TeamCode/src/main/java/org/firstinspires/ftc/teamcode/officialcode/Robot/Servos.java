package org.firstinspires.ftc.teamcode.officialcode.Robot;

import com.qualcomm.robotcore.hardware.Servo;

class Servos {
    static final String FishTail = "Fish Tail";
    private static final String JackSmith = "JackSmith";
    private Servo FishTailServo;
    private Servo JackSmithServo;
    Servos(Servo FishTail, Servo JackSmith){
        this.FishTailServo = FishTail;
        this.JackSmithServo = JackSmith;
    }
    Servo getServo(String Name){
        if(Name.equalsIgnoreCase(FishTail)){
            return FishTailServo;
        }else if(Name.equalsIgnoreCase(JackSmith)){
            return JackSmithServo;
        }
        return FishTailServo;
    }
}
