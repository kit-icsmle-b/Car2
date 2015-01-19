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
 * �z�C�[���̓���̊Ǘ��E������s���N���X
 * @author BONN
 */
public class WheelControl
{
	
	private static NXTRegulatedMotor LWheel = Motor.C; // Left
	private static NXTRegulatedMotor RWheel = Motor.A; // Right
	private static int Lspeed , Rspeed;				   // ���E�z�C�[���̉�]���x
	
	
	/**�@�z�C�[����]���x��200�ɐݒ肷��@*/
	public WheelControl()
	{
		Rspeed = Lspeed = 200;
		LWheel.setSpeed( Lspeed );
		RWheel.setSpeed( Rspeed );
	}
	
	
	/**
	 * �z�C�[���̉�]���x��speed�̒l�ɐݒ肷��
	 * @param speed �z�C�[���̉�]���x
	 */
	public WheelControl( int Lspeed , int Rspeed )
	{
		LWheel.setSpeed( Lspeed );
		RWheel.setSpeed( Rspeed );
	}
	
	
	/**
	 * �z�C�[���̉�]���x��ύX����
	 * @param speed	�z�C�[���̉�]���x
	 */
	public void setSpeed( int Lspeed , int Rspeed )
	{
		LWheel.setSpeed( Lspeed );
		RWheel.setSpeed( Rspeed );
	}
	
	
	/**
	 * ���z�C�[���̉�]���x���擾����
	 * @return Lspeed
	 */
	public static int getLSpeed()
	{
		return Lspeed;
	}
	
	
	/**
	 * �E�z�C�[���̉�]���x���擾����
	 * @return Rspeed
	 */
	public static int getRSpeed()
	{
		return Rspeed;
	}
	
	
	/**
	 * ���ɐ���
	 */
	public void TurnLeft()
	{
		RWheel.forward();
		LWheel.stop(true);
	}
	
	
	/**
	 * �E�ɐ���
	 */
	public void TurnRight()
	{
		LWheel.forward();
		RWheel.stop(true);
	}
	
	
	/**
	 * �ɂ₩�ɍ�����
	 */
	/*public void TurnLeft2()
	{
		//LWheel.setSpeed( Lspeed );//50
		//RWheel.setSpeed( Rspeed );//250//200
		RWheel.forward();
		LWheel.forward();
	}*/
	
	/**
	 * �ɂ₩�ɉE����
	 */
	/*public void TurnRight2()
	{
		LWheel.setSpeed( 400 );
		RWheel.setSpeed( 70 );
		LWheel.forward();
		RWheel.forward();
	}*/
	
	
	/**
	 * ���̏�ō�����(�}�V���̒��S���͂���Ȃ�)
	 */
	public void RollLeft()
	{
		LWheel.backward();
		RWheel.forward();
	}
	
	
	/**
	 * ���̏�ŉE����(�}�V���̒��S���͂���Ȃ�)
	 */
	public void RollRight()
	{
		LWheel.forward();
		RWheel.backward();
	}
	
	
	/**
	 * �O�i
	 */
	public void forward()
	{
		LWheel.forward();
		RWheel.forward();
	}
	
	
	/**
	 * ��~
	 */
	public void stop()
	{
		LWheel.stop( true );
		RWheel.stop( true );
	}
	
}