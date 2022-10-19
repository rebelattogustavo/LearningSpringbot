package br.senai.sc.editoralivros.model.service;

import br.senai.sc.editoralivros.model.entity.Autor;
import br.senai.sc.editoralivros.model.entity.Livros;
import br.senai.sc.editoralivros.model.entity.Status;
import br.senai.sc.editoralivros.repository.LivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LivroService {
    private LivroRepository livroRepository;

    public boolean existsById(Long isbn) {
        return livroRepository.existsById(isbn);
    }

    public Livros save(Livros livro) {
        return livroRepository.save(livro);
    }

    public Optional<Livros> findById(Long isbn) {
        return livroRepository.findById(isbn);
    }

    public Optional<Livros> findByStatus(Status status) {
        return livroRepository.findByStatus(status);
    }

    public Optional<Livros> findByAutor(Autor autor) {
        return livroRepository.findByAutores(autor);
    }



    public List<Livros> findAll() {
        return livroRepository.findAll();
    }

    public void deleteById(Long isbn) {
        livroRepository.deleteById(isbn);
    }
}
