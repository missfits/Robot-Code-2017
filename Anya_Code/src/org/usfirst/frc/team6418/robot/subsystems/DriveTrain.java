package org.usfirst.frc.team6418.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.GenericHID;
//import org.usfirst.frc.team6418.robot.Robot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

/**s
 *
 */
public class DriveTrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	protected final SpeedController left;
	protected final SpeedController right;
	private final RobotDrive drive;
	/*
	private final RobotDrive leftDrive;
	private final RobotDrive rightDrive;
	*/
	public DriveTrain(SpeedController left, SpeedController right){
		this.left = left;
		this.right = right;
		this.drive = new RobotDrive(left,right);
		/*
		this.leftDrive=new RobotDrive(left,right);
		this.rightDrive=new RobotDrive(right);
		*/
	}
	
	public void driveForward(){
		this.drive.drive(0.5, 0.0);
	}
	
	public void stop(){
		this.drive.stopMotor();
	}
	
	public void arcadeDrive(double x, double y){
		this.drive.arcadeDrive(x, y);
	}
	
	public void tankDrive(double x, double y){
		this.drive.tankDrive(x, y);
		//this.leftDrive.tankDrive(x,y);
		//this.rightDrive.tankDrive(x,y);
	}

	/*
	protected DriveTrain(SpeedController left, SpeedController right){
		this.left = left;
		this.right = right;
		this.drive = new RobotDrive(left,right);
		
	}*/
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
