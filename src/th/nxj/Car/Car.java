package th.nxj.Car;

import lejos.nxt.Button;
import lejos.nxt.LCD;


/*-------------------------------------------------------------------/
/	NXJ Mobius Line Tracer Program	 (C)BONN, 2014
/--------------------------------------------------------------------/
/
/ Copyright (C) 2014 BONN.
/ All Rights Reserved.
/
/-------------------------------------------------------------------*/

/**
 * ���C���g���[�T�̒��S�����ƂȂ�N���X
 * @author BONN
 */
public class Car
{
	
	private static final int LINESTATE_NORMAL = 0;
	private static final int LINESTATE_SEARCH = 1;
	private static final int LINESTATE_MOBIUS = 2;
	
	static DriveMode dmode = new DriveMode();				// �}�V���̑��s���[�h���Ǘ�����@�\�̃C���X�^���X
	
	/**
	 * ���C�����\�b�h
	 * @param args
	 */
	public static void main( String args[] )
	{
		
		int linestate = 0;									// ���݂̑��s���[�h
		
		LCD.drawString( "Car system standby..." , 0 , 0 );
		Button.waitForAnyPress();							// �{�^�����������܂ő҂�
		LCD.drawString( "GO" , 0 , 3 );
		
		for(;;)												// ���C�����[�v
		{
			if( linestate == LINESTATE_NORMAL )				// �ʏ탂�[�h(���̓����𑖍s)��������
				linestate = dmode.InLineDrive();
			else if( linestate == LINESTATE_SEARCH )		// �T�[�`���[�h(������������)��������
				linestate = dmode.SearchLine();
			else if( linestate == LINESTATE_MOBIUS )		// ���r�E�X���[�h(���̎��̏������~�̑��s)��������
				linestate = dmode.OutLineDrive();			// ���\�b�h���ς��Ȃ��Ɓc
		}
		
	}
	
}