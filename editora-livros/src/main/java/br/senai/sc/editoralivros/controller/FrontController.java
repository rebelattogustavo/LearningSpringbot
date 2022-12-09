package br.senai.sc.editoralivros.controller;

import br.senai.sc.editoralivros.security.UsuarioDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String usuario(){
        return "cadastro-usuarios";
    }


}
