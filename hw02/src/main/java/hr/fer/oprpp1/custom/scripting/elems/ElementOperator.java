package hr.fer.oprpp1.custom.scripting.elems;

public class ElementOperator extends Element{
	/**
	 * Symbol of this operator element.
	 */
	private String symbol;
	
	/**
	 * Creates a new ElementOperator with given symbol.
	 * @param value given symbol
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Returns the symbol of this operator element.
	 * @return the symbol of this operator element.
	 */
	public String getValue() {
		return symbol;
	}
	
	/**
	 * Returns string representation of this element.
	 * @return string representation of this element.
	 */
	@Override
	public String asText() {
		return symbol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementOperator))
			return false;
		ElementOperator other = (ElementOperator) obj;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}
	
}
