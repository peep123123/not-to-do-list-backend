package com.example.not_to_do_list_backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.not_to_do_list_backend.model.NotTodo;

/** JpaRepository 는 스프링 데이터 JPA에서 제공하는 인터페이스로
 * CrudRepository를 상속받음*/

public interface NotTodoRepository extends JpaRepository<NotTodo, Long>{

}
