package gcom.gui.cadastro;

import gcom.batch.Processo;
import gcom.cadastro.tarifasocial.TarifaSocialComandoCarta;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Vivianne Sousa
 * @created 01/04/2011
 */
public class RetirarImovelTarifaSocialAction extends GcomAction {
	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Prepara o retorno da A��o
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();
		Map parametros = new HashMap();
		
		String idsComandos = httpServletRequest.getParameter("comando");
		if (idsComandos != null && !idsComandos.equals("")){
			
			Collection colacaoTarifaSocialComandoCartaSessao = (Collection) sessao.getAttribute("colacaoTarifaSocialComandoCarta");
			Iterator itcolacaoTarifaSocialComandoCartaSessao = colacaoTarifaSocialComandoCartaSessao.iterator();
			TarifaSocialComandoCarta comando = null;
			
			String[] idsComandosArray = idsComandos.split(",");
			
			while (itcolacaoTarifaSocialComandoCartaSessao.hasNext()){
				
				comando = (TarifaSocialComandoCarta) itcolacaoTarifaSocialComandoCartaSessao.next();
				
				for(int x=0; x<idsComandosArray.length; x++){
					
					if (comando.getId().equals(new Integer(idsComandosArray[x]))){
						
						if(comando.getQuantidadeCartasGeradas() == null ||
								comando.getQuantidadeCartasGeradas().equals(new Integer("0"))){
							throw new ActionServletException("atencao.quantidade_cartas_zero");
						}
						
						Date dataGeracaoMaisDiasComparecimento =  Util.adicionarNumeroDiasDeUmaData(
								comando.getDataGeracao(),comando.getQuantidadeDiasComparecimento());
						
						if(dataGeracaoMaisDiasComparecimento.compareTo(new Date()) >= 0){
							throw new ActionServletException("atencao.comando.nao.executado", null, Util.formatarData(dataGeracaoMaisDiasComparecimento));
						}
						
						parametros.put("tarifaSocialComandoCarta",comando);
						
					}
				}
			}
			
		}
	
		fachada.inserirProcessoIniciadoParametrosLivres(parametros, 
         		Processo.RETIRAR_IMOVEL_TARIFA_SOCIAL ,
         		this.getUsuarioLogado(httpServletRequest));
		
		montarPaginaSucesso(httpServletRequest, "Processo Inserido.", "", "");
    
		return retorno;
	}
	

}
