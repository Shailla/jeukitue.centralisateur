package jkt.centralisateur.storage.common;

public class PerformanceData {
	private long numberOfExecutions;
	
	private long totalExecutionTime;
	private long minExecutionTime;
	private long maxExecutionTime;
	private long firstExecutionTime;

	private long totalCollectionCount;
	private long minCollectionCount;
	private long maxCollectionCount;
	
	private long totalEntityCount;
	private long minEntityCount;
	private long maxEntityCount;

	public void update(long executionTime, int collectionCount, int entityCount) {
		// Compte une exécution de plus
		numberOfExecutions++;
	
		updateTemps(executionTime);
		updateCollectionCount(collectionCount);
		updateEntityCount(entityCount);
	}
	
	private void updateTemps(long executionTime) {
		// Temps d'exécution minimum
		if(executionTime < minExecutionTime) {
			minExecutionTime = executionTime;
		}
		
		// Temps d'exécution maximum
		if(numberOfExecutions > 1) {	// On ignore le temps de la première ex�cution pour le total et le max
			// Temps d'exécution total
			totalExecutionTime += executionTime;
			
			if(executionTime > maxExecutionTime) {
				maxExecutionTime = executionTime;
			}
		}
		else {
			firstExecutionTime = executionTime;
		}
	}
	
	private void updateCollectionCount(int collectionCount) {
		// Nombre total de collections dans la session
		totalCollectionCount += collectionCount;
		
		// Nombre minimum de collections dans la session au cours d'une exécution
		if(collectionCount < minCollectionCount) {
			minCollectionCount = collectionCount;
		}
		
		// Nombre maximum de collections dans la session au cours d'une exécution
		if(collectionCount > maxCollectionCount) {
			maxCollectionCount = collectionCount;
		}
	}
	
	private void updateEntityCount(int entityCount) {
		// Nombre total de collections dans la session
		totalEntityCount += entityCount;
		
		// Nombre minimum de collections dans la session au cours d'une exécution
		if(entityCount < minEntityCount) {
			minEntityCount = entityCount;
		}
		
		// Nombre maximum de collections dans la session au cours d'une ex�cution
		if(entityCount > maxEntityCount) {
			maxEntityCount = entityCount;
		}
	}
	

	public long getFirstExecutionTime() {
		return firstExecutionTime;
	}
	
	public long getNumberOfExecutions() {
		return numberOfExecutions;
	}
	
	public float getExecutionTimeAverage() {
		if(numberOfExecutions > 1) {
			return totalExecutionTime / (numberOfExecutions - 1);	
		}
		
		return 0f;
	}
	
	public float getTotalExecutionTime() {
		return totalExecutionTime;
	}

	public float getMinExecutionTime() {
		return minExecutionTime;
	}

	public float getMaxExecutionTime() {
		return maxExecutionTime;
	}
	
	public long getCollectionCountAverage() {
		return totalCollectionCount / numberOfExecutions;
	}

	public long getMinCollectionCount() {
		return minCollectionCount;
	}

	public long getMaxCollectionCount() {
		return maxCollectionCount;
	}

	public long getEntityCountAverage() {
		return totalEntityCount / numberOfExecutions;
	}

	public long getMaxEntityCount() {
		return maxEntityCount;
	}
	
	public long getMinEntityCount() {
		return minEntityCount;
	}
}
