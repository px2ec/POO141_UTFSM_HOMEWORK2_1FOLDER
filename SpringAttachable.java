interface SpringAttachable {
	void attachSpring(Elastic s);
	void detachSpring(Elastic s);
	boolean collide(SpringAttachable b);
	double getPosition();
	double getMass();
	double getRadius();
	double getSpeed();
	String getDescription();
}