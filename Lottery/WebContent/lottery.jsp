<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.lang.Comparable,org.cyberneko.html.parsers.DOMParser,java.util.*,java.sql.*,org.dom4j.*,org.dom4j.io.DOMReader,org.xml.sax.SAXException"
    pageEncoding="UTF-8"  %>

<%

class LotNum implements Comparable<Object>{
	int value;
	ArrayList<Integer> stages;
	ArrayList<Integer> special;
	ArrayList<String> dates;
	
	@Override
	public String toString(){
		return String.format("%d-%d-%d",value,stages.size(),special.size());		
	}

	@Override
	public int compareTo(Object o){
		LotNum other = (LotNum) o;
		return other.stages.size()-this.stages.size();
	}
}
LotNum [] lotNums = new LotNum[39];
for(int i=1;i<39;i++){
	LotNum n = new LotNum();
	n.value = i;
	n.stages = new ArrayList<Integer>();
	n.special = new ArrayList<Integer>();
	n.dates = new ArrayList<String>();
	lotNums[i]=n;
}
	

int ecode = 0;
String result = "";
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
// 	System.out.println("0"+document.getFirstChild().toString());
// 	System.out.println("1"+document.getFirstChild().getFirstChild().toString());
// 	System.out.println("2"+document.getFirstChild().getFirstChild().getNextSibling().toString());
// 	System.out.println("3"+document.getFirstChild().getFirstChild().getNextSibling().getChildNodes().toString());
// 	System.out.println("0"+bnodes.item(5).toString());
// 	System.out.println("1"+bnodes.item(5).getFirstChild().toString());
// 	System.out.println("2"+bnodes.item(5).getNextSibling().toString());
// 	System.out.println("3"+bnodes.item(5).getFirstChild().getNextSibling().getChildNodes().toString());
	
	System.out.println("got here"+trs.toString()+":"+trs.getLength());
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
	ecode = -1;LotNum [] lotNums = new LotNum[39];
	for(int i=1;i<39;i++){
		LotNum n = new LotNum();
		n.value = i;
		n.stages = new ArrayList<Integer>();
		n.special = new ArrayList<Integer>();
		n.dates = new ArrayList<String>();
		lotNums[i]=n;
	}
		

	int ecode = 0;
	String result = "";
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
//	 	System.out.println("0"+document.getFirstChild().toString());
//	 	System.out.println("1"+document.getFirstChild().getFirstChild().toString());
//	 	System.out.println("2"+document.getFirstChild().getFirstChild().getNextSibling().toString());
//	 	System.out.println("3"+document.getFirstChild().getFirstChild().getNextSibling().getChildNodes().toString());
//	 	System.out.println("0"+bnodes.item(5).toString());
//	 	System.out.println("1"+bnodes.item(5).getFirstChild().toString());
//	 	System.out.println("2"+bnodes.item(5).getNextSibling().toString());
//	 	System.out.println("3"+bnodes.item(5).getFirstChild().getNextSibling().getChildNodes().toString());
		
		System.out.println("got here"+trs.toString()+":"+trs.getLength());
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
		%> <%=ecode %> <%
		//System.out.println("leaving lottery table, closing connection."+new java.util.Date());
	}
	System.out.println(e.toString());
} catch(NullPointerException e){
	ecode = 1;
	System.out.println("End of table");
} catch(Exception e){
	ecode = -9;
	e.printStackTrace();
} finally {
	%> <%=ecode %> <%
	//System.out.println("leaving lottery table, closing connection."+new java.util.Date());
}	

%>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<center>

<%
for(int k=1;k<39;k++){%>
<%=k%>-<%=lotNums[k].stages.size()%>-<%=lotNums[k].special.size()%><br/><% 
   for(Integer st:lotNums[k].stages){%>
    <%=st %>-
	<%}%><br/><% 
   for(Integer sp:lotNums[k].special){%>
    <%=sp %>
   <%}%><br/><%
}
List<LotNum> numList = Arrays.asList(lotNums);
%><%= numList.toString()%>

</center>
</body>
</html>