package th.nxj.Car;

import lejos.util.Delay;


/*-------------------------------------------------------------------/
/	NXJ Mobius Line Tracer Program	 (C)BONN, 2014
/--------------------------------------------------------------------/
/
/ Copyright (C) 2014 BONN.
/ All Rights Reserved.
/
/-------------------------------------------------------------------*/

/**
 * �}�V���̑��s���[�h���Ǘ�����N���X
 * @author BONN
 */
public class DriveMode
{
	/** ���F��ID */
	public static final int COLOR_ID_BLACK = 7;
	/** ���F��ID */
	public static final int COLOR_ID_WHITE = 6;
	/** �ΐF��ID */
	public static final int COLOR_ID_GREEN = 1;
	/** �ԐF��ID */
	public static final int COLOR_ID_RED = 0;
	
	//private static int state;
	private static int line_lost_time;				// �����������������Ă��鎞��
	private static int color_state_now;
	private static int speed = 250;//300//200
	WheelControl wheel = new WheelControl( speed );		// �^�C���̐��䏈���̃C���X�^���X
	LineSensor sensor = new LineSensor();			// �J���[�Z���T�[�̃C���X�^���X
	
	/**
	 * �R���X�g���N�^
	 */
	public DriveMode()
	{
		//state = 0;
		line_lost_time = 0;
		color_state_now = 50;
	}
	
	/**
	 * �ʏ펞�̑��s���[�h
	 * �������o�ō��ցA
	 * ���F���o�ŉE�֐��񂷂�
	 * @return sstate ���̃��[�v���Ɏ��s���ׂ����s���[�h�̃t���O�l
	 */
	public int InLineDrive()
	{
		int sstate = 0;
		int color = sensor.getState();
		
		
		if( color_state_now != color )
		{
			switch( color )			// �Z���T����F�����擾
			{
			case COLOR_ID_BLACK:
				wheel.TurnLeft2();
				line_lost_time = 0;				// ���������o���ꂽ�̂ŁA���X�g���Ă��鎞�Ԃ�0�ɖ߂�
				break;
			case COLOR_ID_WHITE:
				wheel.TurnRight2();
				//line_lost_time++;				// �������������Ă��鎞�Ԃ𑝂₷
				break;
			case COLOR_ID_GREEN:
				DriveOnTheGreenInside();		// ���s����ւ̐؂�ւ���p�̑��s���[�h��
				sstate = 2;						// �؂�ւ����I�������A���r�E�X���[�h(Car.java���Q��)�ɐ؂�ւ����w��
				break;
			case COLOR_ID_RED:
				wheel.stop();					// �ԐF�̓S�[���Ȃ̂Œ�~
				line_lost_time = 0;
			/*default:
				wheel.TurnLeft();
				line_lost_time++;
				break;*/
			}
			color_state_now = color;
		}
		if( color_state_now == COLOR_ID_WHITE )
			line_lost_time++;
		
		if( line_lost_time > 5000 )			// �������������Ă��鎞�Ԃ�5000�J�E���g�𒴂�����
			sstate = 1;						// �T�[�`���[�h(Car.java���Q��)�ɐ؂�ւ����w��
		
		return sstate;
	}
	
	
	/**
	 * �傫���ւ��珬�����ւɈړ�����Ƃ��̏���
	 */
	private void DriveOnTheGreenInside()
	{
		
		int color;
		
		for(;;)
		{
			color = sensor.getState();
			if( color_state_now != color )
			{
				switch( color )		// �J���[�Z���T����F�����擾
				{
				case COLOR_ID_BLACK:
					return;						// ���������������������ƂɂȂ�̂ŁA�����𔲂���
				case COLOR_ID_WHITE:
					wheel.TurnLeft2();
					break;
				case COLOR_ID_GREEN:
					wheel.TurnRight2();
					break;
				}
				color_state_now = color;
			}
		}
		
	}
	
	
	/**
	 * �������ւ���傫���ւֈړ�����Ƃ��̏���
	 */
	private void DriveOnTheGreenOutside()
	{
		
		int color;
		
		for(;;)
		{
			color = sensor.getState();
			if( color_state_now != color )
			{
				switch( color )		// �Z���T�[����F�����擾
				{
				case COLOR_ID_BLACK:
					return;						// ���������������������ƂɂȂ�̂ŁA�����𔲂���
				case COLOR_ID_WHITE:
					wheel.TurnRight2();
					break;
				case COLOR_ID_GREEN:
					wheel.TurnLeft2();
					break;
					/*default:
					wheel.TurnLeft();
					line_lost_time++;
					break;*/
				}
				color_state_now = color;
			}
		}
		
	}
	
	
	/**
	 * ���r�E�X���[�h(�������ւ̒��𑖍s���鎞�̏���)
	 * @return�@sstate ���̃��[�v���Ɏ��s���ׂ����s���[�h�̃t���O�l
	 */
	public int OutLineDrive()
	{
		int sstate = 2;
		int color = sensor.getState();
		
		if( color_state_now != color )
		{
			switch( color )			// �J���[�Z���T����F�����擾
			{
			case COLOR_ID_BLACK:
				wheel.TurnRight2();
				line_lost_time = 0;
				break;
			case COLOR_ID_WHITE:
				wheel.TurnLeft2();
				//line_lost_time++;
				break;
			case COLOR_ID_GREEN:
				DriveOnTheGreenOutside();		// ���s����ւ̐؂�ւ���p�̑��s���[�h��
				sstate = 0;						// �؂�ւ����I�������A�ʏ탂�[�h(Car.java���Q��)�ɐ؂�ւ����w��
				break;
			case COLOR_ID_RED:
				wheel.TurnRight2();
				line_lost_time = 0;
				/*default:
				wheel.TurnLeft();
				line_lost_time++;
				break;*/
			}
			color_state_now = color;
		}
		if( color_state_now == COLOR_ID_WHITE )
			line_lost_time++;
		
		if( line_lost_time > 5000 )
			sstate = 1;
		
		return sstate;
	}
	
	
	/**
	 * �T�[�`���[�h(�������������Ƃ��̃��[�h)
	 * ������Ƃ������񂵁A����T��
	 * �v����
	 * @return sstate ���̃��[�v���Ɏ��s���ׂ����s���[�h�̃t���O�l
	 */
	public int SearchLine()
	{
		int sstate = 0;
		
		wheel.setSpeed( 100 );
		
		if( sensor.getState() != COLOR_ID_BLACK )
		{
			wheel.TurnRight();
			Delay.msDelay( 200 );
			wheel.stop();
			Delay.msDelay( 500 );
			sstate = 1;
		}
		else
		{
			sstate = 0;
			wheel.setSpeed( speed );
		}
		
		return sstate;
	}
	
}