package gcom.relatorio;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * Datasource usado pelo Hibernate para gera��o de relat�rios no JasperReports
 * 
 * @author rodrigo
 */
public class HibernateQueryResultDataSource implements JRDataSource {

    private String[] fields;

    private Iterator iterator;

    private Object currentValue;

    /**
     * Construtor da classe HibernateQueryResultDataSource
     * 
     * @param list
     *            Descri��o do par�metro
     * @param fields
     *            Descri��o do par�metro
     */
    public HibernateQueryResultDataSource(List list, String[] fields) {
        this.fields = fields;
        this.iterator = list.iterator();
    }

    /**
     * Retorna o valor de fieldValue
     * 
     * @param field
     *            Descri��o do par�metro
     * @return O valor de fieldValue
     * @exception JRException
     *                Descri��o da exce��o
     */
    public Object getFieldValue(JRField field) throws JRException {
        Object value = null;
        int index = getFieldIndex(field.getName());

        if (index > -1) {
            Object[] values = (Object[]) currentValue;

            value = values[index];
        }
        return value;
    }

    /**
     * < <Descri��o do m�todo>>
     * 
     * @return Descri��o do retorno
     * @exception JRException
     *                Descri��o da exce��o
     */
    public boolean next() throws JRException {
        currentValue = iterator.hasNext() ? iterator.next() : null;
        return (currentValue != null);
    }

    /**
     * Retorna o valor de fieldIndex
     * 
     * @param field
     *            Descri��o do par�metro
     * @return O valor de fieldIndex
     */
    private int getFieldIndex(String field) {
        int index = -1;

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].equals(field)) {
                index = i;
                break;
            }
        }
        return index;
    }

}
