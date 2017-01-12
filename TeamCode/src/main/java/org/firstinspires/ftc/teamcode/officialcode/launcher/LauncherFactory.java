package org.firstinspires.ftc.teamcode.officialcode.launcher;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Factory for launcher
 */
public class LauncherFactory {
    //launcher interface declaration
    private static ILauncher launcher;

    /**
     * initialize interface to launcher motors if it is null
     * @param opMode
     * @return
     */
    public static synchronized ILauncher getInstance(OpMode opMode){
        if(launcher == null){
            LauncherMotor lm = getLauncherMotor(opMode);
            launcher = new Launcher(lm);
        }
        return launcher;
    }//getInstance

    /**
     * hardware map and return launcher motors
     * @param opMode
     * @return
     */
    private static LauncherMotor getLauncherMotor(OpMode opMode){
        DcMotor launcher = opMode.hardwareMap.dcMotor.get(Constants.LAUNCHER_MOTOR);

        return new LauncherMotor(launcher);
    }//getLauncherMotor
}
