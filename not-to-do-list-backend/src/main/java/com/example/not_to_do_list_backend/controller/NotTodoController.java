package com.example.not_to_do_list_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.not_to_do_list_backend.model.NotTodo;
import com.example.not_to_do_list_backend.persistence.NotTodoRepository;

//이 클래스가 REST API를 다루는 컨트롤러임을 스프링에게 알림
@RestController

//이 컨트롤러의 모든 API는 "/nottodo"라는 기본 경로를 가짐
@RequestMapping("/nottodo")
public class NotTodoController {

    // 스프링이 NotTodoRepository 객체를 자동으로 주입(생성)
    // new 키워드로 객체를 만들 필요가 없음
	@Autowired
	private NotTodoRepository notTodoRepository;
	
	// HTTP POST 요청을 처리하는 메소드에 붙임
    // 새로운 습관 항목을 생성할 때 사용
    // URL: http://localhost:8080/nottodo
	@PostMapping
	
	// 클라이언트로부터 JSON 데이터를 받아 NotTodo 객체로 변환
	public ResponseEntity<NotTodo> createTodo(@RequestBody NotTodo todo) {
		
		// 데이터베이스에 객체를 저장하고, 저장된 객체를 savedTodo에 담음
		NotTodo savedTodo = notTodoRepository.save(todo);
		
		// HTTP 응답으로 저장된 객체를 반환 
        // 상태 코드 200(성공)과 함께 응답
		return ResponseEntity.ok(savedTodo);
	}
}
