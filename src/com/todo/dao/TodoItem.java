package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int id;
	private String category;
    private String title;
    private String desc;
    private String current_date;
    private String due_date;
    private int is_completed;
    private String remark;
    private String importance;


	public TodoItem(String title, String desc){
        this.title=title;
        this.desc=desc;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date= f.format(new Date());
    }
    
    public TodoItem(String category, String title, String desc, String due_date, String current_date,int is_completed, String remark, String importance){
        this.category = category;
    	this.title=title;
        this.desc=desc;
        this.due_date = due_date;
        this.current_date= current_date;
        this.is_completed = is_completed;
        this.remark = remark;
        this.importance = importance;
    }
    
    public TodoItem(String title, String category, String desc, String due_date,String remark,String importance){
        this.title=title;
        this.category = category;
        this.desc=desc;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date= f.format(new Date());
        this.due_date = due_date;
        this.remark = remark;
        this.importance = importance;
    }
    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
  
    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
		
	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}
	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	@Override
	public String toString() {
		String comp = "";
		String toReturn = "";
		if(is_completed==1) comp = "[V]";
		toReturn =  id + " [" + category + "] " + title + comp + " - " + desc + " - " + due_date + " - " + current_date + " - �߿䵵:" + importance;
		if(!remark.equals("")) 
			toReturn += " (" + remark +")";
		return toReturn;
	}

	public String toSaveString() {
    	return category + "##" + title + "##" + desc + "##" + due_date + "##" + current_date + "\n";
    }
}
