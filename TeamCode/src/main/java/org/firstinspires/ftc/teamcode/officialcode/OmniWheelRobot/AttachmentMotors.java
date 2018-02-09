package org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.officialcode.Constants;

public class AttachmentMotors extends Constants{
    private DcMotor ArmLifterMotor;
    private DcMotor SlideExtenderMotor;
    private DcMotor SlideRetracterMotor;
    private DcMotor ConveyorMotor;

    AttachmentMotors(DcMotor ArmLifter, DcMotor Conveyor, DcMotor SlideExtender, DcMotor SlideRetracter){
        this.ArmLifterMotor = ArmLifter;
        this.SlideExtenderMotor = SlideExtender;
        this.SlideRetracterMotor = SlideRetracter;
        this.ConveyorMotor = Conveyor;
    }
    public DcMotor getMotor(String MotorName){
        switch (MotorName) {
            case ArmLifter:
                return this.ArmLifterMotor;
            case Conveyor:
                return this.ConveyorMotor;
            case SlideExtender:
                return this.SlideExtenderMotor;
            case SlideRetracter:
                return this.SlideRetracterMotor;
        }
        return this.ArmLifterMotor;
    }

}
