
package org.usfirst.frc.team6418.robot;
import java.util.*;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.JoystickBase;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import org.opencv.imgproc.Imgproc;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
//import org.usfirst.frc.team6418.robot.commands.ExampleCommand;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import org.usfirst.frc.team6418.robot.subsystems.*;
import org.opencv.core.*;
import org.usfirst.frc.team6418.robot.commands.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.]
 */
public class Robot extends IterativeRobot {
		
	public static final int WINCHMOTOR = 2;
	//public static OI oi;
	public static RobotDrive driveTrain;
	public static Winch winch;
	
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	public static Joystick stick = new Joystick(0);
	public static Joystick stick2 = new Joystick(1);

	public static JoystickButton TriggerButton = new JoystickButton(stick,1); 
	public static JoystickButton SideButton = new JoystickButton(stick,2);

	public static JoystickButton HighButton = new JoystickButton(stick,5);
	public static JoystickButton LowButton = new JoystickButton(stick, 3);
	public static JoystickButton RightButton = new JoystickButton(stick,4);
	
	public static FrontCam fCam = new FrontCam();
	public static BackCamSubsystem bCam = new BackCamSubsystem(); 
	
	public static String driveMode;
	public static String cameraMode;
	public static String state; 
	
	public static Timer myTimer = new Timer();
	public static ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	public static UltraSonic s1;
	
	public static Encoder leftEncoder;
	public static Encoder rightEncoder;

	public static SpeedController left;
	public static SpeedController right;
	//drivesides 
	
	public static Spark leftSpark;
	public static Spark rightSpark;
	
	public UsbCamera cam0, cam1;
	public VideoSink server;

	public boolean prevTrigger = false;
	public boolean cameraButton;
	
	@Override
	public void robotInit() {
		//oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);

		//	cam0 = CameraServer.getInstance().startAutomaticCapture(0);			
	
		//how to call: public void tankDrive(double x, double y)
		winch = new Winch(WINCHMOTOR, 9);

		leftSpark = new Spark(0);
		rightSpark = new Spark(1);
		
		leftEncoder = new Encoder(1, 2, true, Encoder.EncodingType.k4X);
		rightEncoder = new Encoder(3, 4, true, Encoder.EncodingType.k4X);
		

		
		driveMode ="TANK";
		cameraMode="BACK";
		//togglewhenpressed
		TriggerButton.whenPressed(new SetTankDrive());
		SideButton.whenPressed(new SetArcadeDrive());
		RightButton.whenPressed(new SetTankDriveBack());
		
		double KpR=9/72.0;
		double KpL=9/72.0;
		double Ki = 0.01/72.0;
		double Kd= 1/72.0;
		double Kf = 1.0;
		
		leftEncoder.reset();
		rightEncoder.reset();
		
		this.right = new Good_DriveSide(KpR, Ki, Kd, Kf, rightEncoder, rightSpark);
//		right.setInverted(true);
		this.left = new Good_DriveSide(KpL, Ki, Kd, Kf, leftEncoder, leftSpark);
	
	}

	
	
	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		Robot.left.disable();
		Robot.right.disable();

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		System.out.println("Autonomous!");
		System.out.println("StartTime: " + myTimer.get());
		System.out.println();
		System.out.println();
		
		state = "rightforward1";
		
		double KpR=9/72.0;
		double KpL=9/72.0;
		double Ki = 0.01/72.0;
		double Kd= 1/72.0;
		double Kf = 1.0;
		
		leftEncoder.reset();
		rightEncoder.reset();
		
		this.right = new Good_DriveSide(KpR, Ki, Kd, Kf, rightEncoder, rightSpark);
//		right.setInverted(true);
		this.left = new Good_DriveSide(KpL, Ki, Kd, Kf, leftEncoder, leftSpark);

		
		driveTrain = new RobotDrive(left,right);

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		//autonomousCommand = new MoveForward();
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
		

		myTimer.reset();
		myTimer.start();
		
