package org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;
import org.firstinspires.ftc.teamcode.officialcode.Constants;

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
    public void GiveSensors(ColorSensor SuperNitron9000, BNO055IMU IMU, OpticalDistanceSensor LIGHT){
        this.sensors = new Sensors(SuperNitron9000, IMU, LIGHT);
    }
    public void GiveServos(Servo FishTailLifter,  Servo FishTailSwinger, Servo GrabberOne, Servo GrabberTwo, Servo Clampy, Servo RML, Servo Lifter){
        this.servos = new Servos(FishTailLifter, FishTailSwinger, GrabberOne, GrabberTwo, Clampy, RML,Lifter);
    }

    public void Pause(int Duration){
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
        this.servos.getServo(Servos.FishTailSwinger).setPosition(0.5);
        for(double counter = 0.8; counter >= 0.3; counter -= 0.01) {
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
        this.servos.getServo(Servos.FishTailLifter).setPosition(0.5);
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
                this.servos.getServo(Servos.FishTailSwinger).setPosition(0.65);
                this.Pause(300);
            }
        }
        this.servos.getServo(Servos.FishTailLifter).setPosition(0.95);
        this.Pause(1000);
        this.servos.getServo(Servos.FishTailSwinger).setPosition(0.1);

    }

    private int ROW = 1;
    private int degrees = 0;
    private int Direction = 1;
    public void ScoreAGlyph(String KeyColumn, String Color, String Position){
        if(Position.equals("LEFT")&Color.equals(Constants.RED)){degrees = 270;}
        if(Position.equals("RIGHT")&Color.equals(Constants.RED)){degrees = 0;}
        if(Position.equals("LEFT")&Color.equals(Constants.BLUE)){degrees = 180;}
        if(Color.equals(Constants.RED)){
            Direction = -1;
            if(KeyColumn.equals("LEFT") ) {ROW = 3;}
            if(KeyColumn.equals("CENTER")){ROW = 2;}
            if(KeyColumn.equals("RIGHT")) {ROW = 1;}
        }else{
            if(KeyColumn.equals("LEFT") ) {ROW = 1;}
            if(KeyColumn.equals("CENTER")){ROW = 2;}
            if(KeyColumn.equals("RIGHT")) {ROW = 3;}
        }
        alineRow(ROW);
        fineTune();
    }
    private void alineRow(int row){
        if(Direction == -1){
            this.driveMotors.Move(Constants.W, 1, 0.1);
        }else{
            this.driveMotors.Move(Constants.E, 2.5, 0.1);
        }
        int OldReading;
        this.driveMotors.Turn(degrees);
        this.driveMotors.TurnMotorsOn(0.2 * Direction, -0.2 * Direction
                , -0.2 * Direction, 0.2 * Direction);
        OldReading = this.sensors.getReflectedLight();
        for(int LOOP = row; LOOP != 0;){
            while(OldReading + 35 > this.sensors.getReflectedLight()){OldReading = this.sensors.getReflectedLight();}
            while(OldReading - 35 < this.sensors.getReflectedLight()){OldReading = this.sensors.getReflectedLight();}
            OldReading = this.sensors.getReflectedLight();
            LOOP--;
        }
        this.driveMotors.STOP();
    }
    private void fineTune(){
        this.driveMotors.Turn(degrees);
        this.driveMotors.Turn(degrees);
        int OldReading = this.sensors.getReflectedLight();
        this.driveMotors.Move(Constants.E, 1, 0.1);
        this.driveMotors.TurnMotorsOn(0.1, -0.1,-0.1, 0.1);
        while(OldReading + 50 > this.sensors.getReflectedLight()){OldReading = this.sensors.getReflectedLight();}
        if(Direction == 1){
            while(OldReading - 50 < this.sensors.getReflectedLight()){OldReading = this.sensors.getReflectedLight();}
        }
        this.driveMotors.Turn(degrees);
        this.driveMotors.Turn(degrees);
        this.driveMotors.Turn(degrees);
        this.driveMotors.Move(Constants.S, 1, 0.07);
        this.driveMotors.Move(Constants.N, 2, 0.07);
    }
}
