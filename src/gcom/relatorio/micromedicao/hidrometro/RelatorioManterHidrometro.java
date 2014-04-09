package gcom.relatorio.micromedicao.hidrometro;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.FiltrarHidrometroHelper;
import gcom.micromedicao.hidrometro.FiltroHidrometro;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 
 * Title: GCOM
 * </p>
 * <p>
 * 
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * 
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * 
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Rafael Corr�a
 * @created 23 de Setembro de 2005
 * @version 1.0
 */

public class RelatorioManterHidrometro extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor for the RelatorioManterHidrometro object
	 */
	public RelatorioManterHidrometro(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_HIDROMETRO_MANTER);
	}
	
	@Deprecated
	public RelatorioManterHidrometro() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param hidrometros
	 *            Description of the Parameter
	 * @param HidrometroParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException {
		
		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroHidrometro filtroHidrometro = (FiltroHidrometro) getParametro("filtroHidrometro");
		Hidrometro hidrometroParametros = (Hidrometro) getParametro("hidrometroParametros");
		String fixo = (String) getParametro("fixo");
		String faixaInicial = (String) getParametro("faixaInicial");
		String faixaFinal = (String) getParametro("faixaFinal");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		FiltrarHidrometroHelper helper = (FiltrarHidrometroHelper) getParametro("helper");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterHidrometroBean relatorioBean = null;

		Collection hidrometrosNovos = null;

		if (filtroHidrometro != null) {

			filtroHidrometro
					.adicionarCaminhoParaCarregamentoEntidade("hidrometroClasseMetrologica");
			filtroHidrometro
					.adicionarCaminhoParaCarregamentoEntidade("hidrometroMarca");
			filtroHidrometro
					.adicionarCaminhoParaCarregamentoEntidade("hidrometroDiametro");
			filtroHidrometro
					.adicionarCaminhoParaCarregamentoEntidade("hidrometroCapacidade");
			filtroHidrometro
					.adicionarCaminhoParaCarregamentoEntidade("hidrometroTipo");
			filtroHidrometro
			.adicionarCaminhoParaCarregamentoEntidade("hidrometroSituacao");
			
			filtroHidrometro.setConsultaSemLimites(true);

			// consulta para trazer objeto completo
			hidrometrosNovos = fachada.pesquisar(filtroHidrometro,
					Hidrometro.class.getName());
			
			helper = null;
			
		} else if(helper != null){
			hidrometrosNovos = fachada.pesquisarNumeroHidrometroSituacaoInstaladoRelatorio(helper);
			
		}else {
			hidrometrosNovos = fachada.pesquisarNumeroHidrometroFaixaRelatorio(fixo, faixaInicial, faixaFinal);
		}

		if (hidrometrosNovos != null && !hidrometrosNovos.isEmpty()) {
			// coloca a cole��o de par�metros da analise no iterator
			Iterator hidrometroNovoIterator = hidrometrosNovos.iterator();

			SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");

			// la�o para criar a cole��o de par�metros da analise
			while (hidrometroNovoIterator.hasNext()) {

				Hidrometro hidrometroNovo = (Hidrometro) hidrometroNovoIterator
						.next();
				
				String situacao = hidrometroNovo.getHidrometroSituacao() == null ? ""
						: hidrometroNovo.getHidrometroSituacao()
						.getDescricao();
				
				String matricula = "";
				
				String dataInstalacao = "";
				//alterado por R�mulo Aur�lio CRC 1671 Analista:Rosana Carvalho
				// Caso a situa�ao do hidrometro seja INSTALADO
				//colocar a matr�cula do im�vel junto com a Data de Intalacao do hidrometro
				if(hidrometroNovo.getHidrometroSituacao().getId().equals(Hidrometro.SITUACAO_INSTALADO)){
					Integer idImovel = fachada.pesquisarImovelPeloHidrometro(hidrometroNovo.getId());
					Date dataInstalacaoHidrometro = fachada.pesquisarDataInstalacaoHidrometroAgua(idImovel);
					matricula = idImovel.toString() ;
					
					dataInstalacao = Util.formatarData(dataInstalacaoHidrometro);
					
				}
				relatorioBean = new RelatorioManterHidrometroBean(

						// N�mero
						hidrometroNovo.getNumero(),

						// Data de Aquisi��o
						dataFormatada.format(hidrometroNovo.getDataAquisicao()),

						// Ano de Fabrica��o
						hidrometroNovo.getAnoFabricacao().toString(),

						// Finalidade
						hidrometroNovo.getIndicadorMacromedidor() == 1 ? "COMERCIAL"
								: "OPERACIONAL",

						// Classe Metrol�gicao
						hidrometroNovo.getHidrometroClasseMetrologica()
								.getDescricao(),

						// Marca
						hidrometroNovo.getHidrometroMarca().getDescricao(),

						// Di�metro
						hidrometroNovo.getHidrometroDiametro().getDescricao(),

						// Capacidade
						hidrometroNovo.getHidrometroCapacidade().getDescricao(),

						// N�mero de Digitos
						hidrometroNovo.getNumeroDigitosLeitura().toString(),

						// Tipo
						hidrometroNovo.getHidrometroTipo().getDescricao(),
						
						//Situa��o
						situacao,
						//matricula
						matricula,
						//DataInstalacao
						dataInstalacao);
							

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}

		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dataAquisicao = "";
		if (hidrometroParametros.getDataAquisicao() != null
				&& !hidrometroParametros.getDataAquisicao().equals("")) {
			dataAquisicao = format.format(hidrometroParametros
					.getDataAquisicao());
		}

		String numeroQuadra = "";
		
		String descricao = "";
		
		String codigo = "";
		
		if( helper!=null ){
			
			descricao = helper.getIdLocalidade() + "-" + helper.getNomeLocalidade() + "";
			
			
			codigo = helper.getCodigoSetorComercial();
			
			
			numeroQuadra = helper.getNumeroQuadra();
			
			
		}
		parametros.put("codigoSetorComercial", codigo);
		parametros.put("descricaoLocalidade",descricao);
		parametros.put("numeroQuadra", numeroQuadra);
		parametros.put("numero", hidrometroParametros.getNumero());

		parametros.put("dataAquisicao", dataAquisicao);

		parametros.put("anoFabricacao",
				hidrometroParametros.getAnoFabricacao() == null ? "" : ""
						+ hidrometroParametros.getAnoFabricacao());

		parametros.put("classeMetrologica", hidrometroParametros
				.getHidrometroClasseMetrologica() == null ? ""
				: hidrometroParametros.getHidrometroClasseMetrologica()
						.getDescricao());

		parametros.put("marca",
				hidrometroParametros.getHidrometroMarca() == null ? ""
						: hidrometroParametros.getHidrometroMarca()
								.getDescricao());

		parametros.put("diametro",
				hidrometroParametros.getHidrometroDiametro() == null ? ""
						: hidrometroParametros.getHidrometroDiametro()
								.getDescricao());

		parametros.put("capacidade", hidrometroParametros
				.getHidrometroCapacidade() == null ? "" : hidrometroParametros
				.getHidrometroCapacidade().getDescricao());

		parametros.put("tipo",
				hidrometroParametros.getHidrometroTipo() == null ? ""
						: hidrometroParametros.getHidrometroTipo()
								.getDescricao());

		parametros.put("idLocalArmazenagem", hidrometroParametros
				.getHidrometroLocalArmazenagem().getId() == null ? "" : ""
				+ hidrometroParametros.getHidrometroLocalArmazenagem().getId());

		parametros.put("nomeLocalArmazenagem", hidrometroParametros
				.getHidrometroLocalArmazenagem().getDescricao());

		parametros.put("fixo", fixo);

		parametros.put("faixaInicial", faixaInicial);

		parametros.put("faixaFinal", faixaFinal);

		String finalidade = "";

		if (hidrometroParametros.getIndicadorMacromedidor() != null
				&& !hidrometroParametros.getIndicadorMacromedidor().equals("")) {
			if (hidrometroParametros.getIndicadorMacromedidor().equals(
					new Short("1"))) {
				finalidade = "Comercial";
			} else {
				finalidade = "Operacional";
			}
		}

		parametros.put("finalidade", finalidade);

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_HIDROMETRO_MANTER,
				parametros, ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_HIDROMETRO,
					idFuncionalidadeIniciada);
		} catch (ControladorException e) {
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio() {
		int retorno = 0;

		if (getParametro("filtroHidrometro") != null) {

			retorno = Fachada.getInstancia().totalRegistrosPesquisa(
					(FiltroHidrometro) getParametro("filtroHidrometro"),
					Hidrometro.class.getName());

		}else if(getParametro("helper")!= null){
			retorno = Fachada.getInstancia().pesquisarNumeroHidrometroSituacaoInstaladoPaginacaoCount((FiltrarHidrometroHelper) getParametro("helper"));
			
		} 
		else {

			String faixaInicial = (String) getParametro("faixaInicial");
			String faixaFinal = (String) getParametro("faixaFinal");
			String fixo = (String) getParametro("fixo");

			String numeroFormatadoInicial = "";
			String numeroFormatadoFinal = "";

			numeroFormatadoInicial = Util.adicionarZerosEsquedaNumero(6,
					faixaInicial);
			numeroFormatadoFinal = Util.adicionarZerosEsquedaNumero(6,
					faixaFinal);

			Integer totalRegistros = Fachada.getInstancia()
					.pesquisarNumeroHidrometroFaixaCount(fixo,
							fixo + numeroFormatadoInicial,
							fixo + numeroFormatadoFinal);

			retorno = totalRegistros.intValue();
		}

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterHidrometro", this);
	}

}
