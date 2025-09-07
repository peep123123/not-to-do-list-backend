package com.example.not_to_do_list_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * 하지 말아야 습관(NotTodo) 항목을 나타내는 엔티티 클래스입니다.
 * 데이터베이스의 'NotTodo' 테이블과 매핑됩니다.*/
@Entity
public class NotTodo {

	/**
	 * 하지 말아야할 습관 항목의 고유 식별자(Primary Key).
	 * @Id: 이 필드가 데이터베이스 테이블의 기본키임을 나타냅니다.
	 * @GeneratedValue: id 값이 자동으로 생성되도록 합니다.*/
	@Id
	@GeneratedValue
	private Long id;
	
	// 습관 이름
	private String title;
	
	// 할 일의 완료 여부 (true: 완료, false: 미완료)
	private boolean completed;
	
	// Getter & Setter 추가
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public boolean isCompleted() {
		return completed;
	}
	
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
