package org.usfirst.frc.team6418.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team6418.robot.Robot;

/**
 *
 */
public class SetArcadeDrive extends Command {
	
	//Halie
	
	public SetArcadeDrive() {
		// Use requires() here to declare subsystem dependencies
		//requires(Robot.exampleSubsystem);	
		
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		System.out.println("ARCADE!");
		Robot.driveMode = "ARCADE";
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
