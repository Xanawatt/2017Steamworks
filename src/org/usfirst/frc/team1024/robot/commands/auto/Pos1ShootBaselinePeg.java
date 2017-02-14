package org.usfirst.frc.team1024.robot.commands.auto;

import org.usfirst.frc.team1024.robot.commands.DriveForDistance;
import org.usfirst.frc.team1024.robot.commands.DriveForTime;
import org.usfirst.frc.team1024.robot.commands.PushGearCommand;
import org.usfirst.frc.team1024.robot.commands.ShootCommand;
import org.usfirst.frc.team1024.robot.commands.TurnCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Pos1ShootBaselinePeg extends CommandGroup {
	
	public Pos1ShootBaselinePeg() {
		addSequential(new ShootCommand()); // Assumed that we're not only against the wall, but also right in front of the boiler. 
		addSequential(new TurnCommand()); // After that, we turn a certain distance.
		addSequential(new DriveForDistance(0.5, 95.0)); // Set this later.
		addSequential(new TurnCommand());  // We turn to face the gear.
		addSequential(new PushGearCommand(true)); // We push the gear onto the peg.
		addSequential(new DriveForTime(-0.5, 1.0)); // We retract from the airship.

	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
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