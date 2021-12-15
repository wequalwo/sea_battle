package com.company;

import java.util.ArrayList;

/**
 * Класс Observer хранит поле в виле масства int
 * и отвечает за логику проверки возможности установки корабля на определенное поле
 * <p>
 * TODO: а также проверяет, собарн ли полный набор кораблей у оппонента
 * TODO: запоняет поля соперника
 */
abstract public class Observer implements def
{
	protected int[][] sea;            // массив статусов клеток для визуализации кораблей
	public Set_of_ships set;        // набор кораблей

	Observer(boolean whose)
	{
		sea = new int[10][10];
		set = new Set_of_ships(whose);
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				sea[i][j] = 0;
			}
		}
	}

	/**
	 * получения статуса игры
	 * @return 0 - если на поле, которому принадлежит обсервер, не осталось кораблей
	 */
	public boolean get_if_zero_counter()
	{
		return set.get_counter() == 0;
	}


	/**
	 * возвращает значение в клетке поля по координатам
	 * @param i координата y
	 * @param j координата x
	 * @return статус
	 */
	public int get_sea(int i, int j)
	{
		return sea[i][j];
	}

	/**
	 * Матод, возвращающий все клетки, вокруг уничтоженного судна
	 * @param corpse массив клеток уничтоженного судна
	 * @return все клетки, вокруг уничтоженного судна
	 */
	public ArrayList<int[]> fill(ArrayList<int[]> corpse)
	{
		return set.check_neighbors(corpse);
	}

	/**
	 * метод, описывающий удар ПО полю
	 * @param i координата y удара
	 * @param j координата x удара
	 * @return массив клеток: из одного элемента формата:
	 * {координаты убитого судна} - если удар уничтожил судно
	 * {SUNKEN, SUNKEN} - если удар подбил судно
	 * {0, 0} - если удар мимо
	 * @throws Exception внутренняя ошибка
	 */
	public ArrayList<int[]> shot(int i, int j) throws Exception
	{
		return set.shot(i, j);
	}
}