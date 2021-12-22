package com.company;

/**
 * Sea battle <b>maker</b> и <b>price</b>.
 *
 * @autor wequalwo aka Seraphim Ivanov
 * @version 16.3 eng commented
 */

/**
 * interface for global variables (was not a good decision)
 */
interface def
{


	int DELTA = 1;            // смещение массива - считать с нуля, а не с единицы (из-за окаймления поля)
	int MISS = -1;            // статус - мимо
	int DEAD = -2;            // статус - судно затоплено
	int SUNKEN = 0;            // статус - судно тонет

	int EDGE = 10;            // граница поля
	boolean MINE = false;    // принадлежность игроку
	boolean OPP = true;        // принадлежность противнику
	int FIELD_WIGHT = 1450;    // длина игрового поля (она же длина панели)
	int FIELD_HEIGHT = 800; // ширина игрового поля
	int PANEL_HEIGHT = 100;    // высота панели
	int it_is_the_end = -47;
	boolean FAIL = false;
	boolean OK = true;
	boolean YOU = true;


	int column = 0;
	int line = 1;

	int UP = 0;
	int LEFT = 1;
	int DOWN = 2;
	int RIGHT = 3;

	int UNDEF = -8;
}
class f
{
	private static boolean another_ko = false;
	static boolean setAnother_ko()
	{
		return another_ko;
	}
	static void convert()
	{
		System.out.println("converted to " + !another_ko);
		another_ko = !another_ko;
	}
}
/**
 * Program entry point.
 */
public class Main
{
	static Field field;
	public static void main(String[] args) throws InterruptedException
	{
		field = new Field();

		while(true)
		{
			Thread.sleep(10);
			if(f.setAnother_ko())
			{
				//System.out.println("yep");
				field.move();
			}
		}
	}

	/**
	 * It starts a new game
	 */
	public static void reset()
	{
		field.dispose();
		field = new Field();
	}
}
