package ias.com.co.IASLibrary.infraestructure.entry_points;

import ias.com.co.IASLibrary.application.services.BookService;
import ias.com.co.IASLibrary.domain.exceptions.BookValidationException;
import ias.com.co.IASLibrary.domain.models.Book;
import ias.com.co.IASLibrary.infraestructure.driver_adapters.postgresr2dbc.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class ApiRoutesTest {

    private ApiRoutes apiRoutes;
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository = Mockito.mock(BookRepository.class);
        BookService bookService = Mockito.mock(BookService.class);
        apiRoutes = new ApiRoutes(bookRepository, bookService);
    }

    @Test
    void createBook() {
        Book book = Book.builder()
                .name("Book 1")
                .id(1)
                .borrowed(true)
                .ISBN("asdasfdsa123123")
                .build();
        Mockito.when(bookRepository.save(book)).thenReturn(Mono.just(book));
        StepVerifier.create(apiRoutes.createBook(book))
                .expectNext(book)
                .expectComplete()
                .verify();
    }
    @Test
    void getBooks() {
        Book book = Book.builder()
               .name("Book 1")
               .id(1)
               .borrowed(true)
               .ISBN("asdasfdsa123123")
               .build();
        Book book2 = Book.builder()
                .name("Book 1")
                .id(1)
                .borrowed(true)
                .ISBN("asdasfd12456")
                .build();
        Flux<Book> bookFlux = Flux.just(book, book2);
        Mockito.when(bookRepository.findAll()).thenReturn(bookFlux);
        StepVerifier.create(apiRoutes.getBooks())
               .expectNext(book, book2)
               .expectComplete()
               .verify();
    }


}