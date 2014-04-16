package gcom.gui.util;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Filtro que limpa a sess�o a cada vez que o usu�rio seleciona uma
 * funcionalidade no menu
 * 
 * @author Rafael Santos
 */
public class FiltroLimparSessao extends HttpServlet implements Filter {
	private FilterConfig filterConfig;
	private static final long serialVersionUID = 1L;
	// Handle the passed-in FilterConfig
	/**
	 * <<Descri��o do m�todo>>
	 * 
	 * @param filterConfig
	 *            Descri��o do par�metro
	 */
	public void init(FilterConfig filterConfig) {
		setFilterConfig(filterConfig);
	}

	// Process the request/response pair
	/**
	 * <<Descri��o do m�todo>>
	 * 
	 * @param request
	 *            Descri��o do par�metro
	 * @param response
	 *            Descri��o do par�metro
	 * @param filterChain
	 *            Descri��o do par�metro
	 * @throws ServletException 
	 * @throws IOException 
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		try {
			String url = ((HttpServletRequest) request).getServletPath();

			if (/*(url.startsWith("/Exibir") || url.startsWith("/exibir")) && */url.endsWith(".do")) {
				HttpServletRequest requestPagina = ((HttpServletRequest) request);
				HttpSession sessao = requestPagina.getSession(false);

				if (requestPagina.getParameter("menu") != null
						&& requestPagina.getParameter("menu").equals("sim")) {
					Enumeration parametrosSessao = requestPagina.getSession(false)
							.getAttributeNames();

					while (parametrosSessao.hasMoreElements()) {
						String nomeParametro = (String) parametrosSessao
								.nextElement();

						if (nomeParametro.equalsIgnoreCase("menuGCOM")
								|| nomeParametro
										.equalsIgnoreCase("arvoreFuncionalidades")
								|| nomeParametro
										.equalsIgnoreCase("usuarioLogado")
								|| nomeParametro.equalsIgnoreCase("dataAtual")
								|| nomeParametro
										.equalsIgnoreCase("dataUltimoAcesso")
								|| nomeParametro
										.equalsIgnoreCase("colecaoGruposUsuario")	
								|| nomeParametro
										.equalsIgnoreCase("ultimosAcessos")			
								|| nomeParametro
										.equalsIgnoreCase("mensagemExpiracao")		
								|| nomeParametro
										.equalsIgnoreCase("org.apache.struts.action.LOCALE")
								|| nomeParametro
										.equalsIgnoreCase("gisHelper")
								|| nomeParametro
										.equalsIgnoreCase("gis")) {

							continue;
						} else {
							sessao.removeAttribute(nomeParametro);
						}
					}
				}
			}

			filterChain.doFilter(request, response);
		} catch (ServletException sx) {
			throw sx;
		} catch (IOException iox) {
			throw iox;
		}
	}

	// Clean up resources
	/**
	 * <<Descri��o do m�todo>>
	 */
	public void destroy() {
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
}
