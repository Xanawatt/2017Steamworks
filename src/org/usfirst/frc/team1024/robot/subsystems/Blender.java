package org.usfirst.frc.team1024.robot.subsystems;

import org.usfirst.frc.team1024.robot.RobotMap;
import org.usfirst.frc.team1024.robot.util.Subsystem1024;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *Change Log
 * 1/28/2017: Added the blend and stop functions
 * 1/30/2017: added javadocs
 */
public class Blender extends Subsystem implements Subsystem1024 {
	public final CANTalon blenderMotor = new CANTalon(RobotMap.BLENDER_PORT);
	
	public Blender() {
		LiveWindow.addActuator("Blender", "Blender Motor", blenderMotor);
	}
	
	/**
	 * Sets the power of the blender motor
	 * @param power of the blender motor
	 */
	public void blend(double power) {
		blenderMotor.set(power);
	}
	
	/**
	 * Calls blend at a preset power
	 */
	public void blend(){
		blend(1.0); //Will be set fo-reals later
	}
	
	/**
	 * Stops the blender
	 */
	public void stop() {
		blenderMotor.disable();
	}

	@Override
	public void outputToSmartDashboard() {
		SmartDashboard.putData("Blender Motor", blenderMotor);
	}

	@Override
	public void resetSensors() {
		
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
