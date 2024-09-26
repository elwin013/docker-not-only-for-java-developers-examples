package net.codeer.app.todo;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryTodoDAO implements TodoDAO {
    Map<String, Todo> todoMap = new ConcurrentHashMap<>();

    @Override
    public String addTodo(String content) {
        var id = UUID.randomUUID().toString();
        todoMap.put(id, new Todo(id, content, Instant.now()));
        return id;
    }

    @Override
    public List<Todo> getTodos() {
        return todoMap.values().stream().sorted(Comparator.comparing(Todo::getCreated).reversed())
                      .toList();
    }

    @Override
    public void deleteTodo(String id) {
        todoMap.remove(id);
    }

    @Override
    public void markAsDone(String id) {
        todoMap.get(id).setDone(true);
    }

    @Override
    public void markAsNotDone(String id) {
        todoMap.get(id).setDone(false);
    }
}
