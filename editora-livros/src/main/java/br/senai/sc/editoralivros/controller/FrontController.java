package br.senai.sc.editoralivros.controller;

import br.senai.sc.editoralivros.dto.PessoaDTO;
import br.senai.sc.editoralivros.model.entity.Pessoa;
import br.senai.sc.editoralivros.security.UsuarioDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/editora-livros-api")
public class FrontController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/livro")
    public String livro(){
        return "cadastro-livros";
    }

    @GetMapping("/usuario")
    public String usuario(Authentication authentication, Model model){
        PessoaDTO pessoaDTO = new PessoaDTO();
        if (authentication != null){
            if (authentication instanceof OAuth2AuthenticationToken){
                OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
                pessoaDTO.setNome(oAuth2User.getAttribute("given_name"));
                pessoaDTO.setSobrenome(oAuth2User.getAttribute("family_name"));
                pessoaDTO.setEmail(oAuth2User.getAttribute("email"));
            }
        }
        model.addAttribute("pessoa", pessoaDTO);
        return "cadastro-usuarios";
    }


}
