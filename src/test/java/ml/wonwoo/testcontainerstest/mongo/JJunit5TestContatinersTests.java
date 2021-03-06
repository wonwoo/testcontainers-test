package ml.wonwoo.testcontainerstest.mongo;

import com.mongodb.client.*;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class JJunit5TestContatinersTests {

    @Container
    private JMongoDbContainer mongoDbContainer = new JMongoDbContainer();

    @Test
    void junit5_mongo_db_test() {

        int port = mongoDbContainer.getPort();

        String s = "mongodb://" + mongoDbContainer.getContainerIpAddress() + ":" + port;
        MongoClient mongoClient = MongoClients.create(s);
        MongoDatabase database = mongoClient.getDatabase("test");

        MongoCollection<Document> collection = database.getCollection("users");

        Document document = new Document("name", "wonwoo")
                .append("email", "test@test.com");

        collection.insertOne(document);

        FindIterable<Document> documents = collection.find();


        assertThat(documents).hasSize(1);


        documents.forEach((Consumer<? super Document>) it -> {

            assertThat(it.get("name")).isEqualTo("wonwoo");
            assertThat(it.get("email")).isEqualTo("test@test.com");

        });

    }

}
