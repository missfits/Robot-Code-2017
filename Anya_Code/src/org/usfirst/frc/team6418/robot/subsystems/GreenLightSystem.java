package org.usfirst.frc.team6418.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalOutput;
/**
 *
 */
public class GreenLightSystem extends Subsystem 
{
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	DigitalOutput dio = new DigitalOutput(0);
	
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		dio.set(false);
	
	}
	
	
	public void greenToggle() {
		boolean state = dio.get();
		System.out.println("we just got the channel to be t/f"+state);
		dio.set(!state);//toggle on/off
	}
	
	
	
}