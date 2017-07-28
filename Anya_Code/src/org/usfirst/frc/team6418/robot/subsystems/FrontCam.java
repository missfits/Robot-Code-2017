package org.usfirst.frc.team6418.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class FrontCam extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	
	UsbCamera cam0;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		
	}
	
	public void turnCameraOn() {
		System.out.println("turning cam0 on... ");
		cam0 = CameraServer.getInstance().startAutomaticCapture(0);
	}
	
	
//	make a method to turn the other camera off
   /**
	   * Start automatically capturing images to send to the dashboard.
	   *
	   * @param name The name to give the camera
	   * @param dev The device number of the camera interface
	   */
	/*
	  public UsbCamera startAutomaticCapture(String name, int dev) {
	    UsbCamera camera = new UsbCamera(name, dev);
	    startAutomaticCapture(camera);
	    return camera;
	  }
	*/
//	and then 
	  /**
	   * Removes a camera by name.
	   *
	   * @param name Camera name
	   */
/*	  public void removeCamera(String name) {
	    synchronized (this) {
	      m_sources.remove(name);
	    }
	  }

	to remove the camera? 
*/	
}
