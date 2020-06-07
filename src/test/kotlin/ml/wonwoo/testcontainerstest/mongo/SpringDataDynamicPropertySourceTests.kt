package ml.wonwoo.testcontainerstest.mongo

import ml.wonwoo.testcontainerstest.MongoDbContainer
import ml.wonwoo.testcontainerstest.todo.Todo
import ml.wonwoo.testcontainerstest.todo.TodoRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@DataMongoTest
class SpringDataDynamicPropertySourceTests(@Autowired private val todoRepository: TodoRepository) {

    companion object {

        @Container
        @JvmStatic
        private val mongoDbContainer = MongoDbContainer()

        @JvmStatic
        @DynamicPropertySource
        fun mongoProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri") { "mongodb://${mongoDbContainer.containerIpAddress}:${mongoDbContainer.getPort()}/test" }
        }
    }

    @Test
    fun `spring data mono test`() {
        todoRepository.deleteAll()
        todoRepository.save(Todo(name = "wonwoo", email = "test@test.com"))

        val (_, name, email) = todoRepository.findByName("wonwoo")

        assertThat(name).isEqualTo("wonwoo")
        assertThat(email).isEqualTo("test@test.com")

    }

}