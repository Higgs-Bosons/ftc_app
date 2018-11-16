package org.firstinspires.ftc.teamcode.Robot;

public class customErrors {
    public static class DuplicateTagException extends RuntimeException{
        @Override
        public String getMessage(){
            return "You tried to use a tag that was already in use. That tag is non-repeatable.";
        }
        DuplicateTagException() {
        }
    }
    public static class DuplicateNameException extends RuntimeException{
        @Override
        public String getMessage(){
            return "You tried to name a motor/sensor/servo the same name as a previous motor/sensor/servo. Please change the name.";
        }
        DuplicateNameException(){}
    }
}
