package com.company;

public class My_observer extends Observer
{
	My_observer(boolean whose)
	{
		super(whose);
	}

	/**
	 * It checks possibility of creating a ship here
	 * It does not check a length of the ship, checking of the length is a duty of another class
	 *
	 * @param i coordinate: y
	 * @param j coordinate: x
	 * @return boolean можно или нельзя
	 * @see com.company.Set_of_ships#create(int[][])
	 */
	private boolean is_legal(int i, int j)
	{
		if (i + 1 <= 9 && j + 1 <= 9)
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
			return sea[i - 1][j - 1] != 1;
		}
		return true;
	}

	/**
	 * it creates a fleet
	 *
	 * @return boolean is it possible?
	 * @see com.company.Set_of_ships#create(int[][])
	 */
	public boolean create_fleet()
	{
		return set.create(sea);
	}

	/**
	 * checks if it is possible to create a ship here
	 *
	 * @param i coordinate: y
	 * @param j coordinate: x
	 * @return is it possible?
	 */
	public int check_sea(int i, int j)
	{
		if (sea[i][j] == 1)
			return 1;
		if (!is_legal(i, j))
			return -1;
		return 0;
	}


	/**
	 * inverts the position
	 *
	 * @param i coordinate: y
	 * @param j coordinate: x
	 */
	public void invert_point(int i, int j)
	{
		sea[i][j] = Math.abs(Math.abs(sea[i][j]) - DELTA);
	}
}
