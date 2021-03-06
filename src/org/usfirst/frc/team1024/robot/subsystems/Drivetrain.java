package org.usfirst.frc.team1024.robot.subsystems;

import org.usfirst.frc.team1024.robot.Robot;
import org.usfirst.frc.team1024.robot.RobotMap;
import org.usfirst.frc.team1024.robot.util.KilaTalon;
import org.usfirst.frc.team1024.robot.util.Subsystem1024;

import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * @author team1024
 * Change Log:
 * 1/26/17: added all motors, added drive and stop commands, added shifter
 * 1/28/2017: Added commands for full drive, preset drive, and driveForTime
 * 1/30/2017: Added javadocs
 * 1/31/2017: Now implements our subsystem interface
 * 2/1/17: Removed middle motors and added javadocs
 */
public class Drivetrain extends Subsystem implements Subsystem1024 {
	public final KilaTalon frontLeftDrive   		= new KilaTalon(RobotMap.FRONT_LEFT_DRIVETRAIN_PORT);
	public final KilaTalon rearLeftDrive    		= new KilaTalon(RobotMap.REAR_LEFT_DRIVETRAIN_PORT);
	public final KilaTalon frontRightDrive  		= new KilaTalon(RobotMap.FRONT_RIGHT_DRIVETRAIN_PORT);
	public final KilaTalon rearRightDrive   		= new KilaTalon(RobotMap.REAR_RIGHT_DRIVETRAIN_PORT);
	
	public final Solenoid shifter 		   		= new Solenoid(RobotMap.DRIVETRAIN_SHIFTER_PORT);
	
	public AHRS navx;

	public Drivetrain() {
		LiveWindow.addActuator("Drivetrain", "FrontLeft Motor", frontLeftDrive);
		LiveWindow.addActuator("Drivetrain", "RearLeft Motor", rearLeftDrive);
		LiveWindow.addActuator("Drivetrain", "FrontRight Motor", frontRightDrive);
		LiveWindow.addActuator("Drivetrain", "RearRight Motor", rearRightDrive);
		LiveWindow.addActuator("Drivetrain", "Shifter", shifter);

		setMotorConfig(frontLeftDrive, 0.2, 0.00003, 0.0);
		setMotorConfig(frontRightDrive, 0.2, 0.00003, 0.0);
		rearLeftDrive.changeControlMode(TalonControlMode.Follower);
		rearLeftDrive.set(42);
		rearRightDrive.changeControlMode(TalonControlMode.Follower);
		rearRightDrive.set(2);
		frontRightDrive.reverseOutput(true);
		frontRightDrive.reverseSensor(true);
		frontLeftDrive.enableBrakeMode(true);
		frontRightDrive.enableBrakeMode(true);
		// setFollowerMode(frontRightDrive, rearRightDrive);
		try {
			navx = new AHRS(I2C.Port.kOnboard);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		}
	}
	
	/**
	 * Configures the motors settings
	 * @param motor that is being configured
	 */
	public void setMotorConfig(KilaTalon motor, double p, double i, double d) {
		motor.enableBrakeMode(true);
		motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		motor.configEncoderCodesPerRev(360);
		motor.configNominalOutputVoltage(+0.0f, -0.0f);
        motor.configPeakOutputVoltage(+12.0f, -12.0f);
		motor.setPIDSourceType(PIDSourceType.kDisplacement);
		motor.setPID(p, i, d, 0.0, 0, 0.0, 0);
	}
	
	/**
	 * Sets a motor to follow another motors commands
	 * @param master the motor that is being followed by the slave
	 * @param slave the motor that is following the masters instructions
	 */
	public void setFollowerMode(KilaTalon master, KilaTalon slave){
		slave.changeControlMode(TalonControlMode.Follower);
		slave.set(master.getDeviceID());
	}
	
	public void initDashboard() {
		SmartDashboard.putNumber("Left Drive P", frontLeftDrive.getP());
		SmartDashboard.putNumber("Left Drive I", frontLeftDrive.getI());
		SmartDashboard.putNumber("Left Drive D", frontLeftDrive.getD());
		SmartDashboard.putNumber("Left Drive Setpoint", frontLeftDrive.getSetpoint());

		SmartDashboard.putNumber("Right Drive P", frontRightDrive.getP());
		SmartDashboard.putNumber("Right Drive I", frontRightDrive.getI());
		SmartDashboard.putNumber("Right Drive D", frontRightDrive.getD());
		SmartDashboard.putNumber("Right Drive Setpoint", frontRightDrive.getSetpoint());
		
		SmartDashboard.putBoolean("Drivetrain GO", false);
		SmartDashboard.putBoolean("Reset Encoders", false);
	}

	/**
	 * Outputs motor properties to SmartDashboard.
	 */
	
