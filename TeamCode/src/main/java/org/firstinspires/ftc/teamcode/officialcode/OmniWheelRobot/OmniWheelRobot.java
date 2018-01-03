package org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;

import static org.firstinspires.ftc.teamcode.officialcode.Constants.BLUE;
import static org.firstinspires.ftc.teamcode.officialcode.Constants.RED;


public class OmniWheelRobot{
    public Sensors sensors;
    public Servos servos;
    public DriveMotors driveMotors;
    public AttachmentMotors attachmentMotors;

    public OmniWheelRobot(){

    }
    public void GiveAttachmentMotors(DcMotor ArmLifter, DcMotor HorizontalLift,  DcMotor ConveyorLower, DcMotor ConveyorUpper){
        this.attachmentMotors = new AttachmentMotors(ArmLifter,  HorizontalLift,   ConveyorLower,  ConveyorUpper);
    }
    public void GiveDriveMotors(DcMotor LeftFront, DcMotor RightFront, DcMotor LeftBack, DcMotor RightBack){
        this.driveMotors = new DriveMotors(LeftFront, RightFront, LeftBack, RightBack);
    }
    public void GiveSensors(ColorSensor SuperNitron9000, BNO055IMU IMU){
        this.sensors = new Sensors(SuperNitron9000, IMU);
    }
    public void GiveServos(Servo FishTailLifter,  Servo FishTailSwinger, Servo Grabby){
        this.servos = new Servos(FishTailLifter, FishTailSwinger, Grabby);
    }

    private void Pause(int Duration){
        try{
            Thread.sleep(Duration);
        }catch (Exception ignore){

        }
    }


    public void KnockOffJewel(String Color){
        int RED_COUNT = 0;
        int BLUE_COUNT = 0;
        int RED_READING;
        int BLUE_READING;
        String ColorISee;
        boolean NO_COLOR = false;
        this.servos.getServo(Servos.FishTailSwinger).setPosition(0.47);
        for(double counter = 1.0; counter >= 0.2; counter = counter - 0.01) {
            RED_READING = this.sensors.ReadColor("RED");
            BLUE_READING = this.sensors.ReadColor("BLUE");
            this.servos.getServo(Servos.FishTailLifter).setPosition(counter);
            Pause(10);
            if(RED_READING > BLUE_READING && RED_READING >= 3){
                RED_COUNT++;
            }else if(BLUE_READING > RED_READING && BLUE_READING >= 3){
               BLUE_COUNT++;
            }
        }
        this.servos.getServo(Servos.FishTailLifter).setPosition(0.37);
        Pause(1000);
        if(RED_COUNT > BLUE_COUNT){
            ColorISee = RED;

            AppUtil.getInstance().showToast(UILocation.BOTH,"I SEE RED");
        }else if(RED_COUNT < BLUE_COUNT){
            ColorISee = BLUE;
            AppUtil.getInstance().showToast(UILocation.BOTH,"I SEE BLUE");
        }else{
            NO_COLOR = true;
            ColorISee = BLUE;
            AppUtil.getInstance().showToast(UILocation.BOTH,"I SAW NO COLOR");
        }
        if(!NO_COLOR){

            if(!ColorISee.equalsIgnoreCase(Color)){
                this.servos.getServo(Servos.FishTailSwinger).setPosition(0.35);
                this.Pause(300);
            }else{
                this.servos.getServo(Servos.FishTailSwinger).setPosition(0.57);
                this.Pause(300);
            }
        }
        this.servos.getServo(Servos.FishTailLifter).setPosition(1.0);
        this.Pause(100);
        this.servos.getServo(Servos.FishTailSwinger).setPosition(0.47);
        this.Pause(1000);
    }

}
