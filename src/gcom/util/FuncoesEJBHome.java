package gcom.util;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * < <Descri��o da Interface>>
 * 
 * @author Administrador
 */
public interface FuncoesEJBHome extends EJBHome {
    /**
     * < <Descri��o do m�todo>>
     * 
     * @return Descri��o do retorno
     * @exception RemoteException
     *                Descri��o da exce��o
     * @exception CreateException
     *                Descri��o da exce��o
     */
    public FuncoesEJBRemote create() throws RemoteException, CreateException;
}
