package gcom.financeiro.lancamento;

import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * 
 * Title: GCOM
 * 
 * Description: Reposit�rio de Item de Lan�amento Cont�bil
 * 
 * Copyright: Copyright (c) 2005
 * 
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * 
 * @author Pedro Alexandre
 * @created 23 de Janeiro de 2006
 * @version 1.0
 */
public class RepositorioLancamentoItemContabilHBM implements IRepositorioLancamentoItemContabil{

	//cria uma vari�vel da interface do reposit�rio de lan�amento item cont�bil
	private static IRepositorioLancamentoItemContabil instancia;

	// construtor da classe
	private RepositorioLancamentoItemContabilHBM() {
	}

	// retorna uma inst�ncia do reposit�rio
	public static IRepositorioLancamentoItemContabil getInstancia() {
		// se n�o existe ainda a inst�ncia
		if (instancia == null) {
			// cria a inst�ncia do reposit�rio
			instancia = new RepositorioLancamentoItemContabilHBM();
		}
		// retorna a inst�ncia do reposit�rio
		return instancia;
	}

	/**
	 * Pesquisa uma cole��o de lan�amento de item cont�bil
	 * 	 
	 * @return Cole��o de Lan�amentos de Item Cont�bil 
	 * @exception ErroRepositorioException  Erro no hibernate
	 */
	public Collection<LancamentoItemContabil> pesquisarLancamentoItemContabil() throws ErroRepositorioException {
		// cria a vari�vel que vai armazenar a cole��o pesquisada
		Collection retorno = null;

		// cria a sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		try {
			// cria o HQL para consulta
			String consulta = "select lancamentoItemContabil "
					+ "from LancamentoItemContabil lancamentoItemContabil ";

			// pesquisa a cole��o de acordo com o par�metro informado
			retorno = session.createQuery(consulta).list();

			// erro no hibernate
		} catch (HibernateException e) {
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		} finally {
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}
		// retorna a cole��o pesquisada
		return retorno;
	}
}
