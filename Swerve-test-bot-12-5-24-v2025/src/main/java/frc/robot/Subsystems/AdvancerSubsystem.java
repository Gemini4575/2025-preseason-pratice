
// package frc.robot.Subsystems;

// import com.revrobotics.CANSparkBase;
// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkLowLevel.MotorType;

// import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constanst;

// public class AdvancerSubsystem extends SubsystemBase{
//     private CANSparkMax advancer;
//     public DigitalInput UpperBeam;
//     public DigitalInput LowerBeam;
//     private int advancerStep = 0;
//     private int jiggleStep = 0;
//     private int shootLag = 0;

//     public AdvancerSubsystem () {
//         advancer = new CANSparkMax (Constanst.AdvancerConstansts.ADVANCER_MOTOR, MotorType.kBrushless);
//         UpperBeam = new DigitalInput(Constanst.AdvancerConstansts.UPPER_ADDRESS_DIO);
//         LowerBeam = new DigitalInput(Constanst.AdvancerConstansts.LOWER_ADDRESS_DIO);
//         advancerStep = 0;
//         jiggleStep = 0;
//         // advancer.getEncoder().setPositionConversionFactor(1);
//         // advancer.getEncoder().setVelocityConversionFactor(1);
//     }
    
//     public boolean AdvanceToTop() {
//         if (UpperBeam.get()) {
//             advancer.set(Constanst.AdvancerConstansts.AdvanceToTop_UPPER_BEAM);
//         } else if (LowerBeam.get()) {
//             advancer.set(Constanst.AdvancerConstansts.AdvanceToTop_LOWER_BEAM);
//         } else {
//             advancer.set(0);
//             return true;
//         }

//         return false;
//     }
//     public boolean AlignToTop() {
//         boolean currentBeam = UpperBeam.get();
//         switch (advancerStep) {
//         case 0:
//             if (!currentBeam) {
//                 advancer.set(Constanst.AdvancerConstansts.AlignToTop_FALSE);
//             } else {
//                 advancer.set (0);
//                 advancerStep = 1;
//             }
//         break;
//         case 1:
//             if (currentBeam) {
//                 advancer.set(Constanst.AdvancerConstansts.AlignToTop_TRUE);
//             } else {
//                 advancer.set (0);
//                 advancerStep = 0;
//                 return true;
//             }
//         }

//         return (false);
//     }

//     public boolean AdvanceToShoot() {
// //       advancer.set((UpperBeam.get()? 0 : 1));
//         if (UpperBeam.get()) {
//             if (shootLag > 2) {
//                 advancer.set(0);
//                 return true;
//             }
//             shootLag++;
//         } else {
//             shootLag = 0;
//             advancer.set(Constanst.AdvancerConstansts.AdvanceToShoot);
//             return (false);
//         }
//         return false;
//     }

//     public boolean AdvanceToClear () {
//         advancer.set ((!UpperBeam.get()? 1 : 0));
//         return (!UpperBeam.get());
//     }

//      public boolean RetractToBottom () {
//         advancer.set ((LowerBeam.get()? 0.7 : 0.0));
//         return (!LowerBeam.get());
//     }

//      public boolean RetractToRelease () {
//         advancer.set (1);
//         return (LowerBeam.get());
//     }

//      public boolean Jiggle (double numCycles) {
//         if (jiggleStep < numCycles) {
//             advancer.set(0.2);
//         } else {
//             advancer.set(-0.2);
//         }

//         if (jiggleStep >= numCycles * 2) {
//             advancer.set (0);
//             jiggleStep = 0;
//             return true;
//         }

//         jiggleStep++;
//         return false;
//     }

//      public boolean JiggleVariable (int timePerStep, double numSteps) {
//         int curStep = jiggleStep/timePerStep;

//         if (jiggleStep % timePerStep == 0) {
//             advancer.set(0.2 * (Math.pow(-1,curStep)));
//         }

//         if (curStep >= numSteps) {
//             return true;
//         }

//         jiggleStep++;

//         return (false);
//     }

//     public boolean RingHeld (){
//         return (!LowerBeam.get() || !UpperBeam.get());
//     }
//     public boolean RingAtTop (){
//         return (!UpperBeam.get());
//     }
//     public boolean RingAtBottom (){
//         return (!LowerBeam.get());
//     }
//     public void DisplaySensors (){
//         SmartDashboard.putBoolean ("upper Beam Break", UpperBeam.get());
//         SmartDashboard.putBoolean ("lower Beam Break", LowerBeam.get());
//     }
//     public boolean Stop () {
//         advancer.set(0);

//         return (true);
//     }
//     public double RPM () {
//         var advancerVelo = advancer.getEncoder().getVelocity();
//         SmartDashboard.putNumber("Advancer RPM", advancerVelo);
//         SmartDashboard.putNumber("Advancer RPM geared", advancerVelo / 4);

//         return advancerVelo;
//     }
// }
