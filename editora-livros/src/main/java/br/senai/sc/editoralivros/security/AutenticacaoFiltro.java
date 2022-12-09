package br.senai.sc.editoralivros.security;

import br.senai.sc.editoralivros.model.entity.Pessoa;
import br.senai.sc.editoralivros.security.service.JpaService;
import br.senai.sc.editoralivros.security.users.UserJPA;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class AutenticacaoFiltro extends OncePerRequestFilter {

    private JpaService jpaService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")){
            token = token.substring(7);
        }else{
            token = null;
        }
        Boolean valido = jpaService.validarToken(token);

        if (valido){
            UserJPA usuario = jpaService.getUsuario(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario.getUsername(),
                    null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (!request.getRequestURI().contains("/editora-livros-api/login")){
            response.setStatus(401);
        }
        filterChain.doFilter(request, response);
    }
}
