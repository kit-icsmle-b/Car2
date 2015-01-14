package th.nxj.Car;

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;
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
 * ���C���Z���T���Ǘ��E���䂷��N���X
 * @author BONN
 *
 */
public class LineSensor extends Thread
{
	/** �e�F�̖��O�B�v�f�̔ԍ��͐F��ID�l�ɑΉ����Ă��� */
	public static final String[] colorNames = {"Red", "Green", "Blue", "Yellow", "Magenta", "Orange",
            "White", "Black", "Pink", "Gray", "Light gray", "Dark Gray", "Cyan"	};
	/** ���F��ID */
	public static final int COLOR_ID_BLACK = 7;
	/** ���F��ID */
	public static final int COLOR_ID_WHITE = 6;
	/** �f�B���C�̗L������ */
	private final int DELAY_VALUE = 50;//100
	/** ���݃Z���T���擾���Ă���F��ID */
	private static int state = 0;
	/** �J���[�Z���T����p�̃C���X�^���X */
	private static ColorHTSensor CSensor = new ColorHTSensor( SensorPort.S1 );
	
	/**
	 * �R���X�g���N�^���Ăяo���ꂽ���_�ŃX���b�h�̓�����J�n����
	 */
	public LineSensor()
	{
		start();
	}
	
	/**
	 * 200�~���b���ƂɐF�����擾���ALCD�ɐF��ID�l��\������
	 */
	public void run()
	{
		for(;;)
		{
			state = CSensor.getColorID();
			LCD.drawInt( state , 0, 0, 4);
			Delay.msDelay( DELAY_VALUE );
		}
	}
	
	/**
	 * �Z���T�[����擾�����F��ID�l��Ԃ�
	 * @return �F��ID�l
	 */
	public int getState()
	{
		return state;
	}
	
}