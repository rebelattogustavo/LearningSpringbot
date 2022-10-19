package br.senai.sc.editoralivros.repository;

import br.senai.sc.editoralivros.model.entity.Autor;
import br.senai.sc.editoralivros.model.entity.Livros;
import br.senai.sc.editoralivros.model.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livros, Long> {

    Optional<Livros> findByStatus(Status status);
    Optional<Livros> findByAutor(Autor autor);
}
