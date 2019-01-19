package org.firstinspires.ftc.teamcode.Galaxy;

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
    public static final int DISTANCE_SENSOR = 12;

//-------{SENSOR READING TAG}-----------------------------------------------------------------------
    //public static final int NO_TAG = -1; - You can use the variable NO_TAG for above (line 12)
    public static final int COLOR_RED = 0;
    public static final int COLOR_GREEN = 1;
    public static final int COLOR_BLUE = 2;
    public static final int COLOR_RGB = 3;
    public static final int GYRO_X = 4;
    public static final int GYRO_Y = 5;
    public static final int GYRO_Z = 6;
    public static final int TOUCH_VALUE = 7;
    public static final int TOUCH_BOOLEAN = 8;
    public static final int IR_SEEKER_ANGLE = 9;
    public static final int IR_SEEKER_STRENGTH = 10;
    public static final int ACCELERATION_X = 11;
    public static final int ACCELERATION_Y = 12;
    public static final int ACCELERATION_Z = 13;
    public static final int DISTANCE_IN_INCHES = 14;
    public static final int DISTANCE_IN_CENTIMETERS = 15;
    public static final int DISTANCE_IN_METERS = 16;
    public static final int DISTANCE_IN_MILLIMETERS = 17;

//-------{Directions}-------------------------------------------------------------------------------
    public static final double NORTH = 0;
    public static final double EAST = 90;
    public static final double SOUTH = 180;
    public static final double WEST = 270;

//-------{MENU OPTIONS}-----------------------------------------------------------------------------
    public static final String CRATER_ON_THE_LEFT = "  LEFT";
    public static final String CRATER_ON_THE_RIGHT = "  RIGHT";
    public static final String LEFT_SIDE_OF_THE_LANDER = "  LEFT";
    public static final String RIGHT_SIDE_OF_THE_LANDER = "  RIGHT";

//-------{ORIENTATIONS}-----------------------------------------------------------------------------
    public static final int UPRIGHT = 0;
    public static final int UPSIDE_DOWN = 1;
    public static final int LANDSCAPE_LEFT = 2;
    public static final int LANDSCAPE_RIGHT = 3;

//-------{AI CONTROL MODES}-------------------------------------------------------------------------
    public static final String ON = "ON";
    public static final String OFF = "OFF";
    public static final String LIMITED = "LIMITED";

//-------{@IntDef}----------------------------------------------------------------------------------
    @IntDef({FORWARDS, REVERSE})
    @Retention(RetentionPolicy.CLASS)
    public @interface MotorDirections{}


    @IntDef({LEFT_FRONT,RIGHT_FRONT, RIGHT_BACK, LEFT_BACK, NO_TAG})
    @Retention(RetentionPolicy.CLASS)
    public @interface MotorTag{}


    @IntDef({COLOR_SENSOR, GYRO_SENSOR, TOUCH_SENSOR, ULTRASONIC_SENSOR,
    OPTICAL_DISTANCE_SENSOR, LIGHT_SENSOR, IR_SEEKER_SENSOR, IMU,
    ACCELERATION_SENSOR, COMPASS_SENSOR, VOLTAGE_SENSOR, DISTANCE_SENSOR})
    @Retention(RetentionPolicy.CLASS)
    public @interface SensorTypes{}


    @IntDef({ IR_SEEKER_ANGLE,     ACCELERATION_X,   GYRO_Z,   COLOR_RED,      TOUCH_BOOLEAN,
              IR_SEEKER_STRENGTH,  ACCELERATION_Y,   GYRO_Y,   COLOR_BLUE,     TOUCH_VALUE,
              COLOR_RGB,           ACCELERATION_Z,   GYRO_X,   COLOR_GREEN,    DISTANCE_IN_CENTIMETERS,
              DISTANCE_IN_INCHES,  DISTANCE_IN_METERS, DISTANCE_IN_MILLIMETERS, NO_TAG})
    @Retention(RetentionPolicy.CLASS)
    public @interface ReadingTags{}


    @IntDef({UPRIGHT,UPSIDE_DOWN,LANDSCAPE_LEFT, LANDSCAPE_RIGHT})
    @Retention(RetentionPolicy.CLASS)
    public @interface CameraOrientations{}


    @IntDef({UPRIGHT,UPSIDE_DOWN})
    @Retention(RetentionPolicy.CLASS)
    public @interface IMUOrientations{}

//-------{@StringDef}-------------------------------------------------------------------------------
    @StringDef({FIRST_LETTER_NO_SPACE_UPPERCASE, FIRST_LETTER_WITH_SPACE_UPPERCASE, FIRST_LETTER_WITH_UNDERSCORE_UPPERCASE,
                FIRST_LETTER_NO_SPACE_LOWERCASE, FIRST_LETTER_WITH_SPACE_LOWERCASE, FIRST_LETTER_WITH_UNDERSCORE_LOWERCASE,
                FULL_NAME_NO_SPACE_UPPERCASE,    FULL_NAME_WITH_SPACE_UPPERCASE,    FULL_NAME_WITH_UNDERSCORE_UPPERCASE,
                FULL_NAME_NO_SPACE,              FULL_NAME_WITH_SPACE,              FULL_NAME_WITH_UNDERSCORE_UPPERCASE})
    @Retention(RetentionPolicy.CLASS)
    public @interface MotorNameTypes{}
}
