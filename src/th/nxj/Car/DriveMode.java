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
 * @author Taka
 */
public class DriveMode
{
	
	public static final int COLOR_ID_BLACK = 7;		// | 
	public static final int COLOR_ID_WHITE = 6;		// |�e�F�̔ԍ�
	public static final int COLOR_ID_GREEN = 1;		// |
	public static final int COLOR_ID_RED = 0;
	
	private static int state;
	private static int line_lost_time;				// �����������������Ă��鎞��
	WheelControl wheel = new WheelControl();		// �^�C���̐��䏈���̃C���X�^���X
	LineSensor sensor = new LineSensor();			// �J���[�Z���T�[�̃C���X�^���X
	
	/**
	 * �R���X�g���N�^
	 */
	public DriveMode()
	{
		state = 0;
		line_lost_time = 0;
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
		
		switch( sensor.getState() )			// �Z���T����F�����擾
		{
		case COLOR_ID_BLACK:
			wheel.TurnLeft();
			line_lost_time = 0;				// ���������o���ꂽ�̂ŁA���X�g���Ă��鎞�Ԃ�0�ɖ߂�
			break;
		case COLOR_ID_WHITE:
			wheel.TurnRight();
			line_lost_time++;				// �������������Ă��鎞�Ԃ𑝂₷
			break;
		case COLOR_ID_GREEN:
			DriveOnTheGreenInside();		// ���s����ւ̐؂�ւ���p�̑��s���[�h��
			sstate = 2;						// �؂�ւ����I�������A���r�E�X���[�h(Car.java���Q��)�ɐ؂�ւ����w��
			break;
		case COLOR_ID_RED:
			wheel.TurnLeft();
			line_lost_time = 0;
		/*default:
			wheel.TurnLeft();
			line_lost_time++;
			break;*/
		}
		
		if( line_lost_time > 5000 )			// �������������Ă��鎞�Ԃ�5000�J�E���g�𒴂�����
			sstate = 1;						// �T�[�`���[�h(Car.java���Q��)�ɐ؂�ւ����w��
		
		return sstate;
	}
	
	
	/**
	 * �傫���ւ��珬�����ւɈړ�����Ƃ��̏���
	 */
	private void DriveOnTheGreenInside()
	{
		for(;;)
		{
			switch( sensor.getState() )		// �J���[�Z���T����F�����擾
			{
			case COLOR_ID_BLACK:
				return;						// ���������������������ƂɂȂ�̂ŁA�����𔲂���
			case COLOR_ID_WHITE:
				wheel.TurnLeft();
				break;
			case COLOR_ID_GREEN:
				wheel.TurnRight();
				break;
			/*default:
				wheel.TurnLeft();
				line_lost_time++;
				break;*/
			}
		}
	}
	
	
	/**
	 * �������ւ���傫���ւֈړ�����Ƃ��̏���
	 */
	private void DriveOnTheGreenOutside()
	{
		for(;;)
		{
			switch( sensor.getState() )		// �Z���T�[����F�����擾
			{
			case COLOR_ID_BLACK:
				return;						// ���������������������ƂɂȂ�̂ŁA�����𔲂���
			case COLOR_ID_WHITE:
				wheel.TurnRight();
				break;
			case COLOR_ID_GREEN:
				wheel.TurnLeft();
				break;
			/*default:
				wheel.TurnLeft();
				line_lost_time++;
				break;*/
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
		
		switch( sensor.getState() )			// �J���[�Z���T����F�����擾
		{
		case COLOR_ID_BLACK:
			wheel.TurnRight();
			line_lost_time = 0;
			break;
		case COLOR_ID_WHITE:
			wheel.TurnLeft();
			line_lost_time++;
			break;
		case COLOR_ID_GREEN:
			DriveOnTheGreenOutside();		// ���s����ւ̐؂�ւ���p�̑��s���[�h��
			sstate = 0;						// �؂�ւ����I�������A�ʏ탂�[�h(Car.java���Q��)�ɐ؂�ւ����w��
			break;
		case COLOR_ID_RED:
			wheel.TurnRight();
			line_lost_time = 0;
		/*default:
			wheel.TurnLeft();
			line_lost_time++;
			break;*/
		}
		
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
		
		if( sensor.getState() != COLOR_ID_BLACK )
		{
			wheel.TurnLeft();
			Delay.msDelay( 200 );
			wheel.stop();
			Delay.msDelay( 500 );
			sstate = 1;
		}
		else
			sstate = 0;
		
		return sstate;
	}
	
}