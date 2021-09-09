package br.com.repository;

import java.util.List;

import br.com.entity.Lancamento;

public interface IDaoLancamento {
	
	public List<Lancamento> consultar(Long codUser);

}
