package com.company;

import java.util.ArrayList;

/**
 * Класс Observer хранит поле в виле масства int
 * и отвечает за логику проверки возможности установки корабля на определенное поле
 * <p>
 * TODO: а также проверяет, собарн ли полный набор кораблей у оппонента
 * TODO: запоняет поля соперника
 */
abstract public class Observer implements def
{
	protected int[][] sea;    		// массив статусов клеток для визуализации кораблей
	protected Set_of_ships set;		// набор кораблей
	Observer(boolean whose)
	{
		sea = new int[10][10];
		set = new Set_of_ships(whose);
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				sea[i][j] = 0;
			}
		}
	}
	public int get_sea(int i, int j)
	{
		return sea[i][j];
	}
}
class My_observer extends Observer
{
	My_observer(boolean whose)
	{
		super(MINE);
	}
/*	private boolean lines(int i, int j)
	{
		//TODO: проверка на длину и загибания
		return true;
	}*/
	/**
	 * Проверка на воможностть установки части корабля на данную клетку
	 */
	private boolean is_legal(int i, int j)
	{
		if (i + 1 < 9 && j + 1 <= 9)
		{
			if (sea[i + 1][j + 1] == 1)
				return false;
		}
		if (i + 1 <= 9 && j - 1 >= 0)
		{
			if (sea[i + 1][j - 1] == 1)
				return false;
		}
		if (i - 1 >= 0 && j + 1 <= 9)
		{
			if (sea[i - 1][j + 1] == 1)
				return false;
		}
		if (i - 1 >= 0 && j - 1 >= 0)
		{
			return sea[i - 1][j - 1] != 1;
		}
		return true;
	}
	/**
	 * Метод check_sea возвращает -1, если в принятую клетку установить часть судна невозможно
	 * и статус клетки, если возможно:
	 * (1) - уже что-то стоит
	 * (0) - клетка путса
	 */
	public int check_sea(int i, int j)
	{
		if(sea[i][j] == 1)
			return 1;
		if (!is_legal(i, j))
			return -1;
		return 0;
	}
	/**
	 * Инвертирует данное поле (устанавливает/снимает метку судна)
	 */
	public void invert_point(int i, int j)
	{
		sea[i][j] = Math.abs(Math.abs(sea[i][j]) - 1);
	}

}
class Op_observer extends Observer
{
	Op_observer(boolean whose)
	{
		super(OPP);
	}
	public ArrayList<int[]> shot(int i, int j) throws Exception
	{
		return set.shot(i, j);
	}
}