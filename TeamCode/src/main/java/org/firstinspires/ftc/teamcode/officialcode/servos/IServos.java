package org.firstinspires.ftc.teamcode.officialcode.servos;

/**
 * Created by Higgs Bosons on 11/23/2016.
 */
public interface IServos {
    void handleMessage() throws InterruptedException;
    void setState(Object state);
}
