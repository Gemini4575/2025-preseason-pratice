package frc.robot.Subsystems;


import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constanst;

public class FlagsSubsystem extends SubsystemBase {
    SparkMax GreenFlag;
    SparkMax RedFlag;
    SparkMax YellowFlag;
  /** Creates a new ExampleSubsystem. */
  public FlagsSubsystem() {
    GreenFlag = new SparkMax(Constanst.FlagsConstants.GreenFlagMotor, MotorType.kBrushless);
    RedFlag = new SparkMax(Constanst.FlagsConstants.RedFlagMotor, MotorType.kBrushless);
    YellowFlag = new SparkMax(Constanst.FlagsConstants.YellowFlagMotor, MotorType.kBrushless);
  }
  
  public void RunGreenFlag() {
    GreenFlag.set(Constanst.FlagsConstants.GreenFlagMotor);
  }

  public void RunRedFlag() {
    RedFlag.set(Constanst.FlagsConstants.RedFlagMotor);
  }

  public void YellowFlag() {
    YellowFlag.set(Constanst.FlagsConstants.YellowFlagMotor);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  
}