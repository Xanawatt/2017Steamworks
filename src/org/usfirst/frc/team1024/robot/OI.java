package org.usfirst.frc.team1024.robot;

import org.usfirst.frc.team1024.robot.commands.EmptyCommand;
import org.usfirst.frc.team1024.robot.commands.GearClampCommand;
import org.usfirst.frc.team1024.robot.commands.PushGearCommand;
import org.usfirst.frc.team1024.robot.commands.ShootCommand;
import org.usfirst.frc.team1024.robot.commands.ShooterSpeedResetCommand;
import org.usfirst.frc.team1024.robot.util.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

public class OI {
	public Joystick logi;
	public Joystick lJoy;
	public Joystick rJoy;
	public Button gearClampOpenButton;
	public Button gearClampCloseButton;
	public Button gearClampOffButton;
	public Button gearPushButton;
	public Button shooterSpeedIncreaseButton;
	public Button shooterSpeedDecreaseButton;
	public Button shootButton;
	public Button speedResetButton;
	public Button hopperFlapButton;
	public Button agitator;
	
	public OI() {
		logi = new Joystick(RobotMap.LOGITECH_PORT);
		lJoy = new Joystick(RobotMap.LEFT_JOYSTICK_PORT);
		rJoy = new Joystick(RobotMap.RIGHT_JOYSTICK_PORT);
		
		gearClampOpenButton = new JoystickButton(logi, 4);
		gearClampCloseButton = new JoystickButton(logi, 2);
		gearClampOffButton = new JoystickButton(logi, 1);
		
		gearPushButton = new JoystickButton(logi, 7);
		
		shootButton = new JoystickButton(logi, 8);
		speedResetButton = new JoystickButton(logi, 6);
		
		hopperFlapButton = new JoystickButton(logi, 3);
		agitator = new JoystickButton(logi, 10);
		
		
		
		gearClampOpenButton.whenPressed(new EmptyCommand() {
			{ requires(Robot.gear); }
			protected void execute() { Robot.gear.clamp(1); }
		});
		gearClampCloseButton.whenPressed(new EmptyCommand() {
			{ requires(Robot.gear); }
			protected void execute() { Robot.gear.clamp(-1); }
		});
		gearClampOffButton.whenPressed(new EmptyCommand() {
			{ requires(Robot.gear); }
			protected void execute() { Robot.gear.clamp(0); }
		});
		
		gearPushButton.whileHeld(new EmptyCommand() {
			{ requires(Robot.gear); }
			protected void execute() { Robot.gear.push(true); }
			protected void end() { Robot.gear.push(false); }
		});
		
		shootButton.whileHeld(new EmptyCommand() {
			{ requires(Robot.shooter); }
			protected void execute() {
				Robot.shooter.shooter.setSetpoint(Robot.shooter.shooterSetSpeed);
				Robot.shooter.shooter.enable();
			}
		});
		
		speedResetButton.whileHeld(new EmptyCommand() {
			protected void execute() { 
				Robot.shooter.shooterSetSpeed = Constants.INIT_SHOOTER_POWER;
			}
		});
		
		
	}
	
	/**
	 * Outputs data to the SmartDashboard
	 */
	public void outputToSmartDashboard() {
		
	}
	
//	/**
//	 * 
//	 * @return state of the left bumper
//	 */
//	public boolean getGearClampOpen() {
//		return logi.getButtonLB();
//	}
//	
//	/**
//	 * 
//	 * @return state of left trigger
//	 */
//	public boolean getGearClampClose() {
//		return logi.getButtonLT();
//	}
//	
//	/**
//	 * 
//	 * @return state of Y button
//	 */
//	public boolean getGearPush() {
//		return logi.getButtonY();
//	}
//	
//	/**
//	 * 
//	 * @return state of east D-Pad button
//	 */
//	public boolean getShooterSpeedIncrease() {
//		return logi.getDPadEast();
//	}
//	
//	/**
//	 * 
//	 * @return state of west D-Pad button
//	 */
//	public boolean getShooterSpeedDecrease() {
//		return logi.getDPadWest();
//	}
//	
//	/**
//	 * 
//	 * @return Y state of left analog stick
//	 */
//	public double getBlendSpeed() {
//		return logi.getLeftY();
//	}
//	
//	/**
//	 * 
//	 * @return Y state of right analog stick
//	 */
//	public double getClimbAbsSpeed() {
//		return logi.getRightY();
//	}
//	
//	/**
//	 * 
//	 * @return state of right trigger
//	 */
//	public boolean getShoot() {
//		return logi.getButtonRT();
//	}
//
//	/**
//	 * 
//	 * @return state of right bumper
//	 */
//	public boolean getSpeedReset() {
//		return logi.getButtonRB();
//	}
}