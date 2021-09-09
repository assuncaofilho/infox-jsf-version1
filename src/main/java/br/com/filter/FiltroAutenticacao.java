package br.com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.entity.Usuario;
import br.com.util.JPAUtil;

@WebFilter(urlPatterns = {"/*"})
public class FiltroAutenticacao implements Filter {

	@Override
	public void destroy() {
		
	}

	//Sempre é chamado quando há interceptação de url
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		
		String url = req.getServletPath();
		
		
		
		// o Filtro ignora o index independente do usuário estar logado ou não.
		// Se o usuario não está logado então é redirecionado para a tela de login.
		// Se está logado, passa por fora e o fluxo segue;
		if (!url.equals("/index.jsf") && usuarioLogado == null) {
			
			RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsf"); 
			redireciona.forward(request, response);
			return;
			
		}
		
		if(url.equals("index.jsf") && usuarioLogado != null) {
			//invalida a sessão evitando que o usuario fique logado ao acessar o index;
			session.removeAttribute("usuarioLogado");
			session.invalidate();
		}
		
		chain.doFilter(request, response); //deixar o fluxo correr;
		
	}

	//O init é chamado na inicialização do Servidor
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		JPAUtil.getEntityManager();
	}

}
