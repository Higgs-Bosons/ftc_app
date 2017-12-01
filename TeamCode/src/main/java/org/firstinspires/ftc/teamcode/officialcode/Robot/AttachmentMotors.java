package org.firstinspires.ftc.teamcode.officialcode.Robot;

import com.qualcomm.robotcore.hardware.DcMotor;

public class AttachmentMotors {
    private DcMotor ArmLifterMotor;
    public static final String ArmLifter = "ARM LIFTER";
    AttachmentMotors(DcMotor ArmLifter){
        this.ArmLifterMotor = ArmLifter;
    }
    public DcMotor getMotor(String MotorName){
        if(MotorName.equals(ArmLifter)){
            return ArmLifterMotor;
        }
        return ArmLifterMotor;
    }

}
