package org.usfirst.frc.team1024.robot.commands.redauto;
import org.usfirst.frc.team1024.robot.commands.DriveForDistance;

import org.usfirst.frc.team1024.robot.commands.GearClampCommand;
import org.usfirst.frc.team1024.robot.commands.PushGearCommand;
import org.usfirst.frc.team1024.robot.commands.TurnCommand;
import org.usfirst.frc.team1024.robot.commands.WaitForTimeCommand;
import org.usfirst.frc.team1024.robot.util.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Pos2WPeg extends CommandGroup {
	
	
	public Pos2WPeg() {
		addSequential(new GearClampCommand(0));
		addSequential(new DriveForDistance(Constants.DISTANCE_TO_BASELINE - Constants.ROBOT_LENGTH, 3f));
		addSequential(new WaitForTimeCommand(2.0));
		//addSequential(new DriveForDistance(0.5, 3)); Add exact distance
		addSequential(new GearClampCommand(1));
		addSequential(new WaitForTimeCommand(0.5));
		addSequential(new PushGearCommand());
		addSequential(new DriveForDistance(-0.5, -24));
		//make it so that "isFinished" returns True
	}
	
	@Override
	protected void initialize() {
	}
	
	@Override
	protected void execute() {
		
		//INCOMPLETE!!!!!!!!
		addSequential(new TurnCommand(0.5, 135)); //Set this Later
		//Drives to center, in line with W peg
		addSequential(new DriveForDistance(0.5, 96.25)); //Set this later
		//Turns to be facing W peg
		addSequential(new TurnCommand(0.5, -90)); //Set this later
		//Drive to W peg
		addSequential(new DriveForDistance(0.5, 46.95)); //Set this later
		//Place gear on W peg
		addSequential(new PushGearCommand());
		//Drive back from W peg
		addSequential(new DriveForDistance(0.5, -46.95)); //Set this later

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
