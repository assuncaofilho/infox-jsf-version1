package br.com.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entity.Os;
import br.com.util.JPAUtil;

public class IDaoOsImpl implements IDaoOs {

	@Override
	public Os findByID(String id) {
		
		EntityManager entityManager = JPAUtil.getEntityManager();

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		Os retorno = entityManager.find(Os.class, Long.parseLong(id));
		
		entityTransaction.commit();
		entityManager.close();
		
		return retorno;
		
	}

}
