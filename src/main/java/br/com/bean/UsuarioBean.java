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

import br.com.dao.DaoGeneric;
import br.com.dao.DaoGenericImpl;
import br.com.entity.Usuario;
import br.com.repository.IDaoUsuario;
import br.com.repository.IDaoUsuarioImpl;

@ViewScoped
@ManagedBean(name = "usuarioBean")
public class UsuarioBean {
	
	private Usuario usuario = new Usuario();
	private DaoGeneric<Usuario> daoGeneric = new DaoGenericImpl<Usuario>();
	
	
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	
	private IDaoUsuario iDaoUsuario = new IDaoUsuarioImpl();
	
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
		Usuario user = iDaoUsuario.consultarUsuario(usuario.getLogin(), usuario.getSenha());
		
		if(user != null) { // autenticou
			
			/*Inserir usuário na sessão usuarioLogado*/
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			externalContext.getSessionMap().put("usuarioLogado", user);
			

			
			return "primeirapagina?faces-redirect=true";

		}
		
		}catch(Exception e) {
		e.printStackTrace();
		this.mostrarMsg("Login e/ou senha inválido(s).");
		}
		return "index.jsf"; // retorno que o commandbutton recebe e encaminha a navegação;
							// Se não autenticou retorna para o index.jsf;
	}
	
	private void mostrarMsg(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
	}
	
		
	
	public boolean permiteAcesso(String perfil) {
		
		
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Usuario user = (Usuario) externalContext.getSessionMap().get("usuarioLogado");
		
		return user.getPerfil().equals(perfil);
		
		
	}
	
	public String novo() {
		usuario = new Usuario(); 
		return ""; 
	}
	
	public String limpar() {
		usuario = new Usuario(); 
		return ""; 
	}
	
	public String remove() {
		//daoGeneric.delete(pessoa);
		daoGeneric.deleteByID(usuario);
		usuario = new Usuario(); //limpando os campos
		this.carregarUsuarios();
		this.mostrarMsg("removido com sucesso!");
		return ""; 
	}
	
	public String salvar() {
		usuario = daoGeneric.merge(usuario);
		this.carregarUsuarios();
		mostrarMsg("Cadastrado com sucesso");
		return ""; 
	}
	
	@PostConstruct
	public void carregarUsuarios() {
		
		usuarios = daoGeneric.getListEntity(Usuario.class);
		
	}
	

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	} 
	
	

}
