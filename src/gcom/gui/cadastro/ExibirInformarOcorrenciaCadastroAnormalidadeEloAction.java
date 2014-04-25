package gcom.gui.cadastro;

import gcom.cadastro.imovel.CadastroOcorrencia;
import gcom.cadastro.imovel.EloAnormalidade;
import gcom.cadastro.imovel.FiltroCadastroOcorrencia;
import gcom.cadastro.imovel.FiltroEloAnormalidade;
import gcom.cadastro.imovel.FiltroImovelCadastroOcorrencia;
import gcom.cadastro.imovel.FiltroImovelEloAnormalidade;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCadastroOcorrencia;
import gcom.cadastro.imovel.ImovelEloAnormalidade;
import gcom.cadastro.imovel.bean.ImovelDadosGeraisHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Exibe o caso de uso [UC0491] Informar Ocorr�ncia de Cadastro e/ou Anormalidade de Elo
 * 
 * @author Tiago Moreno
 * @date 20/11/2006
 */
public class ExibirInformarOcorrenciaCadastroAnormalidadeEloAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		//Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("exibirInformarOcorrenciaCadastroAnormalidadeElo");

		Fachada fachada = Fachada.getInstancia();
				
		//Mudar Isso Quando Tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
		
		String idDigitadoEnterImovel = null;
		
		Collection ocorrenciaRemovidas = new ArrayList();
		Collection anormalidadesRemovidas = new ArrayList();
		
		//essa rotina recupera os dados da Jsp inicial sem usar ActionForm
		if (httpServletRequest.getParameter("objetoConsulta") != null) {
			try {
				DiskFileUpload upload = new DiskFileUpload();

				//Parse the Request
				List items = upload.parseRequest(httpServletRequest);

				if (items != null) {
					FileItem item = null;

					//Pega uma Lista de Itens do Form
					Iterator iter = items.iterator();
					while (iter.hasNext()) {

						item = (FileItem) iter.next();

						if (item.getFieldName().equals("idImovel")) {
							idDigitadoEnterImovel = item.getString();
						}
					}
				}
			} catch (FileUploadException e) {
				throw new ActionServletException("erro.sistema", e);
			}
		}
		
		//Instaciando o Objeto Imovel
		Imovel imovel = new Imovel();
		if(idDigitadoEnterImovel == null || idDigitadoEnterImovel.trim().equals("")){
			idDigitadoEnterImovel = httpServletRequest.getParameter("idImovel");
		}
		
		if (idDigitadoEnterImovel != null && !idDigitadoEnterImovel.equalsIgnoreCase("")){

			//Recuperando os dados do imovel
			imovel = (Imovel) fachada.pesquisarImovelDigitado(new Integer (idDigitadoEnterImovel));
			
			if (imovel != null && !imovel.equals("")){
				
				//Recuperando a Inscricao do Imovel Formatada
				String matriculaImovel = fachada.pesquisarInscricaoImovel(new Integer (idDigitadoEnterImovel));
				
				//Recuperando o Endereco do imovel Formatado
				String enderecoImovel = fachada.pesquisarEndereco(new Integer (idDigitadoEnterImovel));
				
				//recuperando a situacao de agua do imovel
				String situacaoAgua = imovel.getLigacaoAguaSituacao().getDescricao();
				
				//recuperando a situacao de esgoto do imovel
				String situacaoEsgoto = imovel.getLigacaoEsgotoSituacao().getDescricao();
				
				//Setando no Objeto Helper dos Valores a Serem Recuperados na JSP
				ImovelDadosGeraisHelper imovelDadosGeraisHelper = new ImovelDadosGeraisHelper
					(new Integer (idDigitadoEnterImovel), matriculaImovel, enderecoImovel, situacaoAgua, situacaoEsgoto);
				//Enviando o Objeto Helper Via Request
				httpServletRequest.setAttribute("imovel", imovelDadosGeraisHelper);
				/**
				 * Alterado por Arthur Carvalho
				 * @date 28/12/2009
				 */
				//Fluxo necessario no reload da pesquisa do imovel
				if ( httpServletRequest.getParameter("objetoConsulta") != null 
						&& httpServletRequest.getParameter("objetoConsulta").equals("1") ) {
					//resgatando as ocorrencias de cadastro do imovel digitado
					FiltroImovelCadastroOcorrencia filtroImovelCadastroOcorrencia = new FiltroImovelCadastroOcorrencia();
					filtroImovelCadastroOcorrencia.adicionarParametro(
							new ParametroSimples(FiltroImovelCadastroOcorrencia.IMOVEL_ID, idDigitadoEnterImovel));
					filtroImovelCadastroOcorrencia.setCampoOrderBy(FiltroImovelCadastroOcorrencia.DATA_OCORRENCIA);
					filtroImovelCadastroOcorrencia.adicionarCaminhoParaCarregamentoEntidade("imovel");
					filtroImovelCadastroOcorrencia.adicionarCaminhoParaCarregamentoEntidade("cadastroOcorrencia");
					Collection colecaoImovelCadastroOcorrencia = fachada.pesquisar(
							filtroImovelCadastroOcorrencia, ImovelCadastroOcorrencia.class.getName());
					
					//setando no request
					sessao.setAttribute("cadastroOcorrencia", colecaoImovelCadastroOcorrencia);
					
					//resgatando as anormalidades de elo do imovel digitado
					FiltroImovelEloAnormalidade filtroImovelEloAnormalidade = new FiltroImovelEloAnormalidade();
					filtroImovelEloAnormalidade.adicionarParametro(
							new ParametroSimples(FiltroImovelEloAnormalidade.IMOVEL_ID, idDigitadoEnterImovel));
					filtroImovelEloAnormalidade.setCampoOrderBy(FiltroImovelEloAnormalidade.DATA_ANORMALIDADE);
					filtroImovelEloAnormalidade.adicionarCaminhoParaCarregamentoEntidade("imovel");
					filtroImovelEloAnormalidade.adicionarCaminhoParaCarregamentoEntidade("eloAnormalidade");
					Collection colecaoImovelEloAnormalidade = fachada.pesquisar(
							filtroImovelEloAnormalidade, ImovelEloAnormalidade.class.getName());
					
					//setando no request
					sessao.setAttribute("eloAnormalidade", colecaoImovelEloAnormalidade);
				} else {
					//Fluxo necessario no reload para remo��o da ocorrencia/anormalidade do imovel.
					//Remove Imovel Ocorrencia
					ArrayList colecaoImovelCadastroOcorrencia = (ArrayList) sessao.getAttribute("cadastroOcorrencia");
					
					if ( httpServletRequest.getParameter("acao") != null && 
							httpServletRequest.getParameter("acao").equals("remover") ){
						
						int obj = new Integer(httpServletRequest.getParameter("id")).intValue();
						
						if ( colecaoImovelCadastroOcorrencia.size() >= ( obj - 1) ) {
						
							if ( sessao.getAttribute("imovelCadastroOcorrenciaRemover") != null ) {
								ocorrenciaRemovidas = (Collection) sessao.getAttribute("imovelCadastroOcorrenciaRemover");  
							} 
							ocorrenciaRemovidas.add(colecaoImovelCadastroOcorrencia.get(obj-1));
							
							colecaoImovelCadastroOcorrencia.remove(obj-1);
							sessao.setAttribute("imovelCadastroOcorrenciaRemover", ocorrenciaRemovidas);
						}
				
					}
					//setando no request
					sessao.setAttribute("cadastroOcorrencia", colecaoImovelCadastroOcorrencia);
					
					//Remove Anormalidade Elo
					ArrayList colecaoImovelEloAnormalidade = (ArrayList) sessao.getAttribute("eloAnormalidade");
					
					if (httpServletRequest.getParameter("acao") != null && 
							httpServletRequest.getParameter("acao").equals("removerAnormalidade") ){
						
						int objElo = new Integer(httpServletRequest.getParameter("idElo")).intValue();
						
						if ( colecaoImovelEloAnormalidade.size() >= objElo ) {
						
							if ( sessao.getAttribute("imovelAnormalidadeRemover") != null ) {
								anormalidadesRemovidas = (Collection) sessao.getAttribute("imovelAnormalidadeRemover");  
							} 
							anormalidadesRemovidas.add(colecaoImovelEloAnormalidade.get(objElo-1));
							
							colecaoImovelEloAnormalidade.remove(objElo-1);
							sessao.setAttribute("imovelAnormalidadeRemover", anormalidadesRemovidas);
						}
	                    
					}
					//setando no request
					sessao.setAttribute("eloAnormalidade", colecaoImovelEloAnormalidade);
					
					/**
					 * Fim da Altera��o
					 * @author Arthur Carvalho
					 */
				}
				
			} else{
				httpServletRequest.setAttribute("inexistente", "1");
			}
				
		}
		//recuperando lista de tipos de Ocorrencias de Cadastro
		FiltroCadastroOcorrencia filtroCadastroOcorrencia = new FiltroCadastroOcorrencia();
		Collection colecaoCadastroOcorrencia = fachada.pesquisar(filtroCadastroOcorrencia, CadastroOcorrencia.class.getName());
		
		//recuperando lista de tipos de Anormalidades de Elo
		FiltroEloAnormalidade FiltroEloAnormalidade = new FiltroEloAnormalidade();
		Collection colecaoEloAnormalidade = fachada.pesquisar(FiltroEloAnormalidade, EloAnormalidade.class.getName());
		
		//setando os tipos de Ocorrencias de Cadastro na sessao para ser recuperado na jsp
		sessao.setAttribute("cadastro", colecaoCadastroOcorrencia);
		
		//setando tipos de Anormalidades de Elo na sessao para ser recuperado na jsp
		sessao.setAttribute("anormalidade", colecaoEloAnormalidade);

		return retorno;
	}

}
