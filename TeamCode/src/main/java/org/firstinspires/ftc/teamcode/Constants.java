package org.firstinspires.ftc.teamcode;

import android.support.annotation.*;
import java.lang.annotation.*;

public class Constants {
//------{MOTOR TAGS}--------------------------------------------------------------------------------
    public static final int RIGHT_FRONT = 201;
    public static final int LEFT_BACK =   202;
    public static final int RIGHT_BACK =  203;
    public static final int LEFT_FRONT =  204;
    public static final int NO_TAG =      100;

//------{MOTOR DIRECTIONS}--------------------------------------------------------------------------
    public static final int REVERSE = 0;
    public static final int FORWARDS = 1;

//------{MOTOR NAME CONFIGURATION}------------------------------------------------------------------
    public static final String FIRST_LETTER_NO_SPACE_UPPERCASE = "Example: LF";
    public static final String FIRST_LETTER_WITH_SPACE_UPPERCASE = "Example: L F";
    public static final String FIRST_LETTER_WITH_UNDERSCORE_UPPERCASE = "Example: L_F";

    public static final String FIRST_LETTER_NO_SPACE_LOWERCASE = "Example: lf";
    public static final String FIRST_LETTER_WITH_SPACE_LOWERCASE = "Example: l f";
    public static final String FIRST_LETTER_WITH_UNDERSCORE_LOWERCASE = "Example: l_f";

    public static final String FULL_NAME_NO_SPACE = "Example: LeftFront";
    public static final String FULL_NAME_WITH_SPACE = "Example: Left Front";
    public static final String FULL_NAME_WITH_UNDERSCORE = "Example: Left_Front";

    public static final String FULL_NAME_NO_SPACE_UPPERCASE = "Example: LEFTFRONT";
    public static final String FULL_NAME_WITH_SPACE_UPPERCASE = "Example: LEFT FRONT";
    public static final String FULL_NAME_WITH_UNDERSCORE_UPPERCASE = "Example: LEFT_FRONT";

//-------{SENSOR TYPES}-----------------------------------------------------------------------------
    public static final int COLOR_SENSOR  = 1;
    public static final int GYRO_SENSOR = 2;
    public static final int TOUCH_SENSOR = 3;
    public static final int ULTRASONIC_SENSOR = 4;
    public static final int OPTICAL_DISTANCE_SENSOR = 5;
    public static final int LIGHT_SENSOR = 6;
    public static final int IR_SEEKER_SENSOR = 7;
    public static final int ACCELERATION_SENSOR = 8;
    public static final int COMPASS_SENSOR = 9;
    public static final int VOLTAGE_SENSOR = 10;
    public static final int IMU = 11;

//-------{@IntDef}----------------------------------------------------------------------------------
    @IntDef({FORWARDS, REVERSE})
    @Retention(RetentionPolicy.CLASS)
    public @interface MotorDirections{}


    @IntDef({LEFT_FRONT,RIGHT_FRONT, RIGHT_BACK, LEFT_BACK})
    @Retention(RetentionPolicy.CLASS)
    public @interface MotorTag{}

    @IntDef({COLOR_SENSOR, GYRO_SENSOR, TOUCH_SENSOR, ULTRASONIC_SENSOR,
    OPTICAL_DISTANCE_SENSOR, LIGHT_SENSOR, IR_SEEKER_SENSOR, IMU,
    ACCELERATION_SENSOR, COMPASS_SENSOR, VOLTAGE_SENSOR,})
    @Retention(RetentionPolicy.CLASS)
    public @interface SensorTypes{}

//-------{@StringDef}-------------------------------------------------------------------------------
    @StringDef({FIRST_LETTER_NO_SPACE_UPPERCASE, FIRST_LETTER_WITH_SPACE_UPPERCASE, FIRST_LETTER_WITH_UNDERSCORE_UPPERCASE,
                FIRST_LETTER_NO_SPACE_LOWERCASE, FIRST_LETTER_WITH_SPACE_LOWERCASE, FIRST_LETTER_WITH_UNDERSCORE_LOWERCASE,
                FULL_NAME_NO_SPACE_UPPERCASE,    FULL_NAME_WITH_SPACE_UPPERCASE,    FULL_NAME_WITH_UNDERSCORE_UPPERCASE,
                FULL_NAME_NO_SPACE,              FULL_NAME_WITH_SPACE,              FULL_NAME_WITH_UNDERSCORE_UPPERCASE})
    @Retention(RetentionPolicy.CLASS)
    public @interface MotorNameTypes{}
}
