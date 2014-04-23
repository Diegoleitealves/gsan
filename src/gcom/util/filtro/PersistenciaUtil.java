package gcom.util.filtro;

import gcom.util.ErroRepositorioException;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.Hibernate;

/**
 * Fun��es para facilitar a manipula��o de cole��es no repositorio
 * 
 * @author rodrigo
 */
public class PersistenciaUtil {

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
								.getClass().getMethod("get" + nomeColecao,
										(Class[]) null).invoke(objetoDado,
										(Object[]) null));

						Iterator iterator = colecao.iterator();

						iterator.next();
					} catch (NoSuchElementException ex) {
						// Caso a cole��o seja vazia

						try {
							objetoDado.getClass()
									.getMethod("set" + nomeColecao,
											new Class[]{Set.class}).invoke(
											objetoDado, (Object[]) null);
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

	/**
	 * Este m�todo carrega todos os objetos informados no filtro na hierarquia
	 * do objeto consultado no reposit�rio
	 * 
	 * @param nomeObjetos
	 *            A lista de parametros que representa os objetos da hierarquia
	 *            do objeto consultado que devem ser carregados. EX.:
	 *            cep.cepTipo
	 * @param colecaoDados
	 *            O resultado da consulta
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public static void processaObjetosParaCarregamento(Collection nomeObjetos,
			Collection colecaoDados) throws ErroRepositorioException {

		// Verifica se o usuario informou algum objeto para ser carregado
		if (!nomeObjetos.isEmpty()) {
			Iterator iteratorNomes = nomeObjetos.iterator();

			// Percorre cada nome informado
			while (iteratorNomes.hasNext()) {
				String nomeColecao = (String) iteratorNomes.next();

				// monta a chamada do m�todo
				// StringBuffer chamadaGet = new StringBuffer();

				// Para cada item da colecao inicializar os objetos informados
				Iterator iteratorDados = colecaoDados.iterator();

				while (iteratorDados.hasNext()) {
					int contadorIteracao = 0;
					Object objetoDado = iteratorDados.next();
					Object retorno = null;

					// Serve de separador para montar a hierarquia de objetos
					// informados
					StringTokenizer separador = new StringTokenizer(
							nomeColecao, ".");

					while (separador.hasMoreTokens()) {
						// Prepara a chamada
						// Ex.: getCep().getCepTipo()
						StringBuffer token = new StringBuffer(separador
								.nextToken());

						token.insert(0, "get");
						token
								.replace(3, 4, token.substring(3, 4)
										.toUpperCase());
						// token.append(token.substring(1, token.length()));

						try {

							// Inicializa o objeto informado para ser disponivel
							// no objeto consultado
							// Teste para ver se o carregamento do parametro
							// anterior foi nulo, impossibilitando
							// o proximo carregamento na hierarquia
							if (retorno == null && contadorIteracao != 0) {
								break;
							} else {
								objetoDado = ((retorno == null)
										? objetoDado
										: retorno);
								contadorIteracao++;
							}

							retorno = objetoDado.getClass().getMethod(
									token.toString(), (Class[]) null).invoke(
									objetoDado, (Object[]) null);
							Hibernate.initialize(retorno);

						} catch (NoSuchElementException ex) {
							throw new ErroRepositorioException("erro.sistema");
						} catch (SecurityException ex1) {
							throw new ErroRepositorioException("erro.sistema");
						} catch (NoSuchMethodException ex1) {
							System.out.print(ex1.getMessage());
							throw new ErroRepositorioException("erro.sistema");
							// throw new
							// ErroRepositorioException("erro.metodo.nao.econtrado",
							// null, ex1.getMessage() + "," + objetoDado);
						} catch (InvocationTargetException ex1) {
							throw new ErroRepositorioException("erro.sistema");
						} catch (IllegalArgumentException ex1) {
							throw new ErroRepositorioException("erro.sistema");
						} catch (IllegalAccessException ex1) {
							throw new ErroRepositorioException("erro.sistema");
						} catch (Exception e) {
							e.printStackTrace();

						}

					}

				}
			}
		}
		// return colecaoDados;
	}

	public static String processaObjetosParaCarregamentoJoinFetch(
			String aliasTabela, Collection nomeObjetos)
			throws ErroRepositorioException {

		String resultadoJoinsMontados = "";

		// Verifica se o usuario informou algum objeto para ser carregado
		if (!nomeObjetos.isEmpty()) {
			Iterator iteratorNomes = nomeObjetos.iterator();

			// Percorre cada nome informado
			while (iteratorNomes.hasNext()) {

				String nomeColecao = (String) iteratorNomes.next();
				StringTokenizer separador = new StringTokenizer(nomeColecao,
						".");

				String elementoJoin = "";
				while (separador.hasMoreTokens()) {
					if (elementoJoin.equals("")) {
						elementoJoin = elementoJoin + aliasTabela + "."
								+ separador.nextToken();

					} else {
						elementoJoin = elementoJoin + "."
								+ separador.nextToken();
					}

					// O join com o comp_id n�o funciona
					// Elimina o join do comp_id
					if (!elementoJoin.endsWith(".comp_id")) {
						resultadoJoinsMontados = resultadoJoinsMontados
								+ " left join fetch " + elementoJoin;
					}

				}

			}
		}
		return resultadoJoinsMontados;
	}

}
