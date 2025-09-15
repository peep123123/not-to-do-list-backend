package com.example.not_to_do_list_backend.controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.not_to_do_list_backend.model.NotTodo;
import com.example.not_to_do_list_backend.persistence.NotTodoRepository;

// @RestController: 이 클래스가 REST API를 다루는 컨트롤러임을 스프링에게 알림
// @RequestMapping: 이 컨트롤러의 모든 API는 "/nottodo"라는 기본 경로를 가짐
// @CrossOrigin: CORS 보안 허용
@RestController
@RequestMapping("/nottodo")
@CrossOrigin(origins = "http://localhost:5173")
public class NotTodoController {

    // 스프링이 NotTodoRepository 객체를 자동으로 주입(생성)
    // new 키워드로 객체를 만들 필요가 없음
	@Autowired
	private final NotTodoRepository notTodoRepository;
	
	public NotTodoController(NotTodoRepository notTodoRepository) {
		this.notTodoRepository = notTodoRepository;
	}
	
	// HTTP POST 요청을 처리하는 메소드에 붙임
    // 새로운 습관 항목을 생성할 때 사용
    // URL: http://localhost:8080/nottodo
	@PostMapping
	// 클라이언트로부터 JSON 데이터를 받아 NotTodo 객체로 변환
	public ResponseEntity<NotTodo> createNotTodo(@RequestBody NotTodo notTodo) {
		
		// 프론트엔드에서 값을 보내지 않을 경우 서버에서 초기화
		if (notTodo.getCreatedAt() == null) {
			notTodo.setCreatedAt(LocalDate.now());
		}		
		if (notTodo.getCompletionDates() == null) {
			notTodo.setCompletionDates(new HashSet<>());
		}
		
		// 데이터베이스에 객체를 저장하고, 저장된 객체를 savedNotTodo에 담음
		NotTodo savedNotTodo = notTodoRepository.save(notTodo);
		
		// HTTP 응답으로 저장된 객체를 반환 
        // 상태 코드 200(성공)과 함께 응답
		return ResponseEntity.ok(savedNotTodo);
	}
	
	// HTTP GET 요청을 처리하는 메소드
	@GetMapping
	public ResponseEntity<List<NotTodo>> getAllNotTodo() {
		
		// 데이터베이스에 저장된 모든 'NotTodo' 목록을 조회
		List<NotTodo> notTodos = notTodoRepository.findAll();
		return ResponseEntity.ok(notTodos);
	}
	
	// HTTP DELETE 요청을 처리하는 메소드임을 스프링에게 알려줌
	// id는 URL 경로에서 id 값을 받겠다는 뜻 
	@DeleteMapping("/{id}")
	
	//@PathVariable: URL 경로에 있는 id 값을 메소도의 매개변수 id로 가져옴
	//Void(클래스): 제네릭 클래스는 기본 타입을 사용할 수 없고, 객체 타입만 허용
	//반환할 데이터가 없을 때 Void 객체를 사용하여 명시적으로 선언함
	public ResponseEntity<Void> deleteNotTodo(@PathVariable Long id){
		
		//매개변수로 받은 ID에 해당하는 데이터를 데이터베이스에서 삭제
		notTodoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	//PUT 요청을 처리하며, URL의 {id} 값을 받아옴
	@PutMapping("/{id}")
	
	//@RequestBody NotTodo updatedNotTodo: 요청 본문(JSON)에 담겨온 수정할 데이터를 updatedNotTodo 객체에 매핑
	public ResponseEntity<NotTodo> updateNotTodo(@PathVariable Long id, @RequestBody NotTodo updateNotTodo) {
		
		//.map(): findById()로 항목을 찾았을 경우, 람다식 내부의 코드를 실행하여 데이터 수정 및 저장
		return notTodoRepository.findById(id)
				.map(notTodo -> {
					notTodo.setTitle(updateNotTodo.getTitle());
					notTodo.setCompleted(updateNotTodo.isCompleted());
					
					// completionDates 업데이트 추가
					notTodo.setCompletionDates(updateNotTodo.getCompletionDates());
					
					NotTodo savedNotTodo = notTodoRepository.save(notTodo);
					return ResponseEntity.ok(savedNotTodo);
				})
				//.orElseGet(): findById()로 항목을 찾지 못했을 경우, 404 에러 반환
				.orElseGet(() -> ResponseEntity.notFound().build()); 
	}
	
	// id로 단일 항목 조회하기
	@GetMapping("/{id}")
	public ResponseEntity<NotTodo> getNotTodoById(@PathVariable Long id) {
		
		return notTodoRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
}
