package com.company;


/**
 * TODO: work work work wolk wolk wolk
 */

import java.util.Random;

public class Opponent
{
	private Random random;
	Opponent()
	{
		random = new Random();
	}
	public int[] force_move()
	{
		int i = random.nextInt(def.EDGE);
		int j = random.nextInt(def.EDGE);
		return (new int[]{i, j});
	}
}
