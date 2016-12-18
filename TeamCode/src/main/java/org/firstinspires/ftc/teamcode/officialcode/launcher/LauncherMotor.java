package org.firstinspires.ftc.teamcode.officialcode.launcher;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class LauncherMotor {
    DcMotor launcher;

    public LauncherMotor(DcMotor launcher){
        this.launcher = launcher;
    }

    public DcMotor getLauncher(){
        return launcher;
    }
}
