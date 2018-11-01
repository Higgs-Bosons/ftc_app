package org.firstinspires.ftc.teamcode.Robots;

import android.support.annotation.IntDef;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Motors extends LinearOpMode {
    public static final int LEFT_FRONT = 4;
    public static final int RIGHT_FRONT = 1;
    public static final int LEFT_BACK = 2;
    public static final int RIGHT_BACK = 3;
    public static final int NO_TAG = 0;
    

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
                if(motorNumber == 0 && tag != 0){
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
    public DcMotor[] getDriveTrain() throws customErrors.motorNotFoundException, customErrors.duplicateTagException {
        DcMotor[] returnArray =  new DcMotor[4];
        returnArray[0] = getMotorByTag(LEFT_FRONT);
        returnArray[1] = getMotorByTag(RIGHT_FRONT);
        returnArray[2] = getMotorByTag(RIGHT_BACK);
        returnArray[3] = getMotorByTag(LEFT_BACK);
        
        return returnArray;

    }

    @IntDef({LEFT_BACK, LEFT_FRONT, RIGHT_BACK, RIGHT_FRONT, NO_TAG})
    @Retention(RetentionPolicy.SOURCE)
    @interface motorTags{}

    public void runOpMode(){

    }
}
