package truman;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
public class lotteryViewCtrl {
	private int [][] plate = new int[7][7];
	private int [] arrayI = new int[50];
	private int [] arrayJ = new int[50];
	private LotteryDAO ld = new LotteryDAO();
	private lotteryNums selectedStage;

	private void setArrays(){
		int i = 0, j = 3;
		for(int ii=0;ii<49;ii++){
			arrayI[ii] = arrayJ[ii] = 0;
			plate[ii/7][ii%7] = 0;
		}
		arrayI[49] = arrayJ[49] = 0;
		arrayI[1] = 0;
		arrayJ[1] = 3;
		plate[i][j] = 1;
		for(int k=2;k<50;k++){
			i--; j--;
			if(i<0) i = 6;
			if(j<0) j = 6;
			if(plate[i][j]>0) {
				i = (i+1)%7;
				i = i + 1;
				j = (j+1)%7;
			}
			if(plate[i][j]>0) {
				System.out.println("Something wrong."+k);
				break;
			} else {
				arrayI[k] = i;
				arrayJ[k] = j;
				plate[i][j] = k;
			}
		}
	}
	public lotteryViewCtrl(){
		setArrays();
	}
	public void setSelectedStage(lotteryNums nums){
		selectedStage = nums;
	}
	public lotteryNums getSelectedStage(){
		return selectedStage;
	}
	public List<lotteryNums> getLotteryData(){
		return ld.getLotteryNums(); 
	}
	public int[][] getPlate(){
		for(int i=0;i<7;i++) for(int j=0;j<7;j++) plate[i][j] = 0;
		if(selectedStage!=null){
			plate[arrayI[selectedStage.getN1()]][arrayJ[selectedStage.getN1()]] = selectedStage.getN1();
			plate[arrayI[selectedStage.getN2()]][arrayJ[selectedStage.getN2()]] = selectedStage.getN2();
			plate[arrayI[selectedStage.getN3()]][arrayJ[selectedStage.getN3()]] = selectedStage.getN3();
			plate[arrayI[selectedStage.getN4()]][arrayJ[selectedStage.getN4()]] = selectedStage.getN4();
			plate[arrayI[selectedStage.getN5()]][arrayJ[selectedStage.getN5()]] = selectedStage.getN5();
			plate[arrayI[selectedStage.getN6()]][arrayJ[selectedStage.getN6()]] = selectedStage.getN6();
			plate[arrayI[selectedStage.getNs()]][arrayJ[selectedStage.getNs()]] = selectedStage.getNs();
		}
		return plate;
	}
	@Command("showNums")
	@NotifyChange("plate")
	public void showNums(){
		
	}
}
