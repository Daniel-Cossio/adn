package converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("dateConverter")
public class DateConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return format.parse(value);
		} catch (ParseException e) {
			throw new ConverterException("Formato de fecha inválido", e);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format((Date) value);
	}

}