package truman;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import truman.DataSource;

public class LotteryDAO {
	private final DataSource ds = DataSource.INSTANCE;
	
	public List<lotteryNums> getLotteryNums(){
		List<lotteryNums> lol = new ArrayList<lotteryNums>();
		String sql = "select * from truman.lottery";
		try{
		Statement stmt = ds.getStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
			lotteryNums l = new lotteryNums();
			l.setN1(rs.getInt("n1"));
			l.setN2(rs.getInt("n2"));
			l.setN3(rs.getInt("n3"));
			l.setN4(rs.getInt("n4"));
			l.setN5(rs.getInt("n5"));
			l.setN6(rs.getInt("n6"));
			l.setNs(rs.getInt("ns"));
			l.setDates(rs.getString("dates"));
			l.setStage(rs.getInt("stage"));
			lol.add(l);
		}
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			ds.close();
		}		
		return lol;
	}
}
