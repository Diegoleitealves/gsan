package gcom.relatorio.micromedicao;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.hidrometro.FiltroHidrometroLocalArmazenagem;
import gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem;
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

public class RelatorioManterLocalArmazenagemHidrometro extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterLocalArmazenagemHidrometro(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_LOCAL_ARMAZENAGEM_HIDROMETRO_MANTER);
	}
	
	@Deprecated
	public RelatorioManterLocalArmazenagemHidrometro() {
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

		FiltroHidrometroLocalArmazenagem filtroHidrometroLocalArmazenagem = (FiltroHidrometroLocalArmazenagem) getParametro("filtroHidrometroLocalArmazenagem");
		HidrometroLocalArmazenagem hidrometroLocalArmazenagemParametros = (HidrometroLocalArmazenagem) getParametro("hidrometroLocalArmazenagemParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		
		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterLocalArmazenagemHidrometroBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoHidrometroLocalArmazenagem = fachada.pesquisar(filtroHidrometroLocalArmazenagem,
				HidrometroLocalArmazenagem.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoHidrometroLocalArmazenagem != null && !colecaoHidrometroLocalArmazenagem.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator hidrometroLocalArmazenagemIterator = colecaoHidrometroLocalArmazenagem.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (hidrometroLocalArmazenagemIterator.hasNext()) {

				HidrometroLocalArmazenagem hidrometroLocalArmazenagem = (HidrometroLocalArmazenagem) hidrometroLocalArmazenagemIterator.next();

				String indicadorOficina = "";
				
				if(hidrometroLocalArmazenagem.getIndicadorOficina().equals(ConstantesSistema.SIM)){
					indicadorOficina = "SIM";
				} else {
					indicadorOficina = "N�O";
				}
				
				String indicadorUso = "";
				
				if(hidrometroLocalArmazenagem.getIndicadorUso().equals(ConstantesSistema.SIM)){
					indicadorUso = "SIM";
				} else {
					indicadorUso = "N�O";
				}
				
				relatorioBean = new RelatorioManterLocalArmazenagemHidrometroBean(
						// ID
						hidrometroLocalArmazenagem.getId().toString(), 
						
						// Descri��o
						hidrometroLocalArmazenagem.getDescricao(), 
						
						//Descri��o Abreviada
						hidrometroLocalArmazenagem.getDescricaoAbreviada(),
						
						//Indicador de Uso
						indicadorUso,
						
						// Indicador Oficina
						indicadorOficina);
						
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
				hidrometroLocalArmazenagemParametros.getId() == null ? "" : ""
						+ hidrometroLocalArmazenagemParametros.getId());
		
		
		if (hidrometroLocalArmazenagemParametros.getDescricao() != null){
			parametros.put("descricao", hidrometroLocalArmazenagemParametros.getDescricao());
		} else {
			parametros.put("descricao", "");
		}
		
		
		if (hidrometroLocalArmazenagemParametros.getDescricaoAbreviada() != null) {
			parametros.put("descricaoAbreviada", hidrometroLocalArmazenagemParametros.getDescricaoAbreviada());
		} else {
			parametros.put("descricaoAbreviada", "");
		}
		
		
		
		String indicadorOficina= "";

		if (hidrometroLocalArmazenagemParametros.getIndicadorOficina() != null
				&& !hidrometroLocalArmazenagemParametros.getIndicadorOficina().equals("")) {
			if (hidrometroLocalArmazenagemParametros.getIndicadorOficina().equals(new Short("1"))) {
				indicadorOficina = "Sim";
			} else if (hidrometroLocalArmazenagemParametros.getIndicadorOficina().equals(
					new Short("2"))) {
				indicadorOficina = "N�o";
			}
		}

		parametros.put("indicadorOficina", indicadorOficina);

		String indicadorUso = "";

		if (hidrometroLocalArmazenagemParametros.getIndicadorUso() != null
				&& !hidrometroLocalArmazenagemParametros.getIndicadorUso().equals("")) {
			if (hidrometroLocalArmazenagemParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (hidrometroLocalArmazenagemParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);
		
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_LOCAL_ARMAZENAGEM_HIDROMETRO_MANTER, parametros,
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
		AgendadorTarefas.agendarTarefa("RelatorioManterLocalArmazenagemHidrometro", this);
	}

}
