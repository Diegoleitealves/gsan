package gcom.micromedicao.hidrometro;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * < <Descri��o da Classe>>
 * 
 * @author S�vio Luiz
 * 
 * Data:22/04/2008
 */
public class FiltroHidrometroRelojoaria extends Filtro implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * Construtor da classe FiltroHidrometroMarca
     */
    public FiltroHidrometroRelojoaria() {
    }

    /**
     * Construtor da classe FiltroHidrometroMarca
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroHidrometroRelojoaria(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

    /**
     * Description of the Field
     */
    public final static String ID = "id";

    /**
     * Description of the Field
     */
    public final static String INDICADOR_USO = "indicadorUso";

    public final static String DESCRICAO = "descricao";

}
