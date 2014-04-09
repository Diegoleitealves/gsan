package gcom.gui.operacional;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.FiltroDistritoOperacional;
import gcom.operacional.ZonaPressao;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AtualizarZonaPressaoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o caminho de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando for implementado o esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarZonaPressaoActionForm atualizarZonaPressaoActionForm = (AtualizarZonaPressaoActionForm) actionForm;

		ZonaPressao zonaPressao = (ZonaPressao) sessao.getAttribute("atualizarZonaPressao");
		
		Collection colecaoPesquisa = null;
		
        String descricaoZonaPressao = atualizarZonaPressaoActionForm.getDescricao();
        String descricaoAbreviadaZonaPressao = atualizarZonaPressaoActionForm.getDescricaoAbreviada();    
        String indicadordeUso = atualizarZonaPressaoActionForm.getIndicadorUso();
        String distritoOperacionalID = atualizarZonaPressaoActionForm.getDistritoOperacionalID();
        
        //Distrito Operacional � obrigat�rio.

        if (distritoOperacionalID == null || distritoOperacionalID.equalsIgnoreCase("")) {
            throw new ActionServletException("atencao.required", null, "Distrito Operacional");
        }
		FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();

		filtroDistritoOperacional.adicionarParametro(new ParametroSimples(
				FiltroDistritoOperacional.ID, distritoOperacionalID));

		filtroDistritoOperacional.adicionarParametro(new ParametroSimples(
				FiltroDistritoOperacional.INDICADORUSO,
		        ConstantesSistema.INDICADOR_USO_ATIVO));

		//Retorna distrito Operacional
		colecaoPesquisa = fachada.pesquisar(filtroDistritoOperacional,
				DistritoOperacional.class.getName());

		if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
		    throw new ActionServletException(
		            "atencao.pesquisa.distrito_operacional_inexistente");
		}

        
    zonaPressao.setDescricaoZonaPressao(atualizarZonaPressaoActionForm.getDescricao());
	zonaPressao.setDescricaoAbreviada(atualizarZonaPressaoActionForm.getDescricaoAbreviada());
	zonaPressao.setIndicadorUso(new Short (atualizarZonaPressaoActionForm.getIndicadorUso()));
	
    DistritoOperacional distritoOperacional = new DistritoOperacional();
    	distritoOperacional.setId(new Integer(atualizarZonaPressaoActionForm.getDistritoOperacionalID()));
    zonaPressao.setDistritoOperacional(distritoOperacional);
    
    zonaPressao.setDescricaoZonaPressao(descricaoZonaPressao);
        
	// Caso n�o tenha sido preenchida a Descri��o Abreviada, no banco ficar� null
    if(atualizarZonaPressaoActionForm.getDescricaoAbreviada() != null 
			&& !atualizarZonaPressaoActionForm.getDescricaoAbreviada().equals("")){
        
		zonaPressao.setDescricaoAbreviada(descricaoAbreviadaZonaPressao);
	
	} else {
	
		zonaPressao.setDescricaoAbreviada(null);
	
	}
        
   	// Seta a data da altera��o
    zonaPressao.setUltimaAlteracao( new Date() );	
    
    // Seta o Indicador de Uso
    zonaPressao.setIndicadorUso( new Short(indicadordeUso));
	
	fachada.atualizar(zonaPressao);

	montarPaginaSucesso(httpServletRequest, "Zona de Press�o "
			+ atualizarZonaPressaoActionForm.getId().toString() + " atualizado com sucesso.",
			"Realizar outra Manuten��o de Zona de Press�o ",
			"exibirFiltrarZonaPressaoAction.do?menu=sim");        
    
	return retorno;
	}
}
