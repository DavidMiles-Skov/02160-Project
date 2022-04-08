package view;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

import controller.InitiateController;

import utils.GridBagLayoutUtils;

public class InitiateView extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private JSlider sliderPlayers;
	private JButton btnStart;
	JPanel difContainer;
	JRadioButton easyDifficulty;
	JRadioButton mediumDifficulty;
	JRadioButton hardDifficulty;
	ButtonGroup difficulty;
	private InitiateController controller;
	
	public InitiateView(InitiateController controller) {
		this.controller = controller;
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Game setup");
		setLayout(new GridBagLayout());
		
		sliderPlayers = new JSlider(2,8,2);
		sliderPlayers.setMajorTickSpacing(1);
		//sliderPlayers.setPaintTicks(true);
		sliderPlayers.setPaintLabels(true);
		
		//Difficulty setup
		difficulty = new ButtonGroup();
		easyDifficulty = new JRadioButton("EASY");
		mediumDifficulty = new JRadioButton("MEDIUM");
		hardDifficulty = new JRadioButton("HARD");
		
		
		
		easyDifficulty.setSelected(true);
		difficulty.add(easyDifficulty);
		difficulty.add(mediumDifficulty);
		difficulty.add(hardDifficulty);
		
		difContainer = new JPanel();
		difContainer.add(easyDifficulty);
		difContainer.add(mediumDifficulty);
		difContainer.add(hardDifficulty);
		
		
		
		
		btnStart = new JButton("Start Game");
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setPlayers(sliderPlayers.getValue());
			}
		});
		
		add(new JLabel("No. of players:"), GridBagLayoutUtils.constraint(0, 0, 5));
		add(sliderPlayers, GridBagLayoutUtils.constraint(1, 0, 5));
		add(new JLabel("Choose a difficulty:"), GridBagLayoutUtils.constraint(0, 1, 5));
		add(difContainer, GridBagLayoutUtils.constraint(1, 1, 5));
		add(btnStart, GridBagLayoutUtils.constraint(1, 2, 5));
		
		pack();
		setLocationRelativeTo(null);
	}
	

}