package model;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

	@JsonTypeInfo(use = Id.NAME,
	include = JsonTypeInfo.As.PROPERTY,
	property = "type")
	@JsonSubTypes({
	@Type(value = Paket.class),
	@Type(value = Entitet.class),
	})

public abstract class PackEnt extends InfResurs{
	
}
