package br.senai.sc.editoralivros.controller;

import br.senai.sc.editoralivros.dto.PessoaDTO;
import br.senai.sc.editoralivros.model.entity.Pessoa;
import br.senai.sc.editoralivros.model.service.PessoaService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/editoralivros/pessoa")
public class PessoaController {
    PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid PessoaDTO pessoaDTO) {
        if(pessoaService.existsById(pessoaDTO.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("message", "CPF já cadastrado!"));
        }
        Optional<Pessoa> pessoaOptional = pessoaService.findById(pessoaDTO.getCpf());

        if (pessoaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este CPF já está cadastrado");
        }
        pessoaOptional = pessoaService.findByEmail(pessoaDTO.getEmail());
        if (pessoaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este e-mail já está cadastrado");
        }

        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDTO, pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.save(pessoa));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Object> update(@PathVariable Long cpf, @RequestBody @Valid PessoaDTO pessoaDTO) {
        if (!pessoaService.existsById(cpf)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
        }
        Optional<Pessoa> pessoaOptional = pessoaService.findById(cpf);
        if (pessoaOptional.isPresent()) {
            Pessoa pessoa = pessoaOptional.get();
            BeanUtils.copyProperties(pessoaDTO, pessoa);
            return ResponseEntity.status(HttpStatus.OK).body(pessoaService.save(pessoa));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Object> findById(@PathVariable(value = "cpf") Long cpf) {

        Optional<Pessoa> pessoaOptional = pessoaService.findById(cpf);
        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Não foi encontrado nenhum usuário com o cpf informado");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(pessoaOptional.get());
    }

//    @GetMapping("/{email}")
//    public ResponseEntity<Object> findByEmail(@PathVariable(value = "email") String email) {
//
//        Optional<Pessoa> pessoaOptional = pessoaService.findByEmail(email);
//        if (pessoaOptional.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Não foi encontrado nenhum usuário com o email informado");
//        }
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(pessoaOptional.get());
//    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "cpf") Long cpf) {
        Optional<Pessoa> pessoaOptional = pessoaService.findById(cpf);
        if (!pessoaService.existsById(cpf)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Não foi encontrado nenhum usuário com o CPF de: " + cpf);
        }
        pessoaService.deleteById(cpf);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário com o CPF de: " +cpf+ " foi deletado com sucesso");
    }
}
