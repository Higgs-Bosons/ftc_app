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
    private double Direction = 0.15;             //Stores which direction we need to scan.

//------------{OmniWheelRobot}--------------------------------------------------------------------------------------------------------------------------
    public OmniWheelRobot(){}//Empty
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
    //And here is where we pass all of the 9 Servos to a Servo object.
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

    //We use this program to align to the correct Cryptobox column, and drop off the preloaded glyph.
    public void ScoreAGlyph(String KeyColumn, String Color){//We pass the key column, the alliance color, and the position on the field.
        if(Color.equals(RED)){                                               //If we are on red, we set Direction to -1, reversing the
            Direction = -0.15;                                                  // direction that we scan the columns. Then we convert the String
            if(KeyColumn.equals("LEFT") ) {ROW = 3;}                         // Vuforia gives us for the key column, to an integer, that we use to go
            if(KeyColumn.equals("CENTER")){ROW = 2;}                         // to the correct column.
            if(KeyColumn.equals("RIGHT")) {ROW = 1;}
        }else{                                                               //If we are not on team red (we are on team blue), we then convert the
            if(KeyColumn.equals("LEFT") ) {ROW = 1;}                         // String to an integer, using a different method.
            if(KeyColumn.equals("CENTER")){ROW = 2;}
            if(KeyColumn.equals("RIGHT")) {ROW = 3;}
        }
        alignRow(ROW);                                                       //Then we pass ROW to alignRow(), which aligns us to the correct column.
        fineTune();                                                          //Then we run fineTune() to align precisely to the column
        dropOff();                                                           //And finally dropOff() to drop in the glyph.
    }
    private void alignRow(int row){
        int OldReading;                                                              //Stores the previous reading.
        if(Direction < 0){                                                           //If Direction is less than 0, which means that we are on team red,
            this.driveMotors.Move(W, 3, 0.1);                    // we move 3 inches to the left, to make sure we see the first
        }else{                                                                       // column. If we are on blue, we move the other way, also to
            this.driveMotors.Move(E, 2.5, 0.1);                  // make sure we see the first column.
        }
        this.driveMotors.Turn(0);                                      //We turn to 0, to make sure we are parallel to the wall.
        this.driveMotors.TurnMotorsOn(Direction, -Direction,-Direction, Direction);  //We then slow move across the Cryptobox.
        OldReading = this.sensors.getReflectedLight();                               //OldReading is set to the current reflected light reading.
        for(int LOOP = row; LOOP != 0;LOOP--){                                       //We then loop till we have looped the number of times row is.
            while(OldReading + 35 > this.sensors.getReflectedLight())                //We wait until the current reading is greater than the OldReading
            {OldReading = this.sensors.getReflectedLight();}                         // plus 35. If it is not, OldReading is set to the current reading.
            while(OldReading - 35 < this.sensors.getReflectedLight())                //We then wait for the current reading to go back down, still setting
            {OldReading = this.sensors.getReflectedLight();}                         // the OldReading to the current reading.
            OldReading = this.sensors.getReflectedLight();                           //We then set OldReading to the current reading, an decreases LOOP
        }                                                                            // (in the for loop).
        this.driveMotors.STOP();                                                     //Finally we stop the driveMotors.
    }
    private void fineTune(){
        this.driveMotors.Turn(0);                                   //We now turn to 0, two times for more accuracy.
        this.driveMotors.Turn(0);
        int OldReading = this.sensors.getReflectedLight();                        //OldReading is set to the current reflected light intensity.
        this.driveMotors.Move(E, 2, 0.2);                     //We move 3 inches to the left (East, we are backwards).
        this.driveMotors.TurnMotorsOn(0.1, -0.1,  //Then we start moving slowly to the right.
                -0.1, 0.1);
        while(OldReading + 50 > this.sensors.getReflectedLight())                 //We wait for the current reading till we se a "spike" (the current
        {OldReading = this.sensors.getReflectedLight();}                          // reading is 50 more than OldReading). While it is a no, we set the
        this.driveMotors.Turn(0);                                           // OldReading to the current reading.
        this.driveMotors.Turn(0);                                    //We now turn to 0, two times for more accuracy.
        this.driveMotors.Move(S, 4, 0.3);                     //Then back up to make sure we are straight,
        this.driveMotors.Move(N, 3, 0.3);                     // and move forward to give the glyph we are going to drop room to land
        this.driveMotors.Turn(0);                                   // flat. An turn back to 0.
    }
    private void dropOff(){
        this.attachmentMotors.getMotor(Conveyor).setPower(-0.5);  //We power the ConveyorBelt, turning it.
        this.Pause(4000);                                //Pause 4 seconds (4000 milliseconds).
        this.attachmentMotors.getMotor(Conveyor).setPower(0);     //We stop the motor.
    }
}
