package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TestMode extends SubsystemBase{
    double speed;
    double translate;
    double strafe;
    double rotate;
    /* Test mode choosers */
        /* Rotaion */
        private final SendableChooser<String> RotatingOrStrafeOrStrait = new SendableChooser<>();
        private final String Rotating = "true";
        private final String Strafe = "Strafe";
        private final String Strait = "Strait";
        private String RotatingSelectedOrStrafeOrStrait;

    public TestMode() {
        /* Starting the Test Mode selectors */
            /* Rotaion */
                RotatingOrStrafeOrStrait.addOption("True", Rotating);
                RotatingOrStrafeOrStrait.setDefaultOption("Strait", Strait);
                RotatingOrStrafeOrStrait.addOption("Strafe", Strafe);
                RotatingSelectedOrStrafeOrStrait = RotatingOrStrafeOrStrait.getSelected();
        
    }
    /**
     * You should call this method at your constuctor for your {@Link RobotContainer}
     * as it publishes key {@link SendableChooser}s to the {@link SmartDashboard}
     * 
     */
    public void start() {
        SmartDashboard.putData("Rotate or Strafe or strait?", RotatingOrStrafeOrStrait);
        SmartDashboard.putNumber("Speed", 0);
    }

    @Override
    public void periodic() {
        speed = SmartDashboard.getNumber("Speed", 0);

        switch (RotatingSelectedOrStrafeOrStrait) {
            case Rotating:
                rotate = speed;
            break;
        
            case Strafe:
                strafe = speed;
            break;
        
            default:
                translate = speed;
            break;
        }


    }

    public double translate() {
        return translate;
    }

    public double rotate() {
        return rotate;
    }

    public double strafe() {
        return strafe;
    }
}
