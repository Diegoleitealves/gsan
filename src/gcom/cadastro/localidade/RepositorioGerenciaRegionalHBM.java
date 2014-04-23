package gcom.cadastro.localidade;

import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;
import gcom.util.Util;

import java.util.Collection;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * 
 * Title: GCOM
 * 
 * Description: Reposit�rio de Ger�ncia Regional
 * 
 * Copyright: Copyright (c) 2005
 * 
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * 
 * @author Pedro Alexandre
 * @created 13 de Janeiro de 2006
 * @version 1.0
 */
public class RepositorioGerenciaRegionalHBM implements
		IRepositorioGerenciaRegional {

	// cria uma vari�vel da inteface do reposit�rio de ger�ncia regional
	private static IRepositorioGerenciaRegional instancia;

	// construtor da classe
	private RepositorioGerenciaRegionalHBM() {
	}

	// retorna uma inst�ncia do reposit�rio
	public static IRepositorioGerenciaRegional getInstancia() {
		// se n�o existe ainda a inst�ncia
		if (instancia == null) {
			// cria a inst�ncia do reposit�rio
			instancia = new RepositorioGerenciaRegionalHBM();
		}
		// retorna a inst�ncia do reposit�rio
		return instancia;
	}

	/**
	 * Pesquisa uma cole��o de ger�ncias regionais
	 * 
	 * @return Cole��o de Ger�ncias Regionais
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Collection<GerenciaRegional> pesquisarGerenciaRegional()
			throws ErroRepositorioException {
		// cria a vari�vel que vai armazenar a cole��o pesquisada
		Collection retorno = null;

		// cria a sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		try {
			// cria o HQL para consulta
			String consulta = "select gerenciaRegional "
					+ "from GerenciaRegional gerenciaRegional ";

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
	 * Pesquisa uma ger�ncia regional pelo id
	 * 
	 * @author Rafael Corr�a
	 * @date 01/08/2006
	 * 
	 * @return Ger�ncia Regional
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Object[] pesquisarObjetoGerenciaRegionalRelatorio(
			Integer idGerenciaRegional) throws ErroRepositorioException {
		// cria a vari�vel que vai armazenar a cole��o pesquisada
				
		Object[] retorno = null;

		// cria a sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		try {
			// cria o HQL para consulta
			String consulta = "select gr.greg_id as id, " +
				"gr.greg_nmabreviado as nomeAbreviado "
					+ "from cadastro.gerencia_regional gr "
					+ "where gr.greg_id = " + idGerenciaRegional.toString();

			// pesquisa a cole��o de acordo com o par�metro informado
			Collection colecaoGerenciasRegionais = session.createSQLQuery(consulta)
			.addScalar("id", Hibernate.INTEGER)
			.addScalar("nomeAbreviado", Hibernate.STRING).list();
			
			retorno = Util.retonarObjetoDeColecaoArray(colecaoGerenciasRegionais);

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
     * Pesquisa o id da ger�ncia regional para a qual a localidade pertence.
     * 
     * [UC0267] Encerrar Arrecada��o do M�s
     * 
     * @author Pedro Alexandre
     * @date 05/01/2007
     * 
     * @param idLocalidade
     * @return
     * @throws ErroRepositorioException
     */
    public Integer pesquisarIdGerenciaParaLocalidade(Integer idLocalidade) throws ErroRepositorioException {

        Integer retorno = null;

        Session session = HibernateUtil.getSession();

        String consulta = "";

        try {

            consulta = "select greg.id from Localidade loca " + "left join loca.gerenciaRegional greg " + "where loca.id = :idLocalidade";

            retorno = (Integer) session.createQuery(consulta).setInteger("idLocalidade", idLocalidade).uniqueResult();

        } catch (HibernateException e) {
            throw new ErroRepositorioException(e, "Erro no Hibernate");
        } finally {
            HibernateUtil.closeSession(session);
        }

        return retorno;
    }

}
