package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<TodoList ���� ��ɾ� ����>");
        System.out.println("add - �׸� �߰�");
        System.out.println("del - �׸� ����");
        System.out.println("edit - �׸� ����");
        System.out.println("ls - ��ü ���");
        System.out.println("ls_name_asc - ����� ����");
        System.out.println("ls_name_desc - ���� ���� ����");
        System.out.println("ls_date - ��¥�� ����");
        System.out.println("ls_date_desc - ��¥ ���� ����");
        System.out.println("ls_cate - ī�װ� ��� ���");
        System.out.println("find <Ű����> - ����� ���뿡�� Ű���� �˻�");
        System.out.println("find_cate <Ű����> - ī�װ����� Ű���� �˻�");
        System.out.println("comp <��ȣ> - �׸� �Ϸ� ó��");
        System.out.println("ls_comp - �Ϸ�� �׸� ���");
        System.out.println("del_comp - �Ϸ�� �׸� ����");
        System.out.println("ls_importance <�߿䵵> - �߿䵵�� ��ġ�ϴ� �׸� ���");
        System.out.println("exit - ����");
    }
    
    public static void prompt() {
    	System.out.print("\nCommand >> ");
    }
}
