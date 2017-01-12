package org.firstinspires.ftc.teamcode.officialcode.launcher;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Class for creating the launcher motor
 */
public class LauncherMotor {
    //Declare launcher motor, initialize it with a constructor, and make a getter function for it
    DcMotor launcher;

    public LauncherMotor(DcMotor launcher){
        this.launcher = launcher;
    }

    public DcMotor getLauncher(){
        return launcher;
    }
}
