package gcom.util;

import gcom.cadastro.DbVersaoBase;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.sistemaparametro.NacionalFeriado;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.filtro.Filtro;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * < <Descri��o da Interface>>
 * 
 * @author rodrigo
 */
public interface ControladorUtilLocal extends javax.ejb.EJBLocalObject {
	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param classe
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public int registroMaximo(Class classe) throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param classe
	 *            Descri��o do par�metro
	 * @param atributo
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public int valorMaximo(Class classe, String atributo)
			throws ControladorException;

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
	 */
	public int valorMaximo(Class classe, String atributo, String parametro1,
			String parametro2) throws ControladorException;

	/**
	 * Retorna o �nico registro do SistemaParametros.
	 * 
	 * @return Descri��o do retorno
	 */
	public SistemaParametro pesquisarParametrosDoSistema()
			throws ControladorException;

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
	 */
	public Collection limiteMaximoFiltroPesquisa(Filtro filtro,
			String pacoteNomeObjeto, int limite) throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param objeto
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception RemoteException
	 *                Descri��o da exce��o
	 */
	public Object inserir(Object objeto) throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param filtro
	 *            Descri��o do par�metro
	 * @param pacoteNomeObjeto
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception RemoteException
	 *                Descri��o da exce��o
	 */
	public Collection pesquisar(Filtro filtro, String pacoteNomeObjeto)
			throws ControladorException;

	public Collection pesquisar(Collection ids, Filtro filtro,
			String pacoteNomeObjeto) throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param ids
	 *            Descri��o do par�metro
	 * @param pacoteNomeObjeto
	 *            Descri��o do par�metro
	 * @exception RemoteException
	 *                Descri��o da exce��o
	 */
	public void remover(String[] ids, String pacoteNomeObjeto,
			OperacaoEfetuada operacaoEfetuada, Collection<UsuarioAcaoUsuarioHelper> acaoUsuarioHelper)
			throws ControladorException;

	public void remover(Object object) throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param id
	 *            Description of the Parameter
	 * @param pacoteNomeObjeto
	 *            Descri��o do par�metro
	 * @exception RemoteException
	 *                Description of the Exception
	 */
	public void removerUm(int id, String pacoteNomeObjeto,
			OperacaoEfetuada operacaoEfetuada, Collection<UsuarioAcaoUsuarioHelper> acaoUsuarioHelper)
			throws ControladorException;

	/**
	 * Description of the Method
	 * 
	 * @param objeto
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 * @exception RemoteException
	 *                Description of the Exception
	 */
	public Object inserirOuAtualizar(Object objeto) throws ControladorException;

	/**
	 * Atualiza Banco de Dados - Roberta Costa
	 * 
	 * @param objeto
	 *            Descri��o do par�metro
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public void atualizar(Object objeto) throws ControladorException;

	/**
	 * Este m�todo recebe 2 datas e as compara gerando uma exce��o se a data fim
	 * for menor que a data inicio
	 * 
	 * @author Vivianne Sousa
	 * @created 18/03/2006
	 * 
	 * @param inicio
	 *            data inicio
	 * @param fim
	 *            data fim
	 * @param msgErro
	 *            constante do application.properties definida no caso de uso
	 * @throws ControladorException
	 */
	public void validarCampoFinalMaiorIgualCampoInicial(Date inicio, Date fim,
			String msgErro) throws ControladorException;

	/**
	 * Este m�todo recebe 2 inteiros e os compara gerando uma exce��o se o campo
	 * fim for menor que a campo inicio
	 * 
	 * @author Vivianne Sousa
	 * @created 18/03/2006
	 * 
	 * @param inicio
	 *            campo inicio
	 * @param fim
	 *            campo fim
	 * @param msgErro
	 *            constante do application.properties definida no caso de uso
	 * @throws ControladorException
	 */
	public void validarCampoFinalMaiorIgualCampoInicial(Integer inicio,
			Integer fim, String msgErro) throws ControladorException;

	/**
	 * Este m�todo recebe 2 bigDecimal e os compara gerando uma exce��o se o
	 * campo fim for menor que a campo inicio
	 * 
	 * @author Vivianne Sousa
	 * @created 18/03/2006
	 * 
	 * @param inicio
	 *            campo inicio
	 * @param fim
	 *            campo fim
	 * @param msgErro
	 *            constante do application.properties definida no caso de uso
	 * @throws ControladorException
	 */
	public void validarCampoFinalMaiorIgualCampoInicial(BigDecimal inicio,
			BigDecimal fim, String msgErro) throws ControladorException;

	/**
	 * Este m�todo recebe 1 data e compara com a data atual gerando uma exce��o
	 * se a data atual for menor que a data passada por paramentro
	 * 
	 * @author Vivianne Sousa
	 * @created 22/03/2006
	 * 
	 * @param data
	 * @param msgErro
	 *            constante do application.properties definida no caso de uso
	 * @throws ControladorException
	 */
	public void validarDataMenorDataAtual(Date data, String msgErro)
			throws ControladorException;

	/**
	 * Este m�todo recebe 1 anoMes e compara com o anoMesAtual gerando uma
	 * exce��o se o anoMesAtual for menor que o anoMes passado por paramentro
	 * 
	 * @author Vivianne Sousa
	 * @created 22/03/2006
	 * 
	 * @param anoMes
	 * @param msgErro
	 *            constante do application.properties definida no caso de uso
	 * @throws ControladorException
	 */
	public void validarAnoMesMenorAnoMesAtual(Integer anoMes, String msgErro)
			throws ControladorException;

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
	 * @throws ControladorException
	 *             Exce��o do controlador
	 */
	public Collection pesquisar(Filtro filtro, int pageOffset,
			String pacoteNomeObjeto) throws ControladorException;

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
	 * @throws ControladorException
	 *             Exce��o do controlador
	 */
	public int totalRegistrosPesquisa(Filtro filtro, String pacoteNomeObjeto)
			throws ControladorException;
	
