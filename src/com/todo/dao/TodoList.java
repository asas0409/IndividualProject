package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByDateReverse;
import com.todo.service.TodoSortByName;

public class TodoList {
	Connection conn;

	public TodoList() {
		this.conn = DbConnect.getConnection();
	}
	
	public void disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int addItem(TodoItem t) {
		Statement stmt1;
		Statement stmt2;
		Statement stmt3;
		String sql = "insert into list (title, memo, category, current_date, due_date, remark, importance)" + " values (?,?,?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		int check = 0;
		try {
			stmt1 = conn.createStatement();
			String sql1 = "select count(id) from category where category = '" + t.getCategory() + "';";
			ResultSet rs = stmt1.executeQuery(sql1);
			rs.next();
			count = rs.getInt("count(id)");
			stmt1.close();
			if(!(count > 0)) {
				stmt2 = conn.createStatement();
				String sql2 = "insert into category (category) values ('" + t.getCategory() + "');";
				stmt2.executeUpdate(sql2);
				stmt2.close();
			}
			stmt3 = conn.createStatement();
			String sql3 = "select id from category where category='"+t.getCategory()+"';";
			ResultSet rs3 = stmt3.executeQuery(sql3);
			rs3.next();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setInt(3, rs3.getInt("id"));
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getRemark());
			pstmt.setString(7, t.getImportance());
			check = pstmt.executeUpdate();
			pstmt.close();
			stmt3.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	public int deleteItem(String index) {
		Statement stmt;
		String sql = "delete from list where id in (" + index +");";
		int check = 0;
		try {
			stmt = conn.createStatement();
			check = stmt.executeUpdate(sql);
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public int deleteComp() {
		Statement stmt;
		String sql = "delete from list where is_completed = 1;";
		int check = 0;
		try {
			stmt = conn.createStatement();
			check = stmt.executeUpdate(sql);
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	public int editItem(TodoItem t) {
		Statement stmt1;
		Statement stmt2;
		Statement stmt3;
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?, remark=?, importance=? where id = ?;";
		PreparedStatement pstmt;
		int check = 0;
		try {
			stmt1 = conn.createStatement();
			String sql1 = "select count(id) from category where category = '" + t.getCategory() + "';";
			ResultSet rs = stmt1.executeQuery(sql1);
			rs.next();
			int count = rs.getInt("count(id)");
			stmt1.close();
			if(!(count > 0)) {
				stmt2 = conn.createStatement();
				String sql2 = "insert into category (category) values ('" + t.getCategory() + "');";
				stmt2.executeUpdate(sql2);
				stmt2.close();
			}
			stmt3 = conn.createStatement();
			String sql3 = "select id from category where category='"+t.getCategory()+"';";
			ResultSet rs3 = stmt3.executeQuery(sql3);
			rs3.next();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setInt(3, rs3.getInt("id"));
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getRemark());
			pstmt.setString(7, t.getImportance());
			pstmt.setInt(8, t.getId());
			check = pstmt.executeUpdate();
			pstmt.close();
			stmt3.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select list.id, category.category, list.title, list.memo, list.due_date, list.current_date, list.is_completed, list.remark, list.importance from list inner join category on list.category = category.id;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String remark = rs.getString("remark");
				String importance = rs.getString("importance");
				TodoItem t = new TodoItem(category, title, description, due_date, current_date,is_completed,remark,importance);
				t.setId(id);
				list.add(t);

			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select list.id, category.category, list.title, list.memo, list.due_date, list.current_date, list.is_completed, list.remark, list.importance from list inner join category on list.category = category.id where title like '%" + keyword + "%' or memo like '%" + keyword + "%';";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String remark = rs.getString("remark");
				String importance = rs.getString("importance");
				TodoItem t = new TodoItem(category, title, description, due_date, current_date,is_completed,remark,importance);
				t.setId(id);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(int check) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select list.id, category.category, list.title, list.memo, list.due_date, list.current_date, list.is_completed, list.remark, list.importance from list inner join category on list.category = category.id where is_completed="+ check +";";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String remark = rs.getString("remark");
				String importance = rs.getString("importance");
				TodoItem t = new TodoItem(category, title, description, due_date, current_date,is_completed,remark,importance);
				t.setId(id);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getImportanceList(String imp) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select list.id, category.category, list.title, list.memo, list.due_date, list.current_date, list.is_completed, list.remark, list.importance from list inner join category on list.category = category.id where importance='"+ imp +"';";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String remark = rs.getString("remark");
				String importance = rs.getString("importance");
				TodoItem t = new TodoItem(category, title, description, due_date, current_date,is_completed,remark,importance);
				t.setId(id);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select distinct category.category from category inner join list on category.id = list.category";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select list.id, category.category, list.title, list.memo, list.due_date, list.current_date, list.is_completed, list.remark, list.importance from list inner join category on list.category = category.id where category.category='" + keyword + "';";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String remark = rs.getString("remark");
				String importance = rs.getString("importance");
				TodoItem t = new TodoItem(category, title, description, due_date, current_date,is_completed,remark,importance);
				t.setId(id);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getCount() {
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "select count(id) from list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select list.id, category.category, list.title, list.memo, list.due_date, list.current_date, list.is_completed, list.remark, list.importance from list inner join category on list.category = category.id order by " + orderby;
			if (ordering==0) {
				sql += " desc";
			}
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String remark = rs.getString("remark");
				String importance = rs.getString("importance");
				TodoItem t = new TodoItem(category, title, description, due_date, current_date,is_completed,remark,importance);
				t.setId(id);
				list.add(t);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int completeItem(String index) {
		Statement stmt;
		String sql = "update list set is_completed=1 where id in (" + index +");";
		int check = 0;
		try {
			stmt = conn.createStatement();
			check = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public Boolean isDuplicate(String title) {
		Statement stmt;
		int count=0;
		try {
			stmt = conn.createStatement();
			String sql = "select count(id) from list where title='" + title + "';";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		if(count>0) return true;
		else return false;
	}

	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date, due_date)" + " values (?,?,?,?,?);";
			int count = 0;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();

				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				int check = pstmt.executeUpdate();
				if (check > 0)
					count++;
				pstmt.close();
			}
			System.out.println(count + " 개의 레코드를 읽었습니다!");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
