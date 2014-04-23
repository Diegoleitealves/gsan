package gcom.gui.cadastro.tarifasocial;

import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author rodrigo
 */
public class ManterTarifaSocialImovelAction extends GcomAction {

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

        ActionForward retorno = actionMapping
                .findForward("gerenciadorProcesso");

        HttpSession sessao = httpServletRequest.getSession(false);

        //Instancia da fachada
        Fachada fachada = Fachada.getInstancia();

        ManterTarifaSocialActionForm manterTarifaSocialActionForm = (ManterTarifaSocialActionForm) actionForm;

        String idImovel = manterTarifaSocialActionForm.getIdImovel();

        if (idImovel != null && !idImovel.equals("")) {

            //Pesquisa e monta uma cole��o de cliente im�veis
            Collection clienteImoveis = fachada.pesquisarClienteImovelPeloImovelParaEndereco(new Integer(idImovel));

            ClienteImovel clienteImovelObj = null;

            //Verifica se o resultado da busca de im�vel foi vazia
            if (clienteImoveis == null || clienteImoveis.isEmpty()) {

                throw new ActionServletException(
                        "atencao.pesquisa.nenhumresultado", null, "imovel");
            } else {
                //Verifica se o im�vel j� est� com a tarifa social

                clienteImovelObj = (ClienteImovel) clienteImoveis.iterator()
                        .next();

                if (clienteImovelObj.getImovel().getImovelPerfil() != null &&
                	!clienteImovelObj.getImovel().getImovelPerfil().getId()
                    .equals(ImovelPerfil.TARIFA_SOCIAL)) {

                    throw new ActionServletException("atencao.imovel.associado.registro_atendimento.nao.esta.tarifa_social");
                }
            }

            //Obter quantidade de economias do im�vel para determinar qual o
            // pr�ximo caso de uso
            //ser� executado
            //[UC0065 - Inserir dados tarifa social - Uma economia]
            //[UC0066 - Inserir dados tarifa social - Mais de uma economia]
            Imovel imovel = new Imovel();

            imovel.setId(new Integer(idImovel));

            int quantidadeEconomiasImovel = fachada
                    .obterQuantidadeEconomias(imovel);

            if (!(quantidadeEconomiasImovel > 0)) {
                //O imovel n�o possui nenhuma economia
                throw new ActionServletException(
                        "atencao.imovel.economias_nao_cadastradas");
            }

            //atualiza a quantidade de economias do ActionForm
            manterTarifaSocialActionForm.setQtdeEconomias("" + quantidadeEconomiasImovel);
            
            //Coloca o imovel na sessao
            sessao.setAttribute("imovelTarifa", clienteImovelObj.getImovel());

        }

        return retorno;
    }
}
