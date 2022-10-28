package br.senai.sc.editoralivros.utils;

import br.senai.sc.editoralivros.dto.LivroDTO;
import br.senai.sc.editoralivros.model.entity.Livros;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.Valid;

public class LivroUtil {
    private ObjectMapper mapper = new ObjectMapper();

    public Livros converterJsonToModel(String livroJson) {
        LivroDTO livroDTO = converterJsonToDTO(livroJson);
        return converterDTOToModel(livroDTO);
    }

    private LivroDTO converterJsonToDTO(String livroJson) {
        try{
            return this.mapper.readValue(livroJson, LivroDTO.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private Livros converterDTOToModel(@Valid LivroDTO livroDTO) {
        return this.mapper.convertValue(livroDTO, Livros.class);
    }
}
