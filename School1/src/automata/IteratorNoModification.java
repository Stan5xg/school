package automata;

/*
 * Новый интерфейс, который не имеет операций изменения коллекции
 * и поэтому может использоваться для траверсинга с одновременной 
 * модификацией без выбрасывания ConcurentModificationException
 */
public interface IteratorNoModification<T> {
	T next();
	
	boolean hasNext();
}
