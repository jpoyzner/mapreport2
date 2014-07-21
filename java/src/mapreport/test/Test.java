
package mapreport.test;


public class Test {

	final static int ROW_SIZE = 10;
	final static int COL_SIZE = 4;
	
	public static void main(String[] args) {
		 System.out.println("main ");
		 
		 char[][] matrix = {
	 				{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'},
	 				{'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'},
	 				{'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';'},
	 				{'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/'}
		 };
		
		 matrix = reverseAllSteps(matrix, "hvhh3hhv5v");
		 
		 /*
		 matrix = reverseHorizontal(matrix);
		 printMatrix(matrix);
		 reverseVertical(matrix);
		 printMatrix(matrix);
		 */
		 printMatrix(matrix);
	}
	
	static char[][] reverseAllSteps(char[][] matrix, String steps) {
		if (steps == null) {
			return null;
		}
		for (int i = 0; i < steps.length(); i++) {
			char step = steps.charAt(i);
			if (step == 'h') {
				matrix = reverseHorizontal(matrix);
			} else if (step == 'v') {
				matrix = reverseVertical(matrix);
			} else {
				matrix = shift(matrix, step - '0');
			}
		}
		return matrix;
		
	}
	
	static char[][] reverseHorizontal(char[][] matrix) {
		for (int rowCntr = 0; rowCntr < COL_SIZE; rowCntr ++) {
			char[] row = matrix[rowCntr];


			StringBuilder rowBuf = new StringBuilder(ROW_SIZE);
			for (int colCntr = 0; colCntr < ROW_SIZE; colCntr++) {
				rowBuf.append(row[colCntr]);
			}
			rowBuf.reverse();
			String rowStr = rowBuf.toString();
			for (int colCntr = 0; colCntr < ROW_SIZE; colCntr++) {
				row[colCntr] = rowStr.charAt(colCntr);
			}

			printRow(row);
			matrix[rowCntr] = row;
				
		}
		return matrix;		
	}
	
	static char[][] reverseVertical(char[][] matrix) {
		for (int colCntr = 0; colCntr < ROW_SIZE; colCntr ++) {

			StringBuilder rowBuf = new StringBuilder(COL_SIZE);
			for (int rowCntr = 0; rowCntr < COL_SIZE; rowCntr ++) {
				rowBuf.append(matrix[rowCntr][colCntr]);
			}
			rowBuf.reverse();
			String rowStr = rowBuf.toString();
			
			for (int rowCntr = 0; rowCntr < COL_SIZE; rowCntr ++) {
				matrix[rowCntr][colCntr] = rowStr.charAt(rowCntr);
			}			
		}
		return matrix;		
	}
	

	static char[][] shift(char[][] matrix, int shift) {
		for (int colCntr = 0; colCntr < ROW_SIZE; colCntr ++) {			
			for (int rowCntr = 0; rowCntr < COL_SIZE; rowCntr ++) {
				
				int newRowCntr = (rowCntr * colCntr - shift) / ROW_SIZE; 
				if (newRowCntr < 0) newRowCntr += COL_SIZE;
				
				int newColCntr = (rowCntr * colCntr - shift) % COL_SIZE;  
				if (newColCntr < 0) newColCntr += ROW_SIZE;
				
				matrix[rowCntr][colCntr] = matrix[newRowCntr][newColCntr];
			}
		}
		return matrix;		
	}
	
	static void printMatrix(char[][] matrix) {
		for (int rowCntr = 0; rowCntr < COL_SIZE; rowCntr ++) {
			char[] row = matrix[rowCntr];
			
			for (int colCntr = 0; colCntr < ROW_SIZE; colCntr++) {
				System.out.print(row[colCntr]);		
			}	

			System.out.println();		
		}
		return;		
	}
	
	static void printRow(char[] row) {
			for (int colCntr = 0; colCntr < ROW_SIZE; colCntr++) {
				System.out.print(row[colCntr]);		
			}	
			System.out.println();		

			return;		
	}
	

}
