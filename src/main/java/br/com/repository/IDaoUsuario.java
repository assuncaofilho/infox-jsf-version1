package br.com.repository;

import java.util.List;

import br.com.entity.Usuario;

public interface IDaoUsuario {

	Usuario consultarUsuario(String login, String senha) throws Exception;
	
	List<Usuario> consultaListar(String nome) throws Exception;
	
}
