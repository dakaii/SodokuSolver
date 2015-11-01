//java
//recursive Method
import java.util.ArrayList;
import java.util.*;
import java.lang.*;

class SudokuSolver {
 	Random random = new Random();
// if the cell is occupied, the i increases by 1. if theres no more possible values to be assigned, the i decreases by 1.

 	public int [][] solve(int[][] puzzle, int[] initialArray,List<List<Integer>> arrlist,int i){
 		int solution;
		if(i==0)
			while(isPreset(initialArray)[i]){i++;}	

 			if(isSolved(puzzle) || i==81){return puzzle;}			//return if the puzzle has been solved.
 			if(isPreset(initialArray)[i]==false){	//if the cell is not preset or filled with 0.
				puzzle[i/9][i%9] = 0;																	// the cell gets initialized here.
			}
 			//		System.out.println(Arrays.deepToString(puzzle).replaceAll("],", "]\n").replaceAll("0", " ").replaceAll(",", "|"));
 			//		System.out.println("\n");

			solution = testValue(possibleSolutions(i/9,i%9,puzzle),arrlist,i);//assign an element thats not 0 or in the arraylist.	
							
				if(solution!=0){
					puzzle[i/9][i%9]=solution;
					arrlist.get(i).add(puzzle[i/9][i%9]);												//add the tested value to an arraylist.
						i++;	
							if(isSolved(puzzle)){return puzzle;}	
							while(isPreset(initialArray)[i]){i++;}					
					solve(puzzle,initialArray,arrlist,i);				
				}else{
					arrlist.get(i).clear();
						i--;
					solve(puzzle,initialArray,arrlist,i);
				}
		return puzzle;
 	}

	public int[][] setup(int numberOfPresets){
		int [][] puzzle = new int [9][9];
		int [] arr = new int [81];
		int iteration = 0;
			for(int i=0;i<81;i++){
				arr[i]=puzzle[i/9][i%9];
			}
	 			puzzle = solve(puzzle,arr,arraylistSetup(),iteration);
			for(int j=0;j<81-numberOfPresets;j++){
				initializeCell(puzzle);
		}
 			return puzzle;
	}

	public boolean check(int row, int column, int randomValue, int[][] puzzle){
			boolean noDuplicate = true;

				noDuplicate = threexthreeFieldCheck(row,column,randomValue,puzzle);
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

	public boolean threexthreeFieldCheck (double row, double column, int randomValue,int [][] puzzle){
		row = row + 1;
		column = column + 1;
		int [] testedField ={3*(int)Math.ceil(row/3),3*(int)Math.ceil(column/3)};
					for (int i=testedField[0]-3; i<testedField[0] ; i++){
						for (int j=testedField[1]-3; j<testedField[1] ;j++){
							if (puzzle[i][j] == randomValue){return false;}	//check if the randomly chosen value exists in the 3x3 field.
						}
					}
			return true;
	}

	public boolean rowCheck (int row, int randomValue,int [][] puzzle){
				for (int j=0; j<9 ; j++){			//row check		
						if (puzzle[row][j] == randomValue){
						return false;
						}
				}
				return true;
	}

	public boolean columnCheck (int column, int randomValue, int [][] puzzle){
				for (int i=0; i<9 ; i++){			//column check		
					if (puzzle[i][column] == randomValue){return false;
					}
				}	
				return true;
	}

	public boolean isSolved (int[][] solvedSudoku){
				for (int i=0; i<9 ; i++){			//column check	
					for (int k=0; k<9 ; k++){		
						if (solvedSudoku[i][k] == 0){return false;
						}
					}
				}	
				return true;
	}

		public boolean[] isPreset (int[] initialArray){ // returns an array where true indicates that the cell is preset.
			boolean [] presetCell = new boolean [81];	
		//	Arrays.fill(presetCell,false);									//this should not be necessary. 
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
			if(check(row,column,solution,puzzle)==true){
 				array[solution-1] = solution;                    // this is where a new value gets assigned to the cell.
 			}
 		}
 		array = shuffleArray(array);
 		return array;
	}


	//possibleSolutions method will be assigned to the solutionArray parameter 
	public int testValue(int[] solutionArray,List<List<Integer>> arrlist,int cellNumber){
		for(int i=0;i<9;i++){
			if(!arrlist.get(cellNumber).contains(solutionArray[i]) && solutionArray[i]!=0)//if arrlist doesnt contain the nonzero value being checked.	
				return solutionArray[i];																										//the value gets returned.
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
			int index=random.nextInt(arr.length-1);
			int temp=arr[index];
			arr[index]=arr[i];
			arr[i]=temp;
		}
		return arr;
	}

	public int[][] initializeCell(int[][] multiArr){
		int row = random.nextInt(9);
		int column = random.nextInt(9);	
		multiArr[row][column] = 0;                   // set a random cell to zero.	
		return multiArr;
	}
 
}
