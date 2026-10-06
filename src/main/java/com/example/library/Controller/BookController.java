package com.example.library.Controller;

import com.example.library.Service.BookService;
import com.example.library.dto.BookDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    private ResponseEntity<BookDTO> createABook(@Valid @RequestBody BookDTO bookDTO){
        BookDTO create =bookService.createBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(create);
    }

    @GetMapping
    private ResponseEntity<List<BookDTO>> getAllBooks(){
        List<BookDTO> bookDTO=bookService.getAll();
        return ResponseEntity.ok(bookDTO);
    }

    @GetMapping("/{id}")
    private ResponseEntity<BookDTO> getBookById(@PathVariable Long id){
        BookDTO bookDTO =bookService.getBookById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(bookDTO);
    }

    @PutMapping("/{id}")
    private ResponseEntity<BookDTO> updateBook(@PathVariable Long id,@RequestBody BookDTO bookDTO){
        return ResponseEntity.ok(bookService.updateBook(id,bookDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id){
        boolean deleted= bookService.deleteBook(id);
        if(deleted){
            return ResponseEntity.noContent().build();
        } else return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{bookid}/author/{authorid}")
    private ResponseEntity<BookDTO> assignAuthor(
            @PathVariable Long bookid,
            @PathVariable Long authorid) {
        BookDTO bookDTO = bookService.assignAuthor(authorid,bookid);
        return ResponseEntity.ok(bookDTO);
    }

    @GetMapping("/search")
    private ResponseEntity<List<BookDTO>> searchBookByTitle(@RequestParam String title){
        List<BookDTO> bookDTO=bookService.searchBookByTitle(title);
        return ResponseEntity.ok(bookDTO);
    }

    @GetMapping("/published-after")
    private ResponseEntity<List<BookDTO>> bookPublishedAfterDate(LocalDate date){
        return ResponseEntity.ok(bookService.findByPublishedAfter(date));
    }


    @GetMapping("/author/{authorid}")
    private ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable Long authorid){
        return ResponseEntity.ok(bookService.findByAuthorId(authorid));
    }
}
