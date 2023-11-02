package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validatorSoloTexto")
public class ValidatorSoloTexto implements Validator {

	private static final String LETRAS_REGEX = "^[a-zA-Z]+$";
	private static final String MENSAJE_SOLO_LETRAS = "El campo solo puede contener letras";

	@Override
	public void validate(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {

		String textoIngresado = (String) value;
		if (textoIngresado != null && !textoIngresado.matches(LETRAS_REGEX)) {
			FacesMessage message = new FacesMessage(MENSAJE_SOLO_LETRAS);
			throw new ValidatorException(message);
		}
	}

}
