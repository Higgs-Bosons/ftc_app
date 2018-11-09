package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Tools;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class Motors{
    private DcMotor[]   motors;
    private String[]    motorNames;
    private int[]       motorTags;
    private HardwareMap hardwareMap;

//-------{INITIALIZING}-----------------------------------------------------------------------------
    public Motors(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        this.motors = new DcMotor[0];
        this.motorNames = new String[0];
        this.motorTags = new int[0];
    }
    public Motors(String motorName,@MotorTag int motorTag, HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        this.motors = new DcMotor[1];
        this.motors[0] = (hardwareMap.dcMotor.get(motorName));

        this.motorNames = new String[1];
        this.motorNames[0] = motorName;

        this.motorTags = new int[1];
        this.motorTags[0] = motorTag;

    }

//-------{ADDING MOTORS}----------------------------------------------------------------------------
    public void addAMotor(String motorName, @MotorTag int motorTag) throws Exception{
        for(String motorNameFromList : motorNames){
            if(motorNameFromList.equals(motorName)){
                throw new customErrors.DuplicateNameException();
            }
        }
        for(int motorTagFromList : motorTags){
            if(motorTagFromList == motorTag && !Tools.isTagRepeatable(motorTag)){
                throw new customErrors.DuplicateTagException();
            }
        }

        if(motorNames.length == 0){
            this.motors = new DcMotor[1];
            this.motors[0] = (hardwareMap.dcMotor.get(motorName));

            this.motorNames = new String[1];
            this.motorNames[0] = motorName;

            this.motorTags = new int[1];
            this.motorTags[0] = motorTag;
        }else{
            DcMotor[] oldMotorArray = this.motors;
            this.motors = new DcMotor[oldMotorArray.length + 1];
            System.arraycopy(oldMotorArray, 0, this.motors, 0, oldMotorArray.length);
            this.motors[oldMotorArray.length-1] = (hardwareMap.dcMotor.get(motorName));

            String[] oldMotorNames = this.motorNames;
            this.motorNames = new String[oldMotorNames.length + 1];
            System.arraycopy(oldMotorNames, 0, this.motorNames, 0, oldMotorNames.length);
            this.motorNames[oldMotorNames.length-1] = (motorName);

            int[] oldMotorTags = this.motorTags;
            this.motorTags = new int[oldMotorTags.length + 1];
            System.arraycopy(oldMotorTags, 0, this.motorTags, 0, oldMotorTags.length);
            this.motorTags[oldMotorTags.length-1] = (motorTag);
        }


    }

//-------{GETTING MOTORS}---------------------------------------------------------------------------
    public DcMotor getMotorByName(String motorName){
        int counter = 0;
        for(String arrayMotorName : motorNames){
            if(arrayMotorName.equals(motorName)){
                return motors[counter];
            }


            counter ++;
        }
        return null;
    }
    public DcMotor getMotorByNumber(int motorNumber){
        try{
            return motors[motorNumber];
        }catch (ArrayIndexOutOfBoundsException ignore){
           return null;
        }
    }
    public DcMotor getMotorByTag(int tag){
        int counter = 0;
        int motorNumber = 0;
        boolean foundTheMotor = false;
        for(int motorTag : motorTags){
            if(motorTag == tag){
                foundTheMotor = true;
                motorNumber = counter;
            }

            counter ++;
        }
        
        if(!foundTheMotor){
            return null;
        }

        return motors[motorNumber];

    }

//-------{GETTING DRIVE TRAINS}----------------------------------------------------------------------
    public DcMotor[] getDriveTrain() {
        DcMotor[] returnArray =  new DcMotor[4];
        returnArray[0] = getMotorByTag(LEFT_FRONT);
        returnArray[1] = getMotorByTag(RIGHT_FRONT);
        returnArray[2] = getMotorByTag(RIGHT_BACK);
        returnArray[3] = getMotorByTag(LEFT_BACK);
        return returnArray;
    }
    public DcMotor[] getAutoDriveTrain(@MotorNameTypes String motorNameType){
        DcMotor[] returnArray = new DcMotor[4];
        switch (motorNameType) {
            case FIRST_LETTER_NO_SPACE_UPPERCASE:
                returnArray[0] = (hardwareMap.dcMotor.get("LF"));
                returnArray[1] = (hardwareMap.dcMotor.get("RF"));
                returnArray[2] = (hardwareMap.dcMotor.get("RB"));
                returnArray[3] = (hardwareMap.dcMotor.get("LB"));
                break;

            case FULL_NAME_NO_SPACE:
                returnArray[0] = (hardwareMap.dcMotor.get("LeftFront"));
                returnArray[1] = (hardwareMap.dcMotor.get("RightFront"));
                returnArray[2] = (hardwareMap.dcMotor.get("RightBack"));
                returnArray[3] = (hardwareMap.dcMotor.get("LeftBack"));
                break;

            case FULL_NAME_WITH_SPACE:
                returnArray[0] = (hardwareMap.dcMotor.get("Left Front"));
                returnArray[1] = (hardwareMap.dcMotor.get("Right Front"));
                returnArray[2] = (hardwareMap.dcMotor.get("Right Back"));
                returnArray[3] = (hardwareMap.dcMotor.get("Left Back"));
                break;

            case FIRST_LETTER_WITH_UNDERSCORE_UPPERCASE:
                returnArray[0] = (hardwareMap.dcMotor.get("L_F"));
                returnArray[1] = (hardwareMap.dcMotor.get("R_F"));
                returnArray[2] = (hardwareMap.dcMotor.get("R_B"));
                returnArray[3] = (hardwareMap.dcMotor.get("L_B"));
                break;

            case FIRST_LETTER_WITH_SPACE_UPPERCASE:
                returnArray[0] = (hardwareMap.dcMotor.get("L F"));
                returnArray[1] = (hardwareMap.dcMotor.get("R F"));
                returnArray[2] = (hardwareMap.dcMotor.get("R B"));
                returnArray[3] = (hardwareMap.dcMotor.get("L B"));
                break;

            case FIRST_LETTER_NO_SPACE_LOWERCASE:
                returnArray[0] = (hardwareMap.dcMotor.get("lf"));
                returnArray[1] = (hardwareMap.dcMotor.get("rf"));
                returnArray[2] = (hardwareMap.dcMotor.get("rb"));
                returnArray[3] = (hardwareMap.dcMotor.get("lb"));
                break;

            case FIRST_LETTER_WITH_UNDERSCORE_LOWERCASE:
                returnArray[0] = (hardwareMap.dcMotor.get("l_f"));
                returnArray[1] = (hardwareMap.dcMotor.get("r_f"));
                returnArray[2] = (hardwareMap.dcMotor.get("r_b"));
                returnArray[3] = (hardwareMap.dcMotor.get("l_b"));
                break;

            case FULL_NAME_WITH_UNDERSCORE:
                returnArray[0] = (hardwareMap.dcMotor.get("Left_Front"));
                returnArray[1] = (hardwareMap.dcMotor.get("Right_Front"));
                returnArray[2] = (hardwareMap.dcMotor.get("Right_Back"));
                returnArray[3] = (hardwareMap.dcMotor.get("Left_Back"));
                break;

            case FULL_NAME_NO_SPACE_UPPERCASE:
                returnArray[0] = (hardwareMap.dcMotor.get("LEFTFRONT"));
                returnArray[1] = (hardwareMap.dcMotor.get("RIGHTFRONT"));
                returnArray[2] = (hardwareMap.dcMotor.get("RIGHTBACK"));
                returnArray[3] = (hardwareMap.dcMotor.get("LEFTBACK"));
                break;

            case FULL_NAME_WITH_SPACE_UPPERCASE:
                returnArray[0] = (hardwareMap.dcMotor.get("LEFT FRONT"));
                returnArray[1] = (hardwareMap.dcMotor.get("RIGHT FRONT"));
                returnArray[2] = (hardwareMap.dcMotor.get("RIGHT BACK"));
                returnArray[3] = (hardwareMap.dcMotor.get("LEFT BACK"));
                break;

            case FULL_NAME_WITH_UNDERSCORE_UPPERCASE:
                returnArray[0] = (hardwareMap.dcMotor.get("LEFT_FRONT"));
                returnArray[1] = (hardwareMap.dcMotor.get("RIGHT_FRONT"));
                returnArray[2] = (hardwareMap.dcMotor.get("RIGHT_BACK"));
                returnArray[3] = (hardwareMap.dcMotor.get("LEFT_BACK"));
                break;

            case FIRST_LETTER_WITH_SPACE_LOWERCASE:
                returnArray[0] = (hardwareMap.dcMotor.get("l f"));
                returnArray[1] = (hardwareMap.dcMotor.get("r f"));
                returnArray[2] = (hardwareMap.dcMotor.get("r b"));
                returnArray[3] = (hardwareMap.dcMotor.get("l b"));
                break;

            default:
                return null;
        }
        return returnArray;
    }
}