	/**
	 * Verificar refer�ncia final menor que refer�ncia inicial
	 * 
	 * @author Rafael Santos
	 * @throws ControladorException
	 * @date 10/05/2006
	 */
	public void validarAnoMesInicialFinalPeriodo(
			String anoMesReferenciaInicial, String anoMesReferenciaFinal,
			String descricaoCampoAnoMesReferenciaInicial,
			String descricaoAnoMesReferenciaFinal,
			String mensagemErroDoApplicationProperties)
			throws ControladorException; 

	/**
	 * Verificar data final menos que data inicial
	 * @author Rafael Santos
	 * @throws ControladorException
	 * @date 10/05/2006
	 */
	public void verificarDataInicialFinalPeriodo(
			String dataPeriodoInicial, String dataPeriodoFinal,
			String descricaoCampoDataReferenciaInicial,
			String descricaoDataReferenciaFinal,
			String mensagemErroDoApplicationProperties)
			throws ControladorException; 	
	
	/**
	 * M�todo que insere uma Lista em Batch
	 *
	 * inserirBatch
	 *
	 * @author Roberta Costa
	 * @date 17/05/2006
	 *
	 * @param list
	 * @throws ControladorException
	 */
	public void inserirBatch(List list) throws ControladorException;
	
	/**
	 * FAVOR N�O USAR!!!
	 * M�todo para ser utilizada apenas em logradouro (atualizarr
	 * 
	 * @author Tiago Moreno  
	 * @date 08/08/2006
	 * @param id
	 *            Description of the Parameter
	 * @param pacoteNomeObjeto
	 *            Descri��o do par�metro
	 * @throws ControladorException
	 */

	
	public void verificaObjetoRemocao(int id, String pacoteNomeObjeto,
			OperacaoEfetuada operacaoEfetuada, Collection<UsuarioAcaoUsuarioHelper> acaoUsuarioHelper)
			throws ControladorException;
	
	
	/**
	 * M�todo que consulta uma cole��o por filtro e valida 
	 * se encontrou registros.
	 * 
	 * [FS0001] - Verificar exist�ncia de dados
	 * 
	 * @param filtro 			Filtro
	 * @param pacoteNomeObjeto 	pacoteNomeObjeto
	 * @param nomeTabela 		nomeTabela
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisar(Filtro filtro, String pacoteNomeObjeto, String nomeTabela) throws ControladorException;

	/**
	 * Retorna todos os feriados nacionais do sistema
	 *
	 * @author Pedro Alexandre
	 * @date 13/09/2006
	 *
	 * @return
	 * @throws ControladorException
	 */
	public Collection<NacionalFeriado> pesquisarFeriadosNacionais() throws ControladorException ;
	
	
	/**
	 * Atualiza a numera��o do �ltimo RA cadastrado manualmente 
	 * 
	 * [UC0494] Gerar Numera��o de RA Manual
	 * 
	 * @author Raphael Rossiter
	 * @date 06/11/2006
	 * 
	 * @param idSistemaParametro, ultimoRAManual
	 * @return void
	 */
	public void atualizarSistemaParametro(SistemaParametro sistemaParametro) throws ControladorException ;
	
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
	
	public DbVersaoBase pesquisarDbVersaoBase()
			throws ControladorException;
	
	
	/**
	 * 
	 * Divide a cole��o em duas partes e cria um map onde vai ter as 2 partes.�
	 * criado outro map que guarda a ordem de como ser� chamada a o map das 2
	 * partes. Ex.:Map<1,Map<objeto1,objeto2>>, onde 1 � a ordem que ser�
	 * chamado o segundo map<objeto1,objeto2> e o objeto1 � primeiro objeto da
	 * cole��o da primeira parte e o objeto2 � o primeiro objeto da segunda
	 * parte da cole��o
	 * 
	 * @author S�vio Luiz
	 * @date 22/01/2007
	 * 
	 * @return
	 * @throws ControladorException
	 */
	public Map<Integer, Map<Object, Object>> dividirColecao(Collection colecao);
	
	/**
	 * 
	 *Metodo de cria��o, e envio de email
	 *
	 * @author Savio passado pra o Util por Fl�vio
	 * @date 12/03/2007
	 *
	 * @param arquivo
	 * @param emailReceptor
	 * @param emailRemetente
	 * @param nomeRemetente
	 * @param tituloMensagem
	 * @param corpoMensagem
	 * @throws ControladorException
	 */
	public void mandaArquivoLeituraEmail(String nomeArquivo,StringBuilder arquivo,
			String emailReceptor, String emailRemetente, 
			String tituloMensagem, String corpoMensagem)
			throws ControladorException ;
	
	
	/**
	 * [UC0747] - Calcular Diferen�a de dias �teis entre duas datas
	 *
	 * @author Raphael Rossiter
	 * @date 12/02/2008
	 *
	 * @param dataInicio
	 * @param dataFim
	 * @param municipio
	 * @throws ControladorException
	 */
	public Integer calcularDiferencaDiasUteisEntreDuasDatas(Date dataInicio, Date dataFim, Municipio municipio)
			throws ControladorException ;
	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param filtro
	 *            Descri��o do par�metro
	 * @param pacoteNomeObjeto
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @throws ControladorException
	 */
	public Collection pesquisarGerencial(Filtro filtro, String pacoteNomeObjeto)
			throws ControladorException;
}
