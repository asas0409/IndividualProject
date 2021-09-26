package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String category, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[�׸� �߰�]");
		
		
		System.out.print("���� >> ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("�ߺ��� ������ ����� �� �����ϴ�.");
			return;
		}
		
		System.out.print("ī�װ� >> ");
		category = sc.next();
		
		sc.nextLine();
		System.out.print("���� >> ");
		desc = sc.nextLine().trim();
		
		System.out.print("�������� >> ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(title, category, desc, due_date);
		list.addItem(t);
		System.out.println("�߰��Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);

		System.out.println("[�׸� ����]");
		System.out.print("������ �׸��� ��ȣ�� �Է��Ͻÿ� >> ");
		int num = 1;
		int del_num = sc.nextInt();
		String del;
		
		for (TodoItem item : l.getList()) {
			if (del_num == num) {
				System.out.println(del_num + ". " +item.toString());
				System.out.print("�� �׸��� �����Ͻðڽ��ϱ�? (y/n) > ");
				del = sc.next();
				if(del.equals("y")) {
					l.deleteItem(item);
					System.out.println("�����Ǿ����ϴ�.");
				}
				else {
					System.out.println("��ҵǾ����ϴ�.");
				}
				break;
			}
			num++;
		}
			
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[�׸� ����]");
		System.out.print("������ �׸��� ��ȣ�� �Է��Ͻÿ� >> ");
		int num = 1;
		int update_num = sc.nextInt();
		
		for (TodoItem item : l.getList()) {
			if (update_num == num) {
				System.out.println(update_num + ". " +item.toString());
				l.deleteItem(item);
				break;
			}
			num++;
		}
		
		System.out.print("�� ���� >> ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��� �� �����ϴ�.");
			return;
		}
		
		System.out.print("�� ī�װ� >> ");
		String new_category = sc.next().trim();
		
		sc.nextLine();
		System.out.print("�� ���� >> ");
		String new_description = sc.nextLine().trim();
		
		System.out.print("�� �������� >> ");
		String new_due_date = sc.next().trim();
		
		TodoItem t = new TodoItem(new_title, new_category ,new_description, new_due_date);
		l.addItem(t);
		System.out.println("�����Ǿ����ϴ�.");
			
		

	}

	public static void listAll(TodoList l) {
		int count = 1;
		System.out.println("[��ü ���, �� " + l.getList().size() + "��]");
		for (TodoItem item : l.getList()) {
			System.out.println(count + ". " + item.toString());
			count++;
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		FileWriter w = null;
		try {
			w = new FileWriter(filename);
			for(TodoItem itemToWrite : l.getList()) {
				w.write(itemToWrite.toSaveString());
			}
			System.out.println("�����Ͱ� ����Ǿ����ϴ�.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				w.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		int count = 0;
		try {
			BufferedReader r = new BufferedReader(new FileReader(filename));
			String data = null;
			while((data = r.readLine())!=null) {
				StringTokenizer stk = new StringTokenizer(data, "##");
				String category = stk.nextToken();
				String title = stk.nextToken();
				String desc = stk.nextToken();
				String due_date = stk.nextToken();
				String current_date = stk.nextToken();
				l.addItem(new TodoItem(category, title, desc, due_date, current_date));
				count++;
			}
			r.close();
			System.out.println(count + "���� �׸��� �о����ϴ�.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(filename + " ������ �����ϴ�.");
		} catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static void find(TodoList l, String keyword) {
		int num=1;
		int count = 0;
		
		for(TodoItem item : l.getList()) {
			if(item.getTitle().contains(keyword)||item.getDesc().contains(keyword)) {
				System.out.println(num + ". " + item.toString());
				count++;
			}
			num++;
		}
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");		
	}
	
	public static void find_cate(TodoList l, String keyword) {
		int num=1;
		int count = 0;
		
		for(TodoItem item : l.getList()) {
			if(item.getCategory().contains(keyword)) {
				System.out.println(num + ". " + item.toString());
				count++;
			}
			num++;
		}
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");		
	}
	
	public static void ls_cate(TodoList l) {
		int count = 0;
		HashSet<String> category = new HashSet<String>();
		for(TodoItem item : l.getList()) {
			category.add(item.getCategory());
		}
		for(String to_print : category) {
			if(count==0) {
				System.out.print(to_print);
			}else {
				System.out.print(" / " + to_print);
			}
			count++;
		}
		System.out.println("\n�� " + count + "���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.");
		
	}
}
