package ml.wonwoo.testcontainerstest.mongo

import ml.wonwoo.testcontainerstest.MongoDbContainer
import ml.wonwoo.testcontainerstest.mongo.SpringDataTestcontatinersTests.Initializer
import ml.wonwoo.testcontainerstest.todo.Todo
import ml.wonwoo.testcontainerstest.todo.TodoRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration

@DataMongoTest
@ContextConfiguration(initializers = [Initializer::class])
class SpringDataTestcontatinersTests(@Autowired private val todoRepository: TodoRepository)  {


    companion object {

        val mongoContainer: MongoDbContainer = MongoDbContainer()

    }

    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

        override fun initialize(applicationContext: ConfigurableApplicationContext) {

            mongoContainer.start()

            TestPropertyValues.of(

                "spring.data.mongodb.uri=mongodb://${mongoContainer.containerIpAddress}:${mongoContainer.getPort()}/test"

            ).applyTo(applicationContext)

        }
    }

    @Test
    fun `spring data mono test`() {
        todoRepository.save(Todo(name = "wonwoo", email = "test@test.com"))

        val (_, name, email) = todoRepository.findByName("wonwoo")

        assertThat(name).isEqualTo("wonwoo")
        assertThat(email).isEqualTo("test@test.com")


    }
}