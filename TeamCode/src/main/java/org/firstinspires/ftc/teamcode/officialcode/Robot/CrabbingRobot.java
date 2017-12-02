package org.firstinspires.ftc.teamcode.officialcode.Robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.internal.*;
import static org.firstinspires.ftc.teamcode.officialcode.Constants.*;


public class CrabbingRobot{
    public Sensors sensors;
    public Servos servos;
    public DriveMotors driveMotors;
    public AttachmentMotors attachmentMotors;

    public CrabbingRobot(){

    }
    public void GiveAttachmentMotors(DcMotor ArmLifter){
        this.attachmentMotors = new AttachmentMotors(ArmLifter);
    }
    public void GiveDriveMotors(DcMotor LeftFront, DcMotor RightFront, DcMotor LeftBack, DcMotor RightBack){
        this.driveMotors = new DriveMotors(LeftFront, RightFront, LeftBack, RightBack);
    }
    public void GiveSensors(ColorSensor SuperNitron9000, BNO055IMU IMU){
        this.sensors = new Sensors(SuperNitron9000, IMU);
    }
    public void GiveServos(Servo FishTail, Servo JackSmith, Servo Grabby){
        this.servos = new Servos(FishTail, JackSmith, Grabby);
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
        for(double counter = 0.16; counter <= 0.9; counter = counter + 0.01) {
            RED_READING = this.sensors.ReadColor("RED");
            BLUE_READING = this.sensors.ReadColor("BLUE");
            this.servos.getServo(Servos.FishTail).setPosition(counter);
            Pause(10);
            if(RED_READING > BLUE_READING && RED_READING >= 3){
                RED_COUNT++;
            }else if(BLUE_READING > RED_READING && BLUE_READING >= 3){
               BLUE_COUNT++;
            }
        }
        this.servos.getServo(Servos.FishTail).setPosition(0.72);
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
                this.driveMotors.TurnMotorsOn(-0.5,0.5,-0.5,0.5);
                this.Pause(300);
            }else{
                this.driveMotors.TurnMotorsOn(0.5,-0.5,0.5,-0.5);
                this.Pause(300);
            }
            this.driveMotors.STOP();
        }
        this.servos.getServo(Servos.FishTail).setPosition(0.16);
        this.Pause(1000);
    }
}
