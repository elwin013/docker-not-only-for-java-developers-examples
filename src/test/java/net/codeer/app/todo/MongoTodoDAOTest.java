package net.codeer.app.todo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import static net.codeer.app.MongoUtil.pojoCodecRegistry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MongoTodoDAOTest {
    static MongoDBContainer mongo = new MongoDBContainer("mongo:7.0");

    MongoClient client;
    MongoTodoDAO dao;
    String DB_NAME = "MongoTodoDAOTest-" + UUID.randomUUID();

    @BeforeAll
    static void beforeAll() {
        mongo.start();
    }

    @AfterAll
    static void afterAll() {
        mongo.stop();
    }

    @BeforeEach
    void beforeEach() {
        client = MongoClients.create(mongo.getConnectionString());
        dao = new MongoTodoDAO(client.getDatabase(DB_NAME).withCodecRegistry(pojoCodecRegistry));
    }

    @AfterEach
    void afterEach() {
        client.close();
    }

    @Test
    void todosArePersisted() {
        dao.addTodo("First todo");
        dao.addTodo("Second todo");

        assertEquals(2, dao.getTodos().size());
        assertEquals(2, client.getDatabase(DB_NAME).getCollection("todo").countDocuments());
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
                () -> todosAfterDelete.stream().filter(t -> Objects.equals(t.getId(), first)).findFirst()
                           .orElseThrow().getDone());

        assertEquals(1, todosAfterDelete.size());
    }
}