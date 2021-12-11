package com.company;
import java.io.IOException;
import java.util.ArrayList;

public class Set_of_ships implements def
{
	private int[][] ships_map;
	final private ArrayList<Ship> ships;
	private int counter;
	Set_of_ships(boolean whose)
	{
		counter = 0;
		ships = new ArrayList<>();
		for(int i = 0; i < 4; i++)
		{
			for (int j = i; j < 4; j++)
			{
				ships.add(new Ship(i + 1));
			}
		}
		if(whose == MINE)
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
		}
		else
		{
			generate();
		}
	}
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
	//TODO: не забыть добавить, когда будет написан класс судьи
	private int check_neighbors(int i, int j)
	{
		for(int p = Math.max(i - 1, 0); p <= Math.min(i + 1, 9); p++)
		{
			for(int o = Math.max(j - 1, 0); o <= Math.min(j + 1, 9); o++)
			{
				if(ships_map[p][o] == -1)
					return NO_CHANGE;
			}
		}
		return CHANGE;
	}
	protected ArrayList<int[]> shot(int i, int j) throws Exception
	{
		if(counter < 10)
		{
			throw new IOException();
		}

		ArrayList<int[]> cord = new ArrayList<>();
		if(ships_map[i][j] == 0)
		{
			cord.add(new int[]{MISS, MISS});
			return cord;
		}
		// ->TODO: адекватное убийство
		if(ships.get(ships_map[i][j] - 1).wound() == ALIVE)
		{
			cord.add(new int[]{HIT, HIT});
			return cord;
		}
		int number = ships_map[i][j];
		for(int p = 0; p < 10; p++)
		{
			for(int o = 0; o < 10; o++)
			{
				if(ships_map[p][o] == number)
				{
					cord.add(new int[]{p + DELTA, o + DELTA});
					ships_map[p][o] = SUNKEN;
				}
			}
		}
		// TODO <-
		return cord;
	}
}

class Ship implements def
{
	private int health;
	Ship(int size)
	{
		health = size;
	}
	protected int wound()
	{
		health--;
		if(health == 0)
		{
			return DEAD;
		}
		return ALIVE;
	}
}