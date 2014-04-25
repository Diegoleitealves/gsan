package gcom.relatorio.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroServicoPerfilTipo;
import gcom.atendimentopublico.ordemservico.ServicoPerfilTipo;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Arthur Carvalho
 * @version 1.0
 */

public class RelatorioManterTipoPerfilServico extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterTipoPerfilServico(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_TIPO_PERFIL_SERVICO_MANTER);
	}
	
	@Deprecated
	public RelatorioManterTipoPerfilServico() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param situacao pagamento
	 *            Description of the Parameter
	 * @param SituacaoPagamentoParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException {

		// ------------------------------------
		//		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroServicoPerfilTipo filtroServicoPerfilTipo = (FiltroServicoPerfilTipo) getParametro("filtroServicoPerfilTipo");
		ServicoPerfilTipo servicoPerfilTipoParametros = (ServicoPerfilTipo) getParametro("servicoPerfilTipoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterTipoPerfilServicoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

//		filtroServicoPerfilTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroServicoPerfilTipo.ID_EQUIPES_COMPONENTES);
		filtroServicoPerfilTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroServicoPerfilTipo.EQUIPAMENTOS_ESPECIAIS);
		
		Collection colecaoServicoPerfilTipo = fachada.pesquisar(filtroServicoPerfilTipo,
				ServicoPerfilTipo.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoServicoPerfilTipo != null && !colecaoServicoPerfilTipo.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator servicoPerfilTipoIterator = colecaoServicoPerfilTipo.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (servicoPerfilTipoIterator.hasNext()) {

				
				
				
				ServicoPerfilTipo servicoPerfilTipo = (ServicoPerfilTipo) servicoPerfilTipoIterator.next();

				String indicadorVeiculoProprio = "";
				
				String indVeiculoProprio = ("" + servicoPerfilTipo.getIndicadorVeiculoProprio());
				
				if(indVeiculoProprio.equals(ConstantesSistema.SIM)){
					indicadorVeiculoProprio = "SIM";
				} else {
					indicadorVeiculoProprio = "N�O";
				}
				
				String indicadorUso = "";
				
				String indUso = ("" + servicoPerfilTipo.getIndicadorUso());
				
				if(indUso .equals(ConstantesSistema.SIM)){
					indicadorUso  = "SIM";
				} else {
					indicadorUso = "N�O";
				}
				
				relatorioBean = new RelatorioManterTipoPerfilServicoBean(
						
						// ID
						servicoPerfilTipo.getId().toString(), 
						
						// Descri��o
						servicoPerfilTipo.getDescricao(), 
						
						//Abreviatura do Perfil de Servi�o
						servicoPerfilTipo.getDescricaoAbreviada(),
						
						//Quantidade Componentes Equipe
						servicoPerfilTipo.getComponentesEquipe().toString(),
						
						//Id equipamentos especiais
						servicoPerfilTipo.getEquipamentosEspeciais() == null ? "" : servicoPerfilTipo.getEquipamentosEspeciais().getId().toString(),
								
						//Equipamentos Especiais
						servicoPerfilTipo.getEquipamentosEspeciais() == null ? "" : servicoPerfilTipo.getEquipamentosEspeciais().getDescricao(),
						// Indicador Veiculo Proprio
						indicadorVeiculoProprio,
						
						indicadorUso);
						
				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada
				.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		//Id Perfil Servico
		parametros.put("id",
				servicoPerfilTipoParametros.getId() == null ? "" : ""
						+ servicoPerfilTipoParametros.getId());
		
		//Descricao Perfil Servico
		parametros.put("descricao", servicoPerfilTipoParametros.getDescricao());
		
		//Descricao Abreviada Perfil Servico
		parametros.put("abrevPerfilServico", servicoPerfilTipoParametros.getDescricaoAbreviada());
		
		//Quantidade de Componentes
		//String qtdComponentes = "";
		if ( servicoPerfilTipoParametros.getComponentesEquipe() != null) {
			
				
				parametros.put("qtdComponentes", "" + servicoPerfilTipoParametros.getComponentesEquipe());
		} else {
				parametros.put("qtdComponentes", "");
		}
		
		
		
		//Indicador Ve�culo Pr�prio
		String indicadorVeiculoProprio= "";
		String indVeiculoProprio = (""+ servicoPerfilTipoParametros.getIndicadorVeiculoProprio() );
		if ( indVeiculoProprio != null
				&& !indVeiculoProprio.equals("")) {
			if (indVeiculoProprio .equals(""+("1"))) {
				indicadorVeiculoProprio = "Sim";
			} else if (indVeiculoProprio .equals(""+("2"))) {
				indicadorVeiculoProprio = "N�o";
			}
		}
		parametros.put("indicadorVeiculoProprio", indicadorVeiculoProprio);

		//Equipamentos especiais
		if (servicoPerfilTipoParametros.getEquipamentosEspeciais() != null) {
			parametros.put("equipamentosEspeciais", servicoPerfilTipoParametros.getEquipamentosEspeciais().getDescricao());
		} else {
			parametros.put("equipamentosEspeciais", "");	
		}
		
		//Indicador de Uso
		String indicadorUso = "";
		String indUso = (""+ servicoPerfilTipoParametros.getIndicadorUso());

		if (  indUso != null
				&& ! indUso.equals("")) {
			if ( indUso.equals(""+("1"))) {
				indicadorUso = "Ativo";
			} else if (indUso.equals(""+("2"))) {
				indicadorUso = "Inativo";
			}
		}
		parametros.put("indicadorUso", indicadorUso);
		
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_TIPO_PERFIL_SERVICO_MANTER, parametros,
				ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
//		try {
//			persistirRelatorioConcluido(retorno, Relatorio.MANTER_LOCALIDADE,
//					idFuncionalidadeIniciada);
//		} catch (ControladorException e) {
//			e.printStackTrace();
//			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
//		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio() {
		int retorno = 0;

//		retorno = Fachada.getInstancia().totalRegistrosPesquisa(
//				(FiltroLocalidade) getParametro("filtroLocalidade"),
//				Localidade.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterTipoPerfilServico", this);
	}

}
