package gcom.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Representa um problema no n�vel do Action
 * 
 * @author rodrigo
 */
public class ActionServletException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<String> parametrosMensagem = new ArrayList<String>();
	
	private String parametroMensagem;
	
	private String urlBotaoVoltar;

	/**
	 * Construtor da classe ActionServletException
	 * 
	 * @param mensagem
	 *            Chave do erro que aconteceu no controlador(mensagem obtida num
	 *            arquivo de properties)
	 * @param excecaoCausa
	 *            Exce��o que originou o problema
	 */
	public ActionServletException(String mensagem, Exception excecaoCausa) {
		super(mensagem, excecaoCausa);

	}

	/**
	 * Construtor da classe ActionServletException
	 * 
	 * @param mensagem
	 *            Descri��o do par�metro
	 */
	public ActionServletException(String mensagem) {
		super(mensagem, null);
	}

	/**
	 * Construtor da classe ActionServletException
	 * 
	 * @param mensagem
	 *            Descri��o do par�metro
	 * @param excecaoCausa
	 *            Descri��o do par�metro
	 * @param parametroMensagem
	 *            Descri��o do par�metro
	 */
	public ActionServletException(String mensagem, Exception excecaoCausa,
			String... parametroMensagem) {
		super(mensagem, excecaoCausa);
		parametrosMensagem.addAll(Arrays.asList(parametroMensagem));

	}

	public ActionServletException(String mensagem, Exception excecaoCausa,
			String parametroMensagem) {
		super(mensagem, excecaoCausa);
		this.parametroMensagem = parametroMensagem;

	}

	public ActionServletException(String mensagem, String parametroMensagem) {
		super(mensagem);
		this.parametroMensagem = parametroMensagem;

	}

	public ActionServletException(String mensagem, String... parametroMensagem) {
		super(mensagem);
		parametrosMensagem.addAll(Arrays.asList(parametroMensagem));
	}

	public ActionServletException(String mensagem, List<String> parametroMensagem) {
		super(mensagem);
		parametrosMensagem.addAll(parametroMensagem);
	}

	public List<String> getParametroMensagem() {
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(parametrosMensagem);
		if (parametroMensagem != null && !parametroMensagem.trim().equals("")) {
			list.add(parametroMensagem);
		}
		return list;
	}

	public String getParametroMensagem(int numeroMensagem) {
		return getParametroMensagem().get(numeroMensagem);

	}

	public void setParametroMensagem(String... parametroMensagem) {
		parametrosMensagem.addAll(Arrays.asList(parametroMensagem));
	}

	/**
	 * Construtor da classe ActionServletException
	 * 
	 * @param mensagem
	 *            Descri��o do par�metro
	 * @param excecaoCausa
	 *            Descri��o do par�metro
	 * @param url bot�o voltar
	 *            Descri��o do par�metro
	 * @param parametroMensagem
	 *            Descri��o do par�metro
	 */
	public ActionServletException(String mensagem, String urlBotaoVoltar, 
			Exception excecaoCausa, String... parametroMensagem) {
		super(mensagem, excecaoCausa);
		this.urlBotaoVoltar = urlBotaoVoltar;
		parametrosMensagem.addAll(Arrays.asList(parametroMensagem));

	}

	public String getUrlBotaoVoltar() {
		return urlBotaoVoltar;
	}

	public void setUrlBotaoVoltar(String urlBotaoVoltar) {
		this.urlBotaoVoltar = urlBotaoVoltar;
	}
	
	
}
