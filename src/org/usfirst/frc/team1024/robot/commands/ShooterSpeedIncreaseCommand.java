package org.usfirst.frc.team1024.robot.commands;

import org.usfirst.frc.team1024.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterSpeedIncreaseCommand extends Command {
	public ShooterSpeedIncreaseCommand() {
		requires(Robot.gear);
	}
	
	@Override
	protected void initialize() {
	}
	
	@Override
	protected void execute() {
		Robot.shooter.shooterSetSpeed += 0.001;
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
	}
	
	@Override
	protected void interrupted() {
	}

}
