package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.ServicoCobrancaValor;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Atualiza��o da vig�ncia do Valor de Cobran�a do Servi�o
 * 
 * @author Josenildo Neves - Hugo Leonardo
 * @date 04/02/2010 - 03/05/2010
 */
public class ReplicarValorCobrancaServicoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		// Forward
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Form
		ReplicarValorCobrancaServicoActionForm replicarCobrancaServicoActionForm = (ReplicarValorCobrancaServicoActionForm) actionForm;

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
		
		Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);
				
		Collection<ServicoCobrancaValor> collServidorValorCobranca = this.setCollServicoCobrancaValor(replicarCobrancaServicoActionForm);
		
		for (ServicoCobrancaValor servicoCobrancaValor : collServidorValorCobranca) {
			
			// FS0008 - Verificar pr�-exist�ncia de vig�ncia para o d�bito tipo
			String dataVigenciaInicial = Util.formatarData(servicoCobrancaValor.getDataVigenciaInicial());
			String dataVigenciaFinal = Util.formatarData(servicoCobrancaValor.getDataVigenciaFinal());
			Integer idServicoTipo = servicoCobrancaValor.getServicoTipo().getId();
			
			Fachada.getInstancia().verificarExistenciaVigenciaServicoTipo(dataVigenciaInicial, 
					dataVigenciaFinal, idServicoTipo, new Integer("2"));
		}
		
		for (ServicoCobrancaValor servicoCobrancaValor : collServidorValorCobranca) {
			
			// ------------ REGISTRAR TRANSA��O ----------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(
					Operacao.OPERACAO_VALOR_COBRANCA_SERVICO_INSERIR, servicoCobrancaValor.getServicoTipo().getId(),
					servicoCobrancaValor.getServicoTipo().getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
					UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			// ------------ REGISTRAR TRANSA��O ----------------
			
			registradorOperacao.registrarOperacao(servicoCobrancaValor);
			
			Fachada.getInstancia().inserirValorCobrancaServico(servicoCobrancaValor);
			
		}		

		// [FS0003 - Verificar sucesso da transa��o].
		montarPaginaSucesso(httpServletRequest, "Todas as vig�ncias foram atualizadas com sucesso.",
				"Replicar Valor de Cobran�a do Servi�o",
				"exibirReplicarValorCobrancaServicoAction.do?menu=sim");
		return retorno;
	}

	/**
	 * Atualiza��o da vig�ncia e do valor na cole��o de Valor de Cobran�a do Servi�o
	 * 
	 * @author Josenildo Neves
	 * @date 04/02/2010
	 * 
	 * @param form
	 * @return servicoCobrancaValor
	 */
	
	private Collection<ServicoCobrancaValor> setCollServicoCobrancaValor(
			ReplicarValorCobrancaServicoActionForm form) {
		
		
		Collection<ServicoCobrancaValor> collServicoCobrancaValor = Fachada.getInstancia().replicarServicoCobrancaValorUltimaVigencia(form.getIdRegistrosSelecionados());
		
		for (ServicoCobrancaValor servicoCobrancaValor : collServicoCobrancaValor) {
			

			servicoCobrancaValor.setDataVigenciaInicial(Util.converteStringParaDate(form.getNovaDataVigenciaInicial()));
			
			servicoCobrancaValor.setDataVigenciaFinal(Util.converteStringParaDate(form.getNovaDataVigenciaFinal()));

			if(!form.getIndiceParaCorrecao().replace(",",".").equals("00.0000")){
				
				BigDecimal novoValor = null;
				BigDecimal indice = new BigDecimal(form.getIndiceParaCorrecao().replace(",","."));
				
				//divide por Cem pois o valor informado na tela � em percentagem "%".
				indice = indice.divide(new BigDecimal(100));

				novoValor = indice.multiply(servicoCobrancaValor.getValor());
				
				novoValor = novoValor.add(servicoCobrancaValor.getValor());
				
				servicoCobrancaValor.setValor(novoValor);
				
				servicoCobrancaValor.setUltimaAlteracao(new Date());

			}else{
				throw new ActionServletException("atencao.valor.�ndice.informado.igual.zero");
			}
		}
		
		return collServicoCobrancaValor;
	}

}
