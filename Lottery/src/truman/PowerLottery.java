package truman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.cyberneko.html.parsers.DOMParser;
import org.xml.sax.SAXException;

public class PowerLottery {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LotNum [] lotNums = new LotNum[39];
		for(int i=0;i<39;i++){
			LotNum n = new LotNum();
			n.value = i;
			n.stages = new ArrayList<Integer>();
			n.special = new ArrayList<Integer>();
			n.dates = new ArrayList<String>();
			lotNums[i]=n;
		}
			

		int ecode = 0;
//		String result = "";
		String url = "http://www.pilio.idv.tw/lto/list.asp?indexpage=";
		String si;
		try {
			DOMParser parser = new DOMParser();
			for(int k=1,t=1;k<3&&t<39;k++){
			parser.parse(url+k);
			org.w3c.dom.Document document = parser.getDocument();
			org.w3c.dom.NodeList bnodes = document.getFirstChild().getFirstChild().getNextSibling().getChildNodes();
			org.w3c.dom.NodeList dnodes = bnodes.item(5).getFirstChild().getNextSibling().getChildNodes();
			org.w3c.dom.NodeList trs = dnodes.item(1).getFirstChild().getNextSibling().getChildNodes();
//		 	System.out.println("0"+document.getFirstChild().toString());
//		 	System.out.println("1"+document.getFirstChild().getFirstChild().toString());
//		 	System.out.println("2"+document.getFirstChild().getFirstChild().getNextSibling().toString());
//		 	System.out.println("3"+document.getFirstChild().getFirstChild().getNextSibling().getChildNodes().toString());
//		 	System.out.println("0"+bnodes.item(5).toString());
//		 	System.out.println("1"+bnodes.item(5).getFirstChild().toString());
//		 	System.out.println("2"+bnodes.item(5).getNextSibling().toString());
//		 	System.out.println("3"+bnodes.item(5).getFirstChild().getNextSibling().getChildNodes().toString());
			
			//System.out.println("got here"+trs.toString()+":"+trs.getLength());
			for(int i=2;i<trs.getLength()&&t<39;i+=2) {
				t++;
				org.w3c.dom.NodeList tds = trs.item(i).getChildNodes();
				for(int j=1;j<tds.getLength()-2;j+=2){
					
					si = tds.item(j).getTextContent();
					// 02?,?04??11?,?18?,?48?,?49??
					// 0123456789abcdef0123456789	
					// System.out.println(j+":"+si);
					switch(j){
					        //012345678901234567890123456789  
					case 5: //01 , 09 , 21 , 28 , 30 , 35 
						int dsi = Integer.parseInt(si.substring(0,2)); 
					lotNums[dsi].dates.add(tds.item(3).getTextContent());
					lotNums[dsi].stages.add(Integer.valueOf(tds.item(1).getTextContent()));
						
						dsi = Integer.parseInt(si.substring(5,7)); 
						lotNums[dsi].dates.add(tds.item(3).getTextContent());
						lotNums[dsi].stages.add(Integer.valueOf(tds.item(1).getTextContent()));
						
						dsi = Integer.parseInt(si.substring(10,12));
						lotNums[dsi].dates.add(tds.item(3).getTextContent());
						lotNums[dsi].stages.add(Integer.valueOf(tds.item(1).getTextContent()));
						
						dsi = Integer.parseInt(si.substring(15,17));
						lotNums[dsi].dates.add(tds.item(3).getTextContent());
						lotNums[dsi].stages.add(Integer.valueOf(tds.item(1).getTextContent()));
						
						dsi = Integer.parseInt(si.substring(20,22));
						lotNums[dsi].dates.add(tds.item(3).getTextContent());
						lotNums[dsi].stages.add(Integer.valueOf(tds.item(1).getTextContent()));
						
						dsi = Integer.parseInt(si.substring(25,27));
						lotNums[dsi].dates.add(tds.item(3).getTextContent());
						lotNums[dsi].stages.add(Integer.valueOf(tds.item(1).getTextContent()));
						
						break;
					case 7: lotNums[Integer.parseInt(si)].special.add(Integer.valueOf(tds.item(1).getTextContent()));
					break; 			
					}
				}
			}
			} // end of for k
		} catch (SAXException e) {	
			ecode = -1;
			System.out.println(e.toString());
		} catch(NullPointerException e){
			ecode = 1;
			System.out.println("End of table");
		} catch(Exception e){
			ecode = -9;
			e.printStackTrace();
		} finally {
			System.out.println(ecode+"");
			//System.out.println("leaving lottery table, closing connection."+new java.util.Date());
		}
		List<LotNum> numList = Arrays.asList(lotNums);
		System.out.println("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊威力彩＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
		System.out.println("過去３８期號碼－出現次數－（特別號次數）："+numList);
		Collections.sort(numList,new NumComparator());
		System.out.println("過去３８期號碼－出現次數－（特別號次數）：（排序後）"+numList);
		
		System.out.println("第一區建議：(有重複的話，重新執行)");
		List<Integer> powerNumbers = new ArrayList<Integer>();
		for(int i=0;i<6;i++){
			int	num = (int)(7*13*19*10103*Math.random()/17)%38;
			powerNumbers.add(numList.get(num).value);
		}
		Collections.sort(powerNumbers);
		for(int n:powerNumbers) System.out.printf("%d ",n);

		Collections.sort(numList,new NumComparator2());
		int num = (int)(17*13*19*10000*Math.random()/7)%8;
		System.out.printf("\n第二區建議：%d\n",numList.get(num).value);
//		System.out.printf("第二區：第%d個",num	);
		
	}

}
