package com.panchen.easyPaxos.future;

import java.util.concurrent.FutureTask;

public class PaxosFutrueTask extends FutureTask<Boolean> {

	public PaxosFutrueTask(PaxosCallable paxosCallable) {
		super(paxosCallable);
	}

}
