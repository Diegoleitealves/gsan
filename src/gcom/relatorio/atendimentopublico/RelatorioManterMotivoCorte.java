package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.ligacaoagua.FiltroMotivoCorte;
import gcom.atendimentopublico.ligacaoagua.MotivoCorte;
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
 * @author Rafael Corr�a
 * @version 1.0
 */

public class RelatorioManterMotivoCorte extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterMotivoCorte(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_MOTIVO_CORTE);
	}
	
	@Deprecated
	public RelatorioManterMotivoCorte() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param localidades
	 *            Description of the Parameter
	 * @param localidadeParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException {

		// ------------------------------------
//		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroMotivoCorte filtroMotivoCorte = (FiltroMotivoCorte) getParametro("filtroMotivoCorte");
		MotivoCorte motivoCorteParametros = (MotivoCorte) getParametro("motivoCorteParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterMotivoCorteBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		filtroMotivoCorte.setConsultaSemLimites(true);

		Collection<MotivoCorte> colecaoMotivosCorte = fachada.pesquisar(filtroMotivoCorte,
				MotivoCorte.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoMotivosCorte != null && !colecaoMotivosCorte.isEmpty()) {

			// la�o para criar a cole��o de par�metros da analise
			for (MotivoCorte motivoCorte : colecaoMotivosCorte) {

				
				String ativoInativo = "";

				if ( motivoCorte.getIndicadorUso().equals( ConstantesSistema.INDICADOR_USO_ATIVO ) ){
				ativoInativo = "Ativo";
				} else {
				ativoInativo = "Inativo";
				}
				
				
				relatorioBean = new RelatorioManterMotivoCorteBean(
						// C�digo
						motivoCorte.getId().toString(), 
						
						// Descri��o
						motivoCorte.getDescricao(), 
						
						// Indicador de Uso
						ativoInativo);

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

		if (motivoCorteParametros.getId() != null) {
			parametros.put("idMotivoCorte",
					motivoCorteParametros.getId().toString());
		} else {
			parametros.put("idMotivoCorte", "");
		}

		parametros.put("descricao", motivoCorteParametros.getDescricao());

		String indicadorUso = "";

		if (motivoCorteParametros.getIndicadorUso() != null
				&& !motivoCorteParametros.getIndicadorUso().equals("")) {
			if (motivoCorteParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (motivoCorteParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MANTER_MOTIVO_CORTE, parametros,
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

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterMotivoCorte", this);
	}

}
