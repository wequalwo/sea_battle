package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * The Opponents_board class implements the graphics and logic of the opponent's field.
 */
public class Opponents_board extends Board
{
	/**
	 * status of the game
	 */
	protected boolean status;
	public boolean game_status;
	/**
	 * observer of the board
	 */
	private Op_observer observer;
	@Override
	protected boolean get_status()
	{
		return OPP;
	}

	@Override
	protected void create_obs()
	{
		observer = new Op_observer(get_status());
	}

	@Override
	public boolean fix(Field field)
	{
		game_status = field.game_status;
		for (int i = 1; i < 11; i++)
		{
			for (int j = 1; j < 11; j++)
			{
				float[] a = new float[3];
				int r = 0;
				// цвет кнопки в rgb
				int g = 0;
				int b = 139;
				Color.RGBtoHSB(r, g, b, a);
				if (observer.get_sea(i - 1, j - 1) == 0)
					buttons[i][j].setBackground(Color.getHSBColor(a[0], a[1], a[2]));
				buttons[i][j].setEnabled(true);
			}
		}
		status = true;
		return true;
	}

	protected void pause()
	{
		status = !status;
		for (int i = 1; i < 11; i++)
		{
			for (int j = 1; j < 11; j++)
			{
				buttons[i][j].setEnabled(status);
			}
		}
	}
	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public void actionPerformed(ActionEvent e)
	{
		boolean search_status = true;
		for (int i = 1; i < 11; i++)
		{
			if (!search_status)
				break;
			for (int j = 1; j < 11; j++)
			{
				if (e.getSource() == buttons[i][j])
				{
					ArrayList<int[]> grind = null;
					try
					{
						grind = observer.shot(i - 1, j - 1);
					} catch (Exception exception)
					{
						exception.printStackTrace();
						System.out.println("Error 1: my set of ships collapsed");
					}
					if (grind == null)
					{
						throw new NullPointerException();
					}

					if (grind.get(0)[0] == MISS) // выстрел мимо
					{
						Sound.playSound("sounds/ne-probil.wav").join();
						buttons[i][j].setBackground(Color.blue);
						buttons[i][j].setEnabled(false);

						/*try
						{
							opponent.field.move();
						} catch (InterruptedException interruptedException)
						{
							interruptedException.printStackTrace();
						}*/
						/*while (opponent.force_move() != MISS) ; // до тех пор, пока соперник не промазал, он ходит*/
						f.convert();
					} else if (grind.get(0)[0] == SUNKEN)// попал
					{
						buttons[i][j].setBackground(Color.orange);
						buttons[i][j].setEnabled(false);
					} else // ответ (выстрел) убил
					{
						Sound.playSound("sounds/vo-krasavchik.wav").join();
						for (int[] p : grind)
						{
							buttons[p[0]][p[1]].setBackground(Color.gray);
							buttons[p[0]][p[1]].setEnabled(false);
							if (observer.get_if_zero_counter())
							{
								game_status = false;
							}
						}
						ArrayList<int[]> off_but = observer.fill(grind);
						for (int[] p : off_but)
						{
							buttons[p[0]][p[1]].setBackground(Color.blue);
							buttons[p[0]][p[1]].setEnabled(false);
						}
					}
					search_status = false;
					break;
				}
			}
		}
		if (!game_status)
		{
			opponent.force_stop(observer.get_counter() == 0);
		}
	}
}

