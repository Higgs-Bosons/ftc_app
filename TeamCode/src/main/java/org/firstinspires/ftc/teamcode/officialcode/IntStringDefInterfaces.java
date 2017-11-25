package org.firstinspires.ftc.teamcode.officialcode;

import android.support.annotation.*;

import java.lang.annotation.*;

public class IntStringDefInterfaces {
    @StringDef({Constants.BLUE, Constants.RED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RedOrBlue{}

    @IntDef({Constants.Backwards, Constants.Forwards})
    @Retention(RetentionPolicy.SOURCE)
    public @interface rection{}
}
