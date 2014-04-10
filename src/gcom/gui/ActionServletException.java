/*
* Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
*
* This file is part of GSAN, an integrated service management system for Sanitation
*
* GSAN is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License.
*
* GSAN is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
*/

/*
* GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
*
* Este programa � software livre; voc� pode redistribu�-lo e/ou
* modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
* publicada pela Free Software Foundation; vers�o 2 da
* Licen�a.
* Este programa � distribu�do na expectativa de ser �til, mas SEM
* QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
* COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
* PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
* detalhes.
* Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
* junto com este programa; se n�o, escreva para Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
* 02111-1307, USA.
*/  
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
