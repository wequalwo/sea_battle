package com.company;

class Observer
{
	private int[][] sea;

	Observer()
	{
		sea = new int[10][10];
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				sea[i][j] = 0;
			}
		}
	}

	private boolean lines(int i, int j)
	{
		//TODO: проверка на длину и загибания
		return true;
	}

	private boolean is_legal(int i, int j)
	{
		if (i + 1 < 9 && j + 1 <= 9)
		{
			if (sea[i + 1][j + 1] == 1)
				return false;
		}
		if (i + 1 <= 9 && j - 1 >= 0)
		{
			if (sea[i + 1][j - 1] == 1)
				return false;
		}
		if (i - 1 >= 0 && j + 1 <= 9)
		{
			if (sea[i - 1][j + 1] == 1)
				return false;
		}
		if (i - 1 >= 0 && j - 1 >= 0)
		{
			if (sea[i - 1][j - 1] == 1)
				return false;
		}
		if (!lines(i, j))
			return false;
		return true;
	}

	public int check_sea(int i, int j)
	{
		if (!is_legal(i, j))
			return -1;
		return sea[i][j];

	}

	public void invert_point(int i, int j)
	{
		sea[i][j] = Math.abs(Math.abs(sea[i][j]) - 1);
	}

}
