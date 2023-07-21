package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantDouble extends Element{
	/**
	 * Value of this double element.
	 */
	private double value;
	
	/**
	 * Creates a new ElementConstantDouble with given double value.
	 * @param value
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Returns the value of this double element.
	 * @return the value of this double element.
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Returns string representation of this element.
	 * @return string representation of this element.
	 */
	@Override
	public String asText() {
		return Double.toString(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementConstantDouble))
			return false;
		ElementConstantDouble other = (ElementConstantDouble) obj;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}
	
}
