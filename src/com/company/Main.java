package com.company;

interface def
{
	int DELTA = 1;			// смещение массива - считать с нуля, а не с единицы (из-за окаймления поля)
	//int HIT = 0;			// статус - попадание


	int MISS = -1;			// статус - мимо
	int DEAD = -2;			// статус - судно затоплено
	int SUNKEN = 0;			// статус - судно тонет

	int EDGE = 10;			// граница поля
	int CHANGE = 1;  		// передача хода
	int NO_CHANGE = 0; 		// ход остается
	boolean MINE = false;	// принадлежность игроку
	boolean OPP = true;		// принадлежность противнику
	int FIELD_WIGHT = 1500;	// длина игрового поля (она же длина панели)
	int FIELD_HEIGHT = 800; // ширина игрового поля
	int PANEL_HEIGHT = 100;	// высота панели
	int start_frame_pos_x = 100; // начальные координаты стартового окна
	int start_frame_pos_y = 100;
	int it_is_the_end = -47;
	boolean FAIL = false;
	boolean OK = true;
	int SLEEP_TIME = 300;
	boolean YOU = true;
}

public class Main
{
	public static void main(String[] args)
	{
		Field field = new Field();
	}
}
