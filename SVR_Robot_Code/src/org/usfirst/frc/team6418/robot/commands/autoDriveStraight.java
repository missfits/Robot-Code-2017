package org.usfirst.frc.team6418.robot.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team6418.robot.Robot;

/**
 *
 */
public class autoDriveStraight extends Command {
	
	protected double numFeet;
	protected double encTicsFromFeet;
	
	public autoDriveStraight(double feetToDrive) {
		// Use requires() here to declare subsystem dependencies
		//requires(Robot.exampleSubsystem);
		numFeet = feetToDrive;
		encTicsFromFeet = numFeet*12/(6*Math.PI)*360/2400;
		requires(Robot.driveTrain);
		//it's not a subsystem, so it won't require the driveTrain
		//we need the encoder methods and data for this command
		requires(Robot.leftEncoder);
		requires(Robot.rightEncoder);
	
	}
	private void requires(Encoder leftEncoder) {
		// TODO Auto-generated method stub
		
	}
	private void requires(RobotDrive driveTrain) {
		// TODO Auto-generated method stub
		
	}
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		/*
		if (leftEncoder.getDistance() < encTicsFromFeet) {
			//feet: 
			left.set(.3);
			right.set(.3);
		
		}
		
		*/
	}
	
	

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		//return (leftEncoder.getDistance() < encTicsFromFeet);
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	//	left.set(0);
	//	right.set(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
	

	
	
	
}