	boolean isDone = false;
	@Override
	public void outputToSmartDashboard() {
		if (SmartDashboard.getBoolean("Reset Encoders", false) == true) {
			frontLeftDrive.setEncPosition(0);
			frontRightDrive.setEncPosition(0);
		}
		if (SmartDashboard.getBoolean("Drivetrain GO", false) == true && isDone != true) {
			frontLeftDrive.setP(SmartDashboard.getNumber("Left Drive P", frontLeftDrive.getP()));
			frontLeftDrive.setI(SmartDashboard.getNumber("Left Drive I", frontLeftDrive.getI()));
			frontLeftDrive.setD(SmartDashboard.getNumber("Left Drive D", frontLeftDrive.getD()));
			frontLeftDrive.goDistanceInInches(SmartDashboard.getNumber("Left Drive Setpoint", 10));
			
			frontRightDrive.setP(SmartDashboard.getNumber("Right Drive P", frontRightDrive.getP()));
			frontRightDrive.setI(SmartDashboard.getNumber("Right Drive I", frontRightDrive.getI()));
			frontRightDrive.setD(SmartDashboard.getNumber("Right Drive D", frontRightDrive.getD()));
			frontRightDrive.goDistanceInInches(SmartDashboard.getNumber("Right Drive Setpoint", 10));
			isDone = true;
			Timer.delay(10);
		} else if (Robot.oi.lJoy.getRawAxis(1) != 0.0 || Robot.oi.rJoy.getRawAxis(1) != 0.0){
		}
		else {
		}

		/*SmartDashboard.putNumber("Left Drive Distance (in.)", frontLeftDrive.getDistance());
		SmartDashboard.putNumber("Right Drive Distance (in.)", frontRightDrive.getDistance());
		SmartDashboard.putNumber("Average Distance (in.)", frontLeftDrive.getDistance() / frontRightDrive.getDistance());*/
/*
		SmartDashboard.putNumber("Left Encoder Distance (in.)", frontLeftDrive.getDistanceInInches());
		SmartDashboard.putNumber("Right Encoder Distance (in.)", frontRightDrive.getDistanceInInches());
		SmartDashboard.putNumber("Average Distance (in.)", frontLeftDrive.getDistanceInInches() / frontRightDrive.getDistanceInInches());
	*/
	}
	
	/**
	 * Drive all motors with all power
	 * @param power
	 */
	public void drive(double power) {
		frontLeftDrive.set(power);
		frontRightDrive.set(power);
	}
	
	/**
	 * Sets the drivetrain motors with left and right powers.
	 * @param leftpower (-1.0, 1.0)
	 * @param rightpower (-1.0, 1.0)
	 */
	public void drive(double leftpower, double rightpower) {
		frontLeftDrive.changeControlMode(TalonControlMode.PercentVbus);
		frontRightDrive.changeControlMode(TalonControlMode.PercentVbus);
		rearLeftDrive.changeControlMode(TalonControlMode.PercentVbus);
		rearRightDrive.changeControlMode(TalonControlMode.PercentVbus);

		frontLeftDrive.set(leftpower);
		frontRightDrive.set(rightpower);
		rearLeftDrive.set(leftpower);
		rearRightDrive.set(rightpower);
		
		frontLeftDrive.enable();
		frontRightDrive.enable();
		rearLeftDrive.enable();
		rearRightDrive.enable();
	}
	
	/**
	 * Stops all drivetrain motors.
	 */
	public void stop() {
		frontLeftDrive.disable();
		frontRightDrive.disable();
		rearLeftDrive.disable();
		rearRightDrive.disable();
	}
	
	/**
	 * Drives all drivetrain motors for a given time and powers.
	 * @param power
	 * @param time
	 */
	public void driveForTime(double power, double time) {
		drive(power);
		Timer.delay(time);
		stop();
	}
	
	/**
	 * Drives all drivetrain motors for a given time and powers.
	 * @param leftPower (-1.0, 1.0)
	 * @param rightPower (-1.0, 1.0)
	 * @param time (seconds)
	 */
	public void driveForTime(double leftPower, double rightPower, double time){
		drive(leftPower, rightPower);
		Timer.delay(time);
		stop();
	}
	
	/**
	 * Resets all sensors.
	 */
	@Override
	public void resetSensors() {
    	navx.reset();
	}
	
	/**
	 * Drives with PID to a distance. Changes the Talon Control Mode to position mode
	 * @param distance (inches)
	 */
	public void driveToDistance(double distance) {
		frontLeftDrive.goDistanceInInches(distance);
		frontRightDrive.goDistanceInInches(distance);
	}
	
	/**
	 * Turns the robot to an angle relative to where it is facing at the time that the function is executed
	 * @param power (-1.0, 1.0)
	 * @param angleChange (-180, 180) Relative to where the robot is facing at that moment
	 */
	public void turnRelative(double power, double angleChange) {
		navx.reset();
		if (angleChange < 180 && angleChange > -180) {
			if (angleChange >= 0) { // Turn left desiredAngle
				while (navx.getAngle() < angleChange && Robot.oi.getBreakButton() == false) {
					drive(-power, power);
				}
				stop();
			} else if (angleChange < 0){ // Turn right desiredAngle
				while (navx.getAngle() > angleChange && Robot.oi.getBreakButton() == false) {
					drive(power, -power);
				}
				stop();
			}
		} else if (angleChange == 180) { // Turn left 180
			while (navx.getAngle() < 180 && Robot.oi.getBreakButton() == false) {
				drive(-power, power);
			}
			stop();
		} else if (angleChange == -180) { // Turn right 180
			while (navx.getAngle() > -180 && Robot.oi.getBreakButton() == false) {
				drive(power, -power);
			}
			stop();
		} else {
			System.out.println("Angle cannot be greater than 180 or less than -180, please input the coterminal version of that angle");
		}
	}
	
	public void turnLeft(double power, double desiredAngle) {
		while(navx.getAngle() <= desiredAngle && Robot.oi.getBreakButton() == false) {
			drive(-power, power);
		}
		stop();
	}
	
	public void turnRight(double power, double desiredAngle) {
		while(navx.getAngle() >= desiredAngle && Robot.oi.getBreakButton() == false) {
			drive(power, -power);
		}
		stop();
	}

	@Override
	protected void initDefaultCommand() {
		
	}
	
}
