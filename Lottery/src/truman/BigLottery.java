package truman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class BigLottery {
	static NumArea[] areaNumbers = new NumArea[9];
	static LotNum[] lotNums = new LotNum[50];

	static private void recordNumbers(int inNum, int stage) {
		// System.out.printf("recording %d, stage %d\n",inNum,stage);
		lotNums[inNum].stages.add(stage);
		for (NumArea a : areaNumbers) {
			if (a.members.contains(lotNums[inNum]))
				a.stages.add(stage);
		}
	}

	static private void recordSNumbers(int inNum, int stage) {
		// System.out.printf("recording special %d, stage %d\n",inNum,stage);
		lotNums[inNum].special.add(stage);
		for (NumArea a : areaNumbers) {
			if (a.members.contains(lotNums[inNum]))
				a.special.add(stage);
		}
	}

	static private int findAreaCode(int inNum) {
		int result = -1;
		for (NumArea a : areaNumbers) {
			if (a.members.contains(lotNums[inNum])) {
				result = a.value;
				break;
			}
		}
		return result;
	}

	static private void getSpecialNumber(List<NumArea> areaList){
		Collections.sort(areaList, new NumComparator2()); // 特別號區別排序
		int num = (int) (11 * 13 * 19 * 10103 * Math.random() / 17) % 8; // 亂數
		List<LotNum> numList = areaList.get(num).members;
		int seed = numList.size();
		num = (int) (11 * 13 * 19 * 10103 * Math.random() / 17) % seed;
		System.out.printf("%d \n", numList.get(num).value);		
	}	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer[] bigNumbers = {0,0,0,0,0,0};
		for (int i = 0; i < 50; i++) {
			LotNum n = new LotNum();
			n.value = i;
			n.stages = new ArrayList<Integer>();
			n.special = new ArrayList<Integer>();
			n.dates = new ArrayList<String>();
			lotNums[i] = n;
		}

		NumArea a = new NumArea(); 
		a.value = 0;
		a.stages = new ArrayList<Integer>();
		a.special = new ArrayList<Integer>();
		a.members = Arrays.asList(lotNums[10], lotNums[18], lotNums[19],
				lotNums[26], lotNums[27], lotNums[28], lotNums[29],
				lotNums[35], lotNums[37]);
		areaNumbers[0] = a;
		a = new NumArea();
		a.value = 1;
		a.stages = new ArrayList<Integer>();
		a.special = new ArrayList<Integer>();
		a.members = Arrays.asList(lotNums[1], lotNums[9], lotNums[17]);
		areaNumbers[1] = a;
		a = new NumArea();
		a.value = 2;
		a.stages = new ArrayList<Integer>();
		a.special = new ArrayList<Integer>();
		a.members = Arrays.asList(lotNums[6], lotNums[7], lotNums[8],
				lotNums[30], lotNums[38], lotNums[39], lotNums[46],
				lotNums[47], lotNums[48]);
		areaNumbers[2] = a;
		a = new NumArea();
		a.value = 3;
		a.stages = new ArrayList<Integer>();
		a.special = new ArrayList<Integer>();
		a.members = Arrays.asList(lotNums[34], lotNums[36], lotNums[45]);
		areaNumbers[3] = a;
		a = new NumArea();
		a.value = 4;
		a.stages = new ArrayList<Integer>();
		a.special = new ArrayList<Integer>();
		a.members = Arrays.asList(lotNums[25]);
		areaNumbers[4] = a;
		a = new NumArea();
		a.value = 5;
		a.stages = new ArrayList<Integer>();
		a.special = new ArrayList<Integer>();
		a.members = Arrays.asList(lotNums[5], lotNums[14], lotNums[16]);
		areaNumbers[5] = a;
		a = new NumArea();
		a.value = 6;
		a.stages = new ArrayList<Integer>();
		a.special = new ArrayList<Integer>();
		a.members = Arrays.asList(lotNums[2], lotNums[3], lotNums[4],
				lotNums[11], lotNums[12], lotNums[20], lotNums[42],
				lotNums[43], lotNums[44]);
		areaNumbers[6] = a;
		a = new NumArea();
		a.value = 7;
		a.stages = new ArrayList<Integer>();
		a.special = new ArrayList<Integer>();
		a.members = Arrays.asList(lotNums[33], lotNums[41], lotNums[49]);
		areaNumbers[7] = a;
		a = new NumArea();
		a.value = 8;
		a.stages = new ArrayList<Integer>();
		a.special = new ArrayList<Integer>();
		a.members = Arrays.asList(lotNums[13], lotNums[15], lotNums[21],
				lotNums[22], lotNums[23], lotNums[24], lotNums[31],
				lotNums[32], lotNums[40]);
		areaNumbers[8] = a;

		String url = "http://www.pilio.idv.tw/ltobig/list.asp?indexpage=";
		int ecode = 0;
		String si;
		try {
			DOMParser parser = new DOMParser();
			parser.parse(url + 1);
			org.w3c.dom.Document document = parser.getDocument();
			org.w3c.dom.NodeList bnodes = document.getFirstChild()
					.getFirstChild().getNextSibling().getChildNodes();
			String pages = bnodes.item(6).getTextContent();
			int ipages = Integer.parseInt(pages.substring(pages.indexOf("有")+1, pages.indexOf("頁")));
			for (int k = 1, t = 1; k < ipages+1; k++) {
				parser.parse(url + k);
				document = parser.getDocument();
				bnodes = document.getFirstChild()
						.getFirstChild().getNextSibling().getChildNodes();
//				for(int i=0;i<bnodes.getLength();i++){
//					System.out.println(bnodes.item(i).getNodeName());
//				}
				org.w3c.dom.NodeList dnodes = bnodes.item(4).getFirstChild()
						.getNextSibling().getChildNodes();// BODY
				org.w3c.dom.NodeList trs = dnodes.item(1).getFirstChild()
						.getNextSibling().getChildNodes();// CENTER

				// System.out.println("got here"+bnodes.toString()+"length:"+trs.getLength());
				for (int i = 2; i < trs.getLength() && t < 50; i += 2) {
					int[] acount = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };

					t++;
					org.w3c.dom.NodeList tds = trs.item(i).getChildNodes();
					si = tds.item(7).getTextContent();

					int dsi = Integer.parseInt(si);
					recordSNumbers(dsi,
							Integer.valueOf(tds.item(1).getTextContent()));
					recordNumbers(dsi,
							Integer.valueOf(tds.item(1).getTextContent()));
					System.out.printf("第%s期 %d- ",
							tds.item(1).getTextContent(), findAreaCode(dsi));
					si = tds.item(5).getTextContent();
					dsi = Integer.parseInt(si.substring(0, 2));
					recordNumbers(dsi,
							Integer.valueOf(tds.item(1).getTextContent()));
					acount[findAreaCode(dsi)] += 1;
					dsi = Integer.parseInt(si.substring(5, 7));
					recordNumbers(dsi,
							Integer.valueOf(tds.item(1).getTextContent()));
					acount[findAreaCode(dsi)] += 1;
					dsi = Integer.parseInt(si.substring(9, 11));
					recordNumbers(dsi,
							Integer.valueOf(tds.item(1).getTextContent()));
					acount[findAreaCode(dsi)] += 1;
					dsi = Integer.parseInt(si.substring(14, 16));
					recordNumbers(dsi,
							Integer.valueOf(tds.item(1).getTextContent()));
					acount[findAreaCode(dsi)] += 1;
					dsi = Integer.parseInt(si.substring(19, 21));
					recordNumbers(dsi,
							Integer.valueOf(tds.item(1).getTextContent()));
					acount[findAreaCode(dsi)] += 1;
					dsi = Integer.parseInt(si.substring(24, 26));
					recordNumbers(dsi,
							Integer.valueOf(tds.item(1).getTextContent()));
					acount[findAreaCode(dsi)] += 1;
					int counter = 0;
					for (int ai = 0; ai < 9; ai++)
						if (acount[ai] > 0) {
							counter++;
							System.out.printf("%d(%d) ", ai, acount[ai]);
						}
					System.out.printf("::%d\n", counter);

				}// end of i for loop
			}// end of k for loop
		} catch (SAXException e) {
			ecode = -1;
			System.out.println(e.toString());
		} catch (NullPointerException e) {
			ecode = 1;
			System.out.println("End of table");
		} catch (Exception e) {
			ecode = -9;
			e.printStackTrace();
		} finally {
			System.out.println(ecode + "");
			// System.out.println("leaving lottery table, closing connection."+new
			// java.util.Date());
		}
		// System.out.println("過去49期號碼－出現次數－（特別號次數）："+numList);
		// Collections.sort(numList,new NumComparator());
		// System.out.println("過去49期號碼－出現次數－（特別號次數）：（排序後）"+numList);
		// List<NumArea> areaList = Arrays.asList(areaNumbers);
		// System.out.println("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊大樂透＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
		// System.out.println("過去49期　區碼－出現次數－（特別號次數）："+areaList);
		// Collections.sort(areaList,new NumComparator());
		// for(NumArea ad:areaNumbers)
		// System.out.printf("過去49期　區碼－出現次數－（特別號次數）：（排序後）%s:%s\n",ad.toString(),ad.members);
		if (args.length > 0) {
			//System.out.println(args.length);
			System.out.println("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊大樂透＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");

			int num = 0; // (int)(11*13*19*10103*Math.random()/17)%49;
			/*
			 * 6 的話，取亂數　８　取　６　區　給排後面的機會　６　以下各取１然後從其中一次加一個
			 */
			int seed = 6;
			for (int round = 0; round < args.length; round++) {
				List<NumArea> areaList = Arrays.asList(areaNumbers);
				Collections.sort(areaList, new NumComparator()); //區別排序
				
				System.out.printf("選%s區建議號碼：\n",args[round]);
				if ("6".equals(args[round])) {
					int counter = 0;
					Integer[] indexes = {9, 9, 9, 9, 9, 9};
					List<Integer> ndxList = Arrays.asList(indexes);
					while (counter < 6) {
						num = (int) (11 * 13 * 19 * 10103 * Math.random() / 17) % 8;						
						if (!ndxList.contains(num)) {
							indexes[counter] = num;
							counter++;
							ndxList = Arrays.asList(indexes);
						}
					}
					System.out.printf("從%s區選號\n",ndxList);
					for (int index = 0; index < 6; index++) {
						List<LotNum> numList = areaList.get(ndxList.get(index)).members;
						Collections.sort(numList, new NumComparator());
						seed = numList.size();
						num = (int) (11 * 13 * 19 * 10103 * Math.random() / 17)
								% seed;
						bigNumbers[index] = numList.get(num).value;
						System.out.printf("%d(%dth) \t", bigNumbers[index],num);
					}
				} else {
					int y = Integer.parseInt(args[round]);
					for (int index = 0; index < y; index++) {						
						List<LotNum> numList = areaList.get(index).members;
						System.out.printf("從第%d區選",areaList.get(index).value);
						Collections.sort(numList, new NumComparator());
						seed = numList.size();
						num = (int) (11 * 13 * 19 * 10103 * Math.random() / 17)
								% seed;
			
						Collections.sort(numList,new NumComparator() );
						bigNumbers[index] = numList.get(num).value;
						System.out.printf("%d(%dth) \t", bigNumbers[index],num);
					}

					for (int index = 0; index < 6-y; index++) {	
						num = ((int) (10103 * Math.random() / 17)+8)
								% y;
						List<LotNum> numList = areaList.get(num).members;
						System.out.printf("從第%d區選",areaList.get(num).value);
						Collections.sort(numList, new NumComparator());
						seed = numList.size();
						num = (int) (17 * 13 * 19 * 10103 * Math.random() / 11)
								% seed;
						
						bigNumbers[y+index] = numList.get(num).value;
						System.out.printf("%d(%dth) \t", bigNumbers[y+index],num);
					}		
					//for(int b:bigNumList) System.out.printf("%d ",b);					
				}// end else
				List<Integer> bigNumList = Arrays.asList(bigNumbers);
				Collections.sort(bigNumList);
				System.out.printf("\n排序：%s : \n",bigNumList);	
				//getSpecialNumber(areaList);
			}
		}
	}
}
