package gcom.gui.faturamento.debito;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipoVigencia;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Inser��o da vig�ncia do D�bito Tipo Vig�ncia
 * 
 * @author Josenildo Neves
 * @date 22/02/2010
 */
public class ReplicarDebitoTipoVigenciaAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		
		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
		
		Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);

		ReplicarDebitoTipoVigenciaActionForm replicarDebitoTipoVigenciaActionForm = (ReplicarDebitoTipoVigenciaActionForm) actionForm;

		//[SB0002] � Replicar os servi�os existentes para uma nova vig�ncia e valor- Replicar os d�bitos existentes para uma nova vig�ncia e valor.		
		Collection<DebitoTipoVigencia> collDebitoTipoVigencia = this.setCollDebitoTipoVigencia(replicarDebitoTipoVigenciaActionForm);
		
		for (DebitoTipoVigencia debitoTipoVigencia : collDebitoTipoVigencia) {
			
			// FS0008 - Verificar pr�-exist�ncia de vig�ncia para o d�bito tipo
			String dataVigenciaInicial = Util.formatarData(debitoTipoVigencia.getDataVigenciaInicial());
			String dataVigenciaFinal = Util.formatarData(debitoTipoVigencia.getDataVigenciaFinal());
			Integer idDebitoTipo = debitoTipoVigencia.getDebitoTipo().getId();
			
			Fachada.getInstancia().verificarExistenciaVigenciaDebito(dataVigenciaInicial, 
					dataVigenciaFinal, idDebitoTipo, new Integer("2"));
		}
		
		for (DebitoTipoVigencia debitoTipoVigencia : collDebitoTipoVigencia) {
			
			// ------------ REGISTRAR TRANSA��O ----------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(
					Operacao.OPERACAO_INSERIR_DEBITO_TIPO_VIGENCIA, debitoTipoVigencia.getDebitoTipo().getId(),
					debitoTipoVigencia.getDebitoTipo().getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
					UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			// ------------ REGISTRAR TRANSA��O ----------------
			registradorOperacao.registrarOperacao(debitoTipoVigencia);
			
			Fachada.getInstancia().inserir(debitoTipoVigencia);
			
		}		

		// [FS0003 - Verificar sucesso da transa��o].
		montarPaginaSucesso(httpServletRequest, "Todas as vig�ncias foram atualizadas com sucesso.",
				"Replicar Debito Tipo Vigencia",
				"exibirReplicarDebitoTipoVigenciaAction.do?menu=sim");
		return retorno;
	}

	/**
	 * Atualiza��o da vig�ncia e do valor na cole��o de D�bito Tipo Vig�ncia
	 * 
	 * @author Josenildo Neves
	 * @date 22/02/2010
	 * 
	 * @param form
	 * @return collDebitoTipoVigencia
	 */
	
	private Collection<DebitoTipoVigencia> setCollDebitoTipoVigencia(
			ReplicarDebitoTipoVigenciaActionForm form) {
		
		Collection<DebitoTipoVigencia> collDebitoTipoVigencia = Fachada.getInstancia()
					.pesquisarDebitoTipoVigenciaUltimaVigenciaSelecionados(form.getIdRegistrosSelecionados());
		
		for (DebitoTipoVigencia debitoTipoVigencia : collDebitoTipoVigencia) {
			
			debitoTipoVigencia.setDataVigenciaInicial(Util.converteStringParaDate(form.getNovaDataVigenciaInicial()));
			
			debitoTipoVigencia.setDataVigenciaFinal(Util.converteStringParaDate(form.getNovaDataVigenciaFinal()));
			
			if(!form.getIndiceParaCorrecao().replace(",",".").equals("00.0000")){
				
				BigDecimal novoValor = null;
				BigDecimal indice = new BigDecimal(form.getIndiceParaCorrecao().replace(",","."));
	
				//divide por Cem pois o valor informado na tela � em percentagem "%".
				indice = indice.divide(new BigDecimal(100));
				
				novoValor = indice.multiply(debitoTipoVigencia.getValorDebito());
				
				novoValor = novoValor.add(debitoTipoVigencia.getValorDebito());
				
				debitoTipoVigencia.setValorDebito(novoValor);
			
				debitoTipoVigencia.setUltimaAlteracao(new Date());
			}else{
				throw new ActionServletException("atencao.valor.�ndice.informado.igual.zero");
			}
		}
		
		return collDebitoTipoVigencia;
	}

}
