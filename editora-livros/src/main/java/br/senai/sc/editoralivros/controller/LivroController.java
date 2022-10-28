package br.senai.sc.editoralivros.controller;

import br.senai.sc.editoralivros.dto.LivroDTO;
import br.senai.sc.editoralivros.model.entity.Autor;
import br.senai.sc.editoralivros.model.entity.Livros;
import br.senai.sc.editoralivros.model.entity.Status;
import br.senai.sc.editoralivros.model.service.LivroService;
import br.senai.sc.editoralivros.utils.LivroUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/editoralivros/livro")
public class LivroController {
    LivroService livroService;

    @PostMapping
    public ResponseEntity<Object> save(
            @RequestParam("livro") String livroJson,
            @RequestParam("arquivo") MultipartFile file) {

        LivroUtil util = new LivroUtil();
        Livros livro = util.converterJsonToModel(livroJson);
        if (livroService.existsById(livro.getIsbn())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Há um livro com o ISBN " + livro.getIsbn() + " cadastrado.");
        }
        livro.setArquivo(file);
        livro.setStatus(Status.AGUARDANDO_REVISAO);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.save(livro));
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Object> update(@PathVariable(value = "isbn") Long isbn, @RequestBody @Valid LivroDTO livroDTO) {
        if (!livroService.existsById(isbn)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado");
        }
        Livros livroModel = livroService.findById(livroDTO.getIsbn()).get();
        BeanUtils.copyProperties(livroDTO, livroModel);
        livroModel.setStatus(Status.AGUARDANDO_REVISAO);
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
                    .body("Não há livros com o status " + status + "!.");
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

    @GetMapping("/page")
    public ResponseEntity<Page<Livros>> findAllPage(
            @PageableDefault(
                    page = 2,
                    size = 9,
                    sort = "isbn",
                    direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(livroService.findAll(pageable));
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "isbn") Long isbn) {
        if (livroService.existsById(isbn)) {
            livroService.deleteById(isbn);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Livro com o ISBN " +isbn+ " deletado com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("O livro com o ISBN " + isbn + " não foi encontrado.");
    }
}
