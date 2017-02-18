package enigma;

public class PlugBoard 
{
	private String[] letters;
	private int day;
	
	public PlugBoard(String[] startingLetters, int startingDay)
	{
		letters = startingLetters;
		day = startingDay;
	}
	
	public void setDay(int d)
	{
		day = d;
	}
	
	public int getDay()
	{
		return day;
	}
	
	public char convert(char input) // will return a null char if input is not in the day's letter list
	{
		
		if(input == ' ')
		{
			return input;
		}
		
		input = Character.toUpperCase(input);
		char output = input; // if input is not in plugboard, it will return the input
		
		boolean finished = false;
		int counter = 0;
		
		while(!finished && !(counter == letters[day].length()))
		{
			if (input == letters[day].charAt(counter))
			{
				boolean isLastLetter = (counter == letters[day].length() - 1);
				boolean spaceAfterLetter = (!isLastLetter && letters[day].charAt(counter + 1) == ' ');
				
				if(spaceAfterLetter || isLastLetter)
					output = letters[day].charAt(counter - 1);
				else
					output = letters[day].charAt(counter + 1);
				
				finished = true;
			}
			
			counter++;
		}
		return output;
	}
}
