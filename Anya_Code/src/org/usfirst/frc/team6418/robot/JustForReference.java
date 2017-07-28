
package org.usfirst.frc.team6418.robot;
import java.util.*;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.hal.EncoderJNI;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import org.opencv.imgproc.Imgproc;
import edu.wpi.cscore.UsbCamera;
//import org.usfirst.frc.team6418.robot.commands.ExampleCommand;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;


import org.usfirst.frc.team6418.robot.subsystems.*;
import org.opencv.core.*;
import org.usfirst.frc.team6418.robot.commands.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class JustForReference extends IterativeRobot {

		
	public static final int WINCHMOTOR = 2;
	//public static OI oi;
	public static RobotDrive driveTrain;
	

	public static Winch winch;
	public static GreenLightSystem greenlight = new GreenLightSystem();
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	public static CameraServer cameraServer;
	//Testing UI, probably should put all joystick and buttons in a separate OI class
	public static CameraServer cameraServer2;
	public Joystick stick = new Joystick(0);
	public Joystick stick2 = new Joystick(1);

	public JoystickButton TriggerButton = new JoystickButton(stick,1); 
	public JoystickButton SideButton = new JoystickButton(stick,2);
	//changed button to 3 to optimize for joystick (was 8)-Anya
	public JoystickButton HighButton = new JoystickButton(stick,5);
	public JoystickButton LowButton = new JoystickButton(stick, 3);
	
	//TEST - Anya
	public JoystickButton RightButton = new JoystickButton(stick,4);
	
	public static String driveMode;
	public static String cameraMode;
	
	public Timer myTimer = new Timer();
	//it's a java class, so we should use the pre-written code? instead of writing our own, it is prob more reliable hk
	
	//Sensors
	public static UltraSonic s1;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	//Anya
	public SpeedController left;
	public SpeedController right;
	//drivesides 
	
	
	
	//double startTimeStamp;
	double initialLeftEncoderRead;
	double initialRightEncoderRead;
	
	@Override
	public void robotInit() {
		//oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		//cameraServer.getInstance().startAutomaticCapture();
		//cameraServer2.getInstance().startAutomaticCapture();


		winch = new Winch(WINCHMOTOR, 9);
		//how to call: public void tankDrive(double x, double y){
		//winch.tankDrive(stick.getRawAxis(2), 0);

		
		//Testing Sensor... CPR
		s1 = new UltraSonic(0);
		//Testing Video ... 
//		new Thread(() -> {
//            UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//            camera.setResolution(640, 480);
//            
//            CvSink cvSink = CameraServer.getInstance().getVideo();
//            CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
//            
//            Mat source = new Mat();
//            Mat output = new Mat();
//            //List<Mat> bgr = new Vector<Mat>();
//            
//            while(!Thread.interrupted()) {
//                cvSink.grabFrame(source);
//              //  Core.split(source,bgr );
//                Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
//                Imgproc.threshold(output, output, 150, 255, Imgproc.THRESH_TOZERO);
//                outputStream.putFrame(output);
//            }
//        }).start();
		
		//switching between drive modes - Halie 
		
		driveMode ="TANK";
		
		cameraMode="BACK";
		//if a is pressed, driveMode = "ARCADE"
		//if b is pressed, diveMode= "TANK"
		//togglewhenpressed
		TriggerButton.whenPressed(new SetTankDrive());
		SideButton.whenPressed(new SetArcadeDrive());
		RightButton.whenPressed(new SetTankDriveBack());
		
		HighButton.whenPressed(new FrontCamera());
		LowButton.whenPressed(new BackCamera());
		//TopButton.whenPressed(new GreenLight());
		
		//RightButton.whenPressed(new MoveForward());
		
		
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		this.left.disable();
		this.right.disable();

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


		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
		
		double KpR=9/72.0;
		double KpL=9/72.0;
		double Ki = 0.01/72.0;
		double Kd= 1/72.0;
		//double KfR= 1.4/36.0;
		//double KfL=1.45/36.0;
		double Kf = 1.0;
		
//		this.right = new Good_DriveSide(KpR, Ki, Kd, Kf, 4, 3, 1);
		right.setInverted(true);
//		this.left = new Good_DriveSide(KpL, Ki, Kd, Kf, 1, 2, 0);
		driveTrain = new RobotDrive(left,right);
		
		System.out.println("Autonomous!");
		System.out.println();
		System.out.println();
		
		initialLeftEncoderRead = ((Good_DriveSide) left).getEncoderCount();
		//CASTS
		
		myTimer.reset();
		myTimer.start();
		
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		System.out.println("AUTO PERIODIC");
		Scheduler.getInstance().run();
	
		//	System.out.println("encoder count: "+ ((Good_DriveSide) left).getEncoderCount());
			//QUESTION: if i made my own method inside good_driveside, which implements speedController
			//do I have to cast left as a goodDriveside instead of speedcontroller?
			
		right.set(.7);
		System.out.println();
		System.out.println("\t\tRight motor: " + right.get());
/*		if (myTimer.get() < 3) {
		//	left.set(.7);
			right.set(.7);
		}
		else {
		//	left.set(0);
			right.set(0);
		}*/
		
		

		
		Encoder leftEncoder = ((Good_DriveSide) left).getEncoder(); 
		
		
		if (leftEncoder.getDistance() < 2) {
			left.set(.7);
		}
		
		else if (leftEncoder.getDistance() == 2) {

			left.set(0);
		}
		
		else
			left.set(0);
		
		
		
		
		
	}
	
	

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		System.out.println("Initiating Teleop Mode");

		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
//needs a String/boolean parameter		
		Scheduler.getInstance().run();

		//double v = s1.getDistance();
	
//		System.out.println(v);
		//if (cameraMode.equalsIgnoreCase("BACK")){
			
			cameraServer.getInstance().startAutomaticCapture();
	//	}
		
	//	else if (cameraMode.equalsIgnoreCase("FRONT")){
	//		cameraServer2.getInstance().startAutomaticCapture();
			
	//	} 

		//tank drive - Aliya / Halie 
		
		if (driveMode.equalsIgnoreCase("TANK")) {

			//driveTrain.tankDrive(-1*stick.getY(),-1*stick.getRawAxis(5));
			driveTrain.tankDrive(-1*stick2.getY(), stick.getY());

			
			
		}

		else if(driveMode.equalsIgnoreCase("ARCADE")){
		//arcade drive
			driveTrain.arcadeDrive(-stick.getY(),stick.getX());
			//driveTrain.arcadeDrive(stick);
		}
		
		else if(driveMode.equalsIgnoreCase("BACK TANK")){
			driveTrain.tankDrive(-1*stick.getY(), stick2.getY());
			
		}
		
		//winch control - Halie 
		winch.tankDrive(stick.getRawAxis(3), 0);

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		left.set(0.5);
		LiveWindow.run();
	}
	 
}

