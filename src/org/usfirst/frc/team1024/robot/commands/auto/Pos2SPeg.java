package org.usfirst.frc.team1024.robot.commands.auto;

<<<<<<< HEAD
import org.usfirst.frc.team1024.robot.commands.DriveForDistance;
import org.usfirst.frc.team1024.robot.commands.DriveForTime;
import org.usfirst.frc.team1024.robot.commands.PushGearCommand;
import org.usfirst.frc.team1024.robot.commands.TurnCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Pos2SPeg extends CommandGroup {
	public Pos2SPeg() {
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		// Goes to shooting position and shoots
		addSequential(new Pos1Shooting());
		// Turn away from shooting position
		addSequential(new TurnCommand(0.5, 90)); // Set this later.
		// Drive toward W peg
		addSequential(new DriveForDistance(0.5, 95.0)); // Set this later.
		// Turn towards W peg
		addSequential(new TurnCommand(0.5, -45)); // Set this later.
		// Place gear on W peg
		addSequential(new PushGearCommand(true)); // Set this later.
		// Drive away from W peg
		addSequential(new DriveForTime(-0.5, 1.0)); // Set this later.
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
=======
public class Pos2SPeg {

>>>>>>> 9699c08ae4174fdd2f4f156141e913cf3456ca03
}