package frc.robot.Commands;

import frc.robot.Subsystems.FlagsSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class RunGreenFlagCommand extends Command {
    private final FlagsSubsystem flagsSubsystem;

    public RunGreenFlagCommand(FlagsSubsystem flagsSubsystem) {
        this.flagsSubsystem = flagsSubsystem;
        addRequirements(flagsSubsystem);
    }

    @Override
    public void execute() {
        flagsSubsystem.RunGreenFlag();
    }

    @Override
    public boolean isFinished() {
        return false;  // You can add a condition to stop it if needed
    }

    @Override
    public void end(boolean interrupted) {
        flagsSubsystem.RunGreenFlag();  // Stop the motor if needed (or leave it running)
    }
}



