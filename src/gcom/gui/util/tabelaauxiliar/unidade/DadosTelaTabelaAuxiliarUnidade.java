package gcom.gui.util.tabelaauxiliar.unidade;

import gcom.atendimentopublico.ordemservico.Material;
import gcom.gui.Funcionalidade;
import gcom.gui.util.tabelaauxiliar.abreviada.DadosTelaTabelaAuxiliarAbreviada;
import gcom.seguranca.acesso.Operacao;
import gcom.util.HibernateUtil;
import gcom.util.SistemaException;
import gcom.util.tabelaauxiliar.TabelaAuxiliar;
import gcom.util.tabelaauxiliar.unidade.TabelaAuxiliarUnidade;

import java.util.HashMap;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 02/08/2006
 */
public class DadosTelaTabelaAuxiliarUnidade extends
		DadosTelaTabelaAuxiliarAbreviada {

	private static HashMap telas = new HashMap();

	private static HashMap configuracaoParametrosTelas = new HashMap();

	/**
	 * Este m�todo busca um map de funcionalidades cadastradas e cria uma
	 * inst�ncia da funcionalidade para ser usada na Tabela Auxiliar
	 * 
	 * @param nome
	 * @return
	 */
	public static DadosTelaTabelaAuxiliarUnidade obterDadosTelaTabelaAuxiliarAbreviada(
			String nome) {
		System.out.println("-----------Adicionando itens");
		// Verifica se a funcionalidade desejada j� foi instanciada e j� est� no
		// cache
		if (!telas.containsKey(nome)) {
			String[] configuracaoTela = (String[]) configuracaoParametrosTelas
					.get(nome);

			try {
				// Cria a inst�ncia do objeto DadosTelaTabelaAuxiliar
				DadosTelaTabelaAuxiliarUnidade dadosTela = new DadosTelaTabelaAuxiliarUnidade(
						configuracaoTela[1], (TabelaAuxiliarUnidade) Class
								.forName(configuracaoTela[0]).newInstance(),
						configuracaoTela[2], nome);
				// Coloca a inst�ncia criada no map que representa o cache com
				// as inst�ncia j� criadas
				telas.put(nome, dadosTela);

				return dadosTela;
			} catch (ClassNotFoundException ex) {
				throw new SistemaException();
			} catch (IllegalAccessException ex) {
				throw new SistemaException();
			} catch (InstantiationException ex) {
				throw new SistemaException();
			}
		} else {
			// Se o a funcionalidade j� estiver no cache, ent�o ela � retornada
			// sem a necessidade de passar pelo m�todo
			return (DadosTelaTabelaAuxiliarUnidade) telas.get(nome);
		}
	}

	static {

		configuracaoParametrosTelas.put("operacao", new String[] {
				Operacao.class.getName(), "Opera��o",
				Funcionalidade.TELA_OPERACAO });

		configuracaoParametrosTelas.put("material", new String[] {
				Material.class.getName(), "Material",
				Funcionalidade.TELA_MATERIAL });

	}

	/**
	 * Construtor da classe DadosTelaTabelaAuxiliarAbreviada
	 * 
	 * @param titulo
	 *            Descri��o do par�metro
	 * @param tabela
	 *            Descri��o do par�metro
	 * @param funcionalidadeTabelaAux
	 *            Descri��o do par�metro
	 */
	protected DadosTelaTabelaAuxiliarUnidade(String titulo,
			TabelaAuxiliar tabela, String funcionalidadeTabelaAux,
			String nomeParametroFuncionalidade) {
		super(titulo, tabela, funcionalidadeTabelaAux,
				nomeParametroFuncionalidade);
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param dados
	 *            Descri��o do par�metro
	 */
	public static void adicionarDadosTela(DadosTelaTabelaAuxiliarAbreviada dados) {
		DadosTelaTabelaAuxiliarAbreviada.adicionarDadosTela(dados);
	}

	/**
	 * Retorna o valor de dadosTela
	 * 
	 * @param nome
	 *            Descri��o do par�metro
	 * @return O valor de dadosTela
	 */
	public static DadosTelaTabelaAuxiliarAbreviada getDadosTela(String nome) {
		return (DadosTelaTabelaAuxiliarAbreviada) DadosTelaTabelaAuxiliarAbreviada.getDadosTela(nome);
	}

	/**
	 * M�todo sobrescrito que retorna o valor de funcionalidadeTabelaAuxManter
	 * 
	 * @return O valor de funcionalidadeTabelaAuxManter
	 */
	public String getFuncionalidadeTabelaAuxManter() {
		return super.getFuncionalidadeTabelaAuxManter().replaceAll(
				Funcionalidade.TABELA_AUXILIAR_MANTER,
				Funcionalidade.TABELA_AUXILIAR_ABREVIADA_MANTER);
	}

	/**
	 * M�todo sobrescrito que retorna o valor de funcionalidadeTabelaAuxManter
	 * 
	 * @return O valor de funcionalidadeTabelaAuxManter
	 */
	public String getFuncionalidadeTabelaAuxInserir() {
		return super.getFuncionalidadeTabelaAuxInserir().replaceAll(
				Funcionalidade.TABELA_AUXILIAR_INSERIR,
				Funcionalidade.TABELA_AUXILIAR_ABREVIADA_INSERIR);
	}

	/**
	 * Retorna o valor de tamanhoMaximoCampo
	 * 
	 * @return O valor de tamanhoMaximoCampo
	 */
	public int getTamanhoMaximoDescricaoAbreviada() {
		return HibernateUtil.getColumnSize(this.getTabelaAuxiliar().getClass(),
				"descricaoAbreviada");
	}

}
