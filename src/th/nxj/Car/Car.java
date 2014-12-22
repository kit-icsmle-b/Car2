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
	
	private static final int LINESTATE_NORMAL = 0;
	private static final int LINESTATE_SEARCH = 1;
	private static final int LINESTATE_MOBIUS = 2;
	
	static DriveMode dmode = new DriveMode();				// マシンの走行モードを管理する機能のインスタンス
	
	/**
	 * メインメソッド
	 * @param args
	 */
	public static void main( String args[] )
	{
		
		int linestate = 0;									// 現在の走行モード
		
		LCD.drawString( "Car system standby..." , 0 , 0 );
		Button.waitForAnyPress();							// ボタンを押されるまで待つ
		LCD.drawString( "GO" , 0 , 3 );
		
		for(;;)												// メインループ
		{
			if( linestate == LINESTATE_NORMAL )				// 通常モード(線の内側を走行)だったら
				linestate = dmode.InLineDrive();
			else if( linestate == LINESTATE_SEARCH )		// サーチモード(線を見失った)だったら
				linestate = dmode.SearchLine();
			else if( linestate == LINESTATE_MOBIUS )		// メビウスモード(八の字の小さい円の走行)だったら
				linestate = dmode.OutLineDrive();			// メソッド名変えないと…
		}
		
	}
	
}