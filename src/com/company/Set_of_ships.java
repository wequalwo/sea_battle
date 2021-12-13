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
	 *
	 * @param sea статусы клеток
	 * @return Ok или нет
	 */
	protected boolean create(int[][] sea)
	{
		int[] numbers = new int[]{4, 7, 9, 10};
		for (int i = 0; i < 10; i++)
		{
			System.arraycopy(sea[i], 0, ships_map[i], 0, 10);
		}
		//обработка по строкам
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
		//оработка нижнего правого угла
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
		//обработка нижнего правого угла
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
		//перенумировка единичных кораблей
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
		//проверка корректности длин
		int[] tmp = new int[]{0, 4, 7, 9};
		for (int i = 0; i < 4; i++)
		{
			if (numbers[i] != tmp[i])
				return FAIL;
		}
		// если все хорошо - ставим число кораблей равным полному сету и возвращаем OK
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

	/**
	 * @param corpse массив, содержащий клетки затопленного судна
	 * @return массив клеток окружения
	 */
	protected ArrayList<int[]> check_neighbors(ArrayList<int[]> corpse)
	{
		ArrayList<int[]> neighbors = new ArrayList<>();
		int direction;
		if (corpse.size() == 1)
		{
			direction = line;
		} else
		{
			if (corpse.get(0)[0] == corpse.get(1)[0])
				direction = line;
			else
				direction = column;
		}
		if (direction == column)
		{
			for (int[] ints : corpse)
			{
				neighbors.add(new int[]{ints[0], ints[1] + 1});
				neighbors.add(new int[]{ints[0], ints[1] - 1});
			}
			for (int i = -1; i <= 1; i++)
			{
				neighbors.add(new int[]{corpse.get(0)[0] - 1, corpse.get(0)[1] + i});
				neighbors.add(new int[]{corpse.get(corpse.size() - 1)[0] + 1, corpse.get(corpse.size() - 1)[1] + i});
			}
		} else
		{
			for (int[] ints : corpse)
			{
				neighbors.add(new int[]{ints[0] + 1, ints[1]});
				neighbors.add(new int[]{ints[0] - 1, ints[1]});
			}
			for (int i = -1; i <= 1; i++)
			{
				neighbors.add(new int[]{corpse.get(0)[0] + i, corpse.get(0)[1] - 1});
				neighbors.add(new int[]{corpse.get(corpse.size() - 1)[0] + i, corpse.get(corpse.size() - 1)[1] + 1});
			}
		}
		ArrayList<int[]> result = new ArrayList<>();
		for (int[] neighbor : neighbors)
		{
			if (!(neighbor[0] < 1 || neighbor[1] < 1 || neighbor[0] > 10 || neighbor[1] > 10))
				result.add(neighbor);
		}
		return result;
	}

	/**
	 * Метод, отвечающий за удар по судну
	 *
	 * @param i - координата y
	 * @param j - - координата x
	 * @return ArrayList со статусом в случае попадания или промаха или все клетки уничтоженого судна
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
		// TODO <-
		return cord;
	}

	/**
	 * @return количество "живых" кораблей
	 */
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
	 *
	 * @return статус судна
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