package gcom.cadastro.funcionario;

import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * O reposit�rio faz a comunica��o com a base de dados atrav�s do hibernate. O
 * cliente usa o reposit�rio como fonte de dados.
 * 
 * @author S�vio Luiz
 * @created 25 de Agosto de 2005
 */

public class RepositorioFuncionarioHBM implements IRepositorioFuncionario {
	private static IRepositorioFuncionario instancia;

	/**
	 * Construtor da classe RepositorioEnderecoHBM
	 */
	private RepositorioFuncionarioHBM() {
	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioFuncionario getInstancia() {

		if (instancia == null) {
			instancia = new RepositorioFuncionarioHBM();
		}

		return instancia;
	}

	public Integer verificarExistenciaFuncionario(Integer idFuncionario)
			throws ErroRepositorioException {
		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try {
			consulta = "select count(f.id) " + "from Funcionario f "
					+ "where f.id = :idFuncionario";

			retorno = (Integer) session.createQuery(consulta).setInteger(
					"idFuncionario", idFuncionario.intValue()).setMaxResults(1)
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
