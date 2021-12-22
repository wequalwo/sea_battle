package com.company;

import java.util.Random;

/**
 * It controls AI.
 * But what did u think? We've really got to keep an eye on AI, otherwise... Well, u know
 * Yeah, i got that this planning of classes is weird. But it reminds me the chains of Tai Lung from Kung Fu Panda
 * funny, isn't it?
 */
public class Opponent implements def
{
	/**
	 * field, witch contains this class
	 */
	protected final Field field;        // поле, на котором происходит действие (для координации между классами)
	/**
	 * it keeps previous shot coordinates
	 */
	private int[] previous_shot;      // сохраненный предыдущий выстрел
	/**
	 * random to create some fun
	 */
	private final Random random;
	/**
	 * fight map to save result of previous shoots
	 */
	private final int[][] map;        // карта высрелов
	/**
	 * directions:
	 * indicates the direction in which the extension of the wrecked vessel is believed to be
	 */
	private int direction;            // направление выстрелов - для добивания подбитых судов
	/**
	 * is the direction reliable
	 */
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
	}

	/**
	 * It keeps a status
	 *
	 * @param status status received from the observer
	 * @return status, but also it can return end-game-status
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
		return future[0] < 0 || future[0] > 9 || future[1] < 0 || future[1] > 9;
	}

	/**
	 * It generate a shot
	 *
	 * @return result of the shot
	 * DEAD
	 * SUNKEN
	 * or
	 * MISS
	 * @see com.company.Main (def)
	 * Don't try to figure it out, save your eye!
	 */
	public int force_move()
	{
		if (map[previous_shot[0]][previous_shot[1]] == SUNKEN)
		{
			int[] future = new int[]{previous_shot[0], previous_shot[1]};
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

				if (b(future))
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
				if(map[future[0]][future[1]] == MISS)
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
			}

			int rez = field.hit(future);
			int regulate = save(rez);
			map[future[0]][future[1]] = rez;
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

						if (b(future))
						{
							direction = (direction + 2) % 4;
							future[0] = previous_shot[0];
							future[1] = previous_shot[1];
						}
					}
					if (direction == UP)
						future[0]++;
					else if (direction == DOWN)
						future[0]--;
					else if (direction == LEFT)
						future[1]++;
					else
						future[1]--;
					future[0] = previous_shot[0];
					future[1] = previous_shot[1];
				} else if (rez == DEAD)
				{
					direction = UNDEF;
					previous_shot[0] = future[0];
					previous_shot[1] = future[1];
				} else
				{
					previous_shot[0] = future[0];
					previous_shot[1] = future[1];
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
				} else
				{
					reliable = true;
					previous_shot[0] = future[0];
					previous_shot[1] = future[1];
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
		return save(field.hit(previous_shot));
	}

	/**
	 * It turns off some cells
	 *
	 * @param point coordinates of the cells
	 */
	public void disable(int[] point)
	{
		map[point[0]][point[1]] = MISS;
	}

	/**
	 * It inverts the game status
	 *
	 * @param who who won?
	 */
	protected void force_stop(boolean who)
	{

		field.over(who);
	}
}
