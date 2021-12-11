package com.company;
/**
 * TODO: work work work wolk wolk wolk
 */

import java.util.Random;

public class Opponent implements def
{
	private Field field;
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
		map[previous_shot[0]][previous_shot[1]] = status;
		System.out.println(status);
		return status;
	}

	public int force_move()
	{
		int i = random.nextInt(def.EDGE);
		int j = random.nextInt(def.EDGE);
		while (map[i][j] != 7)
		{
			i = random.nextInt(def.EDGE);
			j = random.nextInt(def.EDGE);
		}
		previous_shot = new int[]{i, j};
		return save(field.hit(previous_shot));
	}
}
