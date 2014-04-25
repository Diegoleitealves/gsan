package gcom.gui.atendimentopublico.ordemservico;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC1225] Incluir Dados Acompanhamento de Servi�o
 * 
 * Este caso de uso permite a inser��o de dados na tabela de ordem de servi�o para acompanhamento do servi�o.
 * 
 * @author Th�lio Ara�jo
 * @date 22/09/2011
 */
public class AtualizarOrdemServicoAcompanhamentoCelularAction extends GcomAction {

    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ActionForward retorno = actionMapping.findForward("telaSucesso");
        
        try {
            DiskFileUpload upload = new DiskFileUpload();
            
            // Parse the request
            List itensForm = upload.parseRequest(httpServletRequest);
            Iterator iteItensForm = itensForm.iterator();
            
            Fachada fachada = Fachada.getInstancia();            
            
            while ( iteItensForm.hasNext() ){
                
                FileItem item = ( FileItem )iteItensForm.next();
                
                // Caso n�o seja um field do formulario
                // � o arquivo
                if ( !item.isFormField() ){
                    // Lemos 
                    InputStreamReader reader = new InputStreamReader(item.getInputStream());
                    BufferedReader buffer = new BufferedReader(reader);
                    
                    //[FS0002] - Verificar exist�ncia de dados no arquivo
                    //Caso o arquivo esteja sem dados, exibir a mensagem ?Arquivo <<nome do arquivo >> sem dados?
                    //e cancelar a opera��o.
                    if ( buffer != null  ) {  
                    	fachada.retornoAtualizarOrdemServicoAcompanhamentoServico(buffer);
                    }else{
                    	throw new ActionServletException("atencao.ordem_servico_atualiza_celular_sem_dados", item.getFieldName());
                    }
                }                
            }
            
        	// montando p�gina de sucesso
    		montarPaginaSucesso(httpServletRequest,
    			"Ordem Servi�o Movimento Celular Atualizado com sucesso", 
    			"Voltar",
    			"/exibirAtualizarOrdemServicoAcompanhamentoCelularAction.do");
            
            return retorno;            
          
        } catch (FileUploadException e) {
            throw new ActionServletException("erro.atualizacao.nao_concluida");        
        } catch (IOException e) {
            throw new ActionServletException("erro.atualizacao.nao_concluida");        
        }       
    }    
}
