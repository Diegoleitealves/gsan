package gcom.gui.cadastro.geografico;


import gcom.cadastro.geografico.FiltroMicrorregiao;
import gcom.cadastro.geografico.FiltroRegiaoDesenvolvimento;
import gcom.cadastro.geografico.FiltroUnidadeFederacao;
import gcom.cadastro.geografico.Microrregiao;
import gcom.cadastro.geografico.RegiaoDesenvolvimento;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0001]	INSERIR MUNICIPIO
 * 
 * @author K�ssia Albuquerque
 * @date 13/12/2006
 */
 
public class ExibirInserirMunicipioAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		
		ActionForward retorno = actionMapping.findForward("inserirMunicipio");	
		
		Fachada fachada = Fachada.getInstancia();
		
		InserirMunicipioActionForm inserirMunicipioActionForm = (InserirMunicipioActionForm) actionForm;
		
		if (httpServletRequest.getParameter("menu") != null && !httpServletRequest.getParameter("menu").trim().equals("")) {
        	//Retorna o maior id de Munic�pio existente 
			int id = this.getFachada().pesquisarMaximoIdMunicipio();
			// Acrescenta 1 no valor do id para setar o primeiro id vazio para o usu�rio
			id = (id + 1);
			inserirMunicipioActionForm.setCodigoMunicipio("" + id);
			httpServletRequest.setAttribute("nomeCampo", "codigoMunicipio");
			inserirMunicipioActionForm.setIndicadorRelacaoQuadraBairro("2");
		}

		
		// [FS0001]-  Verificar a existencia de dados
		
				// UNIDADE FEDERA��O
				
				FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();
				
				filtroUnidadeFederacao.setCampoOrderBy(FiltroUnidadeFederacao.DESCRICAO);
				
				Collection<UnidadeFederacao> colecaoUnidadeFederacao = fachada.pesquisar(
						filtroUnidadeFederacao, UnidadeFederacao.class.getName());
				
				if (colecaoUnidadeFederacao == null || colecaoUnidadeFederacao.isEmpty()) {
					throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null,"Unidade Federa��o");
				}
		
				httpServletRequest.setAttribute("colecaoUnidadeFederacao",colecaoUnidadeFederacao);
				
				
				// MICRORREGIAO
				
				FiltroMicrorregiao filtroMicrorregiao = new FiltroMicrorregiao();
		
				filtroMicrorregiao.setCampoOrderBy(FiltroMicrorregiao.DESCRICAO);
				
				filtroMicrorregiao.adicionarParametro(new ParametroSimples(
				FiltroMicrorregiao.INDICADOR_USO,
				ConstantesSistema.INDICADOR_USO_ATIVO));
				
				Collection<Microrregiao> colecaoMicrorregiao = fachada.pesquisar(
						filtroMicrorregiao, Microrregiao.class.getName());
				
				if (colecaoMicrorregiao == null || colecaoMicrorregiao.isEmpty()) {
					throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null,"Microrregiao");
				}
		
				httpServletRequest.setAttribute("colecaoMicrorregiao",colecaoMicrorregiao);
		
				
				// REGIAO DESENVOLVIMENTO
				
				FiltroRegiaoDesenvolvimento filtroRegiaoDesenv = new FiltroRegiaoDesenvolvimento();
				
				filtroRegiaoDesenv.setCampoOrderBy(FiltroRegiaoDesenvolvimento.DESCRICAO);
				
				filtroRegiaoDesenv.adicionarParametro(new ParametroSimples(
						FiltroRegiaoDesenvolvimento.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
				
				Collection<RegiaoDesenvolvimento> colecaoRegiaoDesenv = fachada.pesquisar(
						filtroRegiaoDesenv, RegiaoDesenvolvimento.class.getName());
		
				if (colecaoRegiaoDesenv == null || colecaoRegiaoDesenv.isEmpty()) {
					throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null,"Regiao Desenvolvimento");
				}
		
				httpServletRequest.setAttribute("colecaoRegiaoDesenv",colecaoRegiaoDesenv);

		
		return retorno;
	}
}
