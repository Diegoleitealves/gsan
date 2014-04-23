package gcom.relatorio.cadastro.categoria;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe respons�vel por criar o relat�rio de categoria manter de �gua
 * 
 * @author Rafael Corr�a / R�mulo Aur�lio
 * @created 18 de Julho de 2006
 */
public class RelatorioManterCategoria extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterCategoria(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_CATEGORIA_MANTER);
	}
	
	@Deprecated
	public RelatorioManterCategoria() {
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

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroCategoria filtroCategoria = (FiltroCategoria) getParametro("filtroCategoria");
		Categoria categoriaParametros = (Categoria) getParametro("categoriaParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterCategoriaBean relatorioBean = null;

		filtroCategoria.limparCamposOrderBy();
		filtroCategoria.setCampoOrderBy(FiltroCategoria.CODIGO);

		filtroCategoria.setConsultaSemLimites(true);

		Collection colecaoCategorias = fachada.pesquisar(filtroCategoria,
				Categoria.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoCategorias != null && !colecaoCategorias.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoCategoriasIterator = colecaoCategorias.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoCategoriasIterator.hasNext()) {

				Categoria categoria = (Categoria) colecaoCategoriasIterator
						.next();
				
				
				// Faz as valida��es dos campos necess�riose e formata a String
				// para a forma como ir� aparecer no relat�rio
				
				String consumoMinimo = "";
				
				if (categoria.getConsumoMinimo() != null) {
					consumoMinimo = categoria.getConsumoMinimo().toString();
				}
				
				String consumoAlto = "";
				
				if (categoria.getConsumoAlto() != null) {
					consumoAlto = categoria.getConsumoAlto().toString();
				}
				
				String consumoBaixo = "";
				
				if (categoria.getMediaBaixoConsumo() != null) {
					consumoBaixo = categoria.getMediaBaixoConsumo().toString();
				}
				
				String consumoEstouro = "";
				
				if (categoria.getConsumoEstouro() != null) {
					consumoEstouro = categoria.getConsumoEstouro().toString();
				}
				
				String fatorMultiplicativoAlto = "";
				
				if (categoria.getVezesMediaAltoConsumo() != null) {
					fatorMultiplicativoAlto = Util.formatarMoedaReal(categoria
							.getVezesMediaAltoConsumo());
				}
				
				String fatorMultiplicativoEstouro = "";
				
				if (categoria.getVezesMediaEstouro() != null) {
					fatorMultiplicativoEstouro = Util.formatarMoedaReal(categoria
							.getVezesMediaEstouro());
				}
				
				String percentualBaixoConsumo = "";
				
				if (categoria.getPorcentagemMediaBaixoConsumo() != null) {
					fatorMultiplicativoEstouro = Util.formatarMoedaReal(categoria
							.getPorcentagemMediaBaixoConsumo());
				}

				relatorioBean = new RelatorioManterCategoriaBean(

						// C�digo
						categoria.getId().toString(),

						// Descri��o
						categoria.getDescricao(),

						// Descri��o Abreviada
						categoria.getDescricaoAbreviada(),

						// Consumo M�nimo
						consumoMinimo,

						// Consumo Refer�ncia Alto
						consumoAlto,

						// Consumo Refer�ncia Baixo
						consumoBaixo,

						// Consumo Refer�ncia Estouro
						consumoEstouro,

						// Fator Multiplicativo Alto
						fatorMultiplicativoAlto,

						// Fator Multiplicativo Estouro
						fatorMultiplicativoEstouro,

						// Percentual Baixo Consumo
						percentualBaixoConsumo,

						// Indicador de Uso
						categoria.getIndicadorUso().toString());

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		
		// Imagem do Relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// C�digo
		if (categoriaParametros.getId() != null) {
			parametros.put("codigo", categoriaParametros.getId());
		} else {
			parametros.put("codigo", "");
		}

		// Descri��o
		parametros.put("descricao", categoriaParametros.getDescricao());

		// Indicador Uso
		String indicadorUso = "";

		if (categoriaParametros.getIndicadorUso() != null
				&& !categoriaParametros.getIndicadorUso().equals("")) {
			if (categoriaParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (categoriaParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(
				ConstantesRelatorios.RELATORIO_CATEGORIA_MANTER, parametros,
				ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_CATEGORIA,
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
				(FiltroCategoria) getParametro("filtroCategoria"),
				Categoria.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterCategoria", this);
	}
}
