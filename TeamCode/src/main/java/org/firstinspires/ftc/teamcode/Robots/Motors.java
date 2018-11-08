package org.firstinspires.ftc.teamcode.Robots;

import android.support.annotation.IntDef;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class Motors{
    // 2 = It CAN'T be repeated, 1 = it CAN be repeated

    private DcMotor[]  motors;
    private String[]   motorNames;
    private MotorTag[] motorTags;
    HardwareMap hardwareMap;


    public Motors(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        this.motors = new DcMotor[0];
        this.motorNames = new String[0];
        this.motorTags = new MotorTag[0];
    }
    public Motors(String motorName, MotorTag motorTag, HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        this.motors = new DcMotor[1];
        this.motors[0] = (hardwareMap.dcMotor.get(motorName));

        this.motorNames = new String[1];
        this.motorNames[0] = motorName;

        this.motorTags = new MotorTag[1];
        this.motorTags[0] = motorTag;

    }
    public void addAMotor(String motorName, MotorTag motorTag){
        if(motorNames.length == 0){
            this.motors = new DcMotor[1];
            this.motors[0] = (hardwareMap.dcMotor.get(motorName));

            this.motorNames = new String[1];
            this.motorNames[0] = motorName;

            this.motorTags = new MotorTag[1];
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

            MotorTag[] oldMotorTags = this.motorTags;
            this.motorTags = new MotorTag[oldMotorTags.length + 1];
            System.arraycopy(oldMotorTags, 0, this.motorTags, 0, oldMotorTags.length);
            this.motorTags[oldMotorTags.length-1] = (motorTag);
        }


    }
    public DcMotor getMotorByName(String motorName) throws customErrors.motorNotFoundException {
        int counter = 0;
        int motorNumber = 0;
        for(String arrayMotorName : motorNames){
            if(arrayMotorName.equals(motorName)){
                motorNumber = counter;
            }

            counter ++;
        }
        if(counter == motorNames.length-1){
            throw new customErrors.motorNotFoundException(motorName);
        }
        return motors[motorNumber];
    }
    public DcMotor getMotorByNumber(int motorNumber) throws customErrors.motorNotFoundException{
        try{
            return motors[motorNumber];
        }catch (ArrayIndexOutOfBoundsException ignore){
            throw new customErrors.motorNotFoundException(" #" + motorNumber);
        }
    }
    public DcMotor getMotorByTag(MotorTag tag) throws customErrors.motorNotFoundException, customErrors.duplicateTagException {
        int counter = 0;
        int motorNumber = 0;
        for(MotorTag motorTag : motorTags){
            if(motorTag == tag){
                if(!motorTag.canItBeRepeated() && motorNumber != 0){
                    throw new customErrors.duplicateTagException();
                }
                motorNumber = counter;
            }
            if(counter == motorNames.length-1){
                throw new customErrors.motorNotFoundException(" TAG: " + tag);
            }
            counter ++;
        }
        return motors[motorNumber];
    }

    public DcMotor getMotorByName_Try(String motorName){
        int counter = 0;
        int motorNumber = 0;
        for(String arrayMotorName : motorNames){
            if(arrayMotorName.equals(motorName)){
                motorNumber = counter;
            }

            counter ++;
        }
        if(counter == motorNames.length-1){
            return null;
        }
        return motors[motorNumber];
    }
    public DcMotor getMotorByNumber_Try(int motorNumber){
        try{
            return motors[motorNumber];
        }catch (ArrayIndexOutOfBoundsException ignore){
           return null;
        }
    }
    public DcMotor getMotorByTag_Try(MotorTag tag){
        int counter = 0;
        int motorNumber = 0;
        for(MotorTag motorTag : motorTags){
            if(motorTag == tag){
                if(!motorTag.canItBeRepeated() && motorNumber != 0){
                   return null;
                }
                motorNumber = counter;
            }

            counter ++;
        }

        if(counter == motorNames.length){
            return null;
        }

        return motors[motorNumber];

    }

    public  DcMotor[] getDriveTrain_Try() {
        try {
            DcMotor[] returnArray =  new DcMotor[4];
            returnArray[0] = getMotorByTag(LEFT_FRONT);
            returnArray[1] = getMotorByTag(RIGHT_FRONT);
            returnArray[2] = getMotorByTag(RIGHT_BACK);
            returnArray[3] = getMotorByTag(LEFT_BACK);
            return returnArray;
        }
        catch (customErrors.motorNotFoundException | customErrors.duplicateTagException ignore) {}
        return null;
    }

    public DcMotor[] getDriveTrain() throws customErrors.motorNotFoundException, customErrors.duplicateTagException {
        DcMotor[] returnArray =  new DcMotor[4];
        returnArray[0] = getMotorByTag(LEFT_FRONT);
        returnArray[1] = getMotorByTag(RIGHT_FRONT);
        returnArray[2] = getMotorByTag(RIGHT_BACK);
        returnArray[3] = getMotorByTag(LEFT_BACK);
        
        return returnArray;

    }
    public DcMotor[] getAutoDriveTrain(String motorNameType){
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

    public void runOpMode(){

    }
}
