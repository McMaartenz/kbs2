class Selectie
{
	public enum Optie
	{
		DISPOSED,
		BEST_FIT,
		NEXT_FIT,
		FIRST_FIT,
		WORST_FIT,
		BEST_FIT_DECREASING,
		NEXT_FIT_DECREASING,
		FIRST_FIT_DECREASING
	}

	Optie optie;

	public Selectie(Optie optie)
	{
		this.optie = optie;
	}

	@Override
	public String toString()
	{
		return switch(optie)
		{
			case DISPOSED             -> "Geen gekozen";
			case BEST_FIT             -> "Best fit";
			case NEXT_FIT             -> "Next fit";
			case FIRST_FIT            -> "First fit";
			case WORST_FIT            -> "Worst fit";
			case BEST_FIT_DECREASING  -> "Best fit decreasing";
			case NEXT_FIT_DECREASING  -> "Next fit decreasing";
			case FIRST_FIT_DECREASING -> "First fit decreasing";
		};
	}
}
