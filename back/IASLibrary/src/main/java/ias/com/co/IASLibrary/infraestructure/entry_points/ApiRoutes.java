package ias.com.co.IASLibrary.infraestructure.entry_points;

import ias.com.co.IASLibrary.application.services.BookService;
import ias.com.co.IASLibrary.domain.exceptions.BookValidationException;
import ias.com.co.IASLibrary.domain.models.Book;
import ias.com.co.IASLibrary.infraestructure.driver_adapters.postgresr2dbc.BookRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class ApiRoutes {

    private final BookRepository bookRepository;
    private final BookService bookService;

    @PostMapping(value = "/savebooks")
    public Mono<Book> createBook(@RequestBody Book book) {
        if(book.getName() == null || book.getName().isEmpty()) {
            throw new BookValidationException("El nombre del libro es obligatorio.");
        }
        return bookRepository.save(book)
                .onErrorResume(DataAccessException.class,e -> Mono.error(new BookValidationException("Error al Guardar el Libro")));
    }
    @GetMapping(value = "/books")
    public Flux<Book> getBooks(){
        return bookRepository.findAll();
    }

    @PostMapping("/{id}/borrow")
    public Mono<ResponseEntity<Book>> borrowBook(@PathVariable String id) {
        return bookService.borrowBook(id)
                .map(updatedBook -> ResponseEntity.ok(updatedBook))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PostMapping("/{id}/return")
    public Mono<ResponseEntity<Book>> returnBook(@PathVariable String id) {
        return bookService.returnBook(id)
                .map(updatedBook -> ResponseEntity.ok(updatedBook))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
