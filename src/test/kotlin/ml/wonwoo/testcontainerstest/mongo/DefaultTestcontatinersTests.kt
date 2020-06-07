package ml.wonwoo.testcontainerstest.mongo

import com.mongodb.client.MongoClients
import ml.wonwoo.testcontainerstest.MongoDbContainer
import org.assertj.core.api.Assertions.assertThat
import org.bson.Document
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultTestcontatinersTests {

    private val mongoDbContainer = MongoDbContainer()

    @BeforeEach
    fun setup() {

        mongoDbContainer.start()

    }

    @Test
    fun `default mongo db test`() {

        val port = mongoDbContainer.getPort()

        val mongoClient = MongoClients.create("mongodb://${mongoDbContainer.containerIpAddress}:$port")
        val database = mongoClient.getDatabase("test")

        val collection = database.getCollection("users")

        val document = Document("name", "wonwoo")
            .append("email", "test@test.com")

        collection.insertOne(document)

        collection.find().apply {

            assertThat(this).hasSize(1)

            forEach {

                assertThat(it["name"]).isEqualTo("wonwoo")
                assertThat(it["email"]).isEqualTo("test@test.com")

            }
        }

    }
}