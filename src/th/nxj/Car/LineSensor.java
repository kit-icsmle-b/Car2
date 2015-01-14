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
 * ラインセンサを管理・制御するクラス
 * @author BONN
 *
 */
public class LineSensor extends Thread
{
	/** 各色の名前。要素の番号は色のID値に対応している */
	public static final String[] colorNames = {"Red", "Green", "Blue", "Yellow", "Magenta", "Orange",
            "White", "Black", "Pink", "Gray", "Light gray", "Dark Gray", "Cyan"	};
	/** 黒色のID */
	public static final int COLOR_ID_BLACK = 7;
	/** 白色のID */
	public static final int COLOR_ID_WHITE = 6;
	/** ディレイの有効時間 */
	private final int DELAY_VALUE = 50;//100
	/** 現在センサが取得している色のID */
	private static int state = 0;
	/** カラーセンサ制御用のインスタンス */
	private static ColorHTSensor CSensor = new ColorHTSensor( SensorPort.S1 );
	
	/**
	 * コンストラクタが呼び出された時点でスレッドの動作を開始する
	 */
	public LineSensor()
	{
		start();
	}
	
	/**
	 * 200ミリ秒ごとに色情報を取得し、LCDに色のID値を表示する
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
	 * センサーから取得した色のID値を返す
	 * @return 色のID値
	 */
	public int getState()
	{
		return state;
	}
	
}