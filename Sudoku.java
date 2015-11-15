import java.util.ArrayList;
import java.util.*;
import java.lang.*;

class Sudoku {
	public static void main(String[] args) {
	 	Scanner input = new Scanner(System.in);
	 	Random random = new Random();
 		SudokuSolver solver =new SudokuSolver();				
//------------------------------------------------------------------------------------------------------------------------
	
		System.out.println("easy/moderate/hard");
  		String choice = input.nextLine();
  		char level = choice.charAt(0);
  
	  	int	numberOfPresetValues = 17;

		if(level=='e'){
		 	numberOfPresetValues = 17;
		}else if(level=='m'){
			numberOfPresetValues = 20;
		}else if(level=='h'){
			numberOfPresetValues = 23;
		}else{
						numberOfPresetValues = 5;
		}		
//------------------------------------------------------------------------------------------------------------------------
		//setup
			int [][] sudokuField = new int [9][9];
			int [] arr = new int [81];
			sudokuField = solver.setup(numberOfPresetValues);	//assigns random values to random cells within each 3x3 field. will be repeated 9 times.
		
			System.out.println(Arrays.deepToString(sudokuField).replaceAll("],", "]\n").replaceAll("0", " ").replaceAll(",", "|"));
	
//------------------------------------------------------------------------------------------------------------------------
		// solve
			for(int i=0;i<81;i++){
				arr[i]=sudokuField[i/9][i%9];
			}

		System.out.print("Would you like to see the solution? (y/n)");
  		String yesOrNo = input.nextLine();
  		char firstLetter = yesOrNo.charAt(0);
			if(firstLetter=='y'){		

				sudokuField = solver.solve(sudokuField,arr,solver.arraylistSetup());
			}
		System.out.println(Arrays.deepToString(sudokuField).replaceAll("],", "]\n").replaceAll("0", " ").replaceAll(",", "|"));
	}
}