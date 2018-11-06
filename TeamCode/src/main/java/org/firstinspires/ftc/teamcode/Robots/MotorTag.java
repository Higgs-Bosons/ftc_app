package org.firstinspires.ftc.teamcode.Robots;

public class MotorTag {
    private boolean canBeRepeated;
    public MotorTag(boolean canBeRepeated){
        this.canBeRepeated = canBeRepeated;

    }
    boolean canItBeRepeated(){
        return canBeRepeated;
    }
}
