package com.example.library.Controller;

import com.example.library.Service.AuthorService;
import com.example.library.dto.AuthorDTO;
import com.example.library.dto.BookDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    private ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id){
        AuthorDTO authorDTO =authorService.getAutherById(id);
            return ResponseEntity.ok(authorDTO);
    }

    @GetMapping
    private ResponseEntity<List<AuthorDTO>> authorDTOList(){
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @PutMapping("/{id}")
    private ResponseEntity<AuthorDTO> updateBook(@PathVariable Long id, @RequestBody AuthorDTO authorDTO){
        return ResponseEntity.ok(authorService.updateAuthor(id,authorDTO));
    }

    @PostMapping
    private ResponseEntity<AuthorDTO> createAnAuthor(@Valid @RequestBody AuthorDTO authorDTO){
        AuthorDTO authorDTO1 = authorService.createAuthor(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorDTO1);
    }

    @GetMapping("/search")
    private ResponseEntity<List<AuthorDTO>> searhByName(String name){
        return ResponseEntity.ok(authorService.findByName(name));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void>deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
