package com.example.library.Service;

import com.example.library.advices.ResourceNotFoundException;
import com.example.library.dto.AuthorDTO;
import com.example.library.entity.AuthorEntity;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, ModelMapper modelMapper,
                         BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
    }


    public AuthorDTO getAutherById(Long id) {
        return authorRepository.findById(id)
                .map(authorEntity ->modelMapper.map(authorEntity,AuthorDTO.class)
                )
                .orElseThrow(()-> new RuntimeException("Author with id " + id + " not found"));
    }

    public List<AuthorDTO> getAllAuthors(){
        List<AuthorEntity> authorEntity1 =authorRepository.findAll();
        return authorEntity1
                .stream()
                .map(authorEntity-> modelMapper.map(authorEntity,AuthorDTO.class))
                .collect(Collectors.toList());
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO){
        AuthorEntity authorEntity = modelMapper.map(authorDTO,AuthorEntity.class);

        AuthorEntity savedAuthor = authorRepository.save(authorEntity);
        return modelMapper.map(savedAuthor,AuthorDTO.class);
    }

    public List<AuthorDTO> findByName(String name){
        List<AuthorEntity> entity=authorRepository.findByNameContainingIgnoreCase(name);
        return entity.stream()
                .map(authorEntity -> modelMapper.map(authorEntity,AuthorDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteAuthor(Long id){
        AuthorEntity author=authorRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException( "Author not found with id: " + id));
        if(bookRepository.existsByAuthorId(id)){
            throw new IllegalStateException( "Cannot delete author because books are assigned to this author");
        }
        authorRepository.delete(author);
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        AuthorEntity existing = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        modelMapper.map(authorDTO,existing);
        existing.setId(id);

        AuthorEntity updated=authorRepository.save(existing);
        return modelMapper.map(updated,AuthorDTO.class);

    }
    public boolean isExists(Long id){
        boolean exist=authorRepository.existsById(id);
        if(!exist){
            throw new ResourceNotFoundException("Author not found with id: " + id);
        } return true;
    }
}
