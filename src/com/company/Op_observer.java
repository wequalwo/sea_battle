package com.company;

import java.util.ArrayList;

public class Op_observer extends Observer
{
	Op_observer(boolean whose)
	{
		super(whose);
	}

	public ArrayList<int[]> shot(int i, int j) throws Exception
	{
		return set.shot(i, j);
	}

	public int get_counter()
	{
		return set.get_counter();
	}
}