package th.nxj.Car;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

/*-------------------------------------------------------------------/
/	NXJ Mobius Line Tracer Program	 (C)BONN, 2014
/--------------------------------------------------------------------/
/
/ Copyright (C) 2014 BONN.
/ All Rights Reserved.
/
/-------------------------------------------------------------------*/

/**
 * ホイールの動作の管理・制御を行うクラス
 * @author BONN
 *
 */
public class WheelControl
{
	private static NXTRegulatedMotor LWheel = Motor.C; // Left
	private static NXTRegulatedMotor RWheel = Motor.A; // Right
	private static int speed;
	
	/**　ホイール回転速度を50に設定する　*/
	public WheelControl()
	{
		speed = 50;
		LWheel.setSpeed( speed );
		RWheel.setSpeed( speed );
	}
	
	/**
	 * ホイールの回転速度をspeedの値に設定する
	 * @param speed ホイールの回転速度
	 */
	public WheelControl( int speed )
	{
		this.speed = speed;
		LWheel.setSpeed( speed );
		RWheel.setSpeed( speed );
	}
	
	/**
	 * ホイールの回転速度を変更する
	 * @param speed	ホイールの回転速度
	 */
	public void setSpeed( int speed )
	{
		this.speed = speed;
		LWheel.setSpeed( speed );
		RWheel.setSpeed( speed );
	}
	
	/**
	 * 左に旋回
	 */
	public void TurnLeft()
	{
		RWheel.forward();
		LWheel.stop(true);
	}
	
	/**
	 * 右に旋回
	 */
	public void TurnRight()
	{
		LWheel.forward();
		RWheel.stop(true);
	}
	
	/**
	 * 前進
	 */
	public void forward()
	{
		LWheel.forward();
		RWheel.forward();
	}
	
	/**
	 * 緩やかに右旋回
	 */
	public void TurnRight2()
	{
		LWheel.setSpeed( 50 );
		RWheel.setSpeed( 20 );
		LWheel.forward();
		RWheel.forward();
	}
	
	/**
	 * 停止
	 */
	public void stop()
	{
		LWheel.stop( true );
		RWheel.stop( true );
	}
	
}