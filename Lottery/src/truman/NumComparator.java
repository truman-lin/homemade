package truman;

import java.util.Comparator;

public class NumComparator implements Comparator<Object> {
	@Override
	public int compare(Object o1, Object o2) {	
		if(o1 instanceof LotNum){
			return  ((LotNum)o2).stages.size()- ((LotNum)o1).stages.size();			
		} else {
			return  ((NumArea)o2).stages.size()- ((NumArea)o1).stages.size();
		}
	}

}
