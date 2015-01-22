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
 */
public class WheelControl
{
	
	private static NXTRegulatedMotor LWheel = Motor.C; // 左ホイール
	private static NXTRegulatedMotor RWheel = Motor.A; // 右ホイール
	private static int Lspeed , Rspeed;				   // 左右ホイールの回転速度
	
	
	/**　ホイール回転速度を200に設定する　*/
	public WheelControl()
	{
		Rspeed = Lspeed = 200;
		LWheel.setSpeed( Lspeed );
		RWheel.setSpeed( Rspeed );
	}
	
	
	/**
	 * ホイールの回転速度をspeedの値に設定する
	 * @param speed ホイールの回転速度
	 */
	public WheelControl( int Lspeed , int Rspeed )
	{
		LWheel.setSpeed( Lspeed );
		RWheel.setSpeed( Rspeed );
	}
	
	
	/**
	 * ホイールの回転速度を変更する
	 * @param speed	ホイールの回転速度
	 */
	public void setSpeed( int Lspeed , int Rspeed )
	{
		LWheel.setSpeed( Lspeed );
		RWheel.setSpeed( Rspeed );
	}
	
	
	/**
	 * 左ホイールの回転速度を取得する
	 * @return Lspeed
	 */
	public static int getLSpeed()
	{
		return Lspeed;
	}
	
	
	/**
	 * 右ホイールの回転速度を取得する
	 * @return Rspeed
	 */
	public static int getRSpeed()
	{
		return Rspeed;
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
	 * 緩やかに左旋回
	 */
	/*public void TurnLeft2()
	{
		//LWheel.setSpeed( Lspeed );//50
		//RWheel.setSpeed( Rspeed );//250//200
		RWheel.forward();
		LWheel.forward();
	}*/
	
	/**
	 * 緩やかに右旋回
	 */
	/*public void TurnRight2()
	{
		LWheel.setSpeed( 400 );
		RWheel.setSpeed( 70 );
		LWheel.forward();
		RWheel.forward();
	}*/
	
	
	/**
	 * その場で左旋回(マシンの中心軸はずれない)
	 */
	public void RollLeft()
	{
		LWheel.backward();
		RWheel.forward();
	}
	
	
	/**
	 * その場で右旋回(マシンの中心軸はずれない)
	 */
	public void RollRight()
	{
		LWheel.forward();
		RWheel.backward();
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
	 * 停止
	 */
	public void stop()
	{
		LWheel.stop( true );
		RWheel.stop( true );
	}
	
}