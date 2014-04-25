package gcom.gui.micromedicao.hidrometro;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroLocalArmazenagem;
import gcom.micromedicao.hidrometro.FiltroHidrometroMotivoMovimentacao;
import gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem;
import gcom.micromedicao.hidrometro.HidrometroMotivoMovimentacao;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 *
 * @author Fernanda Paiva
 * @created 23 de Janeiro de 2006
 */
public class ExibirFiltrarMovimentacaoHidrometroAction extends GcomAction {
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

        // Obt�m o action form
        HidrometroActionForm hidrometroActionForm = (HidrometroActionForm) actionForm;

        String tela = (String) httpServletRequest.getParameter("tela");
        
        String limparCampos = (String) httpServletRequest.getParameter("limparCampos");

        // Seta a a��o de retorno
        ActionForward retorno = actionMapping
                .findForward("filtrarMovimentacaoHidrometro");

        // Obt�m a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);
        
        String localArmazenagemOrigem = hidrometroActionForm
        		.getLocalArmazenagemOrigem();

        String localArmazenagemDestino = hidrometroActionForm
        		.getLocalArmazenagemDestino();

        String usuario = hidrometroActionForm
				.getUsuario();

        // Obt�m a facahda
        Fachada fachada = Fachada.getInstancia();

        // Obt�m o objetoCosulta vindo no request
        String objetoConsulta = (String) httpServletRequest
                .getParameter("objetoConsulta");

        // Obt�m o objetoCosulta vindo no request 
        String tipo = (String) httpServletRequest
        		.getParameter("tipo");

        // Carregar a data corrente do sistema
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        Calendar dataCorrente = new GregorianCalendar();
        
        //Data Corrente
        httpServletRequest.setAttribute("dataAtual", formatoData.format(dataCorrente.getTime()));
        
        // Verifica se o objeto � diferente de nulo
        if (objetoConsulta != null
                && !objetoConsulta.trim().equalsIgnoreCase("")
                && (Integer.parseInt(objetoConsulta)) == 1) {

            // Filtro para obter o local de armazenagem ativo de id informado
            FiltroHidrometroLocalArmazenagem filtroHidrometroLocalArmazenagem = new FiltroHidrometroLocalArmazenagem();

            if (localArmazenagemOrigem != null  && Integer.parseInt(tipo) == 1) {
                filtroHidrometroLocalArmazenagem
                        .adicionarParametro(new ParametroSimples(
                                FiltroHidrometroLocalArmazenagem.ID ,
                                new Integer(hidrometroActionForm
                                        .getLocalArmazenagemOrigem()),
                                ParametroSimples.CONECTOR_AND));
            } else if (localArmazenagemDestino != null  && Integer.parseInt(tipo) == 2) {
                filtroHidrometroLocalArmazenagem
                        .adicionarParametro(new ParametroSimples(
                                FiltroHidrometroLocalArmazenagem.ID,
                                new Integer(hidrometroActionForm
                                        .getLocalArmazenagemDestino()),
                                ParametroSimples.CONECTOR_AND));
            }
            filtroHidrometroLocalArmazenagem
                    .adicionarParametro(new ParametroSimples(
                            FiltroHidrometroLocalArmazenagem.INDICADOR_USO,
                            ConstantesSistema.INDICADOR_USO_ATIVO));
            filtroHidrometroLocalArmazenagem
                    .setCampoOrderBy( FiltroHidrometroLocalArmazenagem.DESCRICAO);

            // Pesquisa de acordo com os par�metros informados no filtro
            Collection colecaoHidrometroLocalArmazenagem = fachada.pesquisar(
                    filtroHidrometroLocalArmazenagem,
                    HidrometroLocalArmazenagem.class.getName());

            // Verifica se a pesquisa retornou algum objeto para a cole��o
            if (colecaoHidrometroLocalArmazenagem != null
                    && !colecaoHidrometroLocalArmazenagem.isEmpty()) {

                // Obt�m o objeto da cole��o pesquisada
                HidrometroLocalArmazenagem hidrometroLocalArmazenagem = (HidrometroLocalArmazenagem) Util
                        .retonarObjetoDeColecao(colecaoHidrometroLocalArmazenagem);

                if(localArmazenagemOrigem != null  && Integer.parseInt(tipo) == 1){
                	 // Exibe o c�digo e a descri��o pesquisa na p�gina
                    httpServletRequest.setAttribute("corLocalArmazenagemOrigem", "valor");
                }else{
                	if(localArmazenagemDestino != null  && Integer.parseInt(tipo) == 2){
                		 // Exibe o c�digo e a descri��o pesquisa na p�gina
                        httpServletRequest.setAttribute("corLocalArmazenagemDestino", "valor");
                	}
                }
               
                if (localArmazenagemOrigem != null && Integer.parseInt(tipo) == 1) {
                    hidrometroActionForm
                            .setLocalArmazenagemOrigem(hidrometroLocalArmazenagem
                                    .getId().toString());

                    hidrometroActionForm
                            .setLocalArmazenagemDescricaoOrigem(hidrometroLocalArmazenagem
                                    .getDescricao());

                }
                if (localArmazenagemDestino != null && Integer.parseInt(tipo) == 2) {
                    hidrometroActionForm
                            .setLocalArmazenagemDestino(hidrometroLocalArmazenagem
                                    .getId().toString());

                    hidrometroActionForm
                            .setLocalArmazenagemDescricaoDestino(hidrometroLocalArmazenagem
                                    .getDescricao());
                }
                
            } else {
                if (localArmazenagemOrigem != null
						&& !localArmazenagemOrigem.equals("") && Integer.parseInt(tipo) == 1) {
					hidrometroActionForm
							.setLocalArmazenagemDescricaoOrigem(ConstantesSistema.CODIGO_LOCAL_ARMAZENAGEM_INEXISTENTE);
					
					hidrometroActionForm
					.setLocalArmazenagemOrigem("");

					// Exibe mensagem de c�digo inexiste e limpa o campo de
					// c�digo
					httpServletRequest.setAttribute("corLocalArmazenagemOrigem",
							"exception");
				}
				if (localArmazenagemDestino != null
						&& !localArmazenagemDestino.equals("") && Integer.parseInt(tipo) == 2) {
					hidrometroActionForm
							.setLocalArmazenagemDescricaoDestino(ConstantesSistema.CODIGO_LOCAL_ARMAZENAGEM_INEXISTENTE);
					
					hidrometroActionForm
					.setLocalArmazenagemDestino("");
					// Exibe mensagem de c�digo inexiste e limpa o campo de
					// c�digo
					httpServletRequest.setAttribute("corLocalArmazenagemDestino",
							"exception");
                }
            }
        } 
        