		gyro.calibrate();
		//System.out.println("gyro calibration: " + gyro.getAngle());
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
		Scheduler.getInstance().run();
	
//			System.out.println("left encoder count: "+ ((Good_DriveSide) left).getEncoderCount());
//			System.out.println("right encoder count: "+ ((Good_DriveSide) right).getEncoderCount());
			

	//	THIS CODE IS TO TEST THE ENCODERS!! 
/*
	if (leftEncoder.getDistance() < feetToEncUnits(5) && rightEncoder.getDistance() > feetToEncUnits(-5)) {
			//rotations: X * 2400 / 360 
			//feet: 
			left.set(.3);
			right.set(-.3);
			System.out.println("left distance:" + leftEncoder.getDistance());
			System.out.println("right distance:" + rightEncoder.getDistance());
			
		}
		else {
			System.out.println("    it went 5 feet    ");
			left.set(0);
			right.set(0);
		}  
	} */

		//10.83 feet = 129.96 inches
		//9.5167 feet = 114.3 inches
		//have to calculate again with the length of the robot and the distance from the center
		//from left and right positions not the middle
		
		//middle lane 
//		System.out.println(state);
		if (state.equals("middleforward1")) {//midlane
		//	moveForward(9.00);
			leftmoveForward(9.0, 0.6);
			rightmoveForward(9.0, 0.6);
			if (finishedMoving(9.5)) 
				state = "stop";
		}
	
		
		//right lane 
		else if (state.equals("rightforward1")) {
			System.out.println("State is rightforward1!");
			leftmoveForward(3.0, 0.2);
			rightmoveForward(3.0, 0.2);	
			if (finishedMoving(3)){
				System.out.println("left Encoder = " + leftEncoder.getDistance());
				state = "rightforward2";
			}
		}
		
		else if (state.equals("rightforward2")) {
			System.out.println("State is rightforward2!");
			leftmoveForward(1.5, 0.1);
			rightmoveForward(1.5, 0.1);
			if (finishedMoving(1.5)){
				System.out.println("left Encoder = " + leftEncoder.getDistance());
				state = "rightturn1";
			}
		}
		else if (state.equals("rightturn1")) {
			//this is a right turn
			System.out.println("State is rightturn1!");
			changeAngle(10); 
			if (finishedTurning(10))
				state = "rightforward3";
		}
		else if (state.equals("rightforward3")) {
			System.out.println("State is rightforward3!");
			leftmoveForward(1.5, 0.1);
			rightmoveForward(1.5, 0.1);
			//was missing the brackets! for the if statement
			if (finishedMoving(6)){
				System.out.println("left Encoder = " + leftEncoder.getDistance());
				state = "stop";
			}
		}
		
		//when i changed finishedMoving to 6 instead of 1.5 it moved forward... but it should have reset... 
		//it's not properly resetting the encoders? i think
		//the display is not printing "left encoder= "for the states other than rightForward3.
		//its not printing to the console of the driver station, all of the outputs are going to "Riolog" within Java
		//Riolog is another popup within Java, I'm not sure how to get it to go back to Driver Station. 
	

		//have not tested left side yet. Need to. 
		//need to get measurements. 
		//leftlane
		else if (state.equals("leftforward1")) {
			leftmoveForward(9.0, 0.2);
			rightmoveForward(9.0, 0.2);		
			leftmoveForward(0.5, 0.1);
			rightmoveForward(0.5, 0.1);
			if (finishedMoving(9.5))
				state = "leftturn1";
		}
		else if (state.equals("leftturn1")){
			changeAngle(-120);
			if (finishedTurning(-120))
				state = "leftforward2";
		}
		else if (state.equals("leftforward2")) {	
			leftmoveForward(0.5, 0.1);
			rightmoveForward(0.5, 0.1);
			if (finishedMoving(.5));
				state = "stop";
		}
		
