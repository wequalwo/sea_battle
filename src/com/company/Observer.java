package com.company;

/**
 * Клсаа Observer хранит поле в виле масства int
 * и отвечает за логику проверки возможности установки корабля на определенное поле
 *
 * TODO: а также проверяет, собарн ли полный набор кораблей у оппонента
 * TODO: запоняет поля соперника
 */
class Observer
{
	private int[][] sea;	//массив статусов клеток

	Observer()
	{
		sea = new int[10][10];
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
	private boolean lines(int i, int j)
	{
		//TODO: проверка на длину и загибания
		return true;
	}

	/**
	 * Проверка на воможностть установки части корабля на данную клетку
	 * @param i
	 * @param j
	 * @return status
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
			if (sea[i - 1][j - 1] == 1)
				return false;
		}
		if (!lines(i, j))
			return false;
		return true;
	}

	/**
	 * Метод check_sea возвращает -1, если в принятую клетку установить часть судна невозможно
	 * и статус клетки, если возможно:
	 * (1) - уже что-то стоит
	 * (0) - клетка путса
	 * @param i
	 * @param j
	 * @return
	 */
	public int check_sea(int i, int j)
	{
		if (!is_legal(i, j))
			return -1;
		return sea[i][j];

	}

	/**
	 * Инвертирует данное поле (устанавливает/снимает метку судна)
	 * @param i
	 * @param j
	 */
	public void invert_point(int i, int j)
	{
		sea[i][j] = Math.abs(Math.abs(sea[i][j]) - 1);
	}

}
