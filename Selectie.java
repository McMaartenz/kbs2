class Selectie
{
	public enum Optie
	{
		DISPOSED, BEST_FIT, NEXT_FIT, FIRST_FIT, WORST_FIT, BEST_FIT_DECREASING, NEXT_FIT_DECREASING, FIRST_FIT_DECREASING
	}

	Optie optie;

	public Selectie(Optie optie)
	{
		this.optie = optie;
	}
}
