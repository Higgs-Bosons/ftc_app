package org.firstinspires.ftc.teamcode.officialcode.servos;

/**
 * Interface for servos
 */
public interface IServos {
    void handleMessage() throws InterruptedException;
    void setState(Object state);
}//class
