/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * R�mulo Aur�lio de Melo Souza Filho
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */
package gcom.gui.micromedicao;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.ArquivoTextoRoteiroEmpresa;
import gcom.micromedicao.ArquivoTextoRoteiroEmpresaDivisao;
import gcom.micromedicao.FiltroArquivoTextoRoteiroEmpresa;
import gcom.micromedicao.FiltroArquivoTextoRoteiroEmpresaDivisao;
import gcom.micromedicao.SituacaoTransmissaoLeitura;
import gcom.tarefa.TarefaException;
import gcom.util.IoUtil;
import gcom.util.ZipUtil;
import gcom.util.filtro.ParametroSimples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RetornarArquivoZipLeituraAction extends GcomAction {

	/**
	 * Este caso de uso permite Retornar um Arquivo Txt Leitura
	 * 
	 * [UC0629] Retornar Arquivo Txt Leitura
	 * 
	 * 
	 * @author R�mulo Aur�lio
	 * @date 13/10/2008
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = null;

		ConsultarArquivoTextoLeituraActionForm form = (ConsultarArquivoTextoLeituraActionForm) actionForm;

		String[] idsRegistrosRemocao = form.getIdsRegistros();

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoArquivoTextoRoteiroEmpresa = fachada
				.pesquisarArquivosTextoRoteiroEmpresaCompletoParaArquivoZip(idsRegistrosRemocao);

		Iterator colecaoArquivoTextoRoteiroEmpresaIterator = (Iterator) colecaoArquivoTextoRoteiroEmpresa
				.iterator();

		ArquivoTextoRoteiroEmpresa arquivoTextoRoteiroEmpresa = null;

		ZipOutputStream zos = null;

		// filtro para pesquisar os arquivos dividido caso a rota seja dividida.
		FiltroArquivoTextoRoteiroEmpresaDivisao filtroArquivoTextoRoteiroEmpresaDivisao = null;

		if (colecaoArquivoTextoRoteiroEmpresa != null
				&& !colecaoArquivoTextoRoteiroEmpresa.isEmpty()) {

			if (colecaoArquivoTextoRoteiroEmpresaIterator.hasNext()) {
				arquivoTextoRoteiroEmpresa = (ArquivoTextoRoteiroEmpresa) colecaoArquivoTextoRoteiroEmpresaIterator
						.next();
			}

			// Informa��es da rota
			// ----------------------------------------------------------
			String localidade = arquivoTextoRoteiroEmpresa.getLocalidade()
					.getId() + "";
			String setorComercial = arquivoTextoRoteiroEmpresa
					.getCodigoSetorComercial1() + "";
			String codigoRota = arquivoTextoRoteiroEmpresa.getRota()
					.getCodigo() + "";
			// -----------------------------------------------------------------------------/

			String nomeArquivo = "Rol_Grupo";

			String grupo = "";

			// Verifica se o grupo de faturamento est� selecionado e
			// seta o grupo atrav�s do primeiro arquivo pesquisado.
			if (!form.getGrupoFaturamentoID().equals("-1")) {
				grupo = "_"
						+ arquivoTextoRoteiroEmpresa.getFaturamentoGrupo()
								.getId();
			}

			String nomeArquivoZip = nomeArquivo + grupo + ".zip";

			File compactadoTipo = new File(nomeArquivoZip);

			try {
				zos = new ZipOutputStream(new FileOutputStream(compactadoTipo));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

			colecaoArquivoTextoRoteiroEmpresaIterator = (Iterator) colecaoArquivoTextoRoteiroEmpresa
					.iterator();

			try {
				while (colecaoArquivoTextoRoteiroEmpresaIterator.hasNext()) {

					arquivoTextoRoteiroEmpresa = (ArquivoTextoRoteiroEmpresa) colecaoArquivoTextoRoteiroEmpresaIterator
							.next();

					if (arquivoTextoRoteiroEmpresa
							.getSituacaoTransmissaoLeitura().getId()
							.equals(SituacaoTransmissaoLeitura.LIBERADO)
							|| arquivoTextoRoteiroEmpresa
									.getSituacaoTransmissaoLeitura()
									.getId()
									.equals(SituacaoTransmissaoLeitura.EM_CAMPO)) {

						// Id da rota do arquivo principal
						Integer idRota = arquivoTextoRoteiroEmpresa.getRota()
								.getId();
						// Ano e m�s de faturamento da rota
						Integer anoMesFaturamento = arquivoTextoRoteiroEmpresa
								.getAnoMesReferencia();

						// Array contendo situa��o para atualizar o arquivo
						// principal
						// quando a rota for dividida
						Integer[] idsSituacaoTransmissao = new Integer[1];
						idsSituacaoTransmissao[0] = SituacaoTransmissaoLeitura.EM_CAMPO;

						/*
						 * TODO - COSANPA
						 * 
						 * Verifica se a rota � dividida para adicionar ao zip
						 * os arquivos divididos.
						 * 
						 * @author Felipe Santos
						 * 
						 * @date 19/07/2011
						 */
						if (fachada.isRotaDividida(idRota, anoMesFaturamento)) {
							filtroArquivoTextoRoteiroEmpresaDivisao = new FiltroArquivoTextoRoteiroEmpresaDivisao();

							filtroArquivoTextoRoteiroEmpresaDivisao
									.adicionarParametro(new ParametroSimples(
											FiltroArquivoTextoRoteiroEmpresaDivisao.ARQUIVO_TEXTO_ROTEIRO_EMPRESA_ID,
											arquivoTextoRoteiroEmpresa.getId()));

							// Array para ordenar a cole��o por Numero de
							// Sequencia
							// do arquivo e Situacao de Transmissao
							String[] orderby = new String[] {
									FiltroArquivoTextoRoteiroEmpresaDivisao.NUMERO_SEQUENCIA_LEITURA,
									FiltroArquivoTextoRoteiroEmpresaDivisao.SITUACAO_TRANS_LEITURA };

							filtroArquivoTextoRoteiroEmpresaDivisao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroArquivoTextoRoteiroEmpresaDivisao.ARQUIVO_TEXTO_ROTEIRO_EMPRESA);
							filtroArquivoTextoRoteiroEmpresaDivisao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroArquivoTextoRoteiroEmpresa.SITUACAO_TRANS_LEITURA);

							// Seta no filtro o crit�rio para ordena��o da
							// cole��o
							filtroArquivoTextoRoteiroEmpresaDivisao
									.setCampoOrderBy(orderby);

							// Cole��o contendo os arquivos divididos da rota
							Collection colecaoArquivoTextoRoteiroEmpresaDivisao = fachada
									.pesquisar(
											filtroArquivoTextoRoteiroEmpresaDivisao,
											ArquivoTextoRoteiroEmpresaDivisao.class
													.getName());

							Iterator colecaoArquivoTextoRoteiroEmpresaDivisaoIterator = (Iterator) colecaoArquivoTextoRoteiroEmpresaDivisao
									.iterator();

							/*
							 * Para cada arquivo dividido, descomprime o arquivo
							 * e adiciona o n�mnero de sequ�ncia do arquivo em
							 * seu nome. Em seguida, comprime o arquivo
							 * novamente e adiciona-o ao zip.
							 */
							while (colecaoArquivoTextoRoteiroEmpresaDivisaoIterator
									.hasNext()) {
								ArquivoTextoRoteiroEmpresaDivisao arquivoTextoRoteiroEmpresaDivisao = (ArquivoTextoRoteiroEmpresaDivisao) colecaoArquivoTextoRoteiroEmpresaDivisaoIterator
										.next();

								// Arquivo dividido original
								File arquivoOriginal = new File(
										arquivoTextoRoteiroEmpresaDivisao
												.getNomeArquivo());

								FileOutputStream out = new FileOutputStream(
										arquivoOriginal.getAbsolutePath());

								out.write(arquivoTextoRoteiroEmpresaDivisao
										.getArquivoTexto());

								out.close();

								// Descomprime o arquivo dividido original
								File arquivoDescomprimido = ZipUtil
										.descomprimirGzip(arquivoOriginal);

								// Renomeia o arquivo descomprimido com sufixo
								// sendo o n�mero de sequ�ncia do arquivo
								// dividido
								File arquivoDescomprimidoRenomeado = new File(
										arquivoDescomprimido.getAbsolutePath()
												+ "-"
												+ arquivoTextoRoteiroEmpresaDivisao
														.getNumeroSequenciaArquivo());

								arquivoDescomprimido
										.renameTo(arquivoDescomprimidoRenomeado);

								// Comprime o arquivo renomeado
								File arquivoComprimido = ZipUtil
										.comprimirGzip(arquivoDescomprimidoRenomeado);

								// Adiciona no arquivo zip final
								ZipUtil.adicionarArquivo(zos, arquivoComprimido);

								arquivoDescomprimidoRenomeado.delete();
								arquivoComprimido.delete();

								zos.closeEntry();

								// Atualiza situa��o do arquivo dividido para EM
								// CAMPO
								fachada.atualizarArquivoTextoDivisaoEnviado(
										arquivoTextoRoteiroEmpresaDivisao
												.getId(),
										SituacaoTransmissaoLeitura.EM_CAMPO);
							}

							fachada.atualizarArquivoTextoEnviado(
									arquivoTextoRoteiroEmpresa.getId(),
									SituacaoTransmissaoLeitura.EM_CAMPO);

						} else {
							// Arquivo original
							File arquivoOriginal = new File(
									arquivoTextoRoteiroEmpresa.getNomeArquivo());

							FileOutputStream out = new FileOutputStream(
									arquivoOriginal.getAbsolutePath());

							/*
							 * TODO - COSANPA Par�metro do m�todo out.write()
							 * modificado. Adiciona diretamente o array de bytes
							 * -> arquivoTextoRoteiroEmpresa.getArquivoTexto()
							 */
							out.write(arquivoTextoRoteiroEmpresa
									.getArquivoTexto());

							out.close();

							// Adiciona no arquivo zip final
							ZipUtil.adicionarArquivo(zos, arquivoOriginal);

							arquivoOriginal.delete();

							zos.closeEntry();

							// Atualiza situa��o do arquivo para EM CAMPO
							fachada.atualizarArquivoTextoEnviado(
									arquivoTextoRoteiroEmpresa.getId(),
									SituacaoTransmissaoLeitura.EM_CAMPO);
						}
					} else {
						throw new ActionServletException(
								"atencao.arquivo_nao_pode_baixar_rota_invalida",
								localidade, setorComercial, codigoRota);
					}
				}

				zos.flush();

				zos.close();

				httpServletResponse.setContentType("application/zip");
				httpServletResponse.addHeader("Content-Disposition",
						"attachment; filename=" + nomeArquivoZip);

				File arquivoZip = new File(nomeArquivoZip);

				ServletOutputStream sos = httpServletResponse.getOutputStream();

				// Finaliza o arquivo zip final
				sos.write(IoUtil.getBytesFromFile(arquivoZip));

				sos.flush();

				sos.close();

				arquivoZip.delete();

			} catch (IOException e) {
				e.printStackTrace();
				throw new TarefaException("Erro ao gerar o arquivo zip", e);
			} catch (ActionServletException ex2) {
				throw ex2;
			}
		}

		try {
			zos.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new TarefaException("Erro ao fechar o zip do relatorio", e);
		}

		return retorno;
	}

}
