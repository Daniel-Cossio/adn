package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validatorTipoDocumento")
public class ValidadorTipoDocumento implements Validator {

	private static final String MENSAJE_CONDICION_CC = "El campo 'Número de documento' solo puede contener números y un máximo de 10 caracteres";
	private static final String REGEX_CC = "[0-9]{1,10}";

	private static final String MENSAJE_CONDICION_CE = "El campo 'Número de documento' puede contener números y letras, y un máximo de 14 caracteres";
	private static final String REGEX_CE = "[0-9a-zA-Z]{1,14}";

	private static final String MENSAJE_CONDICION_RC = "El campo 'Número de documento' solo puede contener números";
	private static final String REGEX_RC = "[0-9]{1,}";

	private static final String MENSAJE_CONDICION_TI = "El campo 'Número de documento' solo puede contener números";
	private static final String REGEX_TI = "[0-9]{1,}";

	private static final String MENSAJE_DEFAULT = "No se pudo obtener el tipo de documento. ";

	@Override
	public void validate(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {

		String numeroDocumento = (String) value;

		String tipoDocumentoSeleccionado = "";
		try {
			UIComponent selectOneMenu = component.findComponent("tipoDocumento");
			tipoDocumentoSeleccionado = (String) ((UISelectOne) selectOneMenu).getValue();

		} catch (Exception e) {
			e.printStackTrace();
		}

		switch (tipoDocumentoSeleccionado) {
		case "CC":
			// Si la opción seleccionada es CC el campo solo puede contener números y máximo
			// 10 caracteres
			if (!numeroDocumento.matches(REGEX_CC)) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, MENSAJE_CONDICION_CC, null));
			}
			break;
		case "CE":
			// Si la opción seleccionada es CE el campo puede contener números y letras y
			// máximo 14 caracteres
			if (!numeroDocumento.matches(REGEX_CE)) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, MENSAJE_CONDICION_CE, null));
			}
			break;
		case "RC":
			// Si la opción seleccionada es RC el campo solo puede contener números
			if (!numeroDocumento.matches(REGEX_RC)) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, MENSAJE_CONDICION_RC, null));
			}
			break;
		case "TI":
			// Si la opción seleccionada es TI el campo solo puede contener números
			if (!numeroDocumento.matches(REGEX_TI)) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, MENSAJE_CONDICION_TI, null));
			}
			break;
		default:
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, MENSAJE_DEFAULT, null));

		}

	}

}
