package com.company;

import java.util.ArrayList;

/**
 * Class observer for the connection between boards and sets of ships
 */
abstract public class Observer implements def
{
	/**
	 * cells. It contains some ships
	 */
	protected int[][] sea;            // массив статусов клеток для визуализации кораблей
	/**
	 * Set_of_ships class
	 *
	 * @see com.company.Set_of_ships
	 */
	public Set_of_ships set;            // набор кораблей

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

	/**
	 * @return false if there are not ships anymore
	 */
	public boolean get_if_zero_counter()
	{
		return set.get_counter() == 0;
	}

	/**
	 * @param i coordinate: y
	 * @param j coordinate: x
	 * @return status in sea[y][x]
	 */
	public int get_sea(int i, int j)
	{
		return sea[i][j];
	}

	/**
	 * necessary for correct play
	 *
	 * @param corpse flooded ship array
	 * @return all his neighbors
	 */
	public ArrayList<int[]> fill(ArrayList<int[]> corpse)
	{
		return set.check_neighbors(corpse);
	}

	/**
	 * describes a blow
	 *
	 * @param i coordinate of the hit: y
	 * @param j coordinate of the hit: x
	 * @return array of the cages:
	 * {flooded ship cages} - if the blow killed
	 * {SUNKEN, SUNKEN} - if the blow hurt
	 * {0, 0} - if it missed
	 * @throws Exception внутренняя ошибка
	 */
	public ArrayList<int[]> shot(int i, int j) throws Exception
	{
		return set.shot(i, j);
	}
}