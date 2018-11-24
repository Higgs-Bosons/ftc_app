package org.firstinspires.ftc.teamcode.Galaxy.Robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Galaxy.Tools;

import java.util.Hashtable;
import java.util.Map;

import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FIRST_LETTER_NO_SPACE_LOWERCASE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FIRST_LETTER_NO_SPACE_UPPERCASE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FIRST_LETTER_WITH_SPACE_LOWERCASE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FIRST_LETTER_WITH_SPACE_UPPERCASE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FIRST_LETTER_WITH_UNDERSCORE_LOWERCASE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FIRST_LETTER_WITH_UNDERSCORE_UPPERCASE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FULL_NAME_NO_SPACE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FULL_NAME_NO_SPACE_UPPERCASE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FULL_NAME_WITH_SPACE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FULL_NAME_WITH_SPACE_UPPERCASE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FULL_NAME_WITH_UNDERSCORE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.FULL_NAME_WITH_UNDERSCORE_UPPERCASE;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.LEFT_BACK;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.LEFT_FRONT;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.MotorNameTypes;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.MotorTag;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.RIGHT_BACK;
import static org.firstinspires.ftc.teamcode.Galaxy.Constants.RIGHT_FRONT;

public class Motors extends Sensors{
    private HardwareMap hardwareMap;
    private Map<String, DcMotor> motorsByName;
    private Map<Integer, DcMotor> motorsByTag;

    //-------{INITIALIZING}-----------------------------------------------------------------------------
    public Motors(HardwareMap hardwareMap) {
        super(hardwareMap);
        this.hardwareMap = hardwareMap;
        this.motorsByName = new Hashtable<>();
        this.motorsByTag = new Hashtable<>();
    }

    //-------{ADDING MOTORS}----------------------------------------------------------------------------
    public void addAMotor(String motorName, @MotorTag int motorTag) throws RuntimeException{
        if (this.motorsByName.containsKey(motorName)) {
            throw new customErrors.DuplicateNameException();
        }
        if (this.motorsByTag.containsKey(motorTag) && !Tools.isTagRepeatable(motorTag)) {
            throw new customErrors.DuplicateTagException();
        }

        this.motorsByName.put(motorName, hardwareMap.dcMotor.get(motorName));
        this.motorsByTag.put(motorTag, hardwareMap.dcMotor.get(motorName));
    }

    //-------{GETTING MOTORS}---------------------------------------------------------------------------
    public DcMotor getMotorByName(String motorName){
        return this.motorsByName.get(motorName);
    }
    public DcMotor getMotorByTag(int tag) {
        return this.motorsByTag.get(tag);
    }

    public void moveMotorByName(String motorName, double power){
        getMotorByName(motorName).setPower(power);
    }

    //-------{GETTING DRIVE TRAINS}----------------------------------------------------------------------
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
    public DcMotor[] getDriveTrain(){
        DcMotor[] returnArray = new DcMotor[4];
        returnArray[0] = getMotorByTag(LEFT_FRONT);
        returnArray[1] = getMotorByTag(RIGHT_FRONT);
        returnArray[2] = getMotorByTag(RIGHT_BACK);
        returnArray[3] = getMotorByTag(LEFT_BACK);
        return returnArray;
    }
}
