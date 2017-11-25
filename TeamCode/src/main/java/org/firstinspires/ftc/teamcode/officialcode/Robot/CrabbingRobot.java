package org.firstinspires.ftc.teamcode.officialcode.Robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.AppUtil;
import org.firstinspires.ftc.robotcore.internal.UILocation;
import org.firstinspires.ftc.teamcode.officialcode.Constants;

import static org.firstinspires.ftc.teamcode.officialcode.Constants.BLUE;
import static org.firstinspires.ftc.teamcode.officialcode.Constants.RED;


public class CrabbingRobot{
    public Sensors sensors;
    public Servos servos;
    public DriveMotors driveMotors;
    private AttachmentMotors attachmentMotors;

    public CrabbingRobot(){

    }
    public void GiveAttachmentMotors(){
        attachmentMotors = new AttachmentMotors();
    }
    public void GiveDriveMotors(DcMotor LeftFront, DcMotor RightFront, DcMotor LeftBack, DcMotor RightBack){
        driveMotors = new DriveMotors(LeftFront, RightFront, LeftBack, RightBack);
    }
    public void GiveSensors(ColorSensor SuperNitron9000, BNO055IMU IMU){
        this.sensors = new Sensors(SuperNitron9000, IMU);
    }
    public void GiveServos(Servo FishTail, Servo JackSmith){
        this.servos = new Servos(FishTail, JackSmith);
    }

    private void Pause(int Duration){
        try{
            Thread.sleep(Duration);
        }catch (Exception ignore){}
    }
    public void KnockOffJewel(String Color){
        int RED_COUNT = 0;
        int BLUE_COUNT = 0;
        int RED_READING;
        int BLUE_READING;
        String ColorISee;
        for(double counter = 0.05; counter <= 0.8; counter = counter + 0.01) {
            RED_READING = this.sensors.ReadColor("RED");
            BLUE_READING = this.sensors.ReadColor("BLUE");
            servos.getServo(Servos.FishTail).setPosition(counter);
            Pause(10);
            if(RED_READING > BLUE_READING && RED_READING >= 3){
                RED_COUNT++;
            }else if(BLUE_READING > RED_READING && BLUE_READING >= 3){
               BLUE_COUNT++;
            }
        }
        AppUtil.getInstance().showToast(UILocation.BOTH,"RED COUNT: "+RED_COUNT+"\nBLUE COUNT: "+BLUE_COUNT);
        if(RED_COUNT > BLUE_COUNT){
            ColorISee = RED;
            AppUtil.getInstance().showToast(UILocation.BOTH,"I SEE RED");
        }else{
            ColorISee = BLUE;
            AppUtil.getInstance().showToast(UILocation.BOTH,"I SEE BLUE");
        }
        if(ColorISee.equalsIgnoreCase(Color)){
           this.driveMotors.Turn(10);
        }else{
            this.driveMotors.Turn(350);
        }
    }
}
