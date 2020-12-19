import javax.swing.JFrame;
import java.util.*;
import java.awt.*;import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MusicExecute extends JFrame{
	/**
	 * Define Main Function
	 * Set Connection and run JFrame MusicExecute
	 */
	public static void main(String[] args){
		MusicExecute screen = new MusicExecute("Melon");
		System.out.println("STARTED");

		String userID = "dbuser";
		String userPW = "dbpwd";
		String dbName = "dbprj";
		String url = "jdbc:mysql://localhost:3306/" + dbName + "?&serverTimezone=UTC";
		
		myConn = null;myState = null;
		myResSet = null;sql = "";

		try {
			
			myConn = DriverManager.getConnection(url, userID, userPW);
			System.out.println("... Connected to databse " + dbName + " in MySQL with " + myConn.toString() + " ...");
			
			myState = myConn.createStatement();
			initializetable();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	static Connection myConn;static String sql;
	static Statement myState;static ResultSet myResSet;
	int insert_check=0;SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");
	JPanel entire = new JPanel();
	static JPanel song = new JPanel();
	static JPanel artist = new JPanel();
	static JPanel album = new JPanel();
	static JPanel Project = new JPanel(); 
	static JPanel up= new JPanel(); static JLabel info = new JLabel("");
	JPanel insert = new JPanel();JButton set = new JButton("select Privilege"); int insert_stat = 0;
	JLabel ques_1 = new JLabel("select Privilege");JTextField ans_1 = new JTextField(15);
	JLabel ques_2 = new JLabel("");JTextField ans_2 = new JTextField(15);
	JLabel ques_3 = new JLabel("");JTextField ans_3 = new JTextField(15);
	JLabel ques_4 = new JLabel("");JTextField ans_4 = new JTextField(15);
	JTabbedPane jtp = new JTabbedPane();
	MenuBar menbar = new MenuBar();
	static JScrollPane song_scroll = new JScrollPane(song, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
			   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	Menu men_add = new Menu("what"); Menu men_query = new Menu("Privilege"); 
	MenuItem add_1 = new MenuItem("Song"); MenuItem add_2 = new MenuItem("Artist"); MenuItem add_3 = new MenuItem("Album");
	Menu men_option = new Menu("Nested");
	MenuItem nest_1 = new MenuItem("not in");MenuItem nest_2 = new MenuItem("in");
	MenuItem nest_3 = new MenuItem(">");MenuItem nest_4 = new MenuItem("<");
	MenuItem nest_5 = new MenuItem(">=");MenuItem nest_6 = new MenuItem("<=");
	MenuItem query_1 = new MenuItem("Insert"); 
	MenuItem query_2 = new MenuItem("Update");MenuItem query_2_2 = new MenuItem("Update Using Transaction");
	MenuItem query_3 = new MenuItem("Delete");
	MenuItem query_4 = new MenuItem("Select");
	MenuItem query_5 = new MenuItem("select Nested"); 
	MenuItem query_6 = new MenuItem("Select with View");
	MenuItem query_7 = new MenuItem("Print All");
	Menu men_exit = new Menu("More..");
	MenuItem exit_1 = new MenuItem("Exit");
	Color back = new Color(255,255,255);int in = 0;
	
	/**
	 * Define Constructor: JFrame
	 * On report(5.How it works) help your understanding.
	 * @param s: JFrame Name
	 */
	public MusicExecute(String s) {
		super(s);
		setBackground(Color.white);
		setVisible(true);
		
		//JFrame, section 1,2 on GUI window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(new Dimension(screenSize));
		entire.setLayout(new GridLayout(2,2,10,5));this.setLayout(new BorderLayout());
		this.add(jtp);this.setBackground(Color.white);
		this.add(up, BorderLayout.NORTH); up.add(info);
		this.add(entire, BorderLayout.CENTER);song.setLayout(new BoxLayout(song,BoxLayout.Y_AXIS));
		entire.add(song);song.setBackground(back);
		album.setLayout(new BoxLayout(album, BoxLayout.Y_AXIS)); artist.setLayout(new BoxLayout(artist, BoxLayout.Y_AXIS));
		entire.add(artist);artist.setBackground(back);
		entire.add(album);album.setBackground(back);
		entire.add(Project);Project.setBackground(Color.white);Project.setLayout(new  BoxLayout(Project, BoxLayout.Y_AXIS));
		
		//on GUI window, section 4
		this.add(insert, BorderLayout.SOUTH);
		insert.setLayout(new FlowLayout());
		insert.add(ques_1);insert.add(ans_1);
		insert.add(ques_2);insert.add(ans_2);
		insert.add(ques_3);insert.add(ans_3);
		insert.add(ques_4);insert.add(ans_4);
		insert.add(set); set.setActionCommand("add"); set.addActionListener(new query_listener());
		
		//on GUI window, section 1
		setMenuBar(menbar);
		menbar.add(men_add);//MenuBar what
		men_add.add(add_1);men_add.add(add_2);men_add.add(add_3);
		add_1.setActionCommand("song");add_1.addActionListener(new set_listener());
		add_2.setActionCommand("artist");add_2.addActionListener(new set_listener());
		add_3.setActionCommand("album");add_3.addActionListener(new set_listener());
		
		menbar.add(men_query);//MenuBar Privilege
		men_query.add(query_1);men_query.add(query_2);men_query.add(query_2_2);men_query.add(query_3);
		men_query.add(query_4);	men_query.add(query_5);men_query.add(query_6);men_query.add(query_7);
		query_1.setActionCommand("query_1");query_1.addActionListener(new set_listener());
		query_2.setActionCommand("query_2");query_2.addActionListener(new set_listener());
		query_2_2.setActionCommand("query_2_2");query_2_2.addActionListener(new set_listener());
		query_3.setActionCommand("query_3");query_3.addActionListener(new set_listener());
		query_4.setActionCommand("query_4");query_4.addActionListener(new set_listener());
		query_5.setActionCommand("query_5");query_5.addActionListener(new set_listener());
		query_6.setActionCommand("query_6");query_6.addActionListener(new set_listener());
		query_7.setActionCommand("query_7");query_7.addActionListener(new set_listener());
	
		menbar.add(men_option);//MenuBar Nested
		men_option.add(nest_1);nest_1.setActionCommand("in");nest_1.addActionListener(new set_listener());
		men_option.add(nest_2);nest_2.setActionCommand("not in");nest_2.addActionListener(new set_listener());
		men_option.add(nest_3);nest_3.setActionCommand(">");nest_3.addActionListener(new set_listener());
		men_option.add(nest_4);nest_4.setActionCommand("<");nest_4.addActionListener(new set_listener());
		men_option.add(nest_5);nest_5.setActionCommand(">=");nest_5.addActionListener(new set_listener());
		men_option.add(nest_6);nest_6.setActionCommand("<=");nest_6.addActionListener(new set_listener());
		
		menbar.add(men_exit);//MenuBar More
		men_exit.add(exit_1); exit_1.setActionCommand("exit");exit_1.addActionListener(new set_listener());
	}
	

	/**
	 * Define Function that works with Select Query
	 * This function returns the result value of each column
	 */
public Object getResultValue(int columnType, String columnName, ResultSet rs) throws SQLException {
	  if (columnType == Types.NUMERIC || columnType == Types.INTEGER || columnType == Types.BIGINT) {
	    return rs.getInt(columnName);

	  } else if (columnType == Types.DOUBLE || columnType == Types.FLOAT) {
	    return rs.getDouble(columnName);

	  } else if (columnType == Types.CHAR || columnType == Types.VARCHAR || columnType == Types.NVARCHAR) {
	    return rs.getString(columnName);

	  } else {
	    return rs.getString(columnName);
	  }
}


	/**
	 * Define ActionListener for query: Query Listener
	 * 	1.get Text that implies Query term
	 * 	2.Match the query and Execute
	 * 		Basic step
	 * 			- Check which relation is used by insert_check(on MenuBar)
	 * 			- Check "where" condition is null or not, match the term
	 * 		1)Insert
	 * 		2)Update
	 * 		3)Update with Transaction
	 * 			set customized Transaction: setAutoCommit(false) - preparedStatement and execute - commit - setAutoCommit(true)
	 * 		4)Delete
	 * 		5)Select Nested Query
	 * 			" select * from ( Table 1 ) natural join ( Table 2 ) where (      ) in, or and 4 operator (           ) " 
	 * 			To get Nested Query and Join, set the query form and use select function 
	 * 		6)Select / Select with View
	 * 			use Select function
	 */
public class query_listener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		Project.removeAll();
		song.removeAll();artist.removeAll();album.removeAll();	
		//1. get Text that implies Query term
		String str_1 = ans_1.getText();	
		String str_2 = ans_2.getText();
		String str_3 = ans_3.getText();	
		String str_4 = ans_4.getText();
		Object source = e.getActionCommand();
		
		if(source.equals("add")) {
			//2. Match the query and Execute
			
			//1) Insert Query
			if(set.getText()=="Insert") {	
				PreparedStatement pStmt = null;
				try {
					if(insert_check==1) {	//Check which relation is used by insert_check(on MenuBar)
						pStmt = myConn.prepareStatement("Insert into Song values (?, ?, ?, ?)");
						pStmt.setString(1, str_1);pStmt.setString(2, str_2);pStmt.setString(3, str_3);
						Date d = Date.valueOf(str_4);pStmt.setDate(4, d); //format string to Date
					}
					if(insert_check==2) {
						pStmt = myConn.prepareStatement("Insert into Artist values (?, ?, ?)");
						pStmt.setString(1, str_1);pStmt.setString(2, str_2);pStmt.setInt(3, Integer.parseInt(str_3));
					}
					if(insert_check==3) {
						pStmt = myConn.prepareStatement("Insert into Album values (?, ?, ?)");
						pStmt.setString(1, str_1);
						String transDate = afterFormat.format(str_2);Date d = Date.valueOf(transDate);
						pStmt.setDate(2, d);pStmt.setInt(3, Integer.parseInt(str_3));
					}
					pStmt.executeUpdate();info.setText("Insert Completed. Choose Privilege");
				} catch (SQLException e1) {
					e1.printStackTrace();
					info.setText(e1.toString());
					set.setText("Choose Privilege");
				}
			}
			
			//2) Update
			if(set.getText()=="Update") {
				PreparedStatement pStmt = null;
				String where = "";	//Check "where" condition is null or not, match the term
				if(!str_3.isEmpty()) where += str_3;
				if(!str_4.isEmpty()) where = where + " and "+str_4;
				
				try {
					System.out.println("set "+str_1+"="+str_2+" where "+where);
					if(insert_check==1) {
						pStmt = myConn.prepareStatement("Update Song set "+str_1+"="+str_2+" where "+where);
					}
					if(insert_check==2) {
						pStmt = myConn.prepareStatement("Update Artist set "+str_1+"="+str_2+" where "+where);
					}
					if(insert_check==3) {
						pStmt = myConn.prepareStatement("Update Album set "+str_1+"="+str_2+" where "+where);
					}
					pStmt.executeUpdate();
					
				} catch (SQLException e1) {
					e1.printStackTrace();info.setText(e1.toString());
				}
				initializetable();set.setText("Choose Privilege");
				info.setText("Update Completed. Choose Privilege");
			}
	
	//3) Update with Transaction
	if(set.getText()=="Update with Transaction") {
		PreparedStatement pStmt = null;
		String where = "";
		if(!str_3.isEmpty()) where += str_3;
		if(!str_4.isEmpty()) where = where + " and "+str_4;
		
		
		try {
			if(insert_check==1) {
				sql = "Update Song set "+str_1+"="+str_2+" where "+where;
			}
			if(insert_check==2) {
				sql = "Update Artist set "+str_1+"="+str_2+" where "+where;
			}
			if(insert_check==3) {
				sql = "Update Album set "+str_1+"="+str_2+" where "+where;
			}
			
			//set customized Transaction
			//setAutoCommit(false) - preparedStatement and execute - commit - setAutoCommit(true)
			myConn.setAutoCommit(false);
			JLabel com_1 = new JLabel("Set Auto Commit false");Project.add(com_1);
			pStmt = myConn.prepareStatement(sql);
			JLabel com_2 = new JLabel("Set PreparedStatement");Project.add(com_2);
			pStmt.executeUpdate();
			JLabel com_3 = new JLabel("Set Execute Update");Project.add(com_3);
			myConn.commit();
			System.out.println(sql);
			JLabel com_4 = new JLabel("Transaction Commit");Project.add(com_4);
			myConn.setAutoCommit(true);
			JLabel com_5 = new JLabel("Set Auto Commit True");Project.add(com_5);
			info.setText("Transaction-Update Completed Choose Privilege");
		} catch (SQLException e1) {
			e1.printStackTrace();info.setText(e1.toString());
			if(myConn!=null) try{myConn.rollback();}catch(SQLException sqle){}  
		}
		initializetable();set.setText("Choose Privilege");
	}
			
			//4) Delete
			if(set.getText()=="Delete") {
				PreparedStatement pStmt = null;String where = null;
				if(!str_1.isEmpty())where =" where "+ str_1;
				if (!str_2.isEmpty()) where = where + " and "+str_2;
				if (!str_3.isEmpty())where = where + " and "+str_3;
				if (!str_4.isEmpty())where = where + " and "+str_4;
				try {
					if(insert_check==1) {
						pStmt = myConn.prepareStatement("Delete from Song"+where);
					}
					if(insert_check==2) {
						pStmt = myConn.prepareStatement("Delete from Artist"+where);
					}
					if(insert_check==3) {
						pStmt = myConn.prepareStatement("Delete from Album"+where);
					}
					pStmt.executeUpdate();info.setText("Delete Completed. Choose Privilege");
				} catch (SQLException e1) {
					e1.printStackTrace();
					info.setText(e1.toString()); 
				}
				initializetable();set.setText("Choose Privilege");
			}
			
			//5) Select Nested Query
			//select * from ( Table 1 ) natural join ( Table 2 ) where (      ) in/or/>/</<=/>= (           )
			if(set.getText()=="select Nested") {
				PreparedStatement pStmt = null;ResultSet rs;
				sql = "select * from "+str_1+" natural join "+str_2+" where "+str_3;
				
				//get additional options that put on nested query: in/or/>/</<=/>=
				if(in==0)sql+=" in ";
				if(in==1)sql+=" not in ";
				if(in==2)sql+=" > ";
				if(in==3)sql+=" < ";
				if(in==4)sql+=" >= ";
				if(in==5)sql+=" <= ";
				sql = sql+str_4;
					
				select(pStmt, sql);	//use select function
				initializetable();set.setText("Choose Privilege");info.setText("Select Completed. Choose Privilege");
			}
			
			//6) Select / Select with View
			if(set.getText()=="Select"  || set.getText()==("Select with View")) {
				PreparedStatement pStmt = null;ResultSet rs;
				sql = "select "+ str_1 + " from "+ str_2;
				if(!str_3.isEmpty())sql = sql +" where "+ str_3;
				if (!str_4.isEmpty())sql = sql + " and "+str_4;
				select(pStmt, sql);info.setText("Select Completed. Choose Privilege");
		}
			initializetable();set.setText("Choose Privilege");
			
	}
		
	}
}
	

	/**
	 * Define function that works on select query
	 * @param pStmt: PreparedStatement
	 * @param sql: query that used for select query
	 * 	1.Use PreparedStatement to execute query
	 * 	2.Reach ResultSetMetaData for each column name and type
	 * 	3.Get contents using Resultset
	 */
	public void select(PreparedStatement pStmt, String sql){
		Project.removeAll();

		//1. Use PreparedStatement to execute query
		ResultSet rs;
		try {
			System.out.println(sql);
			pStmt = myConn.prepareStatement(sql);
			rs = pStmt.executeQuery();
			
		//2. Reach ResultSetMetaData for each column name and type
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCnt = rsmd.getColumnCount();
			String up ="";
			ArrayList<Map> columnList = new ArrayList<>();
			for (int i = 1; i <= columnCnt; i++) { //using columncount, access each Column information
			    Map<String, Object> map = new HashMap<>();
			    map.put("name", rsmd.getColumnName(i));
			    map.put("type", rsmd.getColumnType(i));
			    columnList.add(map);
			    up = up+String.format("%15s %10s %15s","",rsmd.getColumnName(i),""); 
			}
			JLabel tit = new JLabel(up);
			System.out.println(up);
			Project.add(tit);
			JLabel tit2 = new JLabel(String.format("%200s", "").replace(' ', '-'));Project.add(tit2);
			
		//3. Get contents using Resultset
			while(rs.next()) {
				String result = "";
				for(int i=0;i<columnCnt;i++) {
					Map columnInfo = columnList.get(i);
				    String columnName = (String) columnInfo.get("name");
				    int columnType = (int) columnInfo.get("type");
				        Object obj = getResultValue(columnType, columnName, rs); //getResultValue function
				        result = result+String.format("%5s %10s %5s","",obj.toString(),"");
				}
				JLabel result_lab = new JLabel(result);
				System.out.println(result);
				Project.add(result_lab);info.setText("Select Completed. Choose Privilege");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();info.setText(e1.toString()); //to detect error set TextLabel
		}
	}
	

	/**
	 * Define function to adjust parameters
	 * 	1) Relation(on insert query)
	 * 	2) Query Privilege
	 * 	3) Nested Operator
	 */
	public class set_listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			song.removeAll();artist.removeAll();album.removeAll();	
			//1) Relation
			Object source = e.getActionCommand();
			if(source.equals("song")) {
				insert_check=1;
				info.setText("Select Privilege");
			}
			else if(source.equals("artist")) {
				insert_check=2;
				info.setText("Select Privilege");
			}
			else if(source.equals("album")) {
				insert_check=3;
				info.setText("Select Privilege");
			}
			
			//2) Privilege
			//insert 
			else if(source.equals("query_1")) {
				if(insert_check == 0) {
					info.setText("Check Table from MenuBar");	
				}
				
				//define different query options based on selected relation
				if(insert_check==1) {
					info.setText("Insert into Song");
					set.setText("Insert");
					ques_1.setText("Artist name: ");
					ques_2.setText("Song name: ");
					ques_3.setText("Album name: ");
					ques_4.setText("song Date: ");	
				}
				if(insert_check==2) {
					info.setText("Insert into Artist");
					set.setText("Insert");
					ques_1.setText("Artist name: ");
					ques_2.setText("Agency name: ");
					ques_3.setText("Debut year: ");
					ques_4.setText("Additional: ");	
				}
				if(insert_check==3) {
					info.setText("Insert into Album (Don't have to use \")");
					set.setText("Insert");
					ques_1.setText("Album Title: ");
					ques_2.setText("album Date: ");
					ques_3.setText("Likes: ");
					ques_4.setText("Additional: ");	
				}
			}
			
			//update 
			else if(source.equals("query_2")) {
				set.setText("Update");
				if(insert_check==1) {
					info.setText("Update Song set");
				}
				if(insert_check==2) {
					info.setText("Update Artist set");
				}
				if(insert_check==3) {
					info.setText("Update Album set");
				}
				ques_1.setText("set");
				ques_2.setText("to");
				ques_3.setText("where condition 1:");
				ques_4.setText("where condition 2:");
			}
			
			//Update with Transaction
			else if(source.equals("query_2_2")){
				if(insert_check==1) {
					info.setText("Update Song set");
				}
				if(insert_check==2) {
					info.setText("Update Artist set");
				}
				if(insert_check==3) {
					info.setText("Update Album set");
				}
				set.setText("Update with Transaction");
				ques_1.setText("set");
				ques_2.setText("to");
				ques_3.setText("where condition 1:");
				ques_4.setText("where condition 2:");
			}
			
			// Delete
			else if(source.equals("query_3")) {
				System.out.println("13");
				if(insert_check==1) {
					info.setText("Delete Song set");
				}
				if(insert_check==2) {
					info.setText("Delete Artist set");
				}
				if(insert_check==3) {
					info.setText("Delete Album set");
				}
				set.setText("Delete");
				ques_1.setText("delete condition 1:");
				ques_2.setText("delete condition 2:");
				ques_3.setText("delete condition 3:");
				ques_4.setText("delete condition 4:");
			}
			
			//Select 
			else if(source.equals("query_4")){
				if(insert_check==1) {
					info.setText("Select Song set");
				}
				if(insert_check==2) {
					info.setText("Select Artist set");
				}
				if(insert_check==3) {
					info.setText("Select Album set");
				}
				set.setText("Select");
				ques_1.setText("Project Column");
				ques_2.setText("from");
				ques_3.setText("where condition 1:");
				ques_4.setText("where condition 2:");
			}
			
			//Select Nested query and JOIN
			else if(source.equals("query_5")) {
				info.setText(" select * from ( Table 1 ) natural join ( Table 2 ) where (      ) in/or/>/</<=/>= (           ) ");
				ques_1.setText("Table 1");
				ques_2.setText("Table 2");
				ques_3.setText("where ");
				set.setText("select Nested");
				if(in==0)
					ques_4.setText("in");
				if(in==1)
					ques_4.setText("not in");
				if(in==2)
					ques_4.setText(">");
				if(in==3)
					ques_4.setText("<");
				if(in==5)
					ques_4.setText(">=");
				if(in==6)
					ques_4.setText("<=");
			}
			//printout all
			else if(source.equals("query_7")) {
				initializetable();Project.removeAll();
			//Select View
			}else if(source.equals("query_6")) {
				System.out.println("18");
				Project.removeAll();
				Project.add(new JLabel("This is Song_album View"));
				set.setText("Select with View");
				ques_1.setText("Project Column");
				ques_2.setText("from");
				ques_3.setText("where condition 1:");
				ques_4.setText("where condition 2:");
				PreparedStatement pStmt = null;ResultSet rs;
				select(pStmt, "select * from song_album");
			}
			

			//3) Nested Operator
			else if(source.equals("in")) {in=1;}
			else if(source.equals("not in")) {in=0;}	
			else if(source.equals(">")) {in=2;}
			else if(source.equals("<")) {in=3;}
			else if(source.equals(">=")) {in=4;}
			else if(source.equals("<=")) {in=5;}
			initializetable();
			//exit
			if(source.equals("exit")) {
				if (myResSet != null) {
					try {
						myResSet.close();
						System.out.println("... Close ResultSet ...");
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				}
				
				if (myState != null) {
					try {
						myState.close();
						System.out.println("... Close Statement ...");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
				if (myConn != null) {
					try {
						myConn.close();
						System.out.println("... Close Connection " + myConn.toString() + " ...");
					} catch (SQLException aw) {
						aw.printStackTrace();
					}
				}
				info.setText("Disconnect Completed");
				song.removeAll();artist.removeAll();album.removeAll();Project.removeAll();
			}
		}}
	

	/**
	 * Define function to print all the tables on database
	 * 	1.remove all the contents on each Panel
	 * 	2.Select Query Connect
	 * 	2-1.Set PreparedStatement and execute query
	 * 	2-2.Using resultset, print all contents
	 */
	public static void initializetable() {
		//1. remove all the contents on each Panel
		song.removeAll();artist.removeAll();album.removeAll();	
		
		//use Connection, connect by PreparedStatement and print out using resultset
		try {		
		//2-1. Set PreparedStatement and executequery
		sql = "SELECT * FROM Song";								//2. Select Query Connect: Song relation				
		myResSet = myState.executeQuery(sql);					
		
		String s_artist_name = "";
		String name = "";
		String album_str = "";
		Date song_date, album_date;
		int launched = -1;
		
		JLabel lab1 = new JLabel(String.format("%15s artist_name %30s Name %20s  song_date %20s  album %6s","", "", "","", ""));
		JLabel lab2 = new JLabel(String.format("%200s", "").replace(' ', '-'));
		song.add(lab1);song.add(lab2);
		
		//2-2. Using resultset, print all contents
		while (myResSet.next()) {					
			s_artist_name = myResSet.getString("artist_name");
			name = myResSet.getString("name");
			album_str = myResSet.getString("album");
			song_date = myResSet.getDate("song_date");
			JLabel lab1_1 = new JLabel(String.format(" %30s %30s %30s %10s %20s", s_artist_name, name, song_date.toString(),"",album_str));
			song.add(lab1_1);									
		}
		
		sql = "SELECT * FROM Artist";							//2. Select Query Connect: Artist relation	
		myResSet = myState.executeQuery(sql);
		
		String artist_name = "";
		String agency = "";
		Integer debut_year;
		lab1 = new JLabel(String.format("%15s artist_name %30s agency %30s debut_year ", "","", ""));
		lab2 = new JLabel(String.format("%200s", "").replace(' ', '-'));
		artist.add(lab1);artist.add(lab2);
		
		while (myResSet.next()) {
			
			name = myResSet.getString("artist_name");
			agency = myResSet.getString("agency");
			debut_year = myResSet.getInt("debut_year");
			JLabel lab = new JLabel(String.format(" %40s %30s %20s %4d   ", name, agency,"", debut_year));
			artist.add(lab);
		}
		
		sql = "SELECT * FROM Album";							//2. Select Query Connect: Album relation	
		myResSet = myState.executeQuery(sql);
		String Title = "";
		int Likes = -1;
		lab1 = new JLabel(String.format("%15s Album  %30s Likes  %20s album_date ","", "",""));
		lab2 = new JLabel(String.format("%200s", "").replace(' ', '-'));
		album.add(lab1);album.add(lab2);
		while (myResSet.next()) {
			Title = myResSet.getString("album");
			album_date = myResSet.getDate("album_date");
			Likes = myResSet.getInt("Likes");
			JLabel lab = new JLabel(String.format(" %30s %20s %8d %5s %30s", Title,"", Likes,"", album_date.toString()));
			album.add(lab);
		}
		
	} catch (SQLException e) {
		e.printStackTrace();info.setText(e.toString());
	} finally {}
		
	}
	
	}


