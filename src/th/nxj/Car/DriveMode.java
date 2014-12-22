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
 * マシンの走行モードを管理するクラス
 * @author Taka
 */
public class DriveMode
{
	
	public static final int COLOR_ID_BLACK = 7;		// | 
	public static final int COLOR_ID_WHITE = 6;		// |各色の番号
	public static final int COLOR_ID_GREEN = 1;		// |
	public static final int COLOR_ID_RED = 0;
	
	private static int state;
	private static int line_lost_time;				// 黒線を見失い続けている時間
	WheelControl wheel = new WheelControl();		// タイヤの制御処理のインスタンス
	LineSensor sensor = new LineSensor();			// カラーセンサーのインスタンス
	
	/**
	 * コンストラクタ
	 */
	public DriveMode()
	{
		state = 0;
		line_lost_time = 0;
	}
	
	/**
	 * 通常時の走行モード
	 * 黒線検出で左へ、
	 * 白色検出で右へ旋回する
	 * @return
	 */
	public int InLineDrive()
	{
		int sstate = 0;
		
		switch( sensor.getState() )			// センサから色情報を取得
		{
		case COLOR_ID_BLACK:
			wheel.TurnLeft();
			line_lost_time = 0;				// 黒線が検出されたので、ロストしている時間を0に戻す
			break;
		case COLOR_ID_WHITE:	// White
			wheel.TurnRight();
			line_lost_time++;
			break;
		case COLOR_ID_GREEN:
			DriveOnTheGreenInside();
			sstate = 2;
			break;
		case COLOR_ID_RED:
			wheel.TurnLeft();
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
	
	
	private void DriveOnTheGreenInside()
	{
		for(;;)
		{
			switch( sensor.getState() )
			{
			case COLOR_ID_BLACK:	// Black
				return;
			case COLOR_ID_WHITE:	// White
				wheel.TurnLeft();
				//line_lost_time++;
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
	
	
	private void DriveOnTheGreenOutside()
	{
		for(;;)
		{
			switch( sensor.getState() )
			{
			case COLOR_ID_BLACK:	// Black
				return;
			case COLOR_ID_WHITE:	// White
				wheel.TurnRight();
				//line_lost_time++;
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
	
	
	public int OutLineDrive()
	{
		int sstate = 2;
		
		switch( sensor.getState() )
		{
		case COLOR_ID_BLACK:	// Black
			wheel.TurnRight();
			line_lost_time = 0;
			break;
		case COLOR_ID_WHITE:	// White
			wheel.TurnLeft();
			line_lost_time++;
			break;
		case COLOR_ID_GREEN:
			DriveOnTheGreenOutside();
			sstate = 0;
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
	
	
	public int SearchLine()
	{
		int sstate = 0;
		//int count = 0;
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