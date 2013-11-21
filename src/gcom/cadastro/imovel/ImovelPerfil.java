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
package gcom.cadastro.imovel;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

@ControleAlteracao()

public class ImovelPerfil extends ObjetoTransacao{
	
	private static final long serialVersionUID = 1L;
	
	public static final int  OPERACAO_IMOVEL_PERFIL_INSERIR = 1687;
	public static final int  OPERACAO_IMOVEL_PERFIL_ATUALIZAR = 1702;
	public static final int OPERACAO_IMOVEL_PERFIL_REMOVER = 1698;	

	
	/** TODO : COSANPA
	 * Anota��es retiradas devido o erro de alterar o perfil do im�vel,
	 * pois o sistema estava pegando essas informa��es e salvando no
	 * log como altera��o do im�vel.
	 */
    private Integer id;

    private String descricao;

    private Short indicadorUso;

    private Date ultimaAlteracao;
    
    private Short indicadorGeracaoAutomatica;
    
	private String descricaoComId;
	
	private Short indicadorGerarDadosLeitura;
	
	private Short indicadorBloquearRetificacao;
	
	private Short indicadorGrandeConsumidor; 
	
	private Subcategoria subcategoria;
	
	private Short indicadorInserirManterPerfil;
	
	private Short indicadorBloqueaDadosSocial;
	
	private Short indicadorGeraDebitoSegundaViaConta;	
	
	public final static Integer NORMAL = new Integer(5);

    public final static Integer GRANDE = new Integer(1);

    public final static Integer TARIFA_SOCIAL = new Integer(4);
    
    public final static Integer GRANDE_NO_MES = new Integer(2);
    
    public final static Integer CORPORATIVO = new Integer(3);
    
    public final static Integer GRANDE_TELEMEDIDO = new Integer(7);
    
    public final static Integer VIVA_AGUA = new Integer(6);
    
    public final static Integer CORPORATIVO_TELEMED = new Integer(8);
    
    public final static Integer CADASTRO_PROVISORIO = new Integer(9);
    
    public final static Short SIM = new Short((short)1);
    
    public final static Short NAO = new Short((short)2);    
    
    public ImovelPerfil(String descricao, Short indicadorUso,
            Date ultimaAlteracao) {
        this.descricao = descricao;
        this.indicadorUso = indicadorUso;
        this.ultimaAlteracao = ultimaAlteracao;
    }

    public ImovelPerfil(String descricao, Short indicadorUso,
            Date ultimaAlteracao, Short indicadorGeracaoAutomatica) {
        this.descricao = descricao;
        this.indicadorUso = indicadorUso;
        this.ultimaAlteracao = ultimaAlteracao;
        this.indicadorGeracaoAutomatica = indicadorGeracaoAutomatica;
    }

    public ImovelPerfil() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Short getIndicadorUso() {
        return this.indicadorUso;
    }

    public void setIndicadorUso(Short indicadorUso) {
        this.indicadorUso = indicadorUso;
    }

    public Date getUltimaAlteracao() {
        return this.ultimaAlteracao;
    }

    public void setUltimaAlteracao(Date ultimaAlteracao) {
        this.ultimaAlteracao = ultimaAlteracao;
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }

	public Short getIndicadorGeracaoAutomatica() {
		return indicadorGeracaoAutomatica;
	}

	public void setIndicadorGeracaoAutomatica(Short indicadorGeracaoAutomatica) {
		this.indicadorGeracaoAutomatica = indicadorGeracaoAutomatica;
	}
	public String[] retornaCamposChavePrimaria() {
		String[] retorno = {"id"};
		return retorno;
	}

	public Filtro retornaFiltro() {
		Filtro filtro = new FiltroImovelPerfil();
		filtro.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID,
				this.getId()));		
		return filtro;
	}
	
	/**
	 * @author Pedro Alexandre
	 * @date 19/09/2007
	 *
	 */
	public String getDescricaoComId() {
		
		if(this.getId().compareTo(10) == -1){
			descricaoComId = "0" + getId()+ " - " + getDescricao();
		}else{
			descricaoComId = getId()+ " - " + getDescricao();
		}
		
		return descricaoComId;
	}
	
	@Override
	public String getDescricaoParaRegistroTransacao() {
		return getDescricao();
	}
	
	@Override
	public void initializeLazy() {
		retornaCamposChavePrimaria();
	}

	public Short getIndicadorGerarDadosLeitura() {
		return indicadorGerarDadosLeitura;
	}

	public void setIndicadorGerarDadosLeitura(Short indicadorGerarDadosLeitura) {
		this.indicadorGerarDadosLeitura = indicadorGerarDadosLeitura;
	}

	public Short getIndicadorBloquearRetificacao() {
		return indicadorBloquearRetificacao;
	}

	public void setIndicadorBloquearRetificacao(Short indicadorBloquearRetificacao) {
		this.indicadorBloquearRetificacao = indicadorBloquearRetificacao;
	}

	public Short getIndicadorGrandeConsumidor() {
		return indicadorGrandeConsumidor;
	}

	public void setIndicadorGrandeConsumidor(Short indicadorGrandeConsumidor) {
		this.indicadorGrandeConsumidor = indicadorGrandeConsumidor;
	}

	public Short getIndicadorGeraDebitoSegundaViaConta() {
		return indicadorGeraDebitoSegundaViaConta;
	}

	public void setIndicadorGeraDebitoSegundaViaConta(
			Short indicadorGeraDebitoSegundaViaConta) {
		this.indicadorGeraDebitoSegundaViaConta = indicadorGeraDebitoSegundaViaConta;
	}
	
	@Override
	public String[] retornarAtributosInformacoesOperacaoEfetuada() {
		String []labels = { "descricao"};
		return labels;		
	}
	
	@Override
	public String[] retornarLabelsInformacoesOperacaoEfetuada() {
		String []labels = {"Descricao"};
		return labels;		
	}
	
	public Short getIndicadorBloqueaDadosSocial() {
		return indicadorBloqueaDadosSocial;
	}
	
	public void setIndicadorBloqueaDadosSocial(Short indicadorBloqueaDadosSocial) {
		this.indicadorBloqueaDadosSocial = indicadorBloqueaDadosSocial;
	}
	
	public Short getIndicadorInserirManterPerfil() {
		return indicadorInserirManterPerfil;
	}
	
	public void setIndicadorInserirManterPerfil(Short indicadorInserirManterPerfil) {
		this.indicadorInserirManterPerfil = indicadorInserirManterPerfil;
	}
	
	public Subcategoria getSubcategoria() {
		return subcategoria;
	}
	
	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}
}
