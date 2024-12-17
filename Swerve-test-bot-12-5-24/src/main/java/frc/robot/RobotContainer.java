// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.DriveTrain;
import frc.robot.Commands.RunGreenFlagCommand;
import frc.robot.Commands.RunRedFlagCommand;
import frc.robot.Commands.RunYellowFlagCommand;
import frc.robot.Commands.TelopSwerve;
import frc.robot.Constanst.JoystickConstants;
import frc.robot.Subsystems.*;

@Component
public class RobotContainer implements InitializingBean{

  /* Test mode choosers */
    /* Initail */
      private final SendableChooser<String> TestMode = new SendableChooser<>();
      private final String Xbox = "Use Xbox controller";
      private final String Input = "Use Inputs";
      private String TestModeSelected;

  /* Controllers */
    private final XboxController driver = new XboxController(JoystickConstants.DRIVER_USB);
    private final Joystick operator = new Joystick(JoystickConstants.OPERATOR_USB);

  /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, JoystickConstants.BACK_BUTTON);

  /* Subsystems */
    private final FlagsSubsystem flag = new FlagsSubsystem();
    private final TestMode test = new TestMode();

    @Autowired
    private LimelightSubsystem limelight;
    @Autowired
    private DriveTrain s_swerve;

  /* Pathplanner stuff */
    private SendableChooser<Command> autoChoosers;

  @Override
  public void afterPropertiesSet() throws Exception {
    
    /* Starting the Test Mode selectors*/
      test.start();
      TestMode.setDefaultOption("Use Xbox controller", Xbox);
      TestMode.addOption("Use Inputs", Input);
      TestModeSelected = TestMode.getSelected();
      SmartDashboard.putData(TestMode);
    autoChoosers = AutoBuilder.buildAutoChooser();
    double jiggle_count = SmartDashboard.getNumber("Advancer Jiggle Number Auto", 5);
      s_swerve.setDefaultCommand(
        new TelopSwerve(
          s_swerve,
          () -> -driver.getLeftY(),
          () -> -driver.getLeftX(), 
          () -> -driver.getRightX()
          )
      );
    
    
    configureBindings();

      /* Flags */
        NamedCommands.registerCommand("Green Flag", new RunGreenFlagCommand(flag));
        NamedCommands.registerCommand("Red Flag", new RunRedFlagCommand(flag));
        NamedCommands.registerCommand("Yellow Flag", new RunYellowFlagCommand(flag));

        SmartDashboard.putData("Auto Chooser", autoChoosers);
  }
  
  public void testPeriodic() {
   switch (TestModeSelected) {
        case Input:
          s_swerve.setDefaultCommand(
            new TelopSwerve(
              s_swerve,
              () -> test.translate(),
              () -> test.strafe(),
              () -> test.rotate()
              )
          );


          break;
      
        default:
          s_swerve.setDefaultCommand(
            new TelopSwerve(
              s_swerve,
              () -> -driver.getLeftY(),
              () -> -driver.getLeftX(), 
              () -> -driver.getRightX()
              )
          );

          break;
      }
  }

  private void configureBindings() {
    
    /* Driver Controls */
      zeroGyro.onTrue (new InstantCommand(() -> s_swerve.ResetDrives()));

    /* Operator Controls */
      /* Flag Controls */
        // new JoystickButton(operator, Constanst.JoystickConstants.GREEN_BUTTON).onTrue(new RunGreenFlagCommand(flag));
        // new JoystickButton(operator, Constanst.JoystickConstants.RED_BUTTON).onTrue(new RunRedFlagCommand(flag));
        // new JoystickButton(operator, Constanst.JoystickConstants.YELLOW_BUTTON).onTrue(new RunYellowFlagCommand(flag));
  }

  public Command getAutonomousCommand() {
    return autoChoosers.getSelected();
  }
}
