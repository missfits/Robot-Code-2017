package org.usfirst.frc.team6418.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BackCamSubsystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	
	UsbCamera cam1;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		
	}
	
	public void turnCameraOn() {
		System.out.println("turning cam1 on... ");
		cam1 = CameraServer.getInstance().startAutomaticCapture(1);
	}
	
	
	
}
