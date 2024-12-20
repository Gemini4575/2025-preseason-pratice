package frc.robot.Commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constanst;


public class TelopSwerve extends Command {    
    private DriveTrain s_Swerve;    
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private boolean isFinished;
    double i = 0.0;
    /**
     * This is the command that dirves your swerve robot. When setting this up in robot container
     * use the {@link DriveTrain#setDefaultCommand}.
     * When putting in your paramenters use lamda code i.e. () -> {@link DoubleSupplier}, 
     * instead of just {@link DoubleSupplier}.
     * 
     * @param s_Swerve you drivetrain subsystem
     * @param translationSup the axies that controlls the foward/backwards movment of you robot
     * @param strafeSup the axies that controlls the side to side movment of you robot
     * @param rotationSup the axies that controlls the angle of your robot
     */
    public TelopSwerve(DriveTrain s_Swerve, DoubleSupplier translationSup, DoubleSupplier strafeSup, 
    DoubleSupplier rotationSup) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);
        isFinished = false;
        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
    }

    @Override
    public void execute() {
        isFinished = false;
        String mode = "normal";
        // double teleOpMaxSpeed = Constants.Swerve.maxSpeed;
        // double teleOpMaxAngularVelocity = Constants.Swerve.maxAngularVelocity;

        /*speedMulti = teleOpMaxSpeed * .90;
        rotMulti = teleOpMaxAngularVelocity * .90;

        if (slowMode.getAsBoolean()) {
            speedMulti = slowSpeed * teleOpMaxSpeed;
            rotMulti = slowRotation * teleOpMaxAngularVelocity;
            mode="slow";
        }

        if (middleMode.getAsBoolean()) {
            speedMulti = middleSpeed * teleOpMaxSpeed;
            rotMulti = middleRotation * teleOpMaxAngularVelocity;
            mode="middle";
        }
*/

        /* Get Values, Deadband*/
        double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), Constanst.stickDeadband);
        double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), Constanst.stickDeadband);
        double rotationVal = MathUtil.applyDeadband(rotationSup.getAsDouble(), Constanst.stickDeadband);

        // if(autoTargeting.getAsBoolean()) {
        //     rotationVal = targetingUtil.calculateRotation();
        //     rotMulti = teleOpMaxAngularVelocity;
        //     mode="auto";
        // }


        /* Drive */
        s_Swerve.drive(
           strafeVal,
           translationVal,
           rotationVal,
           true
        );
        SmartDashboard.putString("swerve mode", mode);
    }

    @Override
    public void end(boolean interupted) {}

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}