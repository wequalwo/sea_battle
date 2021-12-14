package com.company;

import java.util.Random;

//TODO: осмысленный выстрел
public class Opponent implements def
{
	private final Field field;
	private int[] previous_shot;
	private final Random random;
	private final int[][] map;

	Opponent(Field field)
	{
		this.field = field;
		previous_shot = new int[]{-1, -1};
		random = new Random();
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

	public int force_move()
	{
		int i = random.nextInt(EDGE);
		int j = random.nextInt(EDGE);
		while (map[i][j] != 7)
		{
			i = random.nextInt(EDGE);
			j = random.nextInt(EDGE);
		}
		previous_shot = new int[]{i, j};
		return save(field.hit(previous_shot));
	}
	public void disable(int[] point)
	{
		map[point[0]][point[1]] = MISS;
	}
	protected void force_stop(boolean who)
	{

		field.over(who);
	}
}
