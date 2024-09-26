package net.codeer.app.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemoryTodoDAOTest {
    TodoDAO dao;

    @BeforeEach
    void beforeEach() {
        dao = new MemoryTodoDAO();
    }

    void todosArePersisted() {
        dao.addTodo("First todo");
        dao.addTodo("Second todo");

        assertEquals(2, dao.getTodos().size());
        assertEquals(2, ((MemoryTodoDAO) dao).todoMap.size());
    }

    @Test
    void todosCanBeMarkedAsDone() {
        var first = dao.addTodo("First todo");
        var second = dao.addTodo("Second todo");

        dao.markAsDone(first);

        var todos = dao.getTodos();

        assertEquals(true, todos.stream().filter(t -> Objects.equals(t.getId(), first)).findFirst()
                                .orElseThrow().getDone());
        assertEquals(false,
                todos.stream().filter(t -> Objects.equals(t.getId(), second)).findFirst()
                     .orElseThrow().getDone());
    }

    @Test
    void todosCanBeDeleted() {
        var first = dao.addTodo("First todo");
        var second = dao.addTodo("Second todo");

        var todosBeforeDelete = dao.getTodos();
        assertEquals(2, todosBeforeDelete.size());

        dao.deleteTodo(first);

        var todosAfterDelete = dao.getTodos();

        assertThrows(NoSuchElementException.class,
                () -> todosAfterDelete.stream().filter(t -> Objects.equals(t.getId(), first))
                                      .findFirst().orElseThrow().getDone());

        assertEquals(1, todosAfterDelete.size());
    }
}