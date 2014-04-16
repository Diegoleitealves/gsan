package gcom.util;

import gcom.cadastro.DbVersaoBase;
import gcom.cadastro.sistemaparametro.NacionalFeriado;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.filtro.Filtro;

import java.util.Collection;
import java.util.List;

/**
 * Interface para o reposit�rio de util
 * 
 * @author rodrigo
 */
public interface IRepositorioUtil {

	/**
	 * Retorna a contagem m�xima de registros de uma determinada classe no
	 * sistema
	 * 
	 * @param classe
	 *            Classe (.class) que ser� feita a contagem
	 * @return N�mero de registros
	 * @exception ErroRepositorioException
	 *                Erro no mecanismo hibernate
	 */

	public int registroMaximo(Class classe) throws ErroRepositorioException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param classe
	 *            Descri��o do par�metro
	 * @param atributo
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public int valorMaximo(Class classe, String atributo)
			throws ErroRepositorioException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param classe
	 *            Descri��o do par�metro
	 * @param atributo
	 *            Descri��o do par�metro
	 * @param parametro1
	 *            Descri��o do par�metro
	 * @param parametro2
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public int valorMaximo(Class classe, String atributo, String parametro1,
			String parametro2) throws ErroRepositorioException;

	/**
	 * Retorna o �nico registro do SistemaParametros.
	 * 
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public SistemaParametro pesquisarParametrosDoSistema()
			throws ErroRepositorioException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param filtro
	 *            Descri��o do par�metro
	 * @param pacoteNomeObjeto
	 *            Descri��o do par�metro
	 * @param limite
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public Collection limiteMaximoFiltroPesquisa(Filtro filtro,
			String pacoteNomeObjeto, int limite)
			throws ErroRepositorioException;

	public Object inserir(Object objeto) throws ErroRepositorioException;

	public void atualizar(Object objeto) throws ErroRepositorioException;

	public void remover(int id, String pacoteNomeObjeto,
			OperacaoEfetuada operacaoEfetuada, Collection<UsuarioAcaoUsuarioHelper> acaoUsuarioHelper)
			throws ErroRepositorioException;

	public void remover(Object objeto) throws ErroRepositorioException;

	public Collection pesquisar(Filtro filtro, String pacoteNomeObjeto)
			throws ErroRepositorioException;

	public Object inserirOuAtualizar(Object objeto)
			throws ErroRepositorioException;

	public Collection pesquisar(Collection ids, Filtro filtro,
			String pacoteNomeObjeto) throws ErroRepositorioException;

	/**
	 * Este m�todo de pesquisa serve para localizar qualquer objeto no sistema.
	 * Ele aceita como par�metro um offset que indica a p�gina desejada no
	 * esquema de pagina��o. A pagina��o procura 10 registros de casa vez.
	 * 
	 * @author Rodrigo Silveira
	 * @date 30/03/2006
	 * 
	 * @param filtro
	 *            Filtro da pesquisa
	 * @param pageOffset
	 *            Indicador da p�gina desejada do esquema de pagina��o
	 * @param pacoteNomeObjeto
	 *            Pacote do objeto
	 * @return Cole��o dos resultados da pesquisa
	 * @throws ErroRepositorioException
	 *             Exce��o do reposit�rio
	 */
	public Collection pesquisar(Filtro filtro, int pageOffset,
			String pacoteNomeObjeto) throws ErroRepositorioException;

	/**
	 * Informa o n�mero total de registros de uma pesquisa, auxiliando o
	 * esquema de pagina��o
	 * 
	 * @author Rodrigo Silveira
	 * @date 30/03/2006
	 * 
	 * @param Filtro
	 *            da Pesquisa
	 * @param Pacote
	 *            do objeto pesquisado
	 * @return N�mero de registros da pesquisa
	 * @throws ErroRepositorioException
	 *             Exce��o do reposit�rio
	 */
	public int totalRegistrosPesquisa(Filtro filtro, String pacoteNomeObjeto)
			throws ErroRepositorioException;

	/**
	 * M�todo que insere uma Lista em Batch
	 *
	 * inserirBatch
	 *
	 * @author Roberta Costa
	 * @date 17/05/2006
	 *
	 * @param list
	 * @throws ErroRepositorioException
	 */
	public void inserirBatch(List list) throws ErroRepositorioException;
	
	/**
	 * Recupera a cole��o de feriados nacionais
	 *
	 * @author Pedro Alexandre 
	 * @date 13/09/2006
	 *
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<NacionalFeriado>  pesquisarFeriadosNacionais() throws ErroRepositorioException ;

	/**
	 * Obtem o pr�ximo valor do sequence do Banco do Imovel ou Cliente
	 * 
	 * @author Rafael Santos
	 * @since 17/11/2006
	 * 
	 * @param objeto
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public Object obterNextVal(Object objeto) throws ErroRepositorioException; 
	
	/**
	 * [UC???] - ????????
	 * 
	 * @author R�mulo Aur�lio Filho
	 * @date 25/01/2007
	 * @descricao O m�todo retorna um objeto com a maior data de Implementacao
	 *            do Banco e sua ultima alteracao
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 */
	
	public DbVersaoBase pesquisarDbVersaoBase() throws ErroRepositorioException ;
	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param filtro
	 *            Descri��o do par�metro
	 * @param pacoteNomeObjeto
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public Collection pesquisarGerencial(Filtro filtro, String pacoteNomeObjeto)
			throws ErroRepositorioException;
}
