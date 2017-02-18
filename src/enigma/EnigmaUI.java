package enigma;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EnigmaUI extends JFrame 
{
	
	// The plug board string used will be dependent on the day used/
	private final String[] PBSETTINGS = 
		{
			"TW BI UY GP CK JQ DL RV EM AH",
			"LS DH MT EO AP UZ FQ WY BK GR",
			"DO JW CN IV PZ BM HU AL FR KX",
			"NT HK BW EP LQ AU OY FJ CX GI",
			"HM GV KZ AI DQ NR ES BL OU FT",
			"GW AQ MO FV PS DI RU JZ BN EH",
			"LT DR QX AG IN EU BJ KP FW CM"
		};
	
	// These are the 5 possible rotors, where each contain every letter of the alphabet and a space.
	// Only 3 of the 5 will be used for each enigma machine
	
	private final String[] FIVEROTORS = 
		{
			"GNUAHOVBIPWCJQXDKRY ELSZFMT",
			"EJ OTYCHMRWAFKPUZDINSXBGLQV",
			"BDFHJLNPRTVXZACEGI KMOQSUWY",
			"KPHDEAC VTWQMYNLXSURZOJFBGI",
			"NDYGLQICVEZRPTAOXWBMJSUHK F"	
		};
	
	private JComboBox[] rotors = new JComboBox[3];
	private JComboBox[] shifts = new JComboBox[3];
	private JComboBox days;
	
	private JLabel rotorsLabel = new JLabel("Rotors (Inner, Middle, Outer)");
	private JLabel shiftsLabel = new JLabel("Shifts (Inner, Middle, Outer)");
	private JLabel daysLabel = new JLabel("Day:");
	
	private JButton createConverter = new JButton("Create Converter");
	
	private JPanel initialPanel = new JPanel();
	private JPanel rotorPanel = new JPanel();
	private JPanel shiftsPanel = new JPanel();
	private JPanel daysPanel = new JPanel();
	
	public EnigmaUI()
	{
		buildUI();
		addActionListeners();
	}
	
	private void buildUI()
	{
		setLayout(new GridLayout(3, 1));
		setupComboBoxes();
		
		rotorPanel.add(rotorsLabel);
		for(int i = 0; i < rotors.length; i++) // add rotors combo boxes
		{
			rotorPanel.add(rotors[i]);
		}
		
		shiftsPanel.add(shiftsLabel);
		for(int i = 0; i < shifts.length; i++) // add shifts combo boxes
		{
			shiftsPanel.add(shifts[i]);
		}
		
		daysPanel.add(daysLabel);
		daysPanel.add(days);
		daysPanel.add(createConverter);
		add(rotorPanel);
		add(shiftsPanel);
		add(daysPanel);
		
		setSize(320, 150);
		setTitle("Enigma Machine");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
	
	private void setupComboBoxes()
	{
		for(int i = 0; i < rotors.length; i++) // first begin with initializing stuff in rotors array
		{
			String[] rotorNumbers = {"1", "2", "3", "4", "5"};
			rotors[i] = new JComboBox(rotorNumbers);
		}
		
		String[] shiftNumbers = new String[26]; // then get ready to set up stuff in shifts array
		
		for(int i = 0; i < shiftNumbers.length; i++)
		{
			shiftNumbers[i] = Integer.toString((i+1));
		}
		
		for(int i = 0; i < shifts.length; i++) // set up shifts array
		{
			shifts[i] = new JComboBox(shiftNumbers);
		}
		
		// then finally set up days combobox
		String[] dayNumbers = {"1", "2", "3", "4", "5", "6", "7"};
		days = new JComboBox(dayNumbers);
	}
	
	private void addActionListeners()
	{
		createConverter.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				int[] gr = generateRotors();
				int[] gs = generateShifts();
				new ConverterUI(new Enigma(FIVEROTORS, gr, gs, days.getSelectedIndex(), PBSETTINGS));
			}
			
		});
		
	}
	
	private int[] generateRotors() // this function determines the 3 keys from the selections of the user
	{
		int[] output = new int[3];
		for(int i = 0; i < rotors.length; i++)
		{
			output[i] = rotors[i].getSelectedIndex();
		}
		return output;
	}
	
	private int[] generateShifts() // This function determines the 3 shifts from the selections of the user
	{
		int [] output = new int[3];
		for(int i = 0; i < shifts.length; i++)
		{
			output[i] = shifts[i].getSelectedIndex();
		}
		return output;
	}
}
