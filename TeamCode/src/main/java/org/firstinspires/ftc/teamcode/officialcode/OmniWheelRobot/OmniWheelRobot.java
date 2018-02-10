package org.firstinspires.ftc.teamcode.officialcode.OmniWheelRobot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;
import org.firstinspires.ftc.teamcode.officialcode.Constants;

public class OmniWheelRobot extends Constants{  //This is the class we made to handle all the hardware.
    public Sensors sensors;                     //This controls all our sensors.
    public Servos servos;                       //This controls all the servos on our robot.
    public DriveMotors driveMotors;             //This takes care of moving the Robot.
    public AttachmentMotors attachmentMotors;   //This controls all the motors used for attachments.
    private int ROW = 1;                        //This stores the target Cryptobox column.
    private int degrees = 0;                    //This stores how much we need to turn when we are aligning to the Cryptobox.
    private int Direction = 1;                  //Stores which direction we need to scan.

//------------{OmniWheelRobot}--------------------------------------------------------------------------------------------------------------------------
    public OmniWheelRobot(){

    }//Empty
    //In here we pass all of the Attachment Motors, we can't use hardwareMap in here.
    public void GiveAttachmentMotors(DcMotor ArmLifter, DcMotor Conveyor, DcMotor SlideExtender, DcMotor SlideRetracter){
        //It then gives the motors to an AttachmentMotor object.
        this.attachmentMotors = new AttachmentMotors(ArmLifter, Conveyor, SlideExtender, SlideRetracter);
    }
    //Here is where we pass all of the Drive Motors, and it gives to a DriveMotors object.
    public void GiveDriveMotors(DcMotor LeftFront, DcMotor RightFront, DcMotor LeftBack, DcMotor RightBack){
        this.driveMotors = new DriveMotors(LeftFront, RightFront, LeftBack, RightBack);
    }
    //Here we give the sensors to a Sensors object.
    public void GiveSensors(ColorSensor SuperNitron9000, BNO055IMU IMU, OpticalDistanceSensor LIGHT){
        this.sensors = new Sensors(SuperNitron9000, IMU, LIGHT);
    }
    //And here is where was pass all of the 9 Servos to  a Servo object.
    public void GiveServos(Servo FishTailLifter,Servo FishTailSwinger,Servo GrabberOne,Servo GrabberTwo,Servo Clampy,Servo RML,Servo Lifter,Servo GrabberSpinOne,Servo GrabberSpinTwo){
        this.servos = new Servos(FishTailLifter, FishTailSwinger, GrabberOne, GrabberTwo, Clampy, RML,Lifter, GrabberSpinOne, GrabberSpinTwo);
    }

//-------------{Tools}------------------------------------------------------------------------------------------------------------------------------------
    //Here is just a Thread.sleep, but is in a try/catch block. by using it, we can make our code more concise.
    public void Pause(int Duration){
        try{
            Thread.sleep(Duration);
        }catch (Exception ignore){

        }
    }

//------------{Pre-programed tasks}-----------------------------------------------------------------------------------------------------------------------
    //We use this program at the beginning of each Autonomous.
    public void KnockOffJewel(String Color){    //Color is the alliance color you are
        //VARIABLES:
        int RED_COUNT = 0;         //Stores the number of times the color sensor saw red
        int BLUE_COUNT = 0;        //Stores the number of times the color sensor saw blue
        int RED_READING;           //Stores the red value the color sensor saw
        int BLUE_READING;          //Stores the blue value the color sensor saw
        String ColorISee = "NULL"; //Stores the color the robot saw the most in a String
        boolean NO_COLOR = false;  //Stores a Yes/No so if we see no color, we don't hit the wrong Jewel off

        // READING THE JEWELS COLOR:
        this.servos.getServo(Servos.FishTailSwinger).setPosition(0.5);          //Moves the fish tail swinger to the center position
        for(double counter = 0.85; counter >= 0.35; counter -= 0.01) {          //Because of we use a servo, we slowing decrees the position,
                                                                                // so it swings slowly down
            RED_READING = this.sensors.ReadColor("RED");                        //Reads the color sensor, and sores the red value is sees in RED_READING
            BLUE_READING = this.sensors.ReadColor("BLUE");                      //Reads the color sensor, and sores the blue value is sees in BLUE_READING
            this.servos.getServo(Servos.FishTailLifter).setPosition(counter);   //Lowers the fish tail a little
            Pause(10);                                                 //Pause 10 millisecond
            if(RED_READING > BLUE_READING && RED_READING >= 3){                 //If the red reading is more than blue, and if red reading is more than
                RED_COUNT++;                                                    // 3 (which we do to keep from over sensitivity), it adds 1 to RED_COUNT.
            }else if(BLUE_READING > RED_READING && BLUE_READING >= 3){          //If the blue reading is more than red, and if blue reading is more than
               BLUE_COUNT++;                                                    // 3, it adds 1 to BLUE_COUNT.
            }
        }
        this.servos.getServo(Servos.FishTailLifter).setPosition(0.5);           //Then it raises the fish tail a little, so when it moves, it does not
                                                                                // drag when we move it to knock off the jewel.

        if(RED_COUNT > BLUE_COUNT){                                             //If number of times it saw red is greater than the times it saw blue,
            ColorISee = RED;                                                    // it says the color it sees is red, and displays that realization in a Toast.
            AppUtil.getInstance().showToast(UILocation.BOTH,"I SEE RED");
        }else if(RED_COUNT < BLUE_COUNT){                                       //If number of times it saw blue is greater than the times it saw red,
            ColorISee = BLUE;                                                   // it says the color it sees is blue, and displays that realization in a Toast.
            AppUtil.getInstance().showToast(UILocation.BOTH,"I SEE BLUE");
        }else{                                                                  //If neither of the above are true (They are equal), it sets NO_COLOR to
            NO_COLOR = true;                                                    // true, leaves ColorISee as NULL, and displays a Toast with this info.
            AppUtil.getInstance().showToast(UILocation.BOTH,"I SAW NO COLOR");
        }
        if(!NO_COLOR){                                                          //Then, if it saw a color, it:
            if(!ColorISee.equalsIgnoreCase(Color)){                                    //Checks is the color it saw is equal to the alliance color.
                this.servos.getServo(Servos.FishTailSwinger).setPosition(0.4);         //If it does not, it swings to hit off the ball it saw.
            }else{
                this.servos.getServo(Servos.FishTailSwinger).setPosition(0.6);         //If it matches, it moves the other way and knocks of the other ball.
            }
        }                                                                       //If it did not see a color, it does nothing.
        this.servos.getServo(Servos.FishTailLifter).setPosition(0.95);          //It raises the fish tail all the way up,
        this.Pause(1000);                                              // pauses 1000 milliseconds (1 second),
        this.servos.getServo(Servos.FishTailSwinger).setPosition(0.1);          // and moves it lowers it onto the support beam.
    }

