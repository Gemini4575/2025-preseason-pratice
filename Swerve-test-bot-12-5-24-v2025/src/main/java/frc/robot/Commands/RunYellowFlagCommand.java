package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.FlagsSubsystem;

public class RunYellowFlagCommand extends Command {
    private final FlagsSubsystem flagsSubsystem;

    public RunYellowFlagCommand(FlagsSubsystem flagsSubsystem) {
        this.flagsSubsystem = flagsSubsystem;
        addRequirements(flagsSubsystem);
    }

    @Override
    public void execute() {
        flagsSubsystem.YellowFlag();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        flagsSubsystem.YellowFlag();
    }
}