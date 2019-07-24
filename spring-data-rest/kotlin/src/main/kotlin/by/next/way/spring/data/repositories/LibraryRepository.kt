package by.next.way.spring.data.repositories

import by.next.way.spring.data.models.Library
import org.springframework.data.repository.CrudRepository

interface LibraryRepository : CrudRepository<Library, Long>
