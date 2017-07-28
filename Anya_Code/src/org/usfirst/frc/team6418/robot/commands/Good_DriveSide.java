package org.usfirst.frc.team6418.robot.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDController6418;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Spark;

public class Good_DriveSide implements SpeedController {

	private PIDController6418 pid;
	//private SpeedController motor;	
	private Encoder encoder;
	
	private SpeedController motor;
	
	private double Kp;
	private double Ki;
	private double Kd;
	private double Kf;
	
	
	public Good_DriveSide(double kp, double ki, double kd, double kf, Encoder encoderInput, final SpeedController motorInput ) {
		super();
		
		encoder = encoderInput;
		encoder.setMinRate(1.0/12.0);
		encoder.setDistancePerPulse(1.0/2400.0);
		//outputs 95% max power hk
		//calculated value based on the distance traveled by the wheel and the gears
		//scale factor between the pulses and the distance 
		encoder.setSamplesToAverage(7);
		encoder.setPIDSourceType(PIDSourceType.kRate);
		encoder.reset();
		//make sure the encoder is set to 0 before each use? each time it's called hk
		
		this.motor = motorInput;
		
		Kp=kp;
		Ki=ki;
		Kd=kd;
		Kf=kf;
		
		this.pid=new PIDController6418(Kp, Ki, Kd, Kf, encoder, this.motor);
		
		this.pid.setOutputRange(-1, 1);
		this.pid.setInputRange(-1, 1);
		set(0.0);

	}

	@Override
	public void pidWrite(double output) {
		//unnecessary
	}

	@Override
	public double get() {
		return this.pid.get();
	}

	@Override
	public void set(double speed) {
		this.pid.enable();
		this.pid.setSetpoint(speed);
		//double error = speed-encoder.getRate();
		//motor.set(Kp*error+Kf*speed);
	}

	@Override
	public void setInverted(boolean isInverted) {
		
		this.motor.setInverted(isInverted);
		encoder.setReverseDirection(isInverted);
		
	}

	@Override
	public boolean getInverted() {

		return this.motor.getInverted();
		
	}
	
	public void enable() {
		this.pid.enable();
	}

	@Override
	public void disable() {
		set(0.0);
		this.pid.disable();
		
	}

	@Override
	public void stopMotor() {
		
		this.motor.stopMotor();
		
	}
	
	public double getEncoderCount() {
		return this.encoder.get();
		//returns the current count of the encoder
		
	}
	
	
	public boolean moveForward(double inFeet, double initialEncoder) {
	
		double feetInTicks = inFeet * 229.3;
		//229.3 ticks per foot
		double readEncoder = getEncoderCount();
	
		double encoderReading = Math.abs( readEncoder - initialEncoder);
		if (encoderReading < 229.3) {
		
			//moving forward code set(.8);
			return true;
		
		}
		return false; 
	}
	
	
	public Encoder getEncoder() {
		
		return encoder;
	}
	

	
}