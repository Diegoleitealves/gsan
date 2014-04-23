package gcom.relatorio.cadastro;

import gcom.cadastro.imovel.FiltroFonteAbastecimento;
import gcom.cadastro.imovel.FonteAbastecimento;
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

public class RelatorioManterFonteAbastecimento extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterFonteAbastecimento(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_FONTE_ABASTECIMENTO_MANTER);
	}
	
	@Deprecated
	public RelatorioManterFonteAbastecimento() {
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

		FiltroFonteAbastecimento filtroFonteAbastecimento = (FiltroFonteAbastecimento) getParametro("filtroFonteAbastecimento");
		FonteAbastecimento fonteAbastecimentoParametros = (FonteAbastecimento) getParametro("fonteAbastecimentoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterFonteAbastecimentoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoFonteAbastecimento = fachada.pesquisar(filtroFonteAbastecimento,
				FonteAbastecimento.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoFonteAbastecimento != null && !colecaoFonteAbastecimento.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator fonteAbastecimentoIterator = colecaoFonteAbastecimento.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (fonteAbastecimentoIterator.hasNext()) {

				FonteAbastecimento fonteAbastecimento = (FonteAbastecimento) fonteAbastecimentoIterator.next();

				
				String indicadorCalcularVolumeFixo = "";
				
				if(fonteAbastecimento.getIndicadorCalcularVolumeFixo().equals(ConstantesSistema.SIM)){
					indicadorCalcularVolumeFixo = "SIM";
				} else {
					indicadorCalcularVolumeFixo = "N�O";
				}
				
				String indicadorUso = "";
				
				if(fonteAbastecimento.getIndicadorUso().equals(ConstantesSistema.SIM)){
					indicadorUso = "ATIVO";
				} else {
					indicadorUso = "INATIVO";
				}
				
				relatorioBean = new RelatorioManterFonteAbastecimentoBean(
						// CODIGO
						fonteAbastecimento.getId().toString(), 
						
						// Descri��o
						fonteAbastecimento.getDescricao(), 
						
						//Descri��o Abreviada
						fonteAbastecimento.getDescricaoAbreviada(),
						
						//Indicador de Uso
						indicadorUso,
						
						//Indicador de Calcular Volume Fixo de Esgoto
						indicadorCalcularVolumeFixo);
						
						
						
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
				fonteAbastecimentoParametros.getId() == null ? "" : ""
						+ fonteAbastecimentoParametros.getId());
		
		
		if (fonteAbastecimentoParametros.getDescricao() != null){
			parametros.put("descricao", fonteAbastecimentoParametros.getDescricao());
		} else {
			parametros.put("descricao", "");
		}
		
		
		if (fonteAbastecimentoParametros.getDescricaoAbreviada() != null) {
			parametros.put("descricaoAbreviada", fonteAbastecimentoParametros.getDescricaoAbreviada());
		} else {
			parametros.put("descricaoAbreviada", "");
		}
		
		
		String indicadorUso = "";

		if (fonteAbastecimentoParametros.getIndicadorUso() != null
				&& !fonteAbastecimentoParametros.getIndicadorUso().equals("")) {
			if (fonteAbastecimentoParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (fonteAbastecimentoParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);
		
		String indicadorCalcularVolumeFixo = "";

		if (fonteAbastecimentoParametros.getIndicadorCalcularVolumeFixo() != null
				&& !fonteAbastecimentoParametros.getIndicadorCalcularVolumeFixo().equals("")) {
			if (fonteAbastecimentoParametros.getIndicadorCalcularVolumeFixo().equals(new Short("1"))) {
				indicadorCalcularVolumeFixo = "Sim";
			} else if (fonteAbastecimentoParametros.getIndicadorCalcularVolumeFixo().equals(
					new Short("2"))) {
				indicadorCalcularVolumeFixo = "N�o";
			}
		}

		parametros.put("indicadorCalcularVolumeFixo", indicadorCalcularVolumeFixo);
		
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_FONTE_ABASTECIMENTO_MANTER, parametros,
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
		AgendadorTarefas.agendarTarefa("RelatorioManterFonteAbastecimento", this);
	}

}
