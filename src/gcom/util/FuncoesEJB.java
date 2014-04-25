package gcom.util;

import java.sql.Connection;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

/**
 * < <Descri��o da Classe>>
 * 
 * @author
 */
public class FuncoesEJB implements SessionBean {
	private static final long serialVersionUID = 1L;
    private SessionContext sessionContext;

    /**
     * < <Descri��o do m�todo>>
     */
    public void ejbCreate() {
    }

    /**
     * < <Descri��o do m�todo>>
     */
    public void ejbRemove() {
    }

    /**
     * < <Descri��o do m�todo>>
     */
    public void ejbActivate() {
    }

    /**
     * < <Descri��o do m�todo>>
     */
    public void ejbPassivate() {
    }

    /**
     * Seta o valor de sessionContext
     * 
     * @param sessionContext
     *            O novo valor de sessionContext
     */
    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    /**
     * Retorna uma conex�o
     * 
     * @param contextoJNDI
     *            Uma String representando o contexto jndi
     * @return Uma Conex�o
     */
    public Connection alocarConexao(String contextoJNDI) {

        Connection conexao = null;

        try {
            InitialContext contexto = new InitialContext();
            DataSource dataSource = (DataSource) contexto.lookup(contextoJNDI);

            conexao = dataSource.getConnection();

        } catch (Exception e) {
        	sessionContext.setRollbackOnly();
            e.printStackTrace();
            throw new SistemaException(e, e.getMessage());
        }

        return conexao;
    }

    /**
     * Retorna uma transa��o
     * 
     * @return Uma Transa��o
     */
    public UserTransaction alocarTransacao() {

        UserTransaction ut = null;

        try {
            InitialContext contexto = new InitialContext();

            ut = (UserTransaction) contexto.lookup("java:comp/UserTransaction");

        } catch (Exception e) {
        	sessionContext.setRollbackOnly();
            e.printStackTrace();
            throw new SistemaException(e, e.getMessage());
        }

        return ut;
    }

}
