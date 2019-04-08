package com.panchen.easyPaxos.future;

import java.util.concurrent.FutureTask;

public class PaxosFutrueTask<T> extends FutureTask<T> {
	
	public PaxosFutrueTask(PaxosCallable<T> paxosCallable) {
		super(paxosCallable);
	}

}
