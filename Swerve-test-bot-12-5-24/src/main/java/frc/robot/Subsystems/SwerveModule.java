// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
// import com.revrobotics.spark.SparkSim;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.util.SwerveModuleConstants;
import frc.robot.Constanst;

public class SwerveModule extends Command {

  

  private final CANSparkMax m_driveMotor;
  private final CANSparkMax m_turningMotor;
  // private final SparkSim driveMotorSim;


  private final RelativeEncoder m_driveEncoder;
  private final AnalogInput m_turningEncoder;

  private double ahhhhhhhhhhh = 0.0;
  private int iii = 1;

  private double encoderOffset = 0;
  
  private int moduleNumber = 0;
  private final RelativeEncoder m_turningEncoderREV;

  // Gains are for example purposes only - must be determined for your own robot!
  private final ProfiledPIDController m_drivePIDController = new ProfiledPIDController(
    .0001, 
    0.0,
    0,
    new TrapezoidProfile.Constraints(15.1,18.8)
    );

  // Gains are for example purposes only - must be determined for your own robot!
  private final ProfiledPIDController m_turningPIDController =
      new ProfiledPIDController(
          16.7 ,//16.7
          0,
          0,
          new TrapezoidProfile.Constraints(
              Constanst.SwerveConstants.kModuleMaxAngularVelocity, Constanst.SwerveConstants.kModuleMaxAngularAcceleration));

  // Gains are for example purposes only - must be determined for your own robot!
  private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(1, 1.5); // this was 3, changed to 1.5 because it was driving too far
  private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(1, 0.5);

  /**
   * Constructs a SwerveModule with a drive motor, turning motor and turning encoder.
   *
   * @param driveMotorChannel PWM output for the drive motor.
   * @param turningMotorChannel PWM output for the turning motor.
   * @param turningEncoderChannelA DIO input for the turning encoder channel A
   */
  public SwerveModule(SwerveModuleConstants moduleConstants) {
    SmartDashboard.putNumber("tueing", ahhhhhhhhhhh);
    m_driveMotor = new CANSparkMax(moduleConstants.driveMotorID, MotorType.kBrushless);
    m_driveMotor.setInverted (true);
    // driveMotorSim = new SparkSim(m_driveMotor, DCMotor.getNEO(1));
    m_turningMotor = new CANSparkMax(moduleConstants.angleMotorID, MotorType.kBrushless);
    m_turningMotor.setInverted (true);

    m_driveEncoder = m_driveMotor.getEncoder();
    m_turningEncoder = new AnalogInput(moduleConstants.cancoderID);

    m_turningEncoderREV = m_turningMotor.getEncoder();

    // Set the distance per pulse for the drive encoder. We can simply use the
    // distance traveled for one rotation of the wheel divided by the encoder
    // resolution.
    /*m_driveEncoder.setPositionConversionFactor(driveAfterEncoderReduction);
    m_driveEncoder.setVelocityConversionFactor(driveAfterEncoderReduction / 60.0);

    m_turningEncoderREV.setPositionConversionFactor(turnAfterEncoderReduction);
*/
    // Set the distance (in this case, angle) in radians per pulse for the turning encoder.
    // This is the the angle through an entire rotation (2 * pi) divided by the
    // encoder resolution.
//    m_turningEncoder.setDistancePerRotation(-2 * Math.PI);

    moduleNumber = moduleConstants.cancoderID;

    // Limit the PID Controller's input range between -pi and pi and set the input
    // to be continuous.
    m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);

