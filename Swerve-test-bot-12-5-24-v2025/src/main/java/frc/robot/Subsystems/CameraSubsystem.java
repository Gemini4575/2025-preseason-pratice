// package frc.robot.Subsystems;

// import org.opencv.core.Mat;

// import edu.wpi.first.cameraserver.CameraServer;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constanst;

// public class CameraSubsystem extends SubsystemBase {

//     Mat frame = new Mat(2, 2, 0);

//     public CameraSubsystem(){
        
//     }

//     public void FindRing() {
        
//     }
//     @Override
//     public void periodic() {
//                 CameraServer.getVideo(CameraServer.startAutomaticCapture(Constanst.CameraConstants.CAM_ONE)).grabFrame(frame);

//         SmartDashboard.putNumberArray(getName(), frame.get(0, 0));
//     }

// }
