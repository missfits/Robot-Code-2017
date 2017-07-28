package org.usfirst.frc.team6418.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.GenericHID;
//import org.usfirst.frc.team6418.robot.Robot;
import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.SpeedController;

/**s
 *
 */
public class Winch extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	protected final int left;
	protected final int badMotor;
	private final RobotDrive drive;
	
	public Winch(int left,int badMotor){
		this.left = left;
		this.badMotor = badMotor;
		this.drive = new RobotDrive(left,badMotor);
	}
	
	public void driveForward(){
		this.drive.drive(0.5, 0.0);
	}
	
	public void stop(){
		this.drive.stopMotor();
	}
	
	public void tankDrive(double readValue, double placeHolderMotor){
		if(readValue<0)
			readValue=0;
		
		this.drive.tankDrive(readValue, 0);
	}
	/*
	protected DriveTrain(SpeedController left, SpeedController badMotor){
		this.left = left;
		this.badMotor = badMotor;
		this.drive = new RobotDrive(left,badMotor);
		
	}*/
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
