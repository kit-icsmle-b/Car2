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
	 * @return sstate 次のループ時に実行すべき走行モードのフラグ値
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
		case COLOR_ID_WHITE:
			wheel.TurnRight();
			line_lost_time++;				// 黒線を見失っている時間を増やす
			break;
		case COLOR_ID_GREEN:
			DriveOnTheGreenInside();		// 走行する輪の切り替え専用の走行モードへ
			sstate = 2;						// 切り替えが終わったら、メビウスモード(Car.javaを参照)に切り替えを指示
			break;
		case COLOR_ID_RED:
			wheel.TurnLeft();
			line_lost_time = 0;
		/*default:
			wheel.TurnLeft();
			line_lost_time++;
			break;*/
		}
		
		if( line_lost_time > 5000 )			// 黒線を見失っている時間が5000カウントを超えたら
			sstate = 1;						// サーチモード(Car.javaを参照)に切り替えを指示
		
		return sstate;
	}
	
	
	/**
	 * 大きい輪から小さい輪に移動するときの処理
	 */
	private void DriveOnTheGreenInside()
	{
		for(;;)
		{
			switch( sensor.getState() )		// カラーセンサから色情報を取得
			{
			case COLOR_ID_BLACK:
				return;						// 無事黒線が見つかったことになるので、処理を抜ける
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
	 * 小さい輪から大きい輪へ移動するときの処理
	 */
	private void DriveOnTheGreenOutside()
	{
		for(;;)
		{
			switch( sensor.getState() )		// センサーから色情報を取得
			{
			case COLOR_ID_BLACK:
				return;						// 無事黒線が見つかったことになるので、処理を抜ける
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
	 * メビウスモード(小さい輪の中を走行する時の処理)
	 * @return　sstate 次のループ時に実行すべき走行モードのフラグ値
	 */
	public int OutLineDrive()
	{
		int sstate = 2;
		
		switch( sensor.getState() )			// カラーセンサから色情報を取得
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
			DriveOnTheGreenOutside();		// 走行する輪の切り替え専用の走行モードへ
			sstate = 0;						// 切り替えが終わったら、通常モード(Car.javaを参照)に切り替えを指示
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
	 * サーチモード(線を見失ったときのモード)
	 * ちょっとずつ左旋回し、線を探す
	 * 要改良
	 * @return sstate 次のループ時に実行すべき走行モードのフラグ値
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