package br.senai.sc.editoralivros.security.service;

import br.senai.sc.editoralivros.model.entity.Pessoa;
import br.senai.sc.editoralivros.repository.PessoaRepository;
import br.senai.sc.editoralivros.security.users.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaService implements UserDetailsService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Pessoa> pessoaOptional;
        try{
            Long cpf = Long.parseLong(username);
            pessoaOptional = pessoaRepository.findById(cpf);
        }catch(NumberFormatException e){
            pessoaOptional = pessoaRepository.findByEmail(username);
        }
        if (pessoaOptional.isPresent()) {
            return new UserJPA(pessoaOptional.get());
        }
        throw new UsernameNotFoundException("Usuário não encontrado!");
    }

    public UserDetails loadUserByCPF(Long cpf) throws UsernameNotFoundException {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(cpf);
        if (pessoaOptional.isPresent()) {
            return new UserJPA(pessoaOptional.get());
        }
        throw new UsernameNotFoundException("Usuário não encontrado!");
    }
}
