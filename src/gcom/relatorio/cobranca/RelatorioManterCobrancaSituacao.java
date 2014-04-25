package gcom.relatorio.cobranca;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaSituacao;
import gcom.cobranca.FiltroCobrancaSituacao;
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

public class RelatorioManterCobrancaSituacao extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterCobrancaSituacao(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_SITUACAO_COBRANCA_MANTER);
	}
	
	@Deprecated
	public RelatorioManterCobrancaSituacao() {
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

		FiltroCobrancaSituacao filtroCobrancaSituacao = (FiltroCobrancaSituacao) getParametro("filtroCobrancaSituacao");
		CobrancaSituacao cobrancaSituacaoParametros = (CobrancaSituacao) getParametro("cobrancaSituacaoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		filtroCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade("contaMotivoRevisao");
		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterCobrancaSituacaoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoCobrancaSituacao = fachada.pesquisar(filtroCobrancaSituacao,
				CobrancaSituacao.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoCobrancaSituacao != null && !colecaoCobrancaSituacao.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator cobrancaSituacaoIterator = colecaoCobrancaSituacao.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (cobrancaSituacaoIterator.hasNext()) {

				CobrancaSituacao cobrancaSituacao = (CobrancaSituacao) cobrancaSituacaoIterator.next();

				
				String indicadorUso = "";
				
				if(cobrancaSituacao.getIndicadorUso().equals(ConstantesSistema.SIM)){
					indicadorUso = "ATIVO";
				} else {
					indicadorUso = "INATIVO";
				}
				String indicadorExigenciaAdvogado = "";
				
				if (cobrancaSituacao.getIndicadorExigenciaAdvogado() != null){
					if(cobrancaSituacao.getIndicadorExigenciaAdvogado().equals(ConstantesSistema.SIM)){
					indicadorExigenciaAdvogado = "SIM";
				} else {
					indicadorExigenciaAdvogado = "N�O";
				}
				
				}
				String indicadorBloqueioParcelamento = "";
				if (cobrancaSituacao.getIndicadorExigenciaAdvogado() != null){
				if(cobrancaSituacao.getIndicadorBloqueioParcelamento().equals(ConstantesSistema.SIM)){
					indicadorBloqueioParcelamento = "SIM";
				} else {
					indicadorBloqueioParcelamento = "N�O";
				}
				}
				
				String contaMotivoRevisao = "";
				
				if (cobrancaSituacao.getContaMotivoRevisao() != null) {
					contaMotivoRevisao = cobrancaSituacao.getContaMotivoRevisao().getDescricaoMotivoRevisaoConta();
				}
				
				
				relatorioBean = new RelatorioManterCobrancaSituacaoBean(
						// CODIGO
						cobrancaSituacao.getId().toString(), 
						
						// Descri��o
						cobrancaSituacao.getDescricao(), 
						
						//Motivo de revisao da conta
						contaMotivoRevisao,
						
						//motivo da situacao especial de faturamento
						indicadorExigenciaAdvogado,
						
						//Bloqueio de Parcelamento
						indicadorBloqueioParcelamento,
						
						//Indicador de Uso
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

		parametros.put("id",
				cobrancaSituacaoParametros.getId() == null ? "" : ""
						+ cobrancaSituacaoParametros.getId());
		
		
		if (cobrancaSituacaoParametros.getDescricao() != null){
			parametros.put("descricao", cobrancaSituacaoParametros.getDescricao());
		} else {
			parametros.put("descricao", "");
		}
		
		if (cobrancaSituacaoParametros.getContaMotivoRevisao() != null) {
			parametros.put("contaMotivoRevisao", cobrancaSituacaoParametros.getContaMotivoRevisao().getDescricaoMotivoRevisaoConta());
		} else {
			parametros.put("contaMotivoRevisao", "");	
		}
		
		
		String indicadorExigenciaAdvogado = "";

		if (cobrancaSituacaoParametros.getIndicadorExigenciaAdvogado() != null && !cobrancaSituacaoParametros.getIndicadorExigenciaAdvogado().equals("")) {
			if (cobrancaSituacaoParametros.getIndicadorExigenciaAdvogado().equals(new Short("1"))) {
				indicadorExigenciaAdvogado = "Sim";
			} else if (cobrancaSituacaoParametros.getIndicadorExigenciaAdvogado().equals(new Short("2"))) {
				indicadorExigenciaAdvogado = "N�o";
			}
		}

		parametros.put("indicadorExigenciaAdvogado", indicadorExigenciaAdvogado);
		
		String indicadorBloqueioParcelamento = "";

		if (cobrancaSituacaoParametros.getIndicadorBloqueioParcelamento() != null && !cobrancaSituacaoParametros.getIndicadorBloqueioParcelamento().equals("")) {
			if (cobrancaSituacaoParametros.getIndicadorBloqueioParcelamento().equals(new Short("1"))) {
				indicadorBloqueioParcelamento = "Sim";
			} else if (cobrancaSituacaoParametros.getIndicadorBloqueioParcelamento().equals(new Short("2"))) {
				indicadorBloqueioParcelamento = "N�o";
			}
		}

		parametros.put("indicadorBloqueioParcelamento", indicadorBloqueioParcelamento);
		
		String indicadorUso = "";

		if (cobrancaSituacaoParametros.getIndicadorUso() != null && !cobrancaSituacaoParametros.getIndicadorUso().equals("")) {
			if (cobrancaSituacaoParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (cobrancaSituacaoParametros.getIndicadorUso().equals(new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);
		
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_SITUACAO_COBRANCA_MANTER, parametros,
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
		AgendadorTarefas.agendarTarefa("RelatorioManterCobrancaSituacao", this);
	}

}
