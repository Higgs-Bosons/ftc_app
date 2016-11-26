package org.firstinspires.ftc.teamcode.officialcode.launcher;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.officialcode.configuration.Constants;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public class LauncherFactory {
    private static ILauncher launcher;

    public static synchronized ILauncher getInstance(OpMode opMode){
        if(launcher == null){
            LauncherMotor lm = getLauncherMotor(opMode);
            launcher = new Launcher(lm);
        }
        return launcher;
    }

    private static LauncherMotor getLauncherMotor(OpMode opMode){
        DcMotor launcher = opMode.hardwareMap.dcMotor.get(Constants.LAUNCHER_MOTOR);

        return new LauncherMotor(launcher);
    }
}
