package org.firstinspires.ftc.teamcode.Robots;

import android.support.annotation.IntDef;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.firstinspires.ftc.teamcode.Constants.*;

public class Motors extends LinearOpMode {
    // 2 = It CAN'T be repeated, 1 = it CAN be repeated

    private DcMotor[]  motors;
    private String[]   motorNames;
    private MotorTag[] motorTags;

    public Motors() {
        this.motors = new DcMotor[0];
        this.motorNames = new String[0];
        this.motorTags = new MotorTag[0];
    }
    public Motors(String motorName, MotorTag motorTag){
        this.motors = new DcMotor[1];
        this.motors[0] = (hardwareMap.dcMotor.get(motorName));

        this.motorNames = new String[1];
        this.motorNames[0] = motorName;

        this.motorTags = new MotorTag[1];
        this.motorTags[0] = motorTag;

    }
    public void addAMotor(String motorName, MotorTag motorTag){
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
    public DcMotor getMotorByName(String motorName) throws customErrors.motorNotFoundException {
        int counter = 0;
        int motorNumber = 0;
        for(String arrayMotorName : motorNames){
            if(arrayMotorName.equals(motorName)){
                motorNumber = counter;
            }
            if(counter == motorNames.length-1){
                throw new customErrors.motorNotFoundException(motorName);
            }
            counter ++;
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
            if(counter == motorNames.length-1){
                return null;
            }
            counter ++;
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
            if(counter == motorNames.length - 1){
                return null;
            }
            counter ++;
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
        if(motorNameType.equals(FIRST_LETTER_NO_SPACE)) {
            returnArray[0] = (hardwareMap.dcMotor.get("LF"));
            returnArray[1] = (hardwareMap.dcMotor.get("RF"));
            returnArray[2] = (hardwareMap.dcMotor.get("RB"));
            returnArray[3] = (hardwareMap.dcMotor.get("LB"));

            return returnArray;
        }
        else if (motorNameType.equals((FULL_NAME_NO_SPACE))) {
            returnArray[0] = (hardwareMap.dcMotor.get("LeftFront"));
            returnArray[1] = (hardwareMap.dcMotor.get("RightFront"));
            returnArray[2] = (hardwareMap.dcMotor.get("RightBack"));
            returnArray[3] = (hardwareMap.dcMotor.get("LeftBack"));

            return returnArray;
        }
        else if (motorNameType.equals(FULL_NAME_WITH_SPACE)) {
            returnArray[0] = (hardwareMap.dcMotor.get("Left Front"));
            returnArray[1] = (hardwareMap.dcMotor.get("Right Front"));
            returnArray[2] = (hardwareMap.dcMotor.get("Right Back"));
            returnArray[3] = (hardwareMap.dcMotor.get("Left Back"));

            return returnArray;
        }
        return null;
    }

    public void runOpMode(){

    }
}
