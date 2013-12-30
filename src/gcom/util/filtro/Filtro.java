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

package gcom.util.filtro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * Representa o filtro b�sico do sistema
 * 
 * @author rodrigo
 */
public abstract class Filtro implements Serializable {

	private static final long serialVersionUID = 1L;
	
    public final static String ID = "id";
    
    public final static String INDICADORUSO = "indicadorUso";
	
	/**
	 * Cole��o dos par�metros que fazem parte do filtro
	 */
	protected Collection parametros = new ArrayList();

	/**
	 * Description of the Field
	 */
	protected Collection colecaoCaminhosParaCarregamentoEntidades = new TreeSet();

	/**
	 * Description of the Field
	 */
	protected boolean consultaSemLimites = false;
	
	protected boolean initializeLazy = false;

	/**
	 * Ordena��o do filtro
	 */
	protected List<String> camposOrderBy = new ArrayList();

	protected String campoOrderBy;

	protected String campoDistinct;

	/**
	 * Retorna o valor de parametros
	 * 
	 * @return O valor de parametros
	 */
	public Collection getParametros() {
		return parametros;
	}

	/**
	 * M�todo para adicionar par�metros no filtro
	 * 
	 * @param filtroParametro
	 *            Descri��o do par�metro
	 */
	public void adicionarParametro(FiltroParametro filtroParametro) {
		this.parametros.add(filtroParametro);
	}

	/**
	 * M�todo para adicionar um campo de cole��o � lista das cole��es que ser�o
	 * carregadas pelo sistema
	 * 
	 * @param campoColecao
	 *            nome do campo
	 */
	public void adicionarCaminhoParaCarregamentoEntidade(String campoColecao) {
		this.colecaoCaminhosParaCarregamentoEntidades.add(campoColecao);
	}

	/**
	 * Seta o valor de parametros
	 * 
	 * @param parametros
	 *            O novo valor de parametros
	 */
	public void setParametros(Collection parametros) {
		this.parametros = parametros;
	}

	/**
	 * Retorna o valor de campoOrderBy
	 * 
	 * @return O valor de campoOrderBy
	 */
	public List<String> getCamposOrderBy() {
		ArrayList<String> list = new ArrayList();
		list.addAll(camposOrderBy);
		if (campoOrderBy != null && !campoOrderBy.trim().equals("")) {
			list.add(campoOrderBy);
		}
		return list;
	}

	/**
	 * Seta o valor de campoOrderBy
	 * 
	 * @param campoOrderBy
	 *            O novo valor de campoOrderBy
	 */
	public void setCampoOrderBy(String... campoOrderBy) {
		camposOrderBy.addAll(Arrays.asList(campoOrderBy));
	}

	public void setCampoOrderBy(String campoOrderBy) {
		camposOrderBy.add(campoOrderBy);
	}

	public void limparCamposOrderBy() {
		campoOrderBy = null;
		camposOrderBy.clear();

	}
	
	public void limparColecaoCaminhosParaCarregamentoEntidades() {
		colecaoCaminhosParaCarregamentoEntidades.clear();

	}

	/**
	 * @return Returns the colecaoCaminhosParaCarregamentoEntidades.
	 */
	public Collection getColecaoCaminhosParaCarregamentoEntidades() {
		Collection colecao = new TreeSet();
		colecao.addAll(colecaoCaminhosParaCarregamentoEntidades);
		return colecao;
	}
	
	public void setColecaoCaminhosParaCarregamentoEntidades(Collection colecaoCaminhosParaCarregamentoEntidades) {
		this.colecaoCaminhosParaCarregamentoEntidades.addAll(colecaoCaminhosParaCarregamentoEntidades);
	}

	/**
	 * < <Descri��o do m�todo>>
	 */
	public void limparListaParametros() {
		parametros.clear();
	}

	/**
	 * Retorna o valor de consultaSemLimites
	 * 
	 * @return O valor de consultaSemLimites
	 */
	public boolean isConsultaSemLimites() {
		return consultaSemLimites;
	}

	/**
	 * Seta o valor de consultaSemLimites
	 * 
	 * @param consultaSemLimites
	 *            O novo valor de consultaSemLimites
	 */
	public void setConsultaSemLimites(boolean consultaSemLimites) {
		this.consultaSemLimites = consultaSemLimites;
	}

	public String getCampoDistinct() {
		return campoDistinct;
	}

	public void setCampoDistinct(String campoDistinct) {
		this.campoDistinct = campoDistinct;
	}

	public boolean isInitializeLazy() {
		return initializeLazy;
	}

	public void setInitializeLazy(boolean initializeLazy) {
		this.initializeLazy = initializeLazy;
	}

}
