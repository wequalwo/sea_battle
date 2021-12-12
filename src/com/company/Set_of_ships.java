package com.company;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Класс содержащий всю информацию о расположении судов и их статусах
 */
public class Set_of_ships implements def
{
	private int[][] ships_map; // так хранить корабли - алоритмически не самое удачное решение, наверное
	final private ArrayList<Ship> ships;
	private int counter;

	Set_of_ships(boolean whose)
	{
		counter = 0;
		ships = new ArrayList<>();
		for (int i = 0; i < 4; i++)
		{
			for (int j = i; j < 4; j++)
			{
				ships.add(new Ship(i + 1));
			}
		}
		if (whose == MINE)
		{
			ships_map = new int[][]
					{
							{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
					};
		} else
		{
			generate();
		}
	}

	/**
	 * Метод, создающий массив для игры по массиву статусов клеток
	 * массив игры содержит не только информацию о положении кораблей, но и ифнормации о порядковом номере суда
	 * @param sea
	 * @return
	 */
	protected boolean create(int[][] sea)
	{
		int[] numbers = new int[]{4, 7, 9, 10};
		for (int i = 0; i < 10; i++)
		{
			System.arraycopy(sea[i], 0, ships_map[i], 0, 10);
		}

		ArrayList<int[]> length = new ArrayList<>();
		for (int i = 0; i < 10; i++)
		{
			for (int j = 1; j < 10; j++)
			{
				if (ships_map[i][j - 1] == 1 && ships_map[i][j] == 1)
				{
					if (length.size() == 0)
					{
						length.add(new int[]{i, j - 1});
					}
					length.add(new int[]{i, j});
				} else
				{
					if (length.size() != 0)
					{
						for (int k = 0; k < length.size(); k++)
						{
							ships_map[length.get(k)[0]][length.get(k)[1]] = numbers[length.size() - 1];
						}
						numbers[length.size() - 1]--;
						length.clear();
					}
				}
			}
		}
		if (length.size() != 0)
		{
			for (int k = 0; k < length.size(); k++)
			{
				ships_map[length.get(k)[0]][length.get(k)[1]] = numbers[length.size() - 1];
			}
			numbers[length.size() - 1]--;
			length.clear();
		}
		length.clear();
		for (int i = 0; i < 10; i++)
		{
			for (int j = 1; j < 10; j++)
			{
				if (ships_map[j - 1][i] == 1 && ships_map[j][i] == 1)
				{
					if (length.size() == 0)
					{
						length.add(new int[]{j - 1, i});
					}
					length.add(new int[]{j, i});
				} else
				{
					if (length.size() > 4)
						return FAIL;
					if (length.size() != 0)
					{
						for (int k = 0; k < length.size(); k++)
						{
							ships_map[length.get(k)[0]][length.get(k)[1]] = numbers[length.size() - 1];
						}
						numbers[length.size() - 1]--;
						length.clear();
					}
				}
			}
		}
		if (length.size() != 0)
		{
			for (int k = 0; k < length.size(); k++)
			{
				ships_map[length.get(k)[0]][length.get(k)[1]] = numbers[length.size() - 1];
			}
			numbers[length.size() - 1]--;
			length.clear();
		}
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (ships_map[i][j] == 1)
				{
					ships_map[i][j] = numbers[0];
					numbers[0]--;
				}
			}
		}

/*		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				System.out.print(ships_map[i][j] + "(" + sea[i][j] + ")" + " ");
			}
			System.out.println();
		}*/

		int[] tmp = new int[]{0, 4, 7, 9};
		for (int i = 0; i < 4; i++)
		{
			if (numbers[i] != tmp[i])
				return FAIL;
		}
		counter = 10;
		return OK;
	}

	/**
	 * генерирует поле автоматически
	 */
	private void generate() //генерация поля соперника (TODO: сделсть рандомное заполнение)
	{
		ships_map =
				new int[][]{
						{0, 0, 0, 10, 10, 10, 10, 0, 0, 0},
						{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						{1, 0, 8, 8, 8, 0, 0, 0, 0, 0},
						{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						{0, 0, 5, 5, 0, 9, 0, 7, 7, 0},
						{2, 0, 0, 0, 0, 9, 0, 0, 0, 0},
						{0, 0, 0, 0, 0, 9, 0, 0, 0, 0},
						{3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						{0, 0, 0, 0, 6, 0, 4, 0, 0, 0},
						{0, 0, 0, 0, 6, 0, 0, 0, 0, 0}
				};
		counter = 10;
	}

	//TODO: не забыть добавить, когда будет написан класс судьи
	private int check_neighbors(int i, int j)
	{
		for (int p = Math.max(i - 1, 0); p <= Math.min(i + 1, 9); p++)
		{
			for (int o = Math.max(j - 1, 0); o <= Math.min(j + 1, 9); o++)
			{
				if (ships_map[p][o] == -1)
					return NO_CHANGE;
			}
		}
		return CHANGE;
	}

	/**
	 * Метод, отвечающий за удар по судну
	 * возвращает ArrayList со статусом в случае попадания или промаха
	 * или все клетки уничтоженого судна
	 * @param i
	 * @param j
	 * @return
	 * @throws Exception
	 */
	protected ArrayList<int[]> shot(int i, int j) throws Exception
	{
		if (counter <= 0)
		{
			throw new IOException();
		}
		ArrayList<int[]> cord = new ArrayList<>();
		if (ships_map[i][j] == 0)
		{
			cord.add(new int[]{MISS, MISS});
			return cord;
		}
		// ->TODO: адекватное убийство
		if (ships.get(ships_map[i][j] - 1).wound() == SUNKEN)
		{
			cord.add(new int[]{SUNKEN, SUNKEN});
			return cord;
		}
		int number = ships_map[i][j];
		for (int p = 0; p < 10; p++)
		{
			for (int o = 0; o < 10; o++)
			{
				if (ships_map[p][o] == number)
				{
					cord.add(new int[]{p + DELTA, o + DELTA});
					ships_map[p][o] = DEAD;
				}
			}
		}
		counter--;
		System.out.println("-1");
		// TODO <-
		return cord;
	}
	public int get_counter()
	{
		return counter;
	}

}

/**
 * класс, отвечаюший за здоровье каждого судна
 */
class Ship implements def
{
	private int health;

	Ship(int size)
	{
		health = size;
	}

	/**
	 * Метод, непосредственно описывающий ранение
	 * @return
	 */
	protected int wound()
	{
		health--;
		if (health == 0)
		{
			return DEAD;
		}
		return SUNKEN;
	}
}