package br.com.repository;

import br.com.entity.Pessoa;

public interface IDaoPessoa {
	
	Pessoa consultarUsuario(String login, String senha) throws Exception;
	
}
