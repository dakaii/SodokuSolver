//java
//recursive Method
import java.util.ArrayList;
import java.util.*;
import java.lang.*;

class SudokuSolver {
 	//Random random = new Random();
 	//private int mm = 0;
// if the cell is occupied, the mm increases by 1. if theres no more possible values to be assigned, the mm decreases by 1.

 	public int [][] solve(int[][] puzzle, int[] initialArray,List<List<Integer>> arrlist, int mm){
 		int solution;
 		int row;
 		int column;
		if(mm==0)
			while(isPreset(initialArray)[mm]){mm++;}
		row = mm/9;
		column = mm%9;	

 		if(isSolved(puzzle) || mm==81){return puzzle;}			//return if the puzzle has been solved.
 		if(isPreset(initialArray)[mm]==false){	//if the cell is not preset or filled with 0.
			puzzle[row][column] = 0;																	// the cell gets initialized here.
		}
 			//System.out.println(Arrays.deepToString(puzzle).replaceAll("],", "]\n").replaceAll("0", " ").replaceAll(",", "|"));
 			//System.out.println("\n");
		solution = testValue(possibleSolutions(row,column,puzzle),arrlist,mm);//assign an element thats not 0 or in the arraylist.						
		if(solution!=0){
			puzzle[row][column]=solution;
			arrlist.get(mm).add(puzzle[row][column]);												//add the tested value to an arraylist.
			mm++;	
			if(isSolved(puzzle)){return puzzle;}	
			while(isPreset(initialArray)[mm]){mm++;}					
			solve(puzzle,initialArray,arrlist,mm);				
		}else{
			arrlist.get(mm).clear();
			mm--;
			solve(puzzle,initialArray,arrlist,mm);
		}
		return puzzle;
 	}

	public int[][] setup(int numberOfPresets){
		int [][] puzzle = new int [9][9];
		int [] arr = new int [81];
		for(int i=0;i<81;i++){
			arr[i]=puzzle[i/9][i%9];
		}
	 	puzzle = solve(puzzle,arr,arraylistSetup(),0);
		for(int j=0;j<81-numberOfPresets;j++){
			initializeCell(puzzle);
		}
		//mm=0;
 		return puzzle;
	}

	public boolean check(int row, int column, int randomValue, int[][] puzzle){
		boolean noDuplicate = true;

		noDuplicate = threeByThreeSectionCheck(row,column,randomValue,puzzle);
		if(noDuplicate==false){return false;}
		noDuplicate = rowCheck(row,randomValue,puzzle);
		if(noDuplicate==false){return false;}
		noDuplicate = columnCheck(column,randomValue,puzzle);
		if(noDuplicate==false){return false;}

		if(puzzle[row][column]==0){
			if (noDuplicate == true){
			  return true;		  
			}
		}
		return false;	
	}

	public boolean threeByThreeSectionCheck (double row, double column, int randomValue,int [][] puzzle){
		row = row + 1;
		column = column + 1;
		int [] testedField ={3*(int)Math.ceil(row/3), 3*(int)Math.ceil(column/3)};
		for (int i=testedField[0]-3; i<testedField[0]; i++){
			for (int j=testedField[1]-3; j<testedField[1]; j++){
				if (puzzle[i][j] == randomValue){return false;}	//check if the randomly chosen value exists in the 3x3 field.
			}
		}
		return true;
	}

	public boolean rowCheck (int row, int randomValue,int [][] puzzle){
		for (int j=0; j<9; j++){			//row check		
			if (puzzle[row][j] == randomValue){
				return false;
			}
		}
		return true;
	}

	public boolean columnCheck (int column, int randomValue, int [][] puzzle){
		for (int i=0; i<9 ; i++){			//column check		
			if (puzzle[i][column] == randomValue){return false;}
		}	
		return true;
	}

	public boolean isSolved (int[][] solvedSudoku){
		for (int i=0; i<9 ; i++){			//column check	
			for (int k=0; k<9 ; k++){		
				if (solvedSudoku[i][k] == 0){return false;}
			}
		}	
		return true;
	}

	public boolean[] isPreset (int[] initialArray){ // returns an array where true indicates that the cell is preset.
		boolean [] presetCell = new boolean [81];	
 		for(int i=0;i<81;i++){
				if(initialArray[i]!=0){	            // determins if the cells is oppupied with a preset value.
			presetCell[i]=true;
			}
		}
		return presetCell; 
	}
	public int[] possibleSolutions(int row, int column, int[][] puzzle){			// here i can use the check method to creat an array of possible solutions.
		int[] array = new int[9];
		for(int solution=1;solution<=9;solution++){
			if(check(row,column,solution,puzzle)){
				array[solution-1] = solution;                    // this is where a new value gets assigned to the cell.
			}
		}
		array = shuffleArray(array);
		return array;
	}


	//possibleSolutions method will be assigned to the solutionArray parameter 
	public int testValue(int[] solutionArray,List<List<Integer>> arrlist,int cellNumber){
		for(int i=0;i<9;i++){
			if(!arrlist.get(cellNumber).contains(solutionArray[i]) && solutionArray[i]!=0)	//if arrlist doesnt contain the nonzero value being checked.	
			return solutionArray[i];		//the value gets returned.
		}
		return 0;
	}


	public List<List<Integer>> arraylistSetup(){
		List<List<Integer>> arrlist = new ArrayList<List<Integer>>(81);
		for(int i = 0; i < 81; i++)  {
	        arrlist.add(new ArrayList<Integer>());
	        arrlist.get(i).add(0);
	    }
	    return arrlist;
	}

	public int[] shuffleArray(int[] arr){
		for(int i=0;i<arr.length;i++){
			int index=new Random().nextInt(arr.length-1);
			int temp=arr[index];
			arr[index]=arr[i];
			arr[i]=temp;
		}
		return arr;
	}

	public int[][] initializeCell(int[][] multiArr){
		int row = new Random().nextInt(9);
		int column = new Random().nextInt(9);	
		multiArr[row][column] = 0;		// set a random cell to zero.	
		return multiArr;
	}
 
}
