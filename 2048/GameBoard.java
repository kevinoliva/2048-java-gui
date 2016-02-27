import java.util.*;


public class GameBoard {
	private List boardList; //entire board
	private List freeList; //list of the indices of the free spaces on the board 
	private List usedList; // list of the indices of the used spaces
	
	//class constructors
	public GameBoard() {
		boardList = new ArrayList();
		freeList = new ArrayList();
		for (int i = 0; i < 16; i++) {
			Integer num = new Integer(i);
			freeList.add(num);
			boardList.add(new Integer(0));
		}
		usedList = new ArrayList();
	}
	
	//accessors
	public List getBoardList() {
		return boardList;
	}
	public List getFreeList() {
		return freeList;
	}
	public List getUsedList() {
		return usedList;
	}
	public List getRow(int rowNum) {
		List rowList = new ArrayList();
		switch (rowNum) {
			case 1: 
				for(int i = 0; i < 4; i++) {rowList.add(boardList.get(i));} break;
			case 2:
				for(int i = 4; i < 8; i++) {rowList.add(boardList.get(i));} break;
			case 3:
				for(int i = 8; i < 12; i++) {rowList.add(boardList.get(i));} break;
			case 4:
				for(int i = 12; i < 16; i++) {rowList.add(boardList.get(i));} break;
			default: break;
		}
		return rowList;
	}
	public List getColumn(int colNum) {
		List colList = new ArrayList();
		switch (colNum) {
			case 1: 
				for(int i = 0; i < 16; i += 4) {colList.add(boardList.get(i));} break;
			case 2:
				for(int i = 1; i < 16; i += 4) {colList.add(boardList.get(i));} break;
			case 3:
				for(int i = 2; i < 16; i += 4) {colList.add(boardList.get(i));} break;
			case 4:
				for(int i = 3; i < 16; i += 4) {colList.add(boardList.get(i));} break;
			default: break;
		}
		return colList;
	}
	
	//mutators
	public void setBoardList(List board) {
		boardList = board;
	}
	public void setFreeList(List free) {
		freeList = free;
	}
	public void setUsedList(List used) {
		usedList = used;
	}
	public void setRow(int rowNum, List rowList) {
		int i = rowNum - 1;
		int j = 0;
		while (j < 4) {
			int index = i*4 + j;
			Integer num = new Integer(index);
			if (rowList.get(j).equals(new Integer(0)) && 
					!boardList.get(index).equals(new Integer(0))) { //erase
				freeList.add(num);
				usedList.remove(num);
			}
			else if(boardList.get(index).equals(new Integer(0)) && //add
					!rowList.get(j).equals(new Integer(0))) {
				usedList.add(num);
				freeList.remove(num);
			}
			boardList.set(index, rowList.get(j));
			j++;
		}
	}
	public void setColumn(int colNum, List colList) {
		int i = colNum - 1;
		int j = 0;
		while (j < 4) {
			int index = j*4 + i;
			Integer num = new Integer(index);
			if (colList.get(j).equals(new Integer(0)) && 
					!boardList.get(index).equals(new Integer(0))) { //erase
				freeList.add(num);
				usedList.remove(num);
			}
			else if(boardList.get(index).equals(new Integer(0)) && //add
					!colList.get(j).equals(new Integer(0))) {
				usedList.add(num);
				freeList.remove(num);
			}
			boardList.set(index, colList.get(j));
			j++;
		}
	}
	
	//methods
	public void addRandom() { //adds a piece randomly to the board
		Random rand = new Random();
		Random rand2 = new Random();
		int values[] = {2, 2, 2, 2, 2, 2, 2, 2, 2, 4}; //higher probability of 2
		int index = rand.nextInt(freeList.size()); //pick a free space
		int piece = values[rand2.nextInt(values.length)]; //pick a randomly assigned 2 or 4
		Integer convert = (Integer) freeList.get(index);
		int conv2 = convert.intValue();
		boardList.set(conv2, new Integer(piece)); //add to board
		usedList.add(freeList.get(index));
		freeList.remove(freeList.get(index));
	}
}
