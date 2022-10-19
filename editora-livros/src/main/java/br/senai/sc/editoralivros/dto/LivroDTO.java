package br.senai.sc.editoralivros.dto;

import br.senai.sc.editoralivros.model.entity.Autor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class LivroDTO {
    private Long isbn;
    private String titulo;
    private Autor autor;
    private Integer qtdPag;
}
