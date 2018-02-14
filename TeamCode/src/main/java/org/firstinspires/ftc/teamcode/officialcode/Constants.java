package org.firstinspires.ftc.teamcode.officialcode;

public class Constants {
    public static final String RED = "RED";
    public static final String BLUE = "BLUE";

    final static int Forwards = 1;
    final static int Backwards = -1;

    public final static RobotDirection N = new RobotDirection(0,-1);
    public final static RobotDirection S = new RobotDirection(0,1);
    public final static RobotDirection E = new RobotDirection(1,0);
    public final static RobotDirection W = new RobotDirection(-1,0);
    public final static RobotDirection NW = new RobotDirection(-0.8,-0.8);
    public final static RobotDirection SW = new RobotDirection(-0.8,0.8);
    public final static RobotDirection NE = new RobotDirection(0.8,-0.8);
    public final static RobotDirection SE = new RobotDirection(0.8,0.8);

    public static final String ArmLifter = "ARM LIFTER";
    public static final String SlideExtender = "Slide Extender";
    public static final String SlideRetracter = "Slide Retracter";
    public static final String Conveyor = "CONVEYOR";

    public static final String RightFront = "RightFront";
    public static final String LeftFront = "LeftFront";
    public static final String RightBack = "RightBack";
    public static final String LeftBack = "LeftBack";

    public final static int Wheel_Diameter = 4; // INCHES
    public final static int Motor_Tick_Per_Rotation = 1150;

    public static final String FishTailLifter = "Fish Tail Lifter";
    public static final String FishTailSwinger = "Fish Tail Swinger";
    public static final String GrabberOne = "GrabberOne";
    public static final String GrabberTwo = "GrabberTwo";
    public static final String GrabberSpinOne = "GrabberSpinOne";
    public static final String GrabberSpinTwo = "GrabberSpinTwo";
    public static final String Clampy = "Clampy";
    public static final String RML = "RML";
    public static final String Lifter = "Lifter";

    public static final String VUFORIA_KEY ="AV0wWub/////AAAAGSKQHdIYCUOUg23YaF6tD9iJGTKb6AvM5+agdRd" +
            "qaxaBKUaNM6IktQg+50ag4j03QdDbsGNhBZwjWpdsU+kQA7EG+aaAhgKqWpzVQlvuC0320Hy8aQZTgVegtu3el9" +
            "rly5X2CeDuM3fzhdeVOmOCwUWviYbH+6GtFlXCWOrX3i09Roe4GOTLeG7sBR7Br28I0hLTRKiwalhFtkr/IRjJT" +
            "KvdL3CQGWhY+8Q30BTEhbYxA18d88OtgZMO712LNfRnD2btkxQjEFKdND+sGo+AovdwCsVCQY/6xmyZSAhi4Fva" +
            "nKtdgHFbdOrUp7MCkoA0CVh2kQfvulGLQGp/Zx3WivkYZ3+euoVTbzjcYo6721C1";

    public static class RobotDirection{
        private double X;
        private double Y;
        public RobotDirection(double X, double Y){
            this.X = X;
            this.Y = Y;
        }
        public double getX(){return this.X;}
        public double getY(){return this.Y;}
    }

}
