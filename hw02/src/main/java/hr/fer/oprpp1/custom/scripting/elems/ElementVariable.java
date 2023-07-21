package hr.fer.oprpp1.custom.scripting.elems;

public class ElementVariable extends Element{
	/**
	 * Name of the variable element.
	 */
	private String name;

	/**
	 * Creates a new ElementVariable with given name.
	 * @param name the given string name
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of this variable element.
	 * @return the name of this variable element.
	 */
	public String getName() {
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
		if (!(obj instanceof ElementVariable))
			return false;
		ElementVariable other = (ElementVariable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
