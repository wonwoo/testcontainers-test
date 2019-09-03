package ml.wonwoo.testcontainerstest.mongo

import ml.wonwoo.testcontainerstest.MongoDbContainer
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

class MongoDbContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    private val mongoContainer: MongoDbContainer = MongoDbContainer()

    override fun initialize(applicationContext: ConfigurableApplicationContext) {

        mongoContainer.start()

        TestPropertyValues.of(

            "spring.data.mongodb.uri=mongodb://${mongoContainer.containerIpAddress}:${mongoContainer.getPort()}/test"

        ).applyTo(applicationContext)
    }
}