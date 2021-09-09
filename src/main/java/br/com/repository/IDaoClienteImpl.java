package br.com.repository;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.entity.Cliente;
import br.com.util.JPAUtil;

public class IDaoClienteImpl implements IDaoCliente {
	
	private EntityManager entityManager;
	
	public IDaoClienteImpl() {
		entityManager = JPAUtil.getEntityManager();
	}

	
	@SuppressWarnings("unchecked")
	public List<Cliente> consultaListar(String nome) {
		
		List<Cliente> listagem = entityManager.createNativeQuery(
				"select * from cliente where nome ilike '%"+nome+"%'", Cliente.class).getResultList();
		
		return listagem;
	}




}
