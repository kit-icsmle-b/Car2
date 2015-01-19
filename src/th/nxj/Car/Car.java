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
 * ライントレーサの中心部分となるクラス
 * @author BONN
 */
public class Car
{
	/** 通常モード(8の字コースの大きい円の内側を走行中) */
	private static final int LINESTATE_NORMAL = 0;
	/** サーチモード(線を見失っている状態) */
	private static final int LINESTATE_SEARCH = 1;
	/** メビウスモード(8の字コースの小さい円の内側を走行中) */
	private static final int LINESTATE_MOBIUS = 2;
	
	static DriveMode dmode = new DriveMode();				// マシンの走行モードを管理する機能のインスタンス
	
	/**
	 * メインメソッド
	 * @param args
	 */
	public static void main( String args[] )
	{
		
		int linestate = 0;									// 現在の走行モード
		
		LCD.drawString( "Car system" , 0 , 0 );
		LCD.drawString( "standby..." , 0 , 1 );
		Button.waitForAnyPress();							// ボタンを押されるまで待つ
		LCD.drawString( "GO" , 0 , 3 );
		
		for(;;)												// メインループ
		{
			if( linestate == LINESTATE_NORMAL )				// 通常モードだったら
				linestate = dmode.InLineDrive();
			else if( linestate == LINESTATE_SEARCH )		// サーチモードだったら
				linestate = dmode.SearchLine();
			else if( linestate == LINESTATE_MOBIUS )		// メビウスモードだったら
				linestate = dmode.OutLineDrive();			// メソッド名変えないと…
		}
		
	}
	
}