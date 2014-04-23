package gcom.cadastro.imovel;

import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * 
 * Title: GCOM
 * 
 * Description: Reposit�rio de Categoria
 * 
 * Copyright: Copyright (c) 2005
 * 
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * 
 * @author Pedro Alexandre
 * @created 13 de Janeiro de 2006
 * @version 1.0
 */
public class RepositorioCategoriaHBM implements IRepositorioCategoria{

	//cria uma vari�vel da interface do reposit�rio de categoria
	private static IRepositorioCategoria instancia;

	// construtor da classe
	private RepositorioCategoriaHBM() {
	}

	// retorna uma inst�ncia do reposit�rio
	public static IRepositorioCategoria getInstancia() {
		// se n�o existe ainda a inst�ncia
		if (instancia == null) {
			// cria a inst�ncia do reposit�rio
			instancia = new RepositorioCategoriaHBM();
		}
		// retorna a inst�ncia do reposit�rio
		return instancia;
	}

	/**
	 * Pesquisa uma cole��o de categorias
	 * 	 
	 * @return Cole��o de Categorias 
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Collection<Categoria> pesquisarCategoria() throws ErroRepositorioException {
		// cria a vari�vel que vai armazenar a cole��o pesquisada
		Collection retorno = null;

		// cria a sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		try {
			// cria o HQL para consulta
			String consulta = "select categoria "
					+ "from Categoria categoria ";

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
	
	
	/**
	 * 
	 * @return Quantidade de categorias cadastradas no BD
	 * @throws ErroRepositorioException
	 */
	public Object pesquisarObterQuantidadeCategoria()
			throws ErroRepositorioException {

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try {
			consulta = "SELECT count(*) "
					+ "FROM gcom.cadastro.imovel.Categoria as catg ";
			
			retorno = session.createQuery(consulta).uniqueResult();

		} catch (HibernateException e) {
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		} finally {
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}
	
	
	/**
	 * 
	 * Autor: Raphael Rossiter
	 * Data: 18/04/2007
	 * 
	 * @return Quantidade de subcategorias
	 * @throws ErroRepositorioException
	 */
	public Object pesquisarObterQuantidadeSubcategoria()
			throws ErroRepositorioException {

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try {
			consulta = "SELECT count(*) "
					+ "FROM gcom.cadastro.imovel.Subcategoria as scat ";
			
			retorno = session.createQuery(consulta).uniqueResult();

		} catch (HibernateException e) {
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		} finally {
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}
	
	/**
	 * 
	 * Autor: S�vio Luiz
	 * Data: 07/05/2009
	 * 
	 * Pesquisa o Fator de Economia
	 * 
	 * 
	 */
	public Short pesquisarFatorEconomiasCategoria(Integer idCategoria)
			throws ErroRepositorioException {

		Short retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try {
			consulta = "SELECT catg.fatorEconomias "
					+ "FROM Categoria as catg "
					+ "WHERE catg.id = :idCategoria";
			
			retorno = (Short)session.createQuery(consulta)
			          .setInteger("idCategoria",idCategoria) 
			          .uniqueResult();

		} catch (HibernateException e) {
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		} finally {
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

}
