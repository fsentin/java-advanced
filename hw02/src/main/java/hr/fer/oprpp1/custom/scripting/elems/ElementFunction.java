package hr.fer.oprpp1.custom.scripting.elems;

public class ElementFunction extends Element{
	/**
	 * Name of the function.
	 */
	private String name;
	
	/**
	 * Creates a new ElementFunction with given name.
	 * @param value given name
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of this function element.
	 * @return the name of this function element.
	 */
	public String getValue() {
		return name;
	}
	
	/**
	 * Returns string representation of this element.
	 * @return string representation of this element.
	 */
	@Override
	public String asText() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementFunction))
			return false;
		ElementFunction other = (ElementFunction) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
