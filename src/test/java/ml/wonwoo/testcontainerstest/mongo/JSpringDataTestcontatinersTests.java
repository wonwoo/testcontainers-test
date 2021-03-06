package ml.wonwoo.testcontainerstest.mongo;

import ml.wonwoo.testcontainerstest.todo.Todo;
import ml.wonwoo.testcontainerstest.todo.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ContextConfiguration(initializers = JMongoDbContainerInitializer.class)
class JSpringDataTestcontatinersTests {

    private final TodoRepository todoRepository;

    @Autowired
    private JSpringDataTestcontatinersTests(TodoRepository todoRepository) {

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