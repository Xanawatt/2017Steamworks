package org.usfirst.frc.team1024.robot.commands.blueauto;

import org.usfirst.frc.team1024.robot.commands.DriveForDistance;
import org.usfirst.frc.team1024.robot.commands.ShootCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class BluePos1Shooting extends CommandGroup {
	public BluePos1Shooting() {
		// Drives a little to align with the boiler.
		addSequential(new DriveForDistance(0.4, 30)); // Correct this
		// Shoots the fuel.
		addSequential(new ShootCommand());
	}
	
	@Override
	protected void initialize() {}
	
	@Override
	protected void execute() {
		
	}
	
	@Override
	protected boolean isFinished() { return true; }
	@Override
	protected void end() {}
	@Override
	protected void interrupted() {}
	
}
