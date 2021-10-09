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
		
		System.out.println("[항목 추가]");
		
		
		System.out.print("제목 >> ");
		title = sc.next();
		if(list.isDuplicate(title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		
		System.out.print("카테고리 >> ");
		category = sc.next();
		
		sc.nextLine();
		System.out.print("내용 >> ");
		desc = sc.nextLine().trim();
		
		System.out.print("마감일자 >> ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(title, category, desc, due_date);
		if(list.addItem(t)>0)
			System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);

		System.out.println("[항목 삭제]");
		System.out.print("삭제할 항목의 번호를 입력하시오 >> ");
		int del_num = sc.nextInt();
		if(l.deleteItem(del_num)>0) {
			System.out.println("삭제되었습니다.");
		}	
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 수정]");
		System.out.print("수정할 항목의 번호를 입력하시오 >> ");

		int update_num = sc.nextInt();
		
		
		System.out.print("새 제목 >> ");
		String new_title = sc.next().trim();
		if(l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		
		System.out.print("새 카테고리 >> ");
		String new_category = sc.next().trim();
		
		sc.nextLine();
		System.out.print("새 내용 >> ");
		String new_description = sc.nextLine().trim();
		
		System.out.print("새 마감일자 >> ");
		String new_due_date = sc.next().trim();
		
		TodoItem t = new TodoItem(new_title, new_category ,new_description, new_due_date);
		t.setId(update_num);
		if(l.editItem(t)>0)
			System.out.println("수정되었습니다.");
			
		

	}

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby,ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, int check) {
		int count = 0;
		for (TodoItem item : l.getList(check)) {
			System.out.println(item.toString());
		}
		System.out.println("총 " + count + "개의 항목이 완료되었습니다.");
	}
	
	
	public static void find(TodoList l, String keyword) {
		int count = 0;
		
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");		
	}
	
	public static void find_cate(TodoList l, String keyword) {
		int count = 0;
		
		for(TodoItem item : l.getListCategory(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");		
	}
	
	public static void ls_cate(TodoList l) {
		int count = 0;
		
		for(String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.println("\n총 " + count + "개의 카테고리가 등록되어 있습니다.");
		
	}
	
	public static void completeItem(TodoList l,int index) {
		if(l.completeItem(index)>0)
			System.out.println("완료 체크하였습니다.");
	}
}
