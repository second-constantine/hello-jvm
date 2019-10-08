package by.next.way.spring.data.repositories

import by.next.way.spring.data.models.Address
import org.springframework.data.repository.CrudRepository

interface AddressRepository : CrudRepository<Address, Long>
