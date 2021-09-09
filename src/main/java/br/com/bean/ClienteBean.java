package br.com.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.dao.DaoGeneric;
import br.com.dao.DaoGenericImpl;
import br.com.entity.Cliente;
import br.com.repository.IDaoCliente;
import br.com.repository.IDaoClienteImpl;

@ViewScoped
@ManagedBean (name = "clienteBean")
public class ClienteBean{
	
	private Cliente cliente = new Cliente();
	private DaoGeneric<Cliente> daoGeneric = new DaoGenericImpl<Cliente>();
	private List<Cliente> clientes = new ArrayList<Cliente>();
	private List<Cliente> clientesBusca = new ArrayList<Cliente>();
	private String nomeBusca = new String();
	
	IDaoCliente iDaoCliente = new IDaoClienteImpl();
	
	
	private void mostrarMsg(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
	}
	
	public String novo() {
		cliente = new Cliente(); 
		return ""; 
	}
	
	public String limpar() {
		cliente = new Cliente(); 
		return ""; 
	}
	
	public String remove() {
		//daoGeneric.delete(pessoa);
		daoGeneric.deleteByID(cliente);
		cliente = new Cliente(); //limpando os campos
		this.carregarClientes();
		this.mostrarMsg("removido com sucesso!");
		return ""; 
	}
	
	public String salvar() {
		cliente = daoGeneric.merge(cliente);
		this.carregarClientes();
		mostrarMsg("Cadastrado com sucesso");
		return ""; 
	}
	

	
	@PostConstruct
	public void carregarClientes() {
		
		clientes = daoGeneric.getListEntity(Cliente.class);
		
	}
	
	public String buscarClientesByName() {
		
		try {
		
		clientesBusca = iDaoCliente.consultaListar(this.nomeBusca);
		
		mostrarMsg(+clientesBusca.size()+ " resultado(s).");
		
		}catch(Exception e) {
			e.printStackTrace();
			mostrarMsg("Falha na busca! Tente novamente!");
		}
		
		return "";
	}
	
	
	public String getNomeBusca() {
		return nomeBusca;
	}
	
	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}
	
	public List<Cliente> getClientesBusca() {
		return clientesBusca;
	}
	
	public void setClientesBusca(List<Cliente> clientesBusca) {
		this.clientesBusca = clientesBusca;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	

}
