package com.company;
/**
 * Морской бой <b>maker</b> и <b>price</b>.
 *
 * @autor Серафим Иванов
 * @version 14.3 commented
 */

/**
 * интерфейс глобальных переменных (для удобного доступа. Идея так себе)
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

public class Main
{
	static Field field;

	public static void main(String[] args)
	{
		field = new Field();
	}

	/**
	 * Метод начала новой игры
	 */
	public static void reset()
	{
		field.dispose();
		field = new Field();
	}
}
