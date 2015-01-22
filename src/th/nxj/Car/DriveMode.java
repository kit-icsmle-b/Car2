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
 * @author BONN
 */
public class DriveMode
{
	/** 黒色のID */
	public static final int COLOR_ID_BLACK = 7;
	/** 白色のID */
	public static final int COLOR_ID_WHITE = 6;
	/** 緑色のID */
	public static final int COLOR_ID_GREEN = 1;
	/** 赤色のID */
	public static final int COLOR_ID_RED = 0;
	
	/** 通常時のスピード */
	public static final int NORMAL_SPEED_1 = 430;
	public static final int NORMAL_SPEED_2 = 50;
	
	/** 緑通過時のスピード */
	public static final int SLOW_SPEED_1 = 300;
	public static final int SLOW_SPEED_2 = 10;
	
	/** メビウスモード(小さい輪の中を走行する時)時のスピード */
	public static final int MOBIUS_SPEED_1 = 400;
	public static final int MOBIUS_SPEED_2 = 170;
	
	/** サーチモード時のスピード */
	public static final int SEARCH_SPEED_1_2 = 100;
	
	
	private static int line_lost_time;												// 黒線を見失い続けている時間
	private static int color_state_prev;											// 直前のカラーセンサの値
	private static int white_scan_count = 0;
	private static int mobius_pass_flag = 0;
	WheelControl wheel = new WheelControl( NORMAL_SPEED_1 , NORMAL_SPEED_1 );		// タイヤの制御処理のインスタンス
	LineSensor sensor = new LineSensor();											// カラーセンサーのインスタンス
	
	/**
	 * コンストラクタ
	 */
	public DriveMode()
	{
		line_lost_time = 0;
		color_state_prev = 50;
	}
	
	/**
	 * 通常時の走行モード
	 * 黒線検出で左へ、
	 * 白色検出で右へ旋回する
	 * @return sstate 次のループ時に実行すべき走行モードのフラグ値
	 */
	public int InLineDrive()
	{
		
		int sstate = 0;												 // 現在の走行モード
		int color = sensor.getState();								 // 最新のセンサ値
		
		if( color_state_prev != color )								 // 最新のセンサ値と直前の値が異なっていたら
		{
			switch( color )
			{
			case COLOR_ID_BLACK:
				wheel.setSpeed( NORMAL_SPEED_2 , NORMAL_SPEED_1 );
				wheel.forward();
				line_lost_time = 0;									 // 黒線が検出されたので、ロストしている時間を0に戻す
				break;
			case COLOR_ID_WHITE:
				if( mobius_pass_flag == 0 )
					wheel.setSpeed( NORMAL_SPEED_1 , NORMAL_SPEED_2 + 60 );
				else
					wheel.setSpeed( NORMAL_SPEED_1 , NORMAL_SPEED_2 + 250 );
				wheel.forward();
				break;
			case COLOR_ID_GREEN:
				DriveOnTheGreenInside();							 // 走行する輪の切り替え専用の走行モードへ
				sstate = 2;											 // 切り替えが終わったら、メビウスモード(Car.javaを参照)に切り替えを指示
				break;
			case COLOR_ID_RED:
				wheel.stop();										 // 赤色はゴールなので停止
				line_lost_time = 0;
				white_scan_count = 0;
				mobius_pass_flag = 0;
			}
			color_state_prev = color;								 // 直前のセンサ値を更新
		}
		if( color_state_prev == COLOR_ID_WHITE )
			line_lost_time++;
		
		if( line_lost_time > /*5000*/10000 )									 // 黒線を見失っている時間が5000カウントを超えたら
			sstate = 1;												 // サーチモード(Car.javaを参照)に切り替えを指示
		
		return sstate;
	}
	
	
	/**
	 * 大きい輪から小さい輪に移動するときの処理
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
					return;											// 無事黒線が見つかったことになるので、処理を抜ける
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
	 * 小さい輪から大きい輪へ移動するときの処理
	 */
	private void DriveOnTheGreenOutside()
	{
		
		int color;
		
		for(;;)
		{
			color = sensor.getState();
			if( color_state_prev != color )
			{
				switch( color )										// センサーから色情報を取得
				{
				case COLOR_ID_BLACK:
					mobius_pass_flag = 1;
					return;											// 無事黒線が見つかったことになるので、処理を抜ける
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
	 * メビウスモード(小さい輪の中を走行する時の処理)
	 * @return　sstate 次のループ時に実行すべき走行モードのフラグ値
	 */
	public int OutLineDrive()
	{
		int sstate = 2;
		int color = sensor.getState();
		
		if( color_state_prev != color )
		{
			switch( color )											// カラーセンサから色情報を取得
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
				DriveOnTheGreenOutside();							// 走行する輪の切り替え専用の走行モードへ
				sstate = 0;											// 切り替えが終わったら、通常モード(Car.javaを参照)に切り替えを指示
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
	 * サーチモード(線を見失ったときのモード)
	 * ちょっとずつ左旋回し、線を探す
	 * 要改良
	 * @return sstate 次のループ時に実行すべき走行モードのフラグ値
	 */
	public int SearchLine()
	{
		int sstate = 0;
		
		wheel.setSpeed( SEARCH_SPEED_1_2 , SEARCH_SPEED_1_2 );
		
		if( sensor.getState() != COLOR_ID_BLACK )				// 黒線を見つけるまで継続
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