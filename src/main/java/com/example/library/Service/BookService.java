package com.example.library.Service;

import com.example.library.advices.ResourceNotFoundException;
import com.example.library.dto.BookDTO;
import com.example.library.entity.AuthorEntity;
import com.example.library.entity.BookEntity;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, ModelMapper modelMapper, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.authorRepository = authorRepository;
    }

    public BookDTO createBook(BookDTO bookDTO){
        BookEntity bookEntity=modelMapper.map(bookDTO,BookEntity.class);

        AuthorEntity author=authorRepository
                .findById(bookDTO.getAuthorId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Author not found with id:" + bookDTO.getAuthorId()));

        bookEntity.setAuthor(author);
        BookEntity savedBook=bookRepository.save(bookEntity);
        return modelMapper.map(savedBook,BookDTO.class);
    }

    public List<BookDTO> getAll(){
        List<BookEntity> books=bookRepository.findAll();
        return  books.stream()
                .map(book -> modelMapper.map(book,BookDTO.class))
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id){
        return bookRepository.findById(id)
                .map(BookEntity->modelMapper
                .map(BookEntity,BookDTO.class))
                .orElseThrow(()->new ResourceNotFoundException(" Book not found with id: " + id));
    }

    public BookDTO updateBook(Long id,BookDTO bookDTO){
        BookEntity existing=bookRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("book not found with id "+id));
        modelMapper.map(bookDTO,existing);
        existing.setId(id);
        BookEntity update=bookRepository.save(existing);
        return modelMapper.map(update,BookDTO.class);
    }

    public boolean isExists(Long id){
        boolean exist=bookRepository.existsById(id);
        if(!exist){
            throw new ResourceNotFoundException("Book not found with id: " + id);
        } return true;
    }

    public boolean deleteBook(Long id) {
        if (isExists(id)) {
            bookRepository.deleteById(id);
            return true;
        } return false;
    }

    public BookDTO assignAuthor(Long authorId,Long bookId){
        BookEntity book=bookRepository
                .findById(bookId)
                .orElseThrow(()->new ResourceNotFoundException("Book not found with Id: " + bookId));

        AuthorEntity author=authorRepository
                .findById(authorId)
                .orElseThrow(()->new ResourceNotFoundException("Author Not Found with Id: "+authorId));

        book.setAuthor(author);
        BookEntity saveAuthor =bookRepository.save(book);

        return modelMapper.map(saveAuthor,BookDTO.class);
    }

    public List<BookDTO> searchBookByTitle(String title){
        List<BookEntity> entity=bookRepository.findByTitleContainingIgnoreCase(title);
        return entity.stream()
                .map(bookEntity-> modelMapper.map(bookEntity,BookDTO.class))
                .collect(Collectors.toList());
    }

    public List<BookDTO> findByPublishedAfter(LocalDate date){
       List<BookEntity> entity=bookRepository.findByPublishedDateAfter(date);
       return entity.stream()
               .map(bookEntity -> modelMapper.map(bookEntity,BookDTO.class))
               .collect(Collectors.toList());
    }

    public List<BookDTO> findByAuthorId(Long id){
        List<BookEntity> entity=bookRepository.findByAuthorId(id);
        return entity.stream()
                .map(bookEntity -> modelMapper.map(bookEntity,BookDTO.class))
                .collect(Collectors.toList());
    }

}
