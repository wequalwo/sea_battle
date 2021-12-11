package com.company;

public class My_observer extends Observer
{
	My_observer(boolean whose)
	{
		super(whose);
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
		if (sea[i][j] == 1)
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
		sea[i][j] = Math.abs(Math.abs(sea[i][j]) - DELTA);
	}

}
