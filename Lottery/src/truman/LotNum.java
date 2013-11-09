package truman;

import java.util.ArrayList;

class LotNum{
	int value;
	ArrayList<Integer> stages;
	ArrayList<Integer> special;
	ArrayList<String> dates;
	
	@Override
	public String toString(){
		return String.format("%d-%d-%d",value,stages.size(),special.size());	
	}
}