    public void ScoreAGlyph(String KeyColumn, String Color, String Position){
        if(Position.equals("LEFT")&Color.equals(RED)){degrees = 270;}
        if(Position.equals("RIGHT")&Color.equals(RED)){degrees = 0;}
        if(Position.equals("LEFT")&Color.equals(BLUE)){degrees = 180;}
        if(Color.equals(RED)){
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
        dropOff();
    }
    private void alineRow(int row){
        if(Direction == -1){
            this.driveMotors.Move(W, 1, 0.3);
        }else{
            this.driveMotors.Move(E, 2.5, 0.3);
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
        this.driveMotors.Move(E, 3, 0.4);
        this.driveMotors.TurnMotorsOn(0.1, -0.1,-0.1, 0.1);
        while(OldReading + 50 > this.sensors.getReflectedLight()){OldReading = this.sensors.getReflectedLight();}
       // if(Direction == 1){
         //   while(OldReading - 50 < this.sensors.getReflectedLight()){OldReading = this.sensors.getReflectedLight();}
        //}
        this.driveMotors.Turn(degrees);
        this.driveMotors.Turn(degrees);
        this.driveMotors.Turn(degrees);
        this.driveMotors.Move(S, 1, 0.3);
        this.driveMotors.Move(N, 3, 0.3);

    }
    private void dropOff(){
        this.attachmentMotors.getMotor(Conveyor).setPower(-0.5);
        this.Pause(4000);
        this.attachmentMotors.getMotor(Conveyor).setPower(0);
    }
}
