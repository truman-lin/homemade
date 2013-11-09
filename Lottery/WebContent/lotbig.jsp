<%@ page language="java" contentType="text/html; charset=UTF-8" import="org.cyberneko.html.parsers.DOMParser,java.util.*,java.sql.*,org.dom4j.*,org.dom4j.io.DOMReader,org.xml.sax.SAXException"
    pageEncoding="UTF-8"   %>

<%
int [][] plate = new int[7][7];
int [] arrayI = new int[50];
int [] arrayJ = new int[50];
int [][] arrayP = new int[7][7];
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
arrayP[3][3] = 25;
for(i=0;i<3;i++) for(j=0;j<3;j++) arrayP[i][j] = 27;
for(i=0;i<3;i++) for(j=4;j<7;j++) arrayP[i][j] = 47;
for(i=4;i<7;i++) for(j=0;j<3;j++) arrayP[i][j] = 3;
for(i=4;i<7;i++) for(j=4;j<7;j++) arrayP[i][j] = 23;
for(i=0;i<3;i++) arrayP[i][3] = 9;
for(i=4;i<7;i++) arrayP[i][3] = 41;
for(j=0;j<3;j++) arrayP[3][j] = 36;
for(j=4;j<7;j++) arrayP[3][j] = 14;

/* for(i=0;i<7;i++){
	for(j=0;j<7;j++)
		System.out.printf("%d\t",plate[i][j]);
	System.out.print("\n");
}
for(i=1;i<50;i++)
	System.out.printf("%d\t",arrayI[i]);
System.out.print("\n");
for(i=1;i<50;i++)
	System.out.printf("%d\t",arrayJ[i]); */

Class.forName("com.mysql.jdbc.Driver");
Connection connection =	DriverManager.getConnection("jdbc:mysql://localhost:3306", "truman", "02mb94");
PreparedStatement stmt = connection.prepareStatement("insert into truman.lottery values(?,?,?,?,?,?,?,?,?,'X',?,?,?,?,?,?,?,0)");
	
stmt.execute("delete from truman.lottery");
int ecode = 0;
String result = "";
String url = "http://www.pilio.idv.tw/ltobig/list.asp?indexpage=";
String si;
try {
	DOMParser parser = new DOMParser();
	for(int k=1;k<21;k++){
	parser.parse(url+k);
	org.w3c.dom.Document document = parser.getDocument();
	org.w3c.dom.NodeList bnodes = document.getFirstChild().getFirstChild().getNextSibling().getChildNodes();
	org.w3c.dom.NodeList dnodes = bnodes.item(3).getFirstChild().getNextSibling().getChildNodes();//BODY
	org.w3c.dom.NodeList trs = dnodes.item(1).getFirstChild().getNextSibling().getChildNodes();//CENTER
	System.out.println("got here"+bnodes.toString()+"length:"+trs.getLength());
	for(i=2;i<trs.getLength();i+=2) {
		org.w3c.dom.NodeList tds = trs.item(i).getChildNodes();
		for(j=1;j<tds.getLength()-2;j+=2){
			si = tds.item(j).getTextContent();
			// 02?,?04??11?,?18?,?48?,?49??
			// 0123456789abcdef0123456789	
			 //System.out.println(si);
			switch(j){
			        //012345678901234567890123456789  
			case 5: //19 , 24  28 , 29 , 41 , 43 
				int dsi = Integer.parseInt(si.substring(0,2));
				stmt.setInt(3,dsi); stmt.setInt(10,arrayP[arrayI[dsi]][arrayJ[dsi]]);
				dsi = Integer.parseInt(si.substring(5,7)); 
				stmt.setInt(4,dsi); stmt.setInt(11,arrayP[arrayI[dsi]][arrayJ[dsi]]);
				dsi = Integer.parseInt(si.substring(9,11)); 
				stmt.setInt(5,dsi); stmt.setInt(12,arrayP[arrayI[dsi]][arrayJ[dsi]]);
				dsi = Integer.parseInt(si.substring(14,16)); 
				stmt.setInt(6,dsi); stmt.setInt(13, arrayP[arrayI[dsi]][arrayJ[dsi]]);
				dsi = Integer.parseInt(si.substring(19,21)); 
				stmt.setInt(7,dsi); stmt.setInt(14, arrayP[arrayI[dsi]][arrayJ[dsi]]);
				dsi = Integer.parseInt(si.substring(24,26)); 
				stmt.setInt(8,dsi); stmt.setInt(15, arrayP[arrayI[dsi]][arrayJ[dsi]]);
				break;
			case 1: stmt.setInt(1, Integer.parseInt(si)); break;
			case 3: stmt.setString(2,si); break;
			case 7: stmt.setInt(9,Integer.parseInt(si)); stmt.setInt(16,arrayP[arrayI[Integer.parseInt(si)]][arrayJ[Integer.parseInt(si)]]); break; 			
			}
		}
		stmt.addBatch();
	}
	} // end of for k
	stmt.executeBatch();
} catch (SAXException e) {	
	ecode = -1;
	System.out.println(e.toString());
} catch(NullPointerException e){
	ecode = 1;
	System.out.println("End of table");
} catch(Exception e){
	ecode = -9;
	System.out.println(e.toString());
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
<table border="1">
<%
for(i=0;i<7;i++){
	%><tr><%
	for(j=0;j<7;j++)
		{%><td><%=plate[i][j]%></td><%}
	%></tr><%
}
%>
</table></center>
</body>
</html>