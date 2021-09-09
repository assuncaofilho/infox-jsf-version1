package br.com.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.com.dao.DaoGeneric;
import br.com.dao.DaoGenericImpl;
import br.com.entity.Cliente;
import br.com.entity.Os;
import br.com.entity.Usuario;
import br.com.repository.IDaoCliente;
import br.com.repository.IDaoClienteImpl;
import br.com.repository.IDaoOs;
import br.com.repository.IDaoOsImpl;
import br.com.repository.IDaoUsuario;
import br.com.repository.IDaoUsuarioImpl;
import br.com.util.JPAUtil;

@ViewScoped
@ManagedBean
public class OsBean {
	
	
	private DaoGeneric<Os> daoGeneric = new DaoGenericImpl<Os>();
	
	
	IDaoCliente iDaoCliente = new IDaoClienteImpl();
	IDaoUsuario iDaoUsuario = new IDaoUsuarioImpl();
	IDaoOs iDaoOs = new IDaoOsImpl();
	
	private List<Os> allos = new ArrayList<Os>();
	private List<Cliente> clientesResult = new ArrayList<Cliente>();
	private List<Usuario> usuariosResult = new ArrayList<Usuario>();
	
	private Os os = new Os();
	private String idOs = new String();
	private String nomeBuscaCliente = new String();
	private String nomeBuscaTecnico = new String();
	
	
	public String buscarClientesByName() {
		
		try {
			
		System.out.println(nomeBuscaCliente);	
		
		clientesResult = iDaoCliente.consultaListar(nomeBuscaCliente);
		
		mostrarMsg(clientesResult.size()+ " resultado(s).");
		
		}catch(Exception e) {
			e.printStackTrace();
			mostrarMsg("Falha na busca! Tente novamente!");
		}
		
		return "";
	}
	
	public String buscarTecnicosByName() {
		
		try {
		
		usuariosResult = iDaoUsuario.consultaListar(this.nomeBuscaTecnico);
		
		mostrarMsg(+clientesResult.size()+ " resultado(s).");
		
		}catch(Exception e) {
			e.printStackTrace();
			mostrarMsg("Falha na busca! Tente novamente!");
		}
		
		return "";
	}
	

	
	private void mostrarMsg(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
	}
	
	public String novo() {
		os = new Os(); 
		return ""; 
	}
	
	public String limpar() {
		os = new Os(); 
		return ""; 
	}
	
	public String remove() {
		//daoGeneric.delete(pessoa);
		daoGeneric.deleteByID(os);
		os = new Os(); //limpando os campos
		this.carregarOs();
		this.mostrarMsg("removido com sucesso!");
		return ""; 
	}
	
	public String salvar() {
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		Cliente cliAtualizado = entityManager.find(Cliente.class, os.getCliente().getId());
		Usuario usuAtualizado = entityManager.find(Usuario.class, os.getUsuario().getId());
		
		os.setCliente(cliAtualizado);
		os.setUsuario(usuAtualizado);
		
		os = daoGeneric.merge(os);
		this.carregarOs();
		mostrarMsg("Cadastrado com sucesso");
		return ""; 
	}
	
	@PostConstruct
	public void carregarOs() {
		
		allos = daoGeneric.getListEntity(Os.class);
		
	}
	
	
	public List<Cliente> getClientesResult() {
		return clientesResult;
	}
	
	public void setClientesResult(List<Cliente> clientesResult) {
		this.clientesResult = clientesResult;
	}
	
	public List<Usuario> getUsuariosResult() {
		return usuariosResult;
	}
	
	public void setUsuariosResult(List<Usuario> usuariosResult) {
		this.usuariosResult = usuariosResult;
	}
	
	public List<Os> getAllos() {
		return allos;
	}
	public void setAllos(List<Os> allos) {
		this.allos = allos;
	}
	public Os getOs() {
		return os;
	}
	public void setOs(Os os) {
		this.os = os;
	}
	
	public String getIdOs() {
		return idOs;
	}
	
	public void setIdOs(String idOs) {
		this.idOs = idOs;
	}

	public String getNomeBuscaCliente() {
		return nomeBuscaCliente;
	}
	public void setNomeBuscaCliente(String nomeBuscaCliente) {
		this.nomeBuscaCliente = nomeBuscaCliente;
	}
	public String getNomeBuscaTecnico() {
		return nomeBuscaTecnico;
	}
	public void setNomeBuscaTecnico(String nomeBuscaTecnico) {
		this.nomeBuscaTecnico = nomeBuscaTecnico;
	}
	
	

}
