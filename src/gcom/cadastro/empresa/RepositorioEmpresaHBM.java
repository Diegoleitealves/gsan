package gcom.cadastro.empresa;

import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * < <Descri��o da Classe>>
 * 
 * @author S�vio Luiz
 */
public class RepositorioEmpresaHBM implements IRepositorioEmpresa {

	private static IRepositorioEmpresa instancia;

	/**
	 * Construtor da classe RepositorioFaturamentoHBM
	 */
	private RepositorioEmpresaHBM() {
	}

	/**
	 * Retorna o valor de instance
	 * 
	 * @return O valor de instance
	 */
	public static IRepositorioEmpresa getInstancia() {
		if (instancia == null) {
			instancia = new RepositorioEmpresaHBM();
		}
		return instancia;
	}

	/**
	 * Pesquisa as empresas que ser�o processadas no emitir contas
	 * 
	 * @author S�vio Luiz
	 * @date 09/01/2007
	 * 
	 */
	public Collection pesquisarIdsEmpresa()
	throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try {
			consulta = "select emp.id from Empresa emp order by emp.id";

			retorno = session.createQuery(consulta).list();

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
