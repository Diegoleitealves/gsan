package gcom.util;

/**
 * Representa uma exce��o relativa a localiza��o / instanciamento de um servi�o
 * 
 * @author Administrador
 */
public class ServiceLocatorException extends Exception {
	private static final long serialVersionUID = 1L;
    /**
     * Construtor da classe ServiceLocatorException
     */
    public ServiceLocatorException() {
    }

    /**
     * Construtor da classe ServiceLocatorException
     * 
     * @param msg
     *            Menssagem da exce��o
     */
    public ServiceLocatorException(String msg) {
        super(msg);
    }

    /**
     * Construtor da classe ServiceLocatorException
     * 
     * @param e
     *            Exce��o que ser� encapsulada
     */
    public ServiceLocatorException(Exception e) {
        super(e);
    }

    /**
     * Construtor da classe ServiceLocatorException
     * 
     * @param e
     *            Exce��o que ser� encapsulada
     * @param msg
     *            Menssagem da exce��o
     */
    public ServiceLocatorException(Exception e, String msg) {
        super(msg, e);
    }
}
