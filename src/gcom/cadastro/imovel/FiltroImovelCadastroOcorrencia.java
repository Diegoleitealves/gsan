package gcom.cadastro.imovel;

import gcom.util.filtro.Filtro;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class FiltroImovelCadastroOcorrencia extends Filtro {
	
	private static final long serialVersionUID = 1L;


    public FiltroImovelCadastroOcorrencia() {
    }

    public final static String ID = "id";
    public final static String DATA_OCORRENCIA = "dataOcorrencia";
    
    public final static String IMOVEL_ID = "imovel.id";
    
    public final static String CADASTRO_OCORRENCIA = "cadastroOcorrencia";
    
    
    /**
     * Construtor da classe FiltroCategoria
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroImovelCadastroOcorrencia(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

}
