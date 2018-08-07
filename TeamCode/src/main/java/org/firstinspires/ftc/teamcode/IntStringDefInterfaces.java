package org.firstinspires.ftc.teamcode.officialcode;

import android.support.annotation.*;


import java.lang.annotation.*;

public class IntStringDefInterfaces {
    @IntDef({Constants.Backwards, Constants.Forwards})
    @Retention(RetentionPolicy.SOURCE)
    public @interface rection{}
/*
    @StringDef({Servos.FishTail,Servos.FishTail})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OmniServoName{}
    */
}
