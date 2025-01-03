// package frc.robot.Subsystems;

// import com.revrobotics.CANSparkMax;
// // import com.revrobotics.spark.SparkMax;
// // import com.revrobotics.spark.SparkLowLevel.MotorType;

// import edu.wpi.first.wpilibj.CAN;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constanst;

// public class IntakeSubsystem extends SubsystemBase {
//     //private TalonFX intake;
//     private CANSparkMax intake;
//     private int jiggleStep = 0;

//     public IntakeSubsystem () {
//         intake = new CANSparkMax (Constanst.IntakeConstansts.INTAKE_MOTOR, com.revrobotics.CANSparkLowLevel.MotorType.kBrushless);
//         //intake = new TalonFX(intakeAddress);
//     }

//     public boolean Load () {        // not named intake because that's the constructor.
//         intake.set(Constanst.IntakeConstansts.INTAKE_LOAD_SPEED_NEO);  // -0.38 for kraken
//         // this is because we tested this so that the intake and advancer would be running at the same speed

//         return (true);
//     }
//     public boolean Outtake () {
//         intake.set(Constanst.IntakeConstansts.INTAKE_OUT_SPEED_NEO); // positive for kraken

//         return (true);
//     }

// 	public boolean SetToSpeed (double inSpeed) {
// 		intake.set(inSpeed);

// 		return true;
// 	}

//     public boolean Jiggle (int numCycles) {
//         if (jiggleStep < numCycles) {
//             intake.set(-Constanst.IntakeConstansts.JIGGLE_SPEED_NEO);
//         } else {
//             intake.set(Constanst.IntakeConstansts.JIGGLE_SPEED_NEO);
//         }

//         if (jiggleStep >= numCycles * 2) {
//             intake.set (0);
//             jiggleStep = 0;
//             return true;
//         }

//         jiggleStep++;
//         return false;
//     }


//     public boolean Stop (){
//         intake.set(0);

//         return (true);
//     }

//     public void RPM () {
//         var intakeVelo = intake.getEncoder().getVelocity();
//         SmartDashboard.putNumber("Intake RPM", intakeVelo);
//         SmartDashboard.putNumber("Intake RPM geared", (16.0/24.0) * intakeVelo);

//         /* for Kraken 
//         SmartDashboard.putNumber("Intake RPM", intake.getRotorVelocity().getValueAsDouble() * 60);
//         SmartDashboard.putNumber("Intake RPM geared", (16.0/24.0) * (intake.getRotorVelocity().getValueAsDouble() * 60));
//         */
//     }
// }
