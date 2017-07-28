package org.usfirst.frc.team6418.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team6418.robot.Robot;

/**
 *
 */
public class MoveForward extends Command {
	public MoveForward() {
		// Use requires() here to declare subsystem dependencies
//		requires(Robot.driveTrain);
		requires(Robot.s1);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		//setTimeout(1);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
//		Robot.driveTrain.driveForward();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		//return isTimedOut();
		return Robot.s1.getDistance() < 0.3;
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
