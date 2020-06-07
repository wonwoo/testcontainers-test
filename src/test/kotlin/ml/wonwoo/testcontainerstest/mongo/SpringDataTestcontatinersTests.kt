package ml.wonwoo.testcontainerstest.mongo

import ml.wonwoo.testcontainerstest.todo.Todo
import ml.wonwoo.testcontainerstest.todo.TodoRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.ContextConfiguration

@DataMongoTest
@ContextConfiguration(initializers = [MongoDbContainerInitializer::class])
class SpringDataTestcontatinersTests(@Autowired private val todoRepository: TodoRepository)  {

    @Test
    fun `spring data mono test`() {
        todoRepository.deleteAll()
        todoRepository.save(Todo(name = "wonwoo", email = "test@test.com"))

        val (_, name, email) = todoRepository.findByName("wonwoo")

        assertThat(name).isEqualTo("wonwoo")
        assertThat(email).isEqualTo("test@test.com")


    }
}