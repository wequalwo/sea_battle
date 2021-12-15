package com.company;

public class Op_observer extends Observer
{
	Op_observer(boolean whose)
	{
		super(whose);
	}

	/**
	 * @return counter
	 */
	public int get_counter()
	{
		return set.get_counter();
	}
}