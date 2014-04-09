package gcom.gerencial.cadastro.imovel;

import javax.ejb.CreateException;


/**
 * Descri��o da classe 
 *
 * @author Pedro Alexandre
 * @date 27/04/2007
 */
public interface ControladorGerencialImovelLocalHome extends javax.ejb.EJBLocalHome {
    
    /**
     * <Breve descri��o sobre o caso de uso>
     *
     * <Identificador e nome do caso de uso>
     *
     * @author Pedro Alexandre
     * @date 27/04/2007
     *
     * @return
     * @throws CreateException
     */
    public ControladorGerencialImovelLocal create() throws CreateException;

}
