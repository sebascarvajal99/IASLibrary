package ias.com.co.IASLibrary.infraestructure.driver_adapters.postgresr2dbc;

import ias.com.co.IASLibrary.domain.models.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book,String> {}
