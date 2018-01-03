package org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot;

import com.qualcomm.robotcore.hardware.DcMotor;

public class AttachmentMotors {
    private DcMotor ArmLifterMotor;
    private DcMotor HorizontalLiftMotor;
    private DcMotor ConveyorLowerMotor;
    private DcMotor ConveyorUpperMotor;

    public static final String ArmLifter = "ARM LIFTER";
    public static final String HorizontalLift = "HORIZONTAL LIFT";
    public static final String ConveyorLower = "CONVEYOR LOWER";
    public static final String ConveyorUpper = "CONVEYOR UPPER";
    AttachmentMotors(DcMotor ArmLifter, DcMotor HorizontalLift,  DcMotor ConveyorLower, DcMotor ConveyorUpper){
        this.ArmLifterMotor = ArmLifter;
        this.HorizontalLiftMotor = HorizontalLift;
        this.ConveyorLowerMotor = ConveyorLower;
        this.ConveyorUpperMotor = ConveyorUpper;
    }
    public DcMotor getMotor(String MotorName){
        switch (MotorName) {
            case ArmLifter:
                return this.ArmLifterMotor;
            case HorizontalLift:
                return this.HorizontalLiftMotor;
            case ConveyorLower:
                return this.ConveyorLowerMotor;
            case ConveyorUpper:
                return this.ConveyorUpperMotor;
        }
        return this.ArmLifterMotor;
    }

}
