package org.firstinspires.ftc.teamcode.Galaxy.Autonomus;

public class Test {


    class Autonomus implements Runnable {

        public void runAutonomous() {
            new Thread(this).start();
        }
        public void run() {

        }
    }
}
