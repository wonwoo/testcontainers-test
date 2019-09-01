package ml.wonwoo.testcontainerstest.mongo

import ml.wonwoo.testcontainerstest.todo.Todo
import ml.wonwoo.testcontainerstest.todo.TodoRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@DataMongoIntegrationTest
class SpringDataCustomizedTestcontatinersTests(@Autowired private val todoRepository: TodoRepository) {


    @Test
    fun `spring data mono customized test`() {

        todoRepository.save(Todo(name = "wonwoo", email = "test@test.com"))

        val (_, name, email) = todoRepository.findByName("wonwoo")

        Assertions.assertThat(name).isEqualTo("wonwoo")
        Assertions.assertThat(email).isEqualTo("test@test.com")
    }

}