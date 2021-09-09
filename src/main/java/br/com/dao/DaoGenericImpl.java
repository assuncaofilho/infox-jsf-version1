package br.com.dao;

import br.com.util.JPAUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DaoGenericImpl<E> implements DaoGeneric<E> {
	
	
	public void salvar(E entity) {
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		entityManager.persist(entity);
		
		entityTransaction.commit();
		entityManager.close();
		
	}
	
	public E merge(E entity) {
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		E retorno = entityManager.merge(entity);
		
		entityTransaction.commit();
		entityManager.close();
		
		return retorno;
		
	}
	
		public void delete(E entity) {
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		//para evitar o erro 'Removing a detached instance' precisamos monitorar a entidade que se quer excluir.
		
		/*Forçando o monitoramento de 'entity' pelo entityManager*/
		entity = entityManager.merge(entity); // aqui ela estará "atachada";
		
		entityManager.remove(entity);
		
		entityTransaction.commit();
		entityManager.close();
		
		
	}
	
	public void deleteByID(E entity) {
		
		Object id = JPAUtil.getPrimaryKey(entity);
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		/*Criteria Delete*/
		entityManager.createQuery("delete from " +entity.getClass().getSimpleName()+ " where id = " +id).executeUpdate();
		
		entityTransaction.commit();
		entityManager.close();
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<E> getListEntity(Class<E> entity){
		
		EntityManager entityManager = JPAUtil.getEntityManager();

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		List<E> retorno = entityManager.createQuery("from " + entity.getSimpleName()).getResultList();
		
		entityTransaction.commit();
		entityManager.close();
		
		return retorno;
		
		
	}

	

}
