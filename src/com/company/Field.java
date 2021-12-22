package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main class.
 * It is responsible for whole graphics and repainting.
 */

final class Field extends JFrame implements ActionListener, def
{

	public static int field_size = 50;                 //коэффициент размера поля
	private static final int panel_position_x = 10;    //положение начальной панели
	private static final int panel_position_y = 100;   // -//-
	private static final int button_long = 100;        // -//-
	private static final int button_height = 50;       // -//-
	private static final int button_space = 20;        //расстояние от верхней границы окна до кнопки "start" и текста
	public static int space = 30;                      //расстоянием между панельями
	public boolean game_status = true;
	public boolean another_ko = false;
	/**
	 * Start game button
	 */
	private final JButton start_button;
	/**
	 * Panels of player and opponent
	 */
	My_board my_board;
	Opponents_board opponents_board;
	Opponent opponent;

	Field()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(FIELD_WIGHT, FIELD_HEIGHT);
		this.setResizable(false);
		my_board = new My_board();
		opponents_board = new Opponents_board();
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//Верхняя панель с кнопкой старта и подписями игроков
		JPanel upper_panel = new JPanel();
		upper_panel.setSize(FIELD_WIGHT, PANEL_HEIGHT);
		upper_panel.setLocation(0, 0);
		upper_panel.setLayout(null);    //сбрасываем настроки компоновки панели, чтобы расположить текст и кнопку так, как хотим
		Font text_format = new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 30);    //Заготовили шрифт
		//Подпись "Ваш флот":
		JLabel your_fleet_l = new JLabel("Your fleet");
		your_fleet_l.setFont(text_format);                                                // Установить шрифт в метке
		your_fleet_l.setForeground(Color.black);                                          // Устанавливаем цвет текста в метке
		your_fleet_l.setBounds(250, button_space, 500, button_height);
		upper_panel.add(your_fleet_l);
		//Подпись "Флот оппонента":
		JLabel your_op_fleet_l = new JLabel("Your opponent's fleet");
		your_op_fleet_l.setFont(text_format);                                            // Установить шрифт в метке
		your_op_fleet_l.setForeground(Color.black);                                      // Устанавливаем цвет текста в метке
		your_op_fleet_l.setBounds(950, button_space, 500, button_height);
		upper_panel.add(your_op_fleet_l);
		//Кнопка начала игры:
		start_button = new JButton("Start");

		start_button.setFont(new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 20));//Шрифт такой же, как и раньше, но совсем другой
		start_button.setBackground(Color.red);
		start_button.setForeground(Color.white);
		start_button.setBounds(field_size * 12 + 2 * space, button_space, button_long, button_height);
		start_button.addActionListener(this);
		upper_panel.add(start_button);       //добавляем кнопку в панель

		this.add(upper_panel);               //добавляем панель в фрейм
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		my_board.setLocation(panel_position_x, panel_position_y);
		opponents_board.setLocation(field_size * 12 + 2 * space + button_long + space, panel_position_y);
		this.add(my_board);
		this.add(opponents_board);
		this.setVisible(true);

		opponent = new Opponent(this);
		show_info("Expand your fleet!");
	}

	protected void rep()
	{
		//this.setVisible(false);
		my_board.repaint();
		this.repaint();
		//this.setVisible(true);
	}

	private void disable_start_button()
	{
		start_button.setBackground(Color.black);
		start_button.setText("Started");
		start_button.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == start_button)
		{
			if (!(my_board.set_start(this, opponent) && opponents_board.set_start(this, opponent)))
			{
				show_error("The ships are not positioned correctly!");
				return;
			}
			disable_start_button();
			// раскомментить!!!
			//show_info("The game is on!");
		}
	}

	private void show_error(String message)
	{
		JOptionPane.showMessageDialog(null, message, "ER notification", JOptionPane.ERROR_MESSAGE);
	}

	private void show_info(String message)
	{
		JOptionPane.showMessageDialog(null, message, "GS notification", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * It helps to fight
	 *
	 * @param pos where you hit
	 * @return result
	 * @see com.company.Opponent
	 */
	public int hit(int[] pos)
	{
		System.out.println("hit reached!");
		int rez = my_board.hit(pos);
		if (rez == it_is_the_end)
		{
			game_status = false;
			opponents_board.game_status = false;
			my_board.game_status = false;
		}
		return rez;
	}

	public void over(boolean who)
	{
		if (who == YOU)
		{
			Sound.playSound("sounds/lyapota.wav").join();
			show_info("Game over. You won!");
		} else
		{
			Sound.playSound("sounds/tobe.wav").join();
			show_info("Game over. Your opponent won. Try again!");
		}
		int input = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Exit frame", JOptionPane.YES_NO_OPTION);
		// 0=yes, 1=no, 2=cancel
		//System.out.println(input);
		if (input == 0)
			Main.reset();
		else
			System.exit(0);
	}

	/**
	 * It pushes opponents move. In fact, this is one big crutch cause it breaks up encapsulation
	 * I do not know how I should fix it, so let it be
	 *
	 * The main problem is delaying between moves. Without this func i can not do it
	 * @throws InterruptedException some f*** errors
	 */
	public void move() throws InterruptedException
	{
		opponents_board.pause();
		if(opponent == null)
			System.out.println("Error 2");
		else
		{
			while (true)
			{
				int a = opponent.force_move();
				if (my_board.game_status == false)
				{
					return;
				}
				if(a == MISS)
				{
					Sound.playSound("sounds/daladna.wav").join();
					break;
				}
				else if(a == DEAD)
				{
					Sound.playSound("sounds/povezlo-povezlo.wav").join();
				}
				else
				{
					Thread.sleep(700);
				}
				System.out.println(a);
			}
		}
		f.convert();
		opponents_board.pause();
	}
}
