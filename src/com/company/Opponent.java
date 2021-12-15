package com.company;

import java.util.Random;

/**
 * Класс, отвечающий за поведение AI
 */
public class Opponent implements def
{
	private final Field field;        // поле, на котором происходит действие (для координации между классами)
	private int[] previous_shot;      // сохраненный предыдущий выстрел
	private final Random random;
	private final int[][] map;        // карта высрелов
	private int direction;            // направление выстрелов - для добивания подбитых судов
	private boolean reliable;         // надежность направлния

	Opponent(Field field)
	{
		this.field = field;
		previous_shot = new int[]{0, 0};
		random = new Random();
		direction = UNDEF;
		reliable = false;
		map = new int[][]
				{
						{7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
						{7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
						{7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
						{7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
						{7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
						{7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
						{7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
						{7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
						{7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
						{7, 7, 7, 7, 7, 7, 7, 7, 7, 7}
				};
		direction = UNDEF;
	}

	/**
	 * Метод схоранения статуса клетки
	 *
	 * @param status статус, полученный от обсервера
	 * @return тот же статус, но корреткирует его у случае, если кораблей не осталось
	 */
	private int save(int status)
	{
		if (status == it_is_the_end)
		{
			map[previous_shot[0]][previous_shot[1]] = DEAD;
			status = MISS;
		} else
			map[previous_shot[0]][previous_shot[1]] = status;
		return status;
	}

	private boolean b(int[] future)
	{
		return future[0] >= 0 && future[0] <= 9 && future[1] >= 0 && future[1] <= 9;
	}

	/**
	 * Метод генерации выстрела
	 *
	 * @return результат выстрела:
	 * DEAD - корабль был убит
	 * SUNKEN - корабль подбит
	 * MISS - промах
	 */
	public int force_move()
	{
		System.out.println("Previous shot: " + previous_shot[0] + " " + previous_shot[1] + ", here is " + map[previous_shot[0]][previous_shot[1]]);
		if (map[previous_shot[0]][previous_shot[1]] == SUNKEN)
		{
			int[] future = new int[]{previous_shot[0], previous_shot[1]};
			//boolean b = future[0] >= 0 && future[0] <= 9 && future[1] >= 0 && future[1] <= 9;

			if (direction == UNDEF)
			{
				direction = random.nextInt(4);
				reliable = false;
			}

			while (map[future[0]][future[1]] != 7)
			{
				if (direction == UP)
					future[0]--;
				else if (direction == DOWN)
					future[0]++;
				else if (direction == LEFT)
					future[1]--;
				else
					future[1]++;

				if (!b(future))
				{
					if (reliable)
					{
						direction = (direction + 2) % 4;
					} else
					{
						direction = (direction + 1) % 4;
					}
					future[0] = previous_shot[0];
					future[1] = previous_shot[1];
				}
				System.out.println("b is " + b(future) + " and future is (" + future[0] + ", " + future[1] + ")");
			}

			int rez = field.hit(future);
			int regulate = save(rez);
			map[future[0]][future[1]] = rez;
			System.out.println("in " + future[0] + " " + future[1] + " " + map[future[0]][future[1]]);
			map[previous_shot[0]][previous_shot[1]] = SUNKEN;

			if (reliable)
			{
				if (rez == MISS)
				{
					direction = (direction + 2) % 4;
					while (map[future[0]][future[1]] != 7)
					{
						if (direction == UP)
							future[0]--;
						else if (direction == DOWN)
							future[0]++;
						else if (direction == LEFT)
							future[1]--;
						else
							future[1]++;
					}
					if (direction == UP)
						future[0]++;
					else if (direction == DOWN)
						future[0]--;
					else if (direction == LEFT)
						future[1]++;
					else
						future[1]--;
					if (!b(future))
					{
						System.out.println("130 Error, fuck u!");
					}
					future[0] = previous_shot[0];
					future[1] = previous_shot[1];
				} else if (rez == DEAD)
				{
					direction = UNDEF;
					previous_shot[0] = future[0];
					previous_shot[1] = future[1];
					System.out.println("CHANGED because of dead!");
				} else
				{
					previous_shot[0] = future[0];
					previous_shot[1] = future[1];
					System.out.println("CHANGED because of sunken");
				}
			} else
			{
				if (rez == MISS)
				{
					direction = (direction + 1) % 4;
					reliable = false;
				} else if (rez == DEAD)
				{
					direction = UNDEF;
					previous_shot[0] = future[0];
					previous_shot[1] = future[1];
					System.out.println("CHANGED because of dead instant");
				} else
				{
					reliable = true;
					previous_shot[0] = future[0];
					previous_shot[1] = future[1];
					System.out.println("CHANGED because of sunken instant");
				}
			}
			field.rep();
			return regulate;
		} else
		{
			int i = random.nextInt(EDGE);
			int j = random.nextInt(EDGE);
			while (map[i][j] != 7)
			{
				i = random.nextInt(EDGE);
				j = random.nextInt(EDGE);
			}
			previous_shot = new int[]{i, j};
			direction = UNDEF;
		}
		field.rep();
		int a = 0;
		return save(field.hit(previous_shot));
	}

	/**
	 * Метод "выключения" клетки - ставит в соответствующиую клетку map MISS
	 *
	 * @param point координаты клетки
	 */
	public void disable(int[] point)
	{
		map[point[0]][point[1]] = MISS;
	}

	/**
	 * Установка статуса окончания игры
	 *
	 * @param who - кто выиграл
	 */
	protected void force_stop(boolean who)
	{

		field.over(who);
	}
}
