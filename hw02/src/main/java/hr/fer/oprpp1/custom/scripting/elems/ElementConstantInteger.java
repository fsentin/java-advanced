package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantInteger extends Element {
	/**
	 * Value of this int element.
	 */
	private int value;
	
	/**
	 * Creates a new ElementConstantInteger with given int value.
	 * @param value given int value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Returns the value of this int element.
	 * @return the value of this int element.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Returns string representation of this element.
	 * @return string representation of this element.
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementConstantInteger))
			return false;
		ElementConstantInteger other = (ElementConstantInteger) obj;
		if (value != other.value)
			return false;
		return true;
	}
	
}
