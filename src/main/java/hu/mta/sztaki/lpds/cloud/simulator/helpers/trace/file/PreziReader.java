package hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.file;

import java.lang.reflect.InvocationTargetException;

import hu.mta.sztaki.lpds.cloud.simulator.helpers.job.Job;

public class PreziReader extends TraceFileReaderFoundation {

	public PreziReader(String fileName, int from, int to, boolean allowReadingFurther, Class<? extends Job> jobType)
			throws SecurityException, NoSuchMethodException {
		super("LOG format", fileName, from, to, allowReadingFurther, jobType);
		// TODO Auto-generated constructor stub
	}

	protected boolean isTraceLine(String line) {
		try {
		String[] fragments = line.split("\\s+");
		String epochCheck = fragments[0]; // Check if epoch time is withing relevent timeframe
		if(10 == epochCheck.length()) {
			long jobArrival = Long.parseLong(fragments[0]);
			float jobDuration = Float.parseFloat(fragments[1]);
			String jobID = fragments[2];
			String jobExecutable = fragments[3];
			if(!(jobExecutable.equals("url") || jobExecutable.equals("default") || jobExecutable.equals("export"))) {
				System.out.println("idwaudawui wdaiuawdsiun");
			}
		}else {
			return false;
		}
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	protected void metaDataCollector(String temp) {
		// do nothing
	}

	protected Job createJobFromLine(String line)
			throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {

		String[] fragments = line.split("\\s+");
		long jobArrival = Long.parseLong(fragments[0]);
		jobArrival = Math.round(jobArrival);
		float jobDuration = Float.parseFloat(fragments[1]);
		String jobID = fragments[2];
		String jobExecutable = fragments[3];
		if (jobDuration == 0)
			return null;
		
		return jobCreator.newInstance(
				jobID, // id:
				jobArrival, // submit time in secs:
				0, //Wait Time
				(long) jobDuration, // run time in secs:
				1, // allocated processors:
				-1, // average cpu time:
				512, // average memory:
				null, // userid:
				null, // groupid:
				jobExecutable, // execid:
				null, 
				0 // delay after
				);
	}

}
