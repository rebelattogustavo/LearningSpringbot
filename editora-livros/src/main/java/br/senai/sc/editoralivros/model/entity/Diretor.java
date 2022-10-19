package br.senai.sc.editoralivros.model.entity;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;

@AllArgsConstructor
@Entity
public class Diretor extends Pessoa {

    public Diretor(Long cpf, String nome, String sobrenome, String email, String senha, Genero genero) {
        super(cpf, nome, sobrenome, email, senha, genero);
    }
}
