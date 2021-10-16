package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int id;
	private String category;
    private String title;
    private String desc;
    private String due_date;
    private String current_date;
    private String is_completed = "0"; //default는 0으로 초기화
    private String is_lated = "0";
    private String is_important = "0";
    
    public TodoItem(String title, String desc, String category, String due_date, String is_completed, String is_lated, String is_important){
    	this.category=category;
    	this.title=title;
        this.desc=desc;
        this.due_date = due_date;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date = f.format(new Date());
        this.is_completed = is_completed;
        this.is_lated=is_lated;
        this.is_important=is_important;
    } //Constructor1
    
    public TodoItem(String title, String desc, String category, String due_date, String current_date, String is_completed, String is_lated, String is_important) {
    	this.category = category;
    	this.title = title;
		this.desc = desc;
		this.due_date = due_date;
		this.current_date = current_date;
		this.is_completed = is_completed;
		this.is_lated=is_lated;
        this.is_important=is_important;
	} //Constructor2
    
    public TodoItem(int id, String title, String desc,String category, String due_date, String current_date, String is_completed, String is_lated, String is_important) {
		this.id = id;
		this.category = category;
		this.title = title;
		this.desc = desc;
		this.due_date = due_date;
		this.current_date = current_date;
		this.is_completed = is_completed;
		this.is_lated=is_lated;
        this.is_important=is_important;
	} //Constructor3

	public int getId() {
		return id;
	}
    
    public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

    public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	
	public String getCurrent_date() {
		return current_date;
	}

	public void setCurrent_date(String current_date) {
		this.current_date = current_date;
	}
	
	public String getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(String is_completed) {
		this.is_completed = is_completed;
	}	
	
	public String getIs_lated() {
		return is_lated;
	}

	public void setIs_lated(String is_lated) {
		this.is_lated = is_lated;
	}

	public String getIs_important() {
		return is_important;
	}

	public void setIs_important(String is_important) {
		this.is_important = is_important;
	}
	
	@Override
    public String toString() {
		if(is_completed.equals("1")) {
			if(is_lated.equals("1")){
				if(is_important.equals("1")) {
					return String.format("%d [%s] %s[완료][지각][중요] - %s - %s - %s", id, category, title, desc, due_date, current_date);
				}
				else {
					return String.format("%d [%s] %s[완료][지각] - %s - %s - %s", id, category, title, desc, due_date, current_date);
				}
			}
			else {
				if(is_important.equals("1")) {
					return String.format("%d [%s] %s[완료][중요] - %s - %s - %s", id, category, title, desc, due_date, current_date);
				}
				else {
					return String.format("%d [%s] %s[완료] - %s - %s - %s", id, category, title, desc, due_date, current_date);
				}
			}
		}
		// if(is_completed.equals("0"))
		else {
			if(is_lated.equals("1")){
				if(is_important.equals("1")) {
					return String.format("%d [%s] %s[지각][중요] - %s - %s - %s", id, category, title, desc, due_date, current_date);
				}
				else {
					return String.format("%d [%s] %s[지각] - %s - %s - %s", id, category, title, desc, due_date, current_date);
				}
			}
			else {
				if(is_important.equals("1")) {
					return String.format("%d [%s] %s[중요] - %s - %s - %s", id, category, title, desc, due_date, current_date);
				}
				else {
					return String.format("%d [%s] %s - %s - %s - %s", id, category, title, desc, due_date, current_date);
				}
			}
		}
    }
    
    public String toSaveString() {
    	return category + "##" + title + "##" + desc + "##" + due_date + "##" + current_date + "##" + is_completed + "##" + is_lated + "##" + is_important + "\n";
    }
}
