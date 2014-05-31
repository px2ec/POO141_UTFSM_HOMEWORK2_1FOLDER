// We realize that there are objects whose next states change
// as a result of its previous state(as a Ball) and 
// its interaction with other elements. These are "simulateable".
// Others, like springs, depend only on other objects.
// So, in order to avoid methods with empty implementations,
// we define this interface. 

interface Simulateable {  
	void computeNextState(double delta_t, MyWorld w);
	void updateState();
}