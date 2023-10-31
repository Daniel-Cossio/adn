package beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

@ManagedBean(name = "formulariobean")
@SessionScoped
public class FormularioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> opcionesTipoDocumento = Arrays.asList("CC", "CE", "RC", "TI");
	private List<String> opcionesAdministradoraSalud = Arrays.asList("EPS001 - Sanitas EPS", "EPS002 - Sura EPS",
			"EPS003 - Coomeva", "EPS004 - Medimas");
	private List<String> opcionesAdministradoraPension = Arrays.asList("AFP001 - Proteccion", "AFP002 - Porvenir",
			"AFP003 - Colpensiones");
	private String tipoDocumento;
	private String numeroDocumento;
	private String administradoraSalud;
	private String administradoraPension;

	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private Date fechaAfiliacionSalud;
	private Date fechaAfiliacionPension;

	private boolean valido = false;
	private String excepcion;

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getAdministradoraSalud() {
		return administradoraSalud;
	}

	public void setAdministradoraSalud(String administradoraSalud) {
		this.administradoraSalud = administradoraSalud;
	}

	public Date getFechaAfiliacionSalud() {
		return fechaAfiliacionSalud;
	}

	public void setFechaAfiliacionSalud(Date fechaAfiliacionSalud) {
		this.fechaAfiliacionSalud = fechaAfiliacionSalud;
	}

	public String getAdministradoraPension() {
		return administradoraPension;
	}

	public void setAdministradoraPension(String administradoraPension) {
		this.administradoraPension = administradoraPension;
	}

	public Date getFechaAfiliacionPension() {
		return fechaAfiliacionPension;
	}

	public void setFechaAfiliacionPension(Date fechaAfiliacionPension) {
		this.fechaAfiliacionPension = fechaAfiliacionPension;
	}

	public FormularioBean() {
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public List<String> getOpcionesTipoDocumento() {
		return opcionesTipoDocumento;
	}

	public void setOpcionesTipoDocumento(List<String> opcionesTipoDocumento) {
		this.opcionesTipoDocumento = opcionesTipoDocumento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<String> getOpcionesAdministradoraSalud() {
		return opcionesAdministradoraSalud;
	}

	public void setOpcionesAdministradoraSalud(List<String> opcionesAdministradoraSalud) {
		this.opcionesAdministradoraSalud = opcionesAdministradoraSalud;
	}

	public List<String> getOpcionesAdministradoraPension() {
		return opcionesAdministradoraPension;
	}

	public void setOpcionesAdministradoraPension(List<String> opcionesAdministradoraPension) {
		this.opcionesAdministradoraPension = opcionesAdministradoraPension;
	}

	public String getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(String excepcion) {
		this.excepcion = excepcion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void Enviar() {
		valido=false;
		excepcion="";
		KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();

		builder.add(ResourceFactory.newClassPathResource("rules/reglas.drl"), ResourceType.DRL);

		if (builder.hasErrors()) {

			throw new RuntimeException(builder.getErrors().toString());
		}
		KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
		knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());
		StatefulKnowledgeSession session = knowledgeBase.newStatefulKnowledgeSession();
		
		session.insert(this);
		int rulesFired = session.fireAllRules();
		session.dispose();
		if (rulesFired == 0) {
			valido = true;
			excepcion = "El registro ha sido exitoso";

		}
	
		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, excepcion, excepcion));
	}

}
