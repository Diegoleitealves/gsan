package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil;
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

public class RelatorioManterPerfilLigacaoEsgoto extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterPerfilLigacaoEsgoto(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_PERFIL_LIGACAO_ESGOTO_MANTER);
	}
	
	@Deprecated
	public RelatorioManterPerfilLigacaoEsgoto() {
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

		FiltroLigacaoEsgotoPerfil filtroLigacaoEsgotoPerfil = (FiltroLigacaoEsgotoPerfil) getParametro("filtroLigacaoEsgotoPerfil");
		LigacaoEsgotoPerfil ligacaoEsgotoPerfilParametros = (LigacaoEsgotoPerfil) getParametro("ligacaoEsgotoPerfilParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterPerfilLigacaoEsgotoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoLigacaoEsgotoPerfil = fachada.pesquisar(filtroLigacaoEsgotoPerfil,
				LigacaoEsgotoPerfil.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoLigacaoEsgotoPerfil != null && !colecaoLigacaoEsgotoPerfil.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator ligacaoEsgotoPerfilIterator = colecaoLigacaoEsgotoPerfil.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (ligacaoEsgotoPerfilIterator.hasNext()) {

				LigacaoEsgotoPerfil ligacaoEsgotoPerfil = (LigacaoEsgotoPerfil) ligacaoEsgotoPerfilIterator.next();

				String indicadorUso = "";
				
				if(ligacaoEsgotoPerfil.getIndicadorUso().equals(ConstantesSistema.INDICADOR_USO_ATIVO)){
					indicadorUso = "ATIVO";
				} else {
					indicadorUso= "INATIVO";
				}
				
				
				relatorioBean = new RelatorioManterPerfilLigacaoEsgotoBean(
						// ID
						ligacaoEsgotoPerfil.getId().toString(), 
						
						// Descri��o
						ligacaoEsgotoPerfil.getDescricao(), 
						
						//Percentual de Esgoto
						ligacaoEsgotoPerfil.getPercentualEsgotoConsumidaColetada().toString(),
						
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
				ligacaoEsgotoPerfilParametros.getId() == null ? "" : ""
						+ ligacaoEsgotoPerfilParametros.getId());

		parametros.put("descricao", ligacaoEsgotoPerfilParametros.getDescricao());
		
		if (ligacaoEsgotoPerfilParametros.getPercentualEsgotoConsumidaColetada() != null) {
			parametros.put("percentualEsgotoConsumidaColetada", ( ligacaoEsgotoPerfilParametros.getPercentualEsgotoConsumidaColetada().toString()));
		} else {
			parametros.put("percentualEsgotoConsumidaColetada", "");
		}

		String indicadorUso = "";

		if (ligacaoEsgotoPerfilParametros.getIndicadorUso() != null
				&& !ligacaoEsgotoPerfilParametros.getIndicadorUso().equals("")) {
			if (ligacaoEsgotoPerfilParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (ligacaoEsgotoPerfilParametros.getIndicadorUso().equals(new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_PERFIL_LIGACAO_ESGOTO_MANTER, parametros,
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
		AgendadorTarefas.agendarTarefa("RelatorioManterPerfilLigacaoEsgoto", this);
	}

}
