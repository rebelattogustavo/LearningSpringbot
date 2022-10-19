package br.senai.sc.editoralivros.model.entity;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;

@AllArgsConstructor
@Entity
public class Revisor extends Pessoa {

    public Revisor(Long cpf, String nome, String sobrenome, String email, String senha, Genero genero) {
        super(cpf, nome, sobrenome, email, senha, genero);
    }
}
