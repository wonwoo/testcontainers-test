package ml.wonwoo.testcontainerstest.todo

import org.springframework.data.mongodb.repository.MongoRepository

interface TodoRepository : MongoRepository<Todo, String> {

    fun findByName(name: String): Todo
}