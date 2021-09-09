package br.com.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.dao.DaoGenericImpl;
import br.com.entity.Lancamento;
import br.com.entity.Pessoa;
import br.com.repository.IDaoLancamento;
import br.com.repository.IDaoLancamentoImpl;

@ViewScoped
@ManagedBean(name = "lancamentoBean")
public class LancamentoBean {
	
	private Lancamento lancamento = new Lancamento();
	private DaoGenericImpl<Lancamento> daoGeneric = new DaoGenericImpl<Lancamento>();
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	private List<Lancamento> lancamentosAll = new ArrayList<Lancamento>();
	
	private IDaoLancamento IDaoLancamento = new IDaoLancamentoImpl();
	
	@PostConstruct
	public void carregarLancamentos() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		
		/*Carregando a lista de lançamentos  do usuário logado*/
		lancamentos = IDaoLancamento.consultar(pessoaUser.getId()); 
		
		/*Carregando a lista de lançamentos de todos os usuários*/
		lancamentosAll = daoGeneric.getListEntity(Lancamento.class);
	}
	
	public String salvar() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		
		lancamento.setUsuario(pessoaUser);
		lancamento = daoGeneric.merge(lancamento);
		
		this.carregarLancamentos();
		
		return "";
	}
	
	public String novo() {
		lancamento = new Lancamento();
		return "";
	}
	
	public String remove() {
		daoGeneric.deleteByID(lancamento);
		lancamento = new Lancamento();
		this.carregarLancamentos();
		return "";
	}
	
	public List<Lancamento> getLancamentosAll() {
		return lancamentosAll;
	}
	
	public void setLancamentosAll(List<Lancamento> lancamentosAll) {
		this.lancamentosAll = lancamentosAll;
	}
	
	public Lancamento getLancamento() {
		return lancamento;
	}
	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	public DaoGenericImpl<Lancamento> getDaoGeneric() {
		return daoGeneric;
	}
	public void setDaoGeneric(DaoGenericImpl<Lancamento> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}
	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	} 
	
	

}
