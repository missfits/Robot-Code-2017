package org.usfirst.frc.team6418.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.AnalogInput;
/**
 *
 */
public class UltraSonic extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private AnalogInput AI;
	
	public UltraSonic(int port){
		this.AI = new AnalogInput(port);
	}
	
	public double getVoltage(){
		return this.AI.getAverageVoltage();
	}
	
	public double getDistance(){
		return this.AI.getVoltage()*0.977;
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
