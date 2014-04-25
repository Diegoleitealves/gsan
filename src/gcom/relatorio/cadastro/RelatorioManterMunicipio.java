package gcom.relatorio.cadastro;

import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Microrregiao;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.geografico.Regiao;
import gcom.cadastro.geografico.RegiaoDesenvolvimento;
import gcom.cadastro.geografico.UnidadeFederacao;
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

public class RelatorioManterMunicipio extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterMunicipio(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MUNICIPIO_MANTER);
	}
	
	@Deprecated
	public RelatorioManterMunicipio() {
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

		FiltroMunicipio filtroMunicipio = (FiltroMunicipio) getParametro("filtroMunicipio");
		Municipio municipioParametros = (Municipio) getParametro("municipioParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		
		RegiaoDesenvolvimento regiaoDesenvolvimentoParametros = (RegiaoDesenvolvimento) getParametro("regiaoDesenvolvimentoParametros");
		
		Regiao regiaoParametros = (Regiao) getParametro("regiaoParametros");
		
		UnidadeFederacao unidadeFederacaoParametros = (UnidadeFederacao) getParametro("unidadeFederacaoParametros");
		
		Microrregiao microrregiaoParametros = (Microrregiao) getParametro("microrregiaoParametros");
		
		filtroMunicipio.adicionarCaminhoParaCarregamentoEntidade("unidadeFederacao");
		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterMunicipioBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoMunicipio = fachada.pesquisar(filtroMunicipio,
				Municipio.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoMunicipio != null && !colecaoMunicipio.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator municipioIterator = colecaoMunicipio.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (municipioIterator.hasNext()) {

				Municipio municipio = (Municipio) municipioIterator.next();

				
				String indicadorUso = "";
				
				if(municipio.getIndicadorUso().equals(ConstantesSistema.SIM)){
					indicadorUso = "ATIVO";
				} else {
					indicadorUso = "INATIVO";
				}
				
				String uf = "";
				
				if (municipio.getUnidadeFederacao().getSigla() != null) {
					
					uf = municipio.getUnidadeFederacao().getSigla();
				}
				
				String cepInicio = "";
				
				if (municipio.getCepInicio() != null) {
					cepInicio = municipio.getCepInicio().toString();
				}
				
				String cepFim = "";
				
				if (municipio.getCepFim() != null) {
					cepFim = municipio.getCepFim().toString();
				}
				
				
				relatorioBean = new RelatorioManterMunicipioBean(
						// CODIGO
						municipio.getId().toString(), 
						
						// Descri��o
						municipio.getNome(), 
						
						//Sigla da unidade federativa
						uf,
						
						//Cep Inicio
						cepInicio,
						
						//Cep Fim
						cepFim,
						
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
				municipioParametros.getId() == null ? "" : ""
						+ municipioParametros.getId());
		
		
		if (municipioParametros.getNome() != null){
			parametros.put("nome", municipioParametros.getNome());
		} else {
			parametros.put("nome", "");
		}
		
		if (unidadeFederacaoParametros.getId() != null) {
			parametros.put("uf", unidadeFederacaoParametros.getSigla());
		} else {
			parametros.put("uf", "");	
		}
		
		if (regiaoDesenvolvimentoParametros.getId() != null) {
			parametros.put("regiaoDesenv", regiaoDesenvolvimentoParametros.getDescricao());
		} else {
			parametros.put("regiaoDesenv", "");	
		}

		if (regiaoParametros.getId() != null) {
			parametros.put("regiao", regiaoParametros.getNome());
		} else {
			parametros.put("regiao", "");	
		}
		
		if (microrregiaoParametros.getId() != null) {
			parametros.put("microrregiao", microrregiaoParametros.getNome());
		} else {
			parametros.put("microrregiao", "");	
		}
		
		String indicadorUso = "";

		if (municipioParametros.getIndicadorUso() != null && !municipioParametros.getIndicadorUso().equals("")) {
			if (municipioParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (municipioParametros.getIndicadorUso().equals(new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);
		
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MUNICIPIO_MANTER, parametros,
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
		AgendadorTarefas.agendarTarefa("RelatorioManterMunicipio", this);
	}

}
