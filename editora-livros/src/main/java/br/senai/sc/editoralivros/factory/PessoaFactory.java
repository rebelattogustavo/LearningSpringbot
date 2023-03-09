package br.senai.sc.editoralivros.factory;
import br.senai.sc.editoralivros.dto.PessoaDTO;
import br.senai.sc.editoralivros.model.entity.*;

public class PessoaFactory {
    public static Pessoa getPessoa(PessoaDTO pessoaDTO) {
        switch (pessoaDTO.getTipo()) {
            case 1:
                return new Autor(pessoaDTO.getCpf(), pessoaDTO.getNome(), pessoaDTO.getSobrenome(), pessoaDTO.getEmail(),
                        pessoaDTO.getSenha(), pessoaDTO.getGenero());
            case 2:
                return new Revisor(pessoaDTO.getCpf(), pessoaDTO.getNome(), pessoaDTO.getSobrenome(), pessoaDTO.getEmail(),
                        pessoaDTO.getSenha(), pessoaDTO.getGenero());
            case 3:
                return new Diretor(pessoaDTO.getCpf(), pessoaDTO.getNome(), pessoaDTO.getSobrenome(), pessoaDTO.getEmail(),
                        pessoaDTO.getSenha(), pessoaDTO.getGenero());
            default:
                return null;
        }
    }
}