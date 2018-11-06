package org.firstinspires.ftc.teamcode.Robots;

import android.support.annotation.IntDef;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Motors extends LinearOpMode {
    // 2 = It CAN'T be repeated, 1 = it CAN be repeated
    public static final int RIGHT_FRONT = 201;
    public static final int LEFT_BACK = 202;
    public static final int RIGHT_BACK = 203;
    public static final int LEFT_FRONT = 204;
    public static final int NO_TAG = 100;

    public static final String FIRST_LETTER_NO_SPACE = "Example: LF";
    public static final String FULL_NAME_WITH_SPACE = "Example: Left Front";
    public static final String FULL_NAME_NO_SPACE = "Example: LeftFront";
    

    private DcMotor[] motors;
    private String[]  motorNames;
    private int[]     motorTags;

    public Motors() {
        this.motors = new DcMotor[0];
        this.motorNames = new String[0];
        this.motorTags = new int[0];
    }
    public Motors(String motorName, @motorTags int motorTag){
        this.motors = new DcMotor[1];
        this.motors[0] = (hardwareMap.dcMotor.get(motorName));

        this.motorNames = new String[1];
        this.motorNames[0] = motorName;

        this.motorTags = new int[1];
        this.motorTags[0] = motorTag;

    }
    public void addAMotor(String motorName, @motorTags int motorTag){
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
    public DcMotor getMotorByTag(@motorTags int tag) throws customErrors.motorNotFoundException, customErrors.duplicateTagException {
        int counter = 0;
        int motorNumber = 0;
        for(int motorTag : motorTags){
            if(motorTag == tag){
                if(!isTagRepeatable(motorTag) && motorNumber != 0){
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
    public DcMotor getMotorByTag_Try(@motorTags int tag){
        int counter = 0;
        int motorNumber = 0;
        for(int motorTag : motorTags){
            if(motorTag == tag){
                if(!isTagRepeatable(motorTag) && motorNumber != 0){
                   return null;
                }
                motorNumber = counter;
            }
            if(counter == motorNames.length-1){
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
        if(motorNameType.equals(FIRST_LETTER_NO_SPACE)) {
            DcMotor[] returnArray = new DcMotor[4];
            returnArray[0] = (hardwareMap.dcMotor.get("LF"));
            returnArray[1] = (hardwareMap.dcMotor.get("RF"));
            returnArray[2] = (hardwareMap.dcMotor.get("RB"));
            returnArray[3] = (hardwareMap.dcMotor.get("LB"));

            return returnArray;
        }
        return null;
    }

    private boolean isTagRepeatable(int tag){
        if(Math.floor(tag / 100) == 1){
            return true;
        }else if(Math.floor(tag / 100) == 2){
            return false;
        }
        return false;
    }


    @IntDef({LEFT_BACK, LEFT_FRONT, RIGHT_BACK, RIGHT_FRONT, NO_TAG})
    @Retention(RetentionPolicy.SOURCE)
    @interface motorTags{}

    public void runOpMode(){

    }
}
