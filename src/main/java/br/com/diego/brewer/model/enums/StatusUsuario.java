package br.com.diego.brewer.model.enums;

import br.com.diego.brewer.repository.UsuarioRepository;
import br.com.diego.brewer.service.UsuarioService;
import br.com.diego.brewer.service.impl.UsuarioServiceImpl;

import java.util.Arrays;

public enum StatusUsuario {

    ATIVAR {
        @Override
        public void executar(Long[] codigos, UsuarioRepository usuarios){
            usuarios.findByCodigoIn(Arrays.asList(codigos)).forEach(u -> u.setAtivo(true));
        }
    },
    DESATIVAR {
        @Override
        public void executar(Long[] codigos, UsuarioRepository usuarios){
            usuarios.findByCodigoIn(Arrays.asList(codigos)).forEach(u -> u.setAtivo(false));
        }
    };

    public abstract void executar(Long[] codigos, UsuarioRepository usuarios);
}
