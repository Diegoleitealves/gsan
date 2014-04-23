package gcom.gui.cobranca.parcelamento;

import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.FiltroImovelSituacaoTipo;
import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.ImovelSituacaoTipo;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cobranca.FiltroResolucaoDiretoria;
import gcom.cobranca.ResolucaoDiretoria;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Action que define o pr�-processamento da p�gina de filtrar Perfil de Parcelamento
 * 
 * @author Vivianne Sousa
 * @created 11/05/2006
 */
public class ExibirFiltrarPerfilParcelamentoAction extends GcomAction {
	/**
	 * Este caso de uso permite o filtro de um Perfil de Parcelamento
	 * 
	 * [UC0222] Filtrar Perfil de Parcelamento
	 * 
	 * 
	 * @author Vivianne Sousa
	 * @date 11/05/2006
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


        ActionForward retorno = actionMapping.findForward("filtrarPerfilParcelamento");
        FiltrarPerfilParcelamentoActionForm filtrarPerfilParcelamentoActionForm = 
        		(FiltrarPerfilParcelamentoActionForm) actionForm;
        Fachada fachada = Fachada.getInstancia();
        HttpSession sessao = httpServletRequest.getSession(false);

        //Pesquisando Resolucao Diretoria
		FiltroResolucaoDiretoria filtroResolucaoDiretoria = new FiltroResolucaoDiretoria();
		filtroResolucaoDiretoria.setCampoOrderBy(FiltroResolucaoDiretoria.NUMERO);
        Collection<ResolucaoDiretoria> collectionResolucaoDiretoria = fachada.pesquisar(filtroResolucaoDiretoria, ResolucaoDiretoria.class.getName());
        sessao.setAttribute("collectionResolucaoDiretoria", collectionResolucaoDiretoria);
        //Fim de pesquisando Resolucao Diretoria
        
        //Pesquisando Tipo da Situacao do Im�vel
        FiltroImovelSituacaoTipo filtroImovelSituacaoTipo = new FiltroImovelSituacaoTipo();
        filtroImovelSituacaoTipo.setCampoOrderBy(FiltroImovelSituacaoTipo.DESCRICAO_IMOVEL_SITUACAO_TIPO);
        Collection<ImovelSituacaoTipo> collectionImovelSituacaoTipo = fachada.pesquisar(filtroImovelSituacaoTipo, ImovelSituacaoTipo.class.getName());
        sessao.setAttribute("collectionImovelSituacaoTipo", collectionImovelSituacaoTipo);
        //Fim de pesquisando Tipo da Situacao do Im�vel
        

        //Pesquisando Perfil do Im�vel
        FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();
        filtroImovelPerfil.adicionarParametro(new ParametroSimples(
        		FiltroImovelPerfil.INDICADOR_USO,  new Short("1")));
        filtroImovelPerfil.setCampoOrderBy(FiltroImovelPerfil.DESCRICAO);
        Collection<ImovelPerfil> collectionImovelPerfil = fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());
        sessao.setAttribute("collectionImovelPerfil", collectionImovelPerfil);
        //Fim de pesquisando Perfil do Im�vel
        
        
        //Pesquisando Categoria
        FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria();
        filtroSubCategoria.adicionarParametro(new ParametroSimples(
        		FiltroSubCategoria.INDICADOR_USO,  new Short("1")));
        filtroSubCategoria.setCampoOrderBy(FiltroSubCategoria.DESCRICAO);
        Collection<Subcategoria> collectionSubcategoria = fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName());
        sessao.setAttribute("collectionSubcategoria", collectionSubcategoria);
        //Fim de pesquisando Categoria

        if (httpServletRequest.getParameter("desfazer") != null
                && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")) {
        	
        	//-------------- bt DESFAZER ---------------
        	
        	filtrarPerfilParcelamentoActionForm.setResolucaoDiretoria("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
        	filtrarPerfilParcelamentoActionForm.setImovelSituacaoTipo("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
        	filtrarPerfilParcelamentoActionForm.setImovelPerfil("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
        	filtrarPerfilParcelamentoActionForm.setSubcategoria("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
        	sessao.setAttribute("indicadorAtualizar","1");
            sessao.removeAttribute("caminhoRetornoTelaPesquisa");

        }
        
        // c�digo para checar ou naum o Atualizar
        String primeiraVez = httpServletRequest.getParameter("menu");
		if (primeiraVez != null && !primeiraVez.equals("")) {
			sessao.setAttribute("indicadorAtualizar","1");
		}
      
        
        sessao.removeAttribute("caminhoRetornoTelaPesquisa");
		sessao.removeAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper");
		sessao.removeAttribute("collectionParcelamentoDescontoInatividade");
		sessao.removeAttribute("collectionParcelamentoDescontoAntiguidade");
		sessao.removeAttribute("collectionParcelamentoDescontoInatividadeAVista");
		
        return retorno;
    }
    
   

}
 
