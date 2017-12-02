package org.firstinspires.ftc.teamcode.officialcode;

import android.support.annotation.*;

import org.firstinspires.ftc.teamcode.officialcode.Robot.Servos;

import java.lang.annotation.*;

public class IntStringDefInterfaces {
    @IntDef({Constants.Backwards, Constants.Forwards})
    @Retention(RetentionPolicy.SOURCE)
    public @interface rection{}

    @StringDef({Servos.Grabby,Servos.JackSmith,Servos.FishTail})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ServoName{}
}
