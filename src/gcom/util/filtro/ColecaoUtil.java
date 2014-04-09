package gcom.util.filtro;

import gcom.util.ErroRepositorioException;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Fun��es para facilitar a manipula��o de cole��es no repositorio
 * 
 * @author rodrigo
 */
public class ColecaoUtil {

    /**
     * < <Descri��o do m�todo>>
     * 
     * @param nomeColecoes
     *            Descri��o do par�metro
     * @param colecaoDados
     *            Descri��o do par�metro
     * @return Descri��o do retorno
     * @exception ErroRepositorioException
     *                Descri��o da exce��o
     */
    public static Collection processaColecoesParaCarregamento(
            Collection nomeColecoes, Collection colecaoDados)
            throws ErroRepositorioException {
        Iterator iteratorNomes = nomeColecoes.iterator();

        if (!nomeColecoes.isEmpty()) {

            while (iteratorNomes.hasNext()) {
                String nomeColecao = (String) iteratorNomes.next();
                Iterator iteratorDados = colecaoDados.iterator();

                while (iteratorDados.hasNext()) {
                    Object objetoDado = iteratorDados.next();

                    try {
                        nomeColecao = nomeColecao.substring(0, 1).toUpperCase()
                                + nomeColecao
                                        .substring(1, nomeColecao.length());

                        Collection colecao = ((Collection) objetoDado
                                .getClass()
                                .getMethod("get" + nomeColecao, (Class[])null).invoke(
                                        objetoDado, (Object[])null));

                        Iterator iterator = colecao.iterator();

                        iterator.next();
                    } catch (NoSuchElementException ex) {
                        //Caso a cole��o seja vazia

                        try {
                            objetoDado.getClass().getMethod(
                                    "set" + nomeColecao,
                                    new Class[] { Set.class }).invoke(
                                    objetoDado, (Object[])null);
                        } catch (SecurityException ex2) {
                            throw new ErroRepositorioException("erro.sistema");
                        } catch (NoSuchMethodException ex2) {
                            throw new ErroRepositorioException("erro.sistema");
                        } catch (InvocationTargetException ex2) {
                            throw new ErroRepositorioException("erro.sistema");
                        } catch (IllegalArgumentException ex2) {
                            throw new ErroRepositorioException("erro.sistema");
                        } catch (IllegalAccessException ex2) {
                            throw new ErroRepositorioException("erro.sistema");
                        }
                    } catch (SecurityException ex1) {
                        throw new ErroRepositorioException("erro.sistema");
                    } catch (NoSuchMethodException ex1) {
                        throw new ErroRepositorioException("erro.sistema");
                    } catch (InvocationTargetException ex1) {
                        throw new ErroRepositorioException("erro.sistema");
                    } catch (IllegalArgumentException ex1) {
                        throw new ErroRepositorioException("erro.sistema");
                    } catch (IllegalAccessException ex1) {
                        throw new ErroRepositorioException("erro.sistema");
                    }

                }

            }
        }
        return colecaoDados;
    }

}
