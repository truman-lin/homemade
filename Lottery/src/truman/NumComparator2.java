package truman;

import java.util.Comparator;

public class NumComparator2 implements Comparator<Object> {
	@Override
	public int compare(Object o1, Object o2) {		
		if(o1 instanceof LotNum){
			return  ((LotNum)o2).special.size()- ((LotNum)o1).special.size();			
		} else {
			return  ((NumArea)o2).special.size()- ((NumArea)o1).special.size();
		}
	}

}
