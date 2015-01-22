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
	
	/** �ʏ펞�̃X�s�[�h */
	public static final int NORMAL_SPEED_1 = 430;
	public static final int NORMAL_SPEED_2 = 50;
	
	/** �Βʉߎ��̃X�s�[�h */
	public static final int SLOW_SPEED_1 = 300;
	public static final int SLOW_SPEED_2 = 10;
	
	/** ���r�E�X���[�h(�������ւ̒��𑖍s���鎞)���̃X�s�[�h */
	public static final int MOBIUS_SPEED_1 = 400;
	public static final int MOBIUS_SPEED_2 = 170;
	
	/** �T�[�`���[�h���̃X�s�[�h */
	public static final int SEARCH_SPEED_1_2 = 100;
	
	
	private static int line_lost_time;												// �����������������Ă��鎞��
	private static int color_state_prev;											// ���O�̃J���[�Z���T�̒l
	private static int white_scan_count = 0;
	private static int mobius_pass_flag = 0;
	WheelControl wheel = new WheelControl( NORMAL_SPEED_1 , NORMAL_SPEED_1 );		// �^�C���̐��䏈���̃C���X�^���X
	LineSensor sensor = new LineSensor();											// �J���[�Z���T�[�̃C���X�^���X
	
	/**
	 * �R���X�g���N�^
	 */
	public DriveMode()
	{
		line_lost_time = 0;
		color_state_prev = 50;
	}
	
	/**
	 * �ʏ펞�̑��s���[�h
	 * �������o�ō��ցA
	 * ���F���o�ŉE�֐��񂷂�
	 * @return sstate ���̃��[�v���Ɏ��s���ׂ����s���[�h�̃t���O�l
	 */
	public int InLineDrive()
	{
		
		int sstate = 0;												 // ���݂̑��s���[�h
		int color = sensor.getState();								 // �ŐV�̃Z���T�l
		
		if( color_state_prev != color )								 // �ŐV�̃Z���T�l�ƒ��O�̒l���قȂ��Ă�����
		{
			switch( color )
			{
			case COLOR_ID_BLACK:
				wheel.setSpeed( NORMAL_SPEED_2 , NORMAL_SPEED_1 );
				wheel.forward();
				line_lost_time = 0;									 // ���������o���ꂽ�̂ŁA���X�g���Ă��鎞�Ԃ�0�ɖ߂�
				break;
			case COLOR_ID_WHITE:
				if( mobius_pass_flag == 0 )
					wheel.setSpeed( NORMAL_SPEED_1 , NORMAL_SPEED_2 + 60 );
				else
					wheel.setSpeed( NORMAL_SPEED_1 , NORMAL_SPEED_2 + 250 );
				wheel.forward();
				break;
			case COLOR_ID_GREEN:
				DriveOnTheGreenInside();							 // ���s����ւ̐؂�ւ���p�̑��s���[�h��
				sstate = 2;											 // �؂�ւ����I�������A���r�E�X���[�h(Car.java���Q��)�ɐ؂�ւ����w��
				break;
			case COLOR_ID_RED:
				wheel.stop();										 // �ԐF�̓S�[���Ȃ̂Œ�~
				line_lost_time = 0;
				white_scan_count = 0;
				mobius_pass_flag = 0;
			}
			color_state_prev = color;								 // ���O�̃Z���T�l���X�V
		}
		if( color_state_prev == COLOR_ID_WHITE )
			line_lost_time++;
		
		if( line_lost_time > /*5000*/10000 )									 // �������������Ă��鎞�Ԃ�5000�J�E���g�𒴂�����
			sstate = 1;												 // �T�[�`���[�h(Car.java���Q��)�ɐ؂�ւ����w��
		
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
			if( color_state_prev != color )
			{
				switch( color )
				{
				case COLOR_ID_BLACK:
					return;											// ���������������������ƂɂȂ�̂ŁA�����𔲂���
				case COLOR_ID_WHITE:
					wheel.setSpeed( SLOW_SPEED_2 , SLOW_SPEED_1 );
					wheel.forward();
					break;
				case COLOR_ID_GREEN:
					wheel.setSpeed( SLOW_SPEED_1 , SLOW_SPEED_2 );
					wheel.forward();
					break;
				}
				color_state_prev = color;
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
			if( color_state_prev != color )
			{
				switch( color )										// �Z���T�[����F�����擾
				{
				case COLOR_ID_BLACK:
					mobius_pass_flag = 1;
					return;											// ���������������������ƂɂȂ�̂ŁA�����𔲂���
				case COLOR_ID_WHITE:
					wheel.setSpeed( SLOW_SPEED_1 , SLOW_SPEED_2 );
					wheel.forward();
					break;
				case COLOR_ID_GREEN:
					wheel.setSpeed( SLOW_SPEED_2 , SLOW_SPEED_1 );
					wheel.forward();
					break;
				}
				color_state_prev = color;
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
		
		if( color_state_prev != color )
		{
			switch( color )											// �J���[�Z���T����F�����擾
			{
			case COLOR_ID_BLACK:
				wheel.setSpeed( MOBIUS_SPEED_1 + white_scan_count , MOBIUS_SPEED_2 );
				wheel.forward();
				line_lost_time = 0;
				break;
			case COLOR_ID_WHITE:
				wheel.setSpeed( MOBIUS_SPEED_2 , MOBIUS_SPEED_1 );
				wheel.forward();
				white_scan_count+=25;
				break;
			case COLOR_ID_GREEN:
				DriveOnTheGreenOutside();							// ���s����ւ̐؂�ւ���p�̑��s���[�h��
				sstate = 0;											// �؂�ւ����I�������A�ʏ탂�[�h(Car.java���Q��)�ɐ؂�ւ����w��
				white_scan_count = 0;
				break;
			case COLOR_ID_RED:
				wheel.stop();										// emergency stop.
			}
			color_state_prev = color;
		}
		if( color_state_prev == COLOR_ID_WHITE )
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
		
		wheel.setSpeed( SEARCH_SPEED_1_2 , SEARCH_SPEED_1_2 );
		
		if( sensor.getState() != COLOR_ID_BLACK )				// ������������܂Ōp��
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
			wheel.setSpeed( 100 , 100 );
		}
		
		return sstate;
	}
	
}