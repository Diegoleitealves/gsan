package gcom.gui.faturamento;

import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Adicionar Guia Pagamento Item Popup
 * 
 * @author Fl�vio Leonardo
 * @created 25/04/2006
 */
public class ExibirAdicionarFaturaClienteResponsavelContaPopupAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("adicionarFaturaClienteResponsavelContaPopup");

		Fachada fachada = Fachada.getInstancia();

		// Inst�ncia do formul�rio que est� sendo utilizado
		FiltrarFaturaClienteResponsavelActionForm form = (FiltrarFaturaClienteResponsavelActionForm) actionForm;		

		if( ( form.getImovelId() != null && !form.getImovelId().equals("") ) ||
			( httpServletRequest.getParameter( "idCampoEnviarDados" ) != null &&
			  !httpServletRequest.getParameter( "idCampoEnviarDados" ).equals( "" )  )	){
			
			Imovel imovelEncontrado = 
				this.pesquisarImovel( 
						( form.getImovelId() != null ? 
						  form.getImovelId() : 
						  httpServletRequest.getParameter( "idCampoEnviarDados" ).toString() ) );
			
			if(imovelEncontrado != null){
				form.setInscricao(fachada.pesquisarInscricaoImovel(imovelEncontrado.getId()));
				form.setImovelId( imovelEncontrado.getId()+"" );
			}else{
				form.setInscricao("Im�vel Inexistente");
	    		httpServletRequest.setAttribute("imovelInexistente", "s");
			}
		}
		
		return retorno;
	}
	
	private Imovel pesquisarImovel(String idImovel){
		
		//Cria a vari�vel que vai armazenar o cliente pesquisado
		Imovel imovelEncontrado = null;
		
		//Cria uma inst�ncia da fachada 
		Fachada fachada = Fachada.getInstancia();
		
		//Pesquisa o cliente informado pelo usu�rio no sistema 
		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
		Collection<Imovel> colecaoImovel =  fachada.pesquisar(filtroImovel, Imovel.class.getName());
		
		//Caso exista o cliente no sistema 
		//Retorna para o usu�rio o cliente retornado pela pesquisa
		//Caso contr�rio retorna um objeto nulo 
		if(colecaoImovel != null && !colecaoImovel.isEmpty()){
			imovelEncontrado =(Imovel) Util.retonarObjetoDeColecao(colecaoImovel);
		}
		
		//Retorna o cliente encontrado ou nulo se n�o existir 
		return imovelEncontrado;
	}
	
}
