package enigma;

public class Enigma 
{
	public static final int INNER = 0;
	public static final int MIDDLE = 1;
	public static final int OUTER = 2; // these constants will be used to refer to the elements in the keys and shifts arrays
	
	public static final int CLOCKWISE = 0;
	public static final int COUNTERCLOCKWISE = 1;
	
	public static final int DECRYPT = 0;
	public static final int ENCRYPT = 1;
	
	private String[] originalKeys; // this will be used to reset the keys and to keep track of when to rotate
	private String[] keys; 
	private String[] rotors; 
	
	private String[] pbSettings;
	
	private int[] keysToUse;
	private int[] shifts;
	
	private int day;
	
	
	public Enigma(String[] rotors, int[] keysToUse, int[] shifts, int day, String[] pbSettings)
	{
		this.rotors = rotors;
		this.shifts = shifts;
		this.day = day;
		this.pbSettings = pbSettings;
		this.keysToUse = keysToUse;
		keys = new String[3];
		
		setupKeys();
		initialShift();
	}
	
	
	public String decipher(String input, int setting) 
	{
		PlugBoard pb = new PlugBoard(pbSettings, day);
		String output = "";
		
		for(int i = 0; i < input.length(); i++)
		{
			char finalChar = input.charAt(i);
			if(Character.isLetter(finalChar) || finalChar == ' ')
			{	
				finalChar = pb.convert(input.charAt(i));
				finalChar = readWheel(finalChar, setting);
				finalChar = pb.convert(finalChar);
			}
				
			output += finalChar;
			
		}
		
		resetKeys();
		return output;
	}
	
	private void initialShift()
	{
		for(int i = 0; i < shifts.length; i++)
		{
			for (int j = 0; j < shifts[i]; j++)
			{
				shiftKey(i, COUNTERCLOCKWISE);
			}
		}
		
		originalKeys = keys.clone();
	}
	
	private void setupKeys()
	{
		for(int i = 0; i < 3; i++) // i < 3 used as condition because there's 3 assigned keys for the machine
		{
			keys[i] = rotors[keysToUse[i]];
		}
		
	}
	
	private void resetKeys()
	{
		keys = originalKeys.clone();
	}
	
	private void rotate()
	{	
		shiftKey(INNER, CLOCKWISE);
		
		if(keys[INNER].equals(originalKeys[INNER])) // If the inner key has fully rotated
		{
			shiftKey(MIDDLE, CLOCKWISE);
			
			if (keys[MIDDLE].equals(originalKeys[MIDDLE])) // if middle key has fully rotated
				shiftKey(OUTER, CLOCKWISE);
		}
	}
	
	private void shiftKey(int position, int mode)
	{
		String tailEnd = "";
		String frontEnd = "";
		
		if (mode == COUNTERCLOCKWISE)
		{
			tailEnd = tailEnd + keys[position].charAt(0);
			frontEnd = frontEnd + keys[position].substring(1, 27); // take first letter and put it at the end
		}
		else if(mode == CLOCKWISE)
		{
			frontEnd = frontEnd + keys[position].charAt(26);
			tailEnd = tailEnd + keys[position].substring(0, 26); // take last letter and put it in the beginning
		}
		
		keys[position] = frontEnd + tailEnd;
	}
	
	private char readWheel(char input, int setting)
	{
		char output = Character.MIN_VALUE;
		if(setting == DECRYPT)
		{
			output = keys[INNER].charAt(keys[OUTER].indexOf(keys[MIDDLE].charAt(keys[OUTER].indexOf(input))));
		}
		
		else if (setting == ENCRYPT)
		{
			output = keys[OUTER].charAt(keys[MIDDLE].indexOf(keys[OUTER].charAt(keys[INNER].indexOf(input))));
		}
		
		else
		{
			System.out.println("Not a valid setting");
		}
		
		rotate();
		return output;
	}
}
