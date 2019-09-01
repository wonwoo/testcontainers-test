package ml.wonwoo.testcontainerstest.mongo;

import ml.wonwoo.testcontainerstest.todo.Todo;
import ml.wonwoo.testcontainerstest.todo.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JDataMongoIntegrationTest
class JSpringDataCustomizedTestcontatinersTests {

    private final TodoRepository todoRepository;

    @Autowired
    private JSpringDataCustomizedTestcontatinersTests(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Test
    void spring_data_mono_customized_test() {

        todoRepository.save(new Todo(null, "wonwoo", "test@test.com"));

        List<Todo> todo = todoRepository.findAll();

        assertThat(todo).hasSize(1);

        todo.forEach(it -> {

            assertThat(it.getName()).isEqualTo("wonwoo");
            assertThat(it.getEmail()).isEqualTo("test@test.com");

        });

    }

}
