package br.com.dao;

import java.util.List;

public interface DaoGeneric<E> {

	public void salvar(E entity);
	
	public E merge(E entity);
	
	public void delete(E entity);
	
	public void deleteByID(E entity);
	
	public List<E> getListEntity(Class<E> entity);
	
	
}
