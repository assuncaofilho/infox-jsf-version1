package br.com.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.dao.DaoGenericImpl;
import br.com.entity.Pessoa;
import br.com.repository.IDaoPessoa;
import br.com.repository.IDaoPessoaImpl;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {
	
	private Pessoa pessoa = new Pessoa();
	private DaoGenericImpl<Pessoa> daoGeneric = new DaoGenericImpl<Pessoa>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>(); 
	
	private IDaoPessoa iDaoPessoa = new IDaoPessoaImpl();
	
	public String logout() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.getSessionMap().remove("usuarioLogado"); // remove o usuário logado de dentro do external context;
		
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true); // acessando a sessão atual;
		
		session.invalidate(); // invalida a sessão;
		
		return "index.jsf";
	}
	
	public String logar() {
		
		try {
		Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());
		
		if(pessoaUser != null) { // autenticou
			
			/*Inserir usuário na sessão usuarioLogado*/
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			externalContext.getSessionMap().put("usuarioLogado", pessoaUser);
			

			
			return "primeirapagina?faces-redirect=true";

		}
		
		}catch(Exception e) {
		e.printStackTrace();
		this.mostrarMsg("Login e/ou senha inválido(s).");
		}
		return "index.jsf"; // retorno que o commandbutton recebe e encaminha a navegação;
							// Se não autenticou retorna para o index.jsf;
	}
	
	
	public boolean permiteAcesso(String perfil) {
		
		
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		
		return pessoaUser.getPerfil().equals(perfil);
		
		
	}
	
	public String salvar() {
		pessoa = daoGeneric.merge(pessoa);
		this.carregarPessoas();
		mostrarMsg("Cadastrado com sucesso");
		return ""; 
	}
	
	private void mostrarMsg(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
	}

	public String novo() {
		pessoa = new Pessoa(); 
		return ""; 
	}
	
	public String limpar() {
		pessoa = new Pessoa(); 
		return ""; 
	}
	
	public String remove() {
		//daoGeneric.delete(pessoa);
		daoGeneric.deleteByID(pessoa);
		pessoa = new Pessoa(); //limpando os campos
		this.carregarPessoas();
		this.mostrarMsg("removido com sucesso!");
		return ""; 
	}
	
	@PostConstruct
	public void carregarPessoas() {
		
		pessoas = daoGeneric.getListEntity(Pessoa.class);
		
	}
	

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DaoGenericImpl<Pessoa> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGenericImpl<Pessoa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}
	
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

}