		else if (state.equals("stop")){
			left.set(0);
			right.set(0);
		}
		else {
			left.set(0);
			right.set(0);
		}
	}
	
	
	
	
	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		left.disable();
		right.disable();
		driveTrain = new RobotDrive(leftSpark, rightSpark);
	}

	/**
	 * This function is called periodically during operator control
	 */
	
	@Override
	public void teleopPeriodic() {
		
		//needs a String/boolean parameter		
		Scheduler.getInstance().run();
		

/*		System.out.println("LEFT\tgetDistance: \t\t" + leftEncoder.getDistance() + "\t\tleftRate: \t\t" + leftEncoder.getRate());
		System.out.println("LEFT JOYSTICK" + -1*stick2.getY());
		System.out.println("RIGHT\tgetDistance: \t\t" + rightEncoder.getDistance() + "\t\trightRate: \t\t" + rightEncoder.getRate());
		System.out.println("RIGHT JOYSTICK" + -1*stick.getY());
		System.out.println(); */
		  if (stick2.getTrigger() && !prevTrigger) {
			    System.out.println("Setting camera 1\n");
			    server.setSource(cam1);
			  }
		  else if (!stick2.getTrigger() && prevTrigger) {
			    System.out.println("Setting camera 0\n");
			    server.setSource(cam0);
			  } 
		  prevTrigger = stick2.getTrigger();
		
			if (driveMode.equalsIgnoreCase("TANK")) {

				//driveTrain.tankDrive(-1*stick.getY(),-1*stick.getRawAxis(5));
				driveTrain.tankDrive(-1*stick2.getY(),-1*stick.getY());
			}
			else if(driveMode.equalsIgnoreCase("BACK TANK")){
				driveTrain.tankDrive(stick.getY(),stick2.getY());
				
			}
			else if(driveMode.equalsIgnoreCase("ARCADE")){
			//arcade drive
				driveTrain.arcadeDrive(-stick.getY(),-stick.getX());
			}
		
		//winch control - Halie 
		winch.tankDrive(stick.getRawAxis(3), 0);
		
	}
			
 
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	 
	public double feetToEncUnits(double feet) {
		return feet*12/(6*Math.PI)*360/2400;
	}
	
	
	public void leftmoveForward(double feet, double speed) {	
		
		if (leftEncoder.getDistance() < feetToEncUnits(feet)) {
			//rotations: X * 2400 / 360 
			//feet: 
			left.set(speed);
		}
		else {
			left.set(0);
		} 
	}
	public void rightmoveForward(double feet, double speed) {	
		
		if (rightEncoder.getDistance() > feetToEncUnits(-feet)) {
			//rotations: X * 2400 / 360 
			//feet: 
			right.set(-speed);
		}
		else {
			right.set(0);
		} 
	}
	
	public boolean finishedMoving(double feet) {
		if (leftEncoder.getDistance() < feetToEncUnits(feet) && rightEncoder.getDistance() > feetToEncUnits(-feet)) {
			return false;
		}
		else 
			leftEncoder.reset();
			rightEncoder.reset();
			System.out.println("Encoder should be: " + feetToEncUnits(feet));
			System.out.println("finished moving!");
			return true;
	}
	
	public void changeAngle(double degrees) {
		//want to turn until getAngle > degrees
		//won't have to be exact 
		
		System.out.println(gyro.getAngle());
		if (degrees >= 0) {
			//degrees is positive, turn to the left, wait until getAngle is more than degrees, so it's finished turning 
			if (gyro.getAngle() > degrees) {
				left.set(0);
				right.set(0);
			} 
			else {
				right.set(.05);
				left.set(.05);
			}
		}
		else if (degrees <= 0) {
			//degrees is negative, turn right 
			if (gyro.getAngle() < degrees) {
				left.set(0);
				right.set(0);
			}
			else {
				right.set(-.05);
				left.set(.05);
			}
		}
		
	}
	
	public boolean finishedTurning(double degrees) {
		if (degrees >= 0) {
			//postitive
			if (gyro.getAngle() > degrees) {
				System.out.println("FINISHED TURNING" );
				return true;
			}
			else {
				return false;
			}
		}
		else if (degrees < 0) {
			//degrees is negative, turn right 
			if (gyro.getAngle() < degrees) {
				return true;
			}
			else {
				return false; 
			}
		}
		return false; 
	}
	
}

