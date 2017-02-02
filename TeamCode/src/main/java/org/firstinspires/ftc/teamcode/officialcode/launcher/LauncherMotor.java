package org.firstinspires.ftc.teamcode.officialcode.launcher;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Class for creating the launcher motor
 */
public class LauncherMotor {
    //Declare launcher motor, initialize it with a constructor, and make a getter function for it
    DcMotor launcher;

    /**
     * constructor for initializing launcher motor
     * @param launcher
     */
    public LauncherMotor(DcMotor launcher){
        this.launcher = launcher;
        this.launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }//constructor

    /**
     * getter method for launcher motor
     * @return launcher
     */
    public DcMotor getLauncher(){
        return launcher;
    }//getLauncher
}//class
