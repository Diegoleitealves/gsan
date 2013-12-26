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
* Copyright (C) <2007> 
* Adriano Britto Siqueira
* Alexandre Santos Cabral
* Ana Carolina Alves Breda
* Ana Maria Andrade Cavalcante
* Aryed Lins de Ara�jo
* Bruno Leonardo Rodrigues Barros
* Carlos Elmano Rodrigues Ferreira
* Cl�udio de Andrade Lira
* Denys Guimar�es Guenes Tavares
* Eduardo Breckenfeld da Rosa Borges
* Fab�ola Gomes de Ara�jo
* Fl�vio Leonardo Cavalcanti Cordeiro
* Francisco do Nascimento J�nior
* Homero Sampaio Cavalcanti
* Ivan S�rgio da Silva J�nior
* Jos� Edmar de Siqueira
* Jos� Thiago Ten�rio Lopes
* K�ssia Regina Silvestre de Albuquerque
* Leonardo Luiz Vieira da Silva
* M�rcio Roberto Batista da Silva
* Maria de F�tima Sampaio Leite
* Micaela Maria Coelho de Ara�jo
* Nelson Mendon�a de Carvalho
* Newton Morais e Silva
* Pedro Alexandre Santos da Silva Filho
* Rafael Corr�a Lima e Silva
* Rafael Francisco Pinto
* Rafael Koury Monteiro
* Rafael Palermo de Ara�jo
* Raphael Veras Rossiter
* Roberto Sobreira Barbalho
* Rodrigo Avellar Silveira
* Rosana Carvalho Barbosa
* S�vio Luiz de Andrade Cavalcante
* Tai Mu Shih
* Thiago Augusto Souza do Nascimento
* Tiago Moreno Rodrigues
* Vivianne Barbosa Sousa
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
package gcom.gui.cadastro.atualizacaocadastral;

import org.apache.struts.action.ActionForm;

public class FiltrarAlteracaoAtualizacaoCadastralActionForm extends ActionForm {
	private static final long serialVersionUID = 1L;

    private String idEmpresa;
    
    private String nomeEmpresa;
    
    private String idLeiturista;
    
    private String nomeLeiturista;
    
    private String periodoRealizacaoInicial;
    
    private String periodoRealizacaoFinal;
    
	private String idRegistrosNaoAutorizados;
	
	private String idRegistrosAutorizados;
	
	private String exibirCampos;
	
	private String[] colunaImoveis;
	
	private String[] colunaImoveisSelecionados;
	
	private String idLocalidadeInicial;	

	private String idLocalidadeFinal;

	private String nomeLocalidadeInicial;	

	private String nomeLocalidadeFinal;
	
	private String idSetorInicial;	
	
	private String idSetorFinal;
	
	private String nomeSetorInicial;	
	
	private String nomeSetorFinal;
	
	private String idQuadraInicial;	
	
	private String idQuadraFinal;
	
	private String codRotaInicial;

	private String seqRotaInicial;

	private String codRotaFinal;

	private String seqRotaFinal;
	

	public String getIdRegistrosAutorizados() {
		return idRegistrosAutorizados;
	}

	public void setIdRegistrosAutorizados(String idRegistrosAutorizados) {
		this.idRegistrosAutorizados = idRegistrosAutorizados;
	}

	public String getIdRegistrosNaoAutorizados() {
		return idRegistrosNaoAutorizados;
	}

	public void setIdRegistrosNaoAutorizados(String idRegistrosNaoAutorizados) {
		this.idRegistrosNaoAutorizados = idRegistrosNaoAutorizados;
	}

	public String getPeriodoRealizacaoFinal() {
		return periodoRealizacaoFinal;
	}

	public void setPeriodoRealizacaoFinal(String periodoRealizacaoFinal) {
		this.periodoRealizacaoFinal = periodoRealizacaoFinal;
	}

	public String getPeriodoRealizacaoInicial() {
		return periodoRealizacaoInicial;
	}

	public void setPeriodoRealizacaoInicial(String periodoRealizacaoInicial) {
		this.periodoRealizacaoInicial = periodoRealizacaoInicial;
	}

