package com.company;

interface def
{
	int DELTA = 1;			// смещение массива - считать с нуля, а не с единицы (из-за окаймления поля)
	int MISS = -1;			// смещение в обраую сторонц
	int HIT = 0;			// статус - попадание
	int DEAD = -1;			// статус - судно затопдено
	int SUNKEN = 1;			// статус - судно тонет
	int EDGE = 10;			// граница поля
	int CHANGE = 1;  		//передача хода
	int NO_CHANGE = 0; 		// ход остается
	boolean MINE = false;	// принадлежность игроку
	boolean OPP = true;		// принадлежность противнику
	int FIELD_WIGHT = 1500;	// длина игрового поля (она же длина панели)
	int FIELD_HEIGHT = 800; // ширина игрового поля
	int PANEL_HEIGHT = 100;	// высота панели

}

public class Main
{
	public static void main(String[] args)
	{
		Field field = new Field();
	}
}
