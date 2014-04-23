package gcom.relatorio.cadastro.geografico;

import gcom.batch.Relatorio;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe respons�vel por criar o relat�rio de bairro manter de �gua
 * 
 * @author S�vio Luiz
 * @created 11 de Julho de 2005
 */
public class RelatorioManterBairro extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterBairro(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_BAIRRO_MANTER);
	}

	@Deprecated
	public RelatorioManterBairro() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */
	public Object executar() throws TarefaException {

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroBairro filtroBairro = (FiltroBairro) getParametro("filtroBairro");
		Bairro bairroParametros = (Bairro) getParametro("bairroParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterBairroBean relatorioBean = null;

		filtroBairro.adicionarCaminhoParaCarregamentoEntidade("municipio");
		filtroBairro.setConsultaSemLimites(true);

		Collection bairros = fachada.pesquisar(filtroBairro, Bairro.class
				.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (bairros != null && !bairros.isEmpty()) {

			// Organizar a cole��o

			Collections.sort((List) bairros, new Comparator() {
				public int compare(Object a, Object b) {
					String municipio1 = ((Bairro) a).getMunicipio().getNome();
					String municipio2 = ((Bairro) b).getMunicipio().getNome();

					return municipio1.compareTo(municipio2);
				}
			});

			// coloca a cole��o de par�metros da analise no iterator
			Iterator bairroIterator = bairros.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (bairroIterator.hasNext()) {

				Bairro bairro = (Bairro) bairroIterator.next();

				relatorioBean = new RelatorioManterBairroBean(""
						+ bairro.getCodigo(), bairro.getNome(), bairro
						.getMunicipio().getNome(), bairro
						.getCodigoBairroPrefeitura(), ""
						+ bairro.getIndicadorUso());

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();
		
		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		
		parametros.put("idMunicipio", bairroParametros.getMunicipio().getId());

		parametros.put("nomeMunicipio", bairroParametros.getMunicipio()
				.getNome() == null ? "" : bairroParametros.getMunicipio()
				.getNome());

		parametros.put("codigoBairro", bairroParametros.getCodigo() == 0 ? ""
				: "" + bairroParametros.getCodigo());

		parametros.put("nomeBairro", bairroParametros.getNome());
		
		String indicadorUso = "";

		if (bairroParametros.getIndicadorUso() != null
				&& !bairroParametros.getIndicadorUso().equals("")) {
			if (bairroParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (bairroParametros.getIndicadorUso()
					.equals(new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_BAIRRO_MANTER,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_BAIRRO,
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

		retorno = Fachada.getInstancia().totalRegistrosPesquisa(
				(FiltroBairro) getParametro("filtroBairro"),
				Bairro.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterBairro", this);

	}

}