        // Pesquisa Usuario
        if(usuario != null && !usuario.equals("")){
        	
        	FiltroUsuario filtroUsuario = new FiltroUsuario();  
            
        	filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, usuario));
            
            Collection colecaoUsuario = fachada.pesquisar(
                    filtroUsuario,Usuario.class.getName());
            
            if (colecaoUsuario != null && !colecaoUsuario.isEmpty()) {
            	httpServletRequest.setAttribute("corUsuario", "valor");
            	
				// O imovel foi encontrado
				hidrometroActionForm.setUsuario(""
						+ ((Usuario) ((List) colecaoUsuario).get(0)).getId());
				hidrometroActionForm.setNomeUsuario(""
						+ ((Usuario) ((List) colecaoUsuario).get(0)).getNomeUsuario());
			} else {
				httpServletRequest.setAttribute("corUsuario","exception");
            	hidrometroActionForm
            		.setNomeUsuario(ConstantesSistema.USUARIO_INEXISTENTE);
			}
        }
        if (sessao.getAttribute("colecaoHidrometroMotivoMovimentacao") == null) {
            // Filtro de hidr�metro motivo movimentacao para obter todas os
            // motivo ativas
            FiltroHidrometroMotivoMovimentacao filtroHidrometroMotivoMovimentacao = new FiltroHidrometroMotivoMovimentacao();

            filtroHidrometroMotivoMovimentacao
                    .adicionarParametro(new ParametroSimples(
                            FiltroHidrometroMotivoMovimentacao.INDICADOR_USO,
                             ConstantesSistema.INDICADOR_USO_ATIVO));
            filtroHidrometroMotivoMovimentacao
                    .setCampoOrderBy(FiltroHidrometroMotivoMovimentacao.DESCRICAO);

            // Pesquisa a cole��o de classe metrol�gica
            Collection colecaoHidrometroMotivoMovimentacao = fachada.pesquisar(
                    filtroHidrometroMotivoMovimentacao,
                    HidrometroMotivoMovimentacao.class.getName());
            // Envia as cole��es na sess�o
            sessao.setAttribute("colecaoHidrometroMotivoMovimentacao",
                    colecaoHidrometroMotivoMovimentacao);

        }
        if(limparCampos != null)
        {
        	hidrometroActionForm.reset(actionMapping, httpServletRequest);
        	limparCampos = null;
        }
        sessao.setAttribute("tela", tela);
        return retorno;
    }

}
