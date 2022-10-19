package br.senai.sc.editoralivros.controller;

import br.senai.sc.editoralivros.dto.LivroDTO;
import br.senai.sc.editoralivros.model.entity.Autor;
import br.senai.sc.editoralivros.model.entity.Livros;
import br.senai.sc.editoralivros.model.entity.Status;
import br.senai.sc.editoralivros.model.service.LivroService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/editoralivros/livro")
public class LivroController {
    LivroService livroService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid LivroDTO livroDTO) {
        if (livroService.existsById(livroDTO.getIsbn())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Há um livro com o ISBN " + livroDTO.getIsbn() + " cadastrado.");
        }
        Livros livro = new Livros();
        BeanUtils.copyProperties(livroDTO, livro);
        livro.setStatus(Status.AGUARDANDO_EDICAO);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.save(livro));
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Object> update(@PathVariable(value = "isbn") Long isbn, @RequestBody @Valid LivroDTO livroDTO) {
        if (!livroService.existsById(isbn)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado");
        }
        Livros livroModel = livroService.findById(livroDTO.getIsbn()).get();
        BeanUtils.copyProperties(livroDTO, livroModel);
        livroModel.setStatus(livroModel.getStatus());
        return ResponseEntity.status(HttpStatus.OK).body(livroService.save(livroModel));
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Object> findById(@PathVariable(value = "isbn") Long isbn) {
        Optional<Livros> livroOptional = livroService.findById(isbn);
        if (livroOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("O livro com o ISBN " + isbn + " não foi encontrado.");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(livroOptional.get());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Object> findByStatus(@PathVariable(value = "status") Status status) {
        Optional<Livros> livroOptional = livroService.findByStatus(status);
        if (livroOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("O livro com o status " + status + " não foi encontrado.");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(livroOptional.get());
    }

    @GetMapping("/autor/{autor}")
    public ResponseEntity<Object> findByAutor(@PathVariable(value = "autor") Autor autor) {
        Optional<Livros> livroOptional = livroService.findByAutor(autor);
        if (livroOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Livros com o autor " + autor + " não foram encontrados.");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(livroOptional.get());
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<Livros> livros = livroService.findAll();
        if (livros.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Nenhum livro foi encontrado.");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(livros);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "isbn") Long isbn) {
        if (livroService.existsById(isbn)) {
            livroService.deleteById(isbn);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Livro com o ISBN" +isbn+ " deletado com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("O livro com o ISBN " + isbn + " não foi encontrado.");
    }
}
