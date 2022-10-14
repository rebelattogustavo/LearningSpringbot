package br.senai.sc.editoralivros.dto;

import br.senai.sc.editoralivros.model.entity.Genero;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
public class PessoaDTO {
    private Long cpf;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private Genero genero;
}
