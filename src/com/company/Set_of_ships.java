package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Класс содержащий всю информацию о расположении судов и их статусах
 */
public class Set_of_ships implements def
{
	private final int[][] ships_map; // так хранить корабли - алоритмически не самое удачное решение, наверное
	final private ArrayList<Ship> ships;
	private int counter;

	private final int tmp_set = 147;
	private final Random random;
	private int CEIL;

	Set_of_ships(boolean whose)
	{
		CEIL = 100;
		random = new Random();
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
			ships_map = new int[10][10];
			int counter = 0;
			for (int i = 0; i < 10; i++)
			{
				for (int j = 0; j < 10; j++)
				{
					ships_map[i][j] = counter;
					counter++;
				}
			}
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
	 * пересчет временных номеров клеток после установки судна
	 */
	private void recall()
	{
		int counter = 0;
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (ships_map[i][j] != 100 && ships_map[i][j] != -100)
				{
					ships_map[i][j] = counter;
					counter++;
				}
			}
		}
	}

	/**
	 * заполнение клеток окружения
	 */
	private void tmp_to_over()
	{
		ArrayList<int[]> corp = new ArrayList<>();
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (ships_map[i][j] == tmp_set)
				{
					ships_map[i][j] = 100;
					corp.add(new int[]{i + 1, j + 1});
				}
			}
		}
		corp = check_neighbors(corp);
		for (int[] ints : corp)
		{
			ships_map[ints[0] - 1][ints[1] - 1] = -100;
		}
	}

	/**
	 * Сброс клетки
	 *
	 * @param num временный номер клетки
	 */
	private void set_into(int num)
	{
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (ships_map[i][j] == num)
				{
					ships_map[i][j] = -100;
					recall();
					return;
				}
			}
		}
		recall();
	}

	/**
	 * непосредсвтенная попытка установки судна
	 */
	private boolean reset_into(int num, int pale)
	{
		int x = 0;
		int y = 0;
		boolean sts = false;
		for (int i = 0; i < 10 && !sts; i++)
		{
			for (int j = 0; j < 10 && !sts; j++)
			{
				if (ships_map[i][j] == num)
				{
					y = i;
					x = j;
					sts = true;
				}
			}
		}
		if (!sts)
			return true;
		int direction = random.nextInt(4);

		for (int i = 0; i < 4; i++, direction = (direction + 1) % 4, sts = true)
		{
			if (direction == UP)
			{
				for (int j = 0; j < pale; j++)
				{
					if (y - j < 0)
					{
						sts = false;
						break;
					}
					if (ships_map[y - j][x] == 100 || ships_map[y - j][x] == -100)
					{
						sts = false;
						break;
					}
				}
				if (sts)
				{
					for (int c = 0; c < pale; c++)
					{
						ships_map[y - c][x] = tmp_set;
					}
					tmp_to_over();

					return false;
				}
			} else if (direction == LEFT)
			{
				for (int j = 0; j < pale; j++)
				{
					if (x - j < 0)
					{
						sts = false;
						break;
					}
					if (ships_map[y][x - j] == 100 || ships_map[y][x - j] == -100)
					{
						sts = false;
						break;
					}
				}
				if (sts)
				{
					for (int c = 0; c < pale; c++)
					{
						ships_map[y][x - c] = tmp_set;
					}
					tmp_to_over();
					return false;
				}
			} else if (direction == DOWN)
			{
				for (int j = 0; j < pale; j++)
				{
					if (y + j > 9)
					{
						sts = false;
						break;
					}
					if (ships_map[y + j][x] == 100 || ships_map[y + j][x] == -100)
					{
						sts = false;
						break;
					}
				}
				if (sts)
				{
					for (int c = 0; c < pale; c++)
					{
						ships_map[y + c][x] = tmp_set;
					}
					tmp_to_over();
					return false;
				}
			} else if (direction == RIGHT)
			{
				for (int j = 0; j < pale; j++)
				{
					if (x + j > 9)
					{
						sts = false;
						break;
					}
					if (ships_map[y][x + j] == 100 || ships_map[y][x + j] == -100)
					{
						sts = false;
						break;
					}
				}
				if (sts)
				{
					for (int c = 0; c < pale; c++)
					{
						ships_map[y][x + c] = tmp_set;
					}
					tmp_to_over();
					return false;
				}
			}
		}

		return true;
	}

	//приведение поля к станадртному виду формата sea[]
	private void clear()
	{
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (ships_map[i][j] == 100)
					ships_map[i][j] = 1;
				else
					ships_map[i][j] = 0;
			}
		}
	}

	/**
	 * генерирует поле автоматически
	 */
	private void generate()
	{
		// расставляем однопалубники
		for (int i = 0; i < 4; i++)
		{
			int num = random.nextInt(CEIL);
			while (reset_into(num, 1))
			{
				set_into(num);
				CEIL--;
				num = random.nextInt(CEIL);
			}
			CEIL -= 1;
		}
		// расставляем двухпалубники
		for (int i = 0; i < 3; i++)
		{
			int num = random.nextInt(CEIL);
			while (reset_into(num, 2))
			{
				set_into(num);
				CEIL--;
				num = random.nextInt(CEIL);
			}
			CEIL -= 2;
		}
		// расставляем трехпалубники
		for (int i = 0; i < 2; i++)
		{
			int num = random.nextInt(CEIL);
			while (reset_into(num, 3))
			{
				set_into(num);
				CEIL--;
				num = random.nextInt(CEIL);
			}
			CEIL -= 3;
		}
		// расставляем четырехпалубники
		int num = random.nextInt(CEIL);
		while (reset_into(num, 4))
		{
			set_into(num);
			CEIL--;
			num = random.nextInt(CEIL);
		}
		clear();
		create(ships_map);
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