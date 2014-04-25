package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe respons�vel por criar o relat�rio de bairro manter de �gua
 * 
 * @author Fernanda Paiva
 * @created 11 de Julho de 2005
 */
public class RelatorioFaturasAgrupadas extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioFaturasAgrupadas(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_FATURAS_AGRUPADAS);
	}

	@Deprecated
	public RelatorioFaturasAgrupadas() {
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

	@SuppressWarnings("unchecked")
	public Object executar() throws TarefaException {

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Fachada fachada = Fachada.getInstancia();

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		Integer anoMes = (Integer) getParametro("anoMes");
		Cliente cliente = (Cliente) getParametro("cliente");
		Collection<Integer> idsClientes = (Collection<Integer>) getParametro("idsClientes");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		SistemaParametro sistemaParametro = fachada
				.pesquisarParametrosDoSistema();

		
		Integer qtdFaturasSelecionadas = fachada
		.pesquisarDadosRelatorioFaturasAgrupadasCount(anoMes, cliente, idsClientes);
		
		if(qtdFaturasSelecionadas == null || qtdFaturasSelecionadas.intValue() == 0){
			throw new ActionServletException(
					"atencao.pesquisa.nenhumresultado", null,"");
		}
		
		Collection colecaoFaturasAgrupadasRelatorio = fachada
		.pesquisarDadosRelatorioFaturasAgrupadas(anoMes, cliente,
				idsClientes);
		
		Iterator iColecaoRelatorioFaturasAgrupadas = colecaoFaturasAgrupadasRelatorio.iterator();
		
		Collection colecaoRelatorioFaturasAgrupadas = new ArrayList();
		
		while (iColecaoRelatorioFaturasAgrupadas.hasNext()){
			
			RelatorioFaturasAgrupadasBean faturaItem = (RelatorioFaturasAgrupadasBean) iColecaoRelatorioFaturasAgrupadas.next();
			
			if (faturaItem.getIndicadorContaHist() == 2){
				
				colecaoRelatorioFaturasAgrupadas.add(faturaItem);
				
			} else if (faturaItem.getIndicadorContaHist() == 1){
				
				fachada.atualizarIndicadorContaHistorico(faturaItem.getIdFaturaItem());
			}
			
		}

//		relatorioBeans.addAll(colecaoFaturasAgrupadasRelatorio);
		relatorioBeans.addAll(colecaoRelatorioFaturasAgrupadas);
		
		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		FiltroBairro filtroBairro = new FiltroBairro();
		filtroBairro.adicionarCaminhoParaCarregamentoEntidade("municipio");
		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.ID,
				sistemaParametro.getBairro().getId()));

		Collection colecaoBairros = fachada.pesquisar(filtroBairro,
				Bairro.class.getName());

		Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(colecaoBairros);

		String endereco = formatarEndereco(sistemaParametro, bairro);

		String telefone = "(" + bairro.getMunicipio().getDdd() + ") "
				+ sistemaParametro.getNumeroTelefone();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("nomeAbreviadoEmpresa", sistemaParametro
				.getNomeAbreviadoEmpresa());

		parametros.put("nomeEmpresa", sistemaParametro.getNomeEmpresa());

		parametros.put("enderecoEmpresa", endereco);

		parametros.put("cgc", Util.formatarCnpj(sistemaParametro
				.getCnpjEmpresa()));

		parametros.put("inscricaoEstadual", sistemaParametro
				.getInscricaoEstadual());

		parametros.put("telefoneEmpresa", telefone);

		parametros.put("telefoneContato", sistemaParametro
				.getNumero0800Empresa());

		BigDecimal percentualAliquota = fachada.pesquisarPercentualAliquota();

		parametros.put("percentualRetencao", percentualAliquota);

		String indicadorTotalizador = (String) getParametro("indicadorTotalizador");

		parametros.put("indicadorTotalizador", indicadorTotalizador);

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		if (sistemaParametro.getCodigoEmpresaFebraban().equals(
				SistemaParametro.CODIGO_EMPRESA_FEBRABAN_COSANPA)) {

			retorno = gerarRelatorio(
					ConstantesRelatorios.RELATORIO_FATURAS_AGRUPADAS_COSANPA,
					parametros, ds, tipoFormatoRelatorio);

		} else if (indicadorTotalizador != null
				&& indicadorTotalizador.equalsIgnoreCase("2")) {

			retorno = gerarRelatorio(
					ConstantesRelatorios.RELATORIO_FATURAS_AGRUPADAS_SINTETICO,
					parametros, ds, tipoFormatoRelatorio);
		} else {

			retorno = gerarRelatorio(
					ConstantesRelatorios.RELATORIO_FATURAS_AGRUPADAS,
					parametros, ds, tipoFormatoRelatorio);
		}

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno,
					Relatorio.RELATORIO_FATURAS_AGRUPADAS,
					idFuncionalidadeIniciada);
		} catch (ControladorException e) {
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	private String formatarEndereco(SistemaParametro sistemaParametro,
			Bairro bairro) {
		String retorno = "";

		Fachada fachada = Fachada.getInstancia();

		FiltroLogradouro filtroLogradouro = new FiltroLogradouro();
		filtroLogradouro
				.adicionarCaminhoParaCarregamentoEntidade("logradouroTipo");
		filtroLogradouro
				.adicionarCaminhoParaCarregamentoEntidade("logradouroTitulo");

		filtroLogradouro.adicionarParametro(new ParametroSimples(
				FiltroLogradouro.ID, sistemaParametro.getLogradouro().getId()));

		Collection colecaoLogradouros = fachada.pesquisar(filtroLogradouro,
				Logradouro.class.getName());

		Logradouro logradouro = (Logradouro) Util
				.retonarObjetoDeColecao(colecaoLogradouros);

		retorno = logradouro.getDescricaoFormatada();

		if (sistemaParametro.getNumeroImovel() != null) {
			retorno = retorno + ", " + sistemaParametro.getNumeroImovel();
		}

		retorno = retorno + " - " + bairro.getNome();

		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio() {

		Fachada fachada = Fachada.getInstancia();

		Integer anoMes = (Integer) getParametro("anoMes");
		Cliente cliente = (Cliente) getParametro("cliente");
		Collection<Integer> idsClientes = (Collection<Integer>) getParametro("idsClientes");

		return fachada.pesquisarDadosRelatorioFaturasAgrupadasCount(anoMes,
				cliente, idsClientes);

	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioFaturasAgrupadas", this);
	}

}
