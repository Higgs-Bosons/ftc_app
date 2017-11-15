package org.firstinspires.ftc.teamcode.officialcode;

import android.support.annotation.*;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.officialcode.Robot.DriveMotors;

import java.lang.annotation.*;

public class IntStringDefInterfaces {
    @StringDef({Constants.BLUE, Constants.RED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RedOrBlue{}

    @IntDef({Constants.Backwards, Constants.Forwards, Constants.Glide})
    @Retention(RetentionPolicy.SOURCE)
    public @interface rection{}
}
