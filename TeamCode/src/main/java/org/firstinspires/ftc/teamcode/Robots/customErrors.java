package org.firstinspires.ftc.teamcode.Robots;

public class customErrors {
    public static class MotorNotFoundException extends Exception{
        String motorName;
        @Override
        public String getMessage(){
            return " Could not find a motor named: "+ motorName+".";
        }
        MotorNotFoundException(String motorName) {
            this.motorName = motorName;
        }
    }
    public static class DuplicateTagException extends Exception{

        @Override
        public String getMessage(){
            return " I found more than one motor with the same tag.";
        }
        DuplicateTagException() {
        }
    }
    public static class DuplicateNameException extends Exception{

        @Override
        public String getMessage(){
            return " I found more than one motor with the same name.";
        }
        DuplicateNameException() {
        }
    }
}
