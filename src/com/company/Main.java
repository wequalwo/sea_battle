package com.company;

interface def
{
	int DELTA = 1;
	int MISS = -1;
	int HIT = 0;
	int DEAD = -1;
	int ALIVE = 1;
	int SUNKEN = 1;
	int EDGE = 10;
	int CHANGE = 1;  //передача хода
	int NO_CHANGE = 0; // ход остается
	boolean MINE = false;
	boolean OPP = true;
/*	final private static int injured = 2;
	private static int killed = 3;*/

}

public class Main
{
	public static void main(String[] args)
	{
		Field field = new Field();
	}
}
