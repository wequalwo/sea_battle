package com.company;

/**
 * Класс Observer хранит поле в виле масства int
 * и отвечает за логику проверки возможности установки корабля на определенное поле
 * <p>
 * TODO: а также проверяет, собарн ли полный набор кораблей у оппонента
 * TODO: запоняет поля соперника
 */
abstract public class Observer implements def
{
	protected int[][] sea;            // массив статусов клеток для визуализации кораблей
	public Set_of_ships set;        // набор кораблей

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
	public boolean get_game_status()
	{
		return set.get_counter() != 0;
	}

	public int get_sea(int i, int j)
	{
		return sea[i][j];
	}
}