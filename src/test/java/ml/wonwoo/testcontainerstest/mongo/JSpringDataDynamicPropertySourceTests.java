package ml.wonwoo.testcontainerstest.mongo;

import ml.wonwoo.testcontainerstest.todo.Todo;
import ml.wonwoo.testcontainerstest.todo.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataMongoTest
public class JSpringDataDynamicPropertySourceTests {

    @Container
    private static JMongoDbContainer mongoDbContainer = new JMongoDbContainer();

    @DynamicPropertySource
    private static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", () ->
                "mongodb://" + mongoDbContainer.getContainerIpAddress() + ":" + mongoDbContainer.getPort() + "/test");
    }

    private final TodoRepository todoRepository;

    @Autowired
    private JSpringDataDynamicPropertySourceTests(TodoRepository todoRepository) {

        this.todoRepository = todoRepository;
    }

    @Test
    void spring_data_mono_test() {
        todoRepository.deleteAll();
        todoRepository.save(new Todo(null, "wonwoo", "test@test.com"));

        List<Todo> todo = todoRepository.findAll();

        assertThat(todo).hasSize(1);

        todo.forEach(it -> {

            assertThat(it.getName()).isEqualTo("wonwoo");
            assertThat(it.getEmail()).isEqualTo("test@test.com");

        });

    }


}