        // load the encoder offset
//    encoderOffset = Preferences.getDouble("encoder" + moduleNumber, encoderOffset);
// hard coding the offset because its better?
switch (moduleNumber) {
  case 0: 
  encoderOffset = 3.769512307;//3.201315307;
  break;
  case 1: 
  encoderOffset = 0.6210603266;// 6.9042456338;//5.333449307 + (Math.PI/2);
  break;
  case 2: 
  encoderOffset = 4.9206722876;//0.2082833072 - (Math.PI/2);
  break;
  case 3: 
  encoderOffset = 3.201315307;//3.769512307;
  break;
}
  }

  // public SparkSim getDriveMotorSim() {
  //   return driveMotorSim;
  // }

  private double encoderValue () {
    var retVal =  m_turningEncoder.getVoltage() / RobotController.getVoltage5V(); // convert voltage to %
    retVal = 2.0 * Math.PI * retVal;    // get % of circle encoder is reading
    //SmartDashboard.putNumber("module " + moduleNumber, retVal);
    if(RobotState.isTest()){
SmartDashboard.putNumber("encoder raw " + moduleNumber, retVal);

    
 SmartDashboard.putNumber("encoder " + moduleNumber, (retVal * 1000) / 1000.0);
 SmartDashboard.putNumber("encoder degrees " + moduleNumber, (retVal *(180/Math.PI) * 1000) / 1000.0);
    }
    retVal = (retVal + encoderOffset) % (2.0 * Math.PI);    // apply offset for this encoder and map it back onto [0, 2pi]
      // might need this so we're in the same range as the pid controller is expecting.
//    retVal = retVal - Math.PI;
    return (retVal);
}

  public void resetEncoder(){
    m_driveMotor.getEncoder().setPosition(0.0);

        // get the turning encoder and write it to preferences
    encoderOffset = m_turningEncoder.getVoltage() / RobotController.getVoltage5V(); // convert voltage to %
    encoderOffset = 2.0 * Math.PI * encoderOffset;    // get % of circle encoder is reading
    encoderOffset = (2.0 * Math.PI) - encoderOffset;
//    Preferences.initDouble("encoder" + moduleNumber, encoderOffset);
    Preferences.setDouble("encoder" + moduleNumber, encoderOffset);
  }
  /**
   * Returns the current state of the module.
   *
   * @return The current state of the module.
   */
  public SwerveModuleState getState() {
   
    return new SwerveModuleState(
        m_driveEncoder.getVelocity(), new Rotation2d(encoderValue()));
  }

  /**
   * Returns the current position of the module.
   *
   * @return The current position of the module.
   */
  public SwerveModulePosition getPosition() {
    return new SwerveModulePosition(
        m_driveEncoder.getPosition(), new Rotation2d(encoderValue()));
  }

  /**
   * Sets the desired state for the module.
   *
   * @param desiredState Desired state with speed and angle.
   */
  public void setDesiredState(SwerveModuleState desiredState) {
    // Optimize the reference state to avoid spinning further than 90 degrees
    SwerveModuleState state =
        SwerveModuleState.optimize(desiredState, new Rotation2d(encoderValue()));

    // Calculate the drive output from the drive PID controller.

      /* this bit from the example uses driveencoder.getrate() - Get the current rate of the 
       * encoder. Units are distance per second as scaled by the value from setDistancePerPulse().
       * rev encoder doesn't have this value only 
       * .getVelocity - Get the velocity of the motor. This returns the native units of 'rotations per second'
       *  by default, and can be changed by a scale factor using setVelocityConversionFactor().
       */

    final 
    double driveOutput =
        m_drivePIDController.calculate(m_driveEncoder.getVelocity(), state.speedMetersPerSecond);

    final double driveFeedforward = m_driveFeedforward.calculate(state.speedMetersPerSecond);
    // Calculate the turning motor output from the turning PID controller.
    final double turnOutput =
        m_turningPIDController.calculate(encoderValue(), state.angle.getRadians());
// SmartDashboard.putNumber("pid " + moduleNumber, turnOutput);
 
    SmartDashboard.putNumber("Setpoint velocity", m_turningPIDController.getSetpoint().velocity);
    final double turnFeedforward =
        m_turnFeedforward.calculate(m_turningPIDController.getSetpoint().velocity);
    SmartDashboard.putNumber("turnFeedforward",turnFeedforward);
    if(RobotState.isAutonomous()) {
      m_driveMotor.setVoltage((driveFeedforward));
      System.out.println("Output: " + driveOutput + " Feedforward: " + driveFeedforward);
      m_turningMotor.setVoltage(turnOutput + turnFeedforward);
    } else if (RobotState.isTeleop()) {
      m_driveMotor.set((driveOutput + driveFeedforward) / 2.1);
      m_turningMotor.setVoltage(turnOutput + turnFeedforward);
    }
    
    if (ahhhhhhhhhhh < SmartDashboard.getNumber("tueing" , 0) || iii == 3000) {
      ahhhhhhhhhhh = (turnOutput + turnFeedforward);
      SmartDashboard.putNumber("tueing", (turnOutput + turnFeedforward));
      iii ++;
    }
    SmartDashboard.putNumber("turnOutput",turnOutput);
     SmartDashboard.putNumber("Drive", (driveOutput + driveFeedforward) /2.1);
    SmartDashboard.putNumber("Turning stuff", Math.max(turnOutput, turnFeedforward));
    if(RobotState.isTest()) {
     
      SmartDashboard.putNumber("Turning stuff", turnOutput + turnFeedforward);
      SmartDashboard.putNumber("target " + moduleNumber, state.angle.getRadians());
    }
    
  }
  
}
