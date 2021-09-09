package br.com.repository;

import java.util.List;

import br.com.entity.Cliente;

public interface IDaoCliente {
	
	public List<Cliente> consultaListar(String nome) throws Exception;

}
