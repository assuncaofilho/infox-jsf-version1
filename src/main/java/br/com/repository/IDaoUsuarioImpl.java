package br.com.repository;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


import br.com.entity.Usuario;
import br.com.util.JPAUtil;

public class IDaoUsuarioImpl implements IDaoUsuario {
	
	private EntityManager entityManager;
	
	public IDaoUsuarioImpl() {
		entityManager = JPAUtil.getEntityManager();
	}

	@Override
	public Usuario consultarUsuario(String login, String senha) throws Exception {
		Usuario usuario = null;
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		usuario = (Usuario) entityManager.createQuery("select u from Usuario u where u.login = '" + login + "' and u.senha = '"+ senha +"'").getSingleResult();
		
		entityTransaction.commit();
		entityManager.close();
		
		return usuario;
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> consultaListar(String nome) {
		
		List<Usuario> listagem = entityManager.createNativeQuery(
				"select * from usuario where nome ilike '%"+nome+"%'", Usuario.class).getResultList();
		
		return listagem;
	}



}
