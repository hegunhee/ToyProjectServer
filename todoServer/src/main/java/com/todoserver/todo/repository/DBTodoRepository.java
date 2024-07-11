package com.todoserver.todo.repository;

import com.todoserver.todo.domain.Todo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DBTodoRepository implements TodoRepository{

    private final EntityManager em;

    @Override
    public List<Todo> findAll() {
        return em.createQuery("select t from Todo t",Todo.class).getResultList();
    }

    @Override
    public Todo findOne(String id) {
        return em.find(Todo.class, id);
    }

    @Override
    public void save(Todo todo) {em.persist(todo);}

    @Override
    public void delete(String id) {
        em.createQuery("DELETE FROM Todo WHERE text = :text")
                .setParameter("text", id)
                .executeUpdate();
    }

    @Override
    public void deleteAll() {
        em.createQuery("DELETE FROM Todo").executeUpdate();
    }
}
