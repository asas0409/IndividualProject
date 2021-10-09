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
		if(list.isDuplicate(title)) {
			System.out.println("������ �ߺ��˴ϴ�!");
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
		if(list.addItem(t)>0)
			System.out.println("�߰��Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);

		System.out.println("[�׸� ����]");
		System.out.print("������ �׸��� ��ȣ�� �Է��Ͻÿ� >> ");
		int del_num = sc.nextInt();
		if(l.deleteItem(del_num)>0) {
			System.out.println("�����Ǿ����ϴ�.");
		}	
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[�׸� ����]");
		System.out.print("������ �׸��� ��ȣ�� �Է��Ͻÿ� >> ");

		int update_num = sc.nextInt();
		
		
		System.out.print("�� ���� >> ");
		String new_title = sc.next().trim();
		if(l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��˴ϴ�!");
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
		t.setId(update_num);
		if(l.editItem(t)>0)
			System.out.println("�����Ǿ����ϴ�.");
			
		

	}

	public static void listAll(TodoList l) {
		System.out.printf("[��ü ���, �� %d��]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[��ü ���, �� %d��]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby,ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, int check) {
		int count = 0;
		for (TodoItem item : l.getList(check)) {
			System.out.println(item.toString());
		}
		System.out.println("�� " + count + "���� �׸��� �Ϸ�Ǿ����ϴ�.");
	}
	
	
	public static void find(TodoList l, String keyword) {
		int count = 0;
		
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");		
	}
	
	public static void find_cate(TodoList l, String keyword) {
		int count = 0;
		
		for(TodoItem item : l.getListCategory(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");		
	}
	
	public static void ls_cate(TodoList l) {
		int count = 0;
		
		for(String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.println("\n�� " + count + "���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.");
		
	}
	
	public static void completeItem(TodoList l,int index) {
		if(l.completeItem(index)>0)
			System.out.println("�Ϸ� üũ�Ͽ����ϴ�.");
	}
}
