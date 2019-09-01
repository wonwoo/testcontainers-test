package ml.wonwoo.testcontainerstest.mongo;


import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

class JDefaultTestcontatinersTests {

    private JMongoDbContainer mongoDbContainer = new JMongoDbContainer();
//            .withEnv("FOO", "BAR")
//            .withCommand("command test")
//            .withLabel("TEST","LABEL")
//            .withNetwork(Network.newNetwork())
//            .dependsOn(new MongoDbContainer());

    @BeforeEach
    void setup() {

        mongoDbContainer.start();
    }

    @Test
    void default_mongo_db_test() {

        int port = mongoDbContainer.getPort();

        MongoClient mongoClient = new MongoClient(mongoDbContainer.getContainerIpAddress(), port);
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

    @BeforeEach
    void close() {

        mongoDbContainer.stop();
    }
}