	public String getNomeLeiturista() {
		return nomeLeiturista;
	}

	public void setNomeLeiturista(String nomeLeiturista) {
		this.nomeLeiturista = nomeLeiturista;
	}

	public String getIdLeiturista() {
		return idLeiturista;
	}

	public void setIdLeiturista(String idLeiturista) {
		this.idLeiturista = idLeiturista;
	}

	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String[] getColunaImoveis() {
		return colunaImoveis;
	}

	public void setColunaImoveis(String[] colunaImoveis) {
		this.colunaImoveis = colunaImoveis;
	}

	public String[] getColunaImoveisSelecionados() {
		return colunaImoveisSelecionados;
	}

	public void setColunaImoveisSelecionados(String[] colunaImoveisSelecionados) {
		this.colunaImoveisSelecionados = colunaImoveisSelecionados;
	}

	public String getExibirCampos() {
		return exibirCampos;
	}

	public void setExibirCampos(String exibirCampos) {
		this.exibirCampos = exibirCampos;
	}

	public String getIdLocalidadeInicial() {
		return idLocalidadeInicial;
	}

	public void setIdLocalidadeInicial(String idLocalidadeInicial) {
		this.idLocalidadeInicial = idLocalidadeInicial;
	}

	public String getIdLocalidadeFinal() {
		return idLocalidadeFinal;
	}

	public void setIdLocalidadeFinal(String idLocalidadeFinal) {
		this.idLocalidadeFinal = idLocalidadeFinal;
	}

	public String getNomeLocalidadeInicial() {
		return nomeLocalidadeInicial;
	}

	public void setNomeLocalidadeInicial(String nomeLocalidadeInicial) {
		this.nomeLocalidadeInicial = nomeLocalidadeInicial;
	}

	public String getNomeLocalidadeFinal() {
		return nomeLocalidadeFinal;
	}

	public void setNomeLocalidadeFinal(String nomeLocalidadeFinal) {
		this.nomeLocalidadeFinal = nomeLocalidadeFinal;
	}

	public String getIdSetorInicial() {
		return idSetorInicial;
	}

	public void setIdSetorInicial(String idSetorInicial) {
		this.idSetorInicial = idSetorInicial;
	}

	public String getIdSetorFinal() {
		return idSetorFinal;
	}

	public void setIdSetorFinal(String idSetorFinal) {
		this.idSetorFinal = idSetorFinal;
	}

	public String getNomeSetorInicial() {
		return nomeSetorInicial;
	}

	public void setNomeSetorInicial(String nomeSetorInicial) {
		this.nomeSetorInicial = nomeSetorInicial;
	}

	public String getNomeSetorFinal() {
		return nomeSetorFinal;
	}

	public void setNomeSetorFinal(String nomeSetorFinal) {
		this.nomeSetorFinal = nomeSetorFinal;
	}

	public String getIdQuadraInicial() {
		return idQuadraInicial;
	}

	public void setIdQuadraInicial(String idQuadraInicial) {
		this.idQuadraInicial = idQuadraInicial;
	}

	public String getIdQuadraFinal() {
		return idQuadraFinal;
	}

	public void setIdQuadraFinal(String idQuadraFinal) {
		this.idQuadraFinal = idQuadraFinal;
	}

	public String getCodRotaInicial() {
		return codRotaInicial;
	}

	public void setCodRotaInicial(String codRotaInicial) {
		this.codRotaInicial = codRotaInicial;
	}

	public String getSeqRotaInicial() {
		return seqRotaInicial;
	}

	public void setSeqRotaInicial(String seqRotaInicial) {
		this.seqRotaInicial = seqRotaInicial;
	}

	public String getCodRotaFinal() {
		return codRotaFinal;
	}

	public void setCodRotaFinal(String codRotaFinal) {
		this.codRotaFinal = codRotaFinal;
	}

	public String getSeqRotaFinal() {
		return seqRotaFinal;
	}

	public void setSeqRotaFinal(String seqRotaFinal) {
		this.seqRotaFinal = seqRotaFinal;
	}	
}