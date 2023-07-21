package hr.fer.oprpp1.custom.scripting.elems;

public class ElementString extends Element{
	/**
	 * String value of this element.
	 */
	private String value;
	
	/**
	 * Creates a new ElementString with given string.
	 * @param value given string
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * Returns the string value of this element.
	 * @return the string value of this element.
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Returns string representation of this element.
	 * @return string representation of this element.
	 */
	@Override
	public String asText() {
		return "\"" + value + "\"";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementString))
			return false;
		ElementString other = (ElementString) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
