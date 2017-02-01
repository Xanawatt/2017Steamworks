package org.usfirst.frc.team1024.robot.subsystems;

import org.usfirst.frc.team1024.robot.RobotMap;
import org.usfirst.frc.team1024.robot.util.Constants;
import org.usfirst.frc.team1024.robot.util.Subsystem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * @author team1024
 * Change Log:
 * 1/26/17: added all motors, added drive and stop commands, added shifter
 * 1/28/2017: Added commands for full drive, preset drive, and driveForTime
 * 1/30/2017: Added javadocs
 * 1/31/2017: Now implements our subsystem interface
 */
public class Drivetrain implements Subsystem {
	public final CANTalon frontLeftDrive   		= new CANTalon(RobotMap.FRONT_LEFT_DRIVETRAIN_PORT);
	public final CANTalon rearLeftDrive    		= new CANTalon(RobotMap.REAR_LEFT_DRIVETRAIN_PORT);
	public final CANTalon frontRightDrive  		= new CANTalon(RobotMap.FRONT_RIGHT_DRIVETRAIN_PORT);
	public final CANTalon rearRightDrive   		= new CANTalon(RobotMap.REAR_RIGHT_DRIVETRAIN_PORT);
	
	public final Solenoid shifter 		   		= new Solenoid(RobotMap.DRIVETRAIN_SHIFTER_PORT);
	
	public final AnalogGyro gyro 				= new AnalogGyro(RobotMap.GYRO_PORT);
	
	public Drivetrain() {
		LiveWindow.addActuator("Drivetrain", "FrontLeft Motor",   frontLeftDrive);
		LiveWindow.addActuator("Drivetrain", "RearLeft Motor",    rearLeftDrive);
		LiveWindow.addActuator("Drivetrain", "FrontRight Motor",  frontRightDrive);
		LiveWindow.addActuator("Drivetrain", "RearRight Motor",   rearRightDrive);
		
		LiveWindow.addSensor("Sensors", "Gyro", 			   gyro);
		
		setMotorConfig(frontLeftDrive);
		setMotorConfig(frontRightDrive);
		
		setFollowerMode(frontLeftDrive, rearLeftDrive);
		setFollowerMode(frontRightDrive, rearRightDrive);
	}
	/**
	 * Configures the motors settings
	 * @param motor that is being configured
	 */
	public void setMotorConfig(CANTalon motor) {
		motor.enableBrakeMode(true);
		motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		motor.configEncoderCodesPerRev((int) (10 * Constants.ENCODER_CONSTANT_INCHES));
		motor.configNominalOutputVoltage(+0.0f, -0.0f);
        motor.configPeakOutputVoltage(+12.0f, 0.0f);
        motor.changeControlMode(TalonControlMode.Position);
		motor.setPIDSourceType(PIDSourceType.kDisplacement);
		motor.setProfile(0);
		motor.setF(0.15);
		motor.setP(0.3);
		motor.setI(0.0);
		motor.setD(0.0);
	}
	/**
	 * Sets a motor to follow another motors commands
	 * @param master the motor that is being followed by the slave
	 * @param slave the motor that is following the masters instructions
	 */
	public void setFollowerMode(CANTalon master, CANTalon slave){
		slave.changeControlMode(TalonControlMode.Follower);
		slave.set(master.getDeviceID());
	}
	
	/**
	 * Drive all motors with all power
	 * @param power
	 */
	public void drive(double power) {
		frontLeftDrive.set(power);
		rearLeftDrive.set(power);
		frontRightDrive.set(power);
		rearRightDrive.set(power);
	}
	
	/**
	 * Sets the drivetrain motors with left and right powers.
	 * @param leftpower (-1.0, 1.0)
	 * @param rightpower (-1.0, 1.0)
	 */
	public void drive(double leftpower, double rightpower) {
		frontLeftDrive.set(leftpower);
		rearLeftDrive.set(leftpower);
		frontRightDrive.set(rightpower);
		rearRightDrive.set(rightpower);
		
	}
	
	/**
	 * Stops all drivetrain motors.
	 */
	public void stop() {
		frontLeftDrive.set(0.0);
		rearLeftDrive.set(0.0);
		frontRightDrive.set(0.0);
		rearRightDrive.set(0.0);
	}
	/**
	 * Drives all drivetrain motors for a given time and powers.
	 * @param time (seconds)
	 * @param leftPower (-1.0, 1.0)
	 * @param rightPower (-1.0, 1.0)
	 */
	public void driveForTime(double time, double leftPower, double rightPower){
		drive(leftPower, rightPower);
		Timer.delay(time);
		stop();
	}
	
	/**
	 * Outputs motor properties to SmartDashboard.
	 */
	@Override
	public void outputToSmartDashboard() {
		SmartDashboard.putData("Front Left Drive",   frontLeftDrive);
		SmartDashboard.putData("Rear Left Drive",    rearLeftDrive);
		SmartDashboard.putData("Front Right Drive",  frontRightDrive);
		SmartDashboard.putData("Rear Right Drive",   rearRightDrive);
	}
	
	/**
	 * Resets all sensors.
	 */
	@Override
	public void resetSensors() {
    	gyro.reset();
	}
	
	/**
	 * Gets the average of the encoder positions on the left and right side of the drivetrain.
	 * @returns the average position of the drive encoders.
	 */
	public double getAverageEncoderDistance() {
		return (frontLeftDrive.getEncPosition() + frontRightDrive.getEncPosition()) / 2;
	}
	
	/**
	 * Drives the drivetrain motors for a specified distance and power.
	 * @param distance (inches)
	 * @param power (-1.0, 1.0)
	 */
	public void driveForDistance(double distance, double power) {
		while (getAverageEncoderDistance() < distance) {
			drive(power);
		}
	}
	
	/**
	 * Gets the distance in inches from the CANTalon
	 * @param encoder that is being read
	 * @return
	 */
	public double getEncoderDisance(CANTalon encoder) {
		double distance;
		distance = encoder.getPosition() * Constants.ENCODER_CONSTANT_INCHES;
		return distance;
	}
	
	/**
	 * Drives with PID to a distance. Changes the Talon Control Mode to position mode
	 * @param distance (inches)
	 */
	public void driveToDistance(double distance) {
		//We need to be in position mode in order to drive for a distance
		frontLeftDrive.changeControlMode(TalonControlMode.Position);
		frontRightDrive.changeControlMode(TalonControlMode.Position);
		//We have to multiply distance by 10 because we can only set the 
		frontLeftDrive.setSetpoint(distance * 10);
		frontRightDrive.setSetpoint(distance * 10);
		frontLeftDrive.enable();
		frontRightDrive.enable();
	}
	
}
