package br.senai.sc.editoralivros.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "tb_livros")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString @EqualsAndHashCode
public class Livros {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 13, nullable = false, unique = true)
    private Long isbn;

    @Column(length = 50, nullable = false)
    private String titulo;

    @ManyToMany
    @JoinTable(name = "tb_livros_autor",
            joinColumns = @JoinColumn(name = "isbn_livro", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "cpf_autor", nullable = false))
    private List<Autor> autores;

    @Column(nullable = false)
    private Integer qtdPag;

    @ManyToOne
    @JoinColumn(name = "cpf_revisor")
    private Revisor revisor;

//    @Column(length = 50, nullable = false)
//    private Integer pagRevisadas;

    @Column(nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "cnpj_editora")
    private Editora editora;
}
