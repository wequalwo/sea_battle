package com.company;


/**
 * TODO: work work work wolk wolk wolk
 */

import java.util.Random;

public class Opponent implements def
{
	private final Random random;
	private final int[][] map;
	Opponent()
	{
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
	public int[] force_move()
	{
		int i = random.nextInt(def.EDGE);
		int j = random.nextInt(def.EDGE);
		while (map[i][j] != 7)
		{
			i = random.nextInt(def.EDGE);
			j = random.nextInt(def.EDGE);
		}
		return (new int[]{i, j});
	}
	public boolean save(int[] pos, int status)
	{
		map[pos[0]][pos[1]] = status;
		System.out.println(status);
		if(status == MISS)
			return false;
		return true;
	}
}
