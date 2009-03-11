/**
 * 
 */
package org.rifidi.edge.core.readers.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.jms.Destination;

import org.rifidi.edge.core.api.SessionStatus;
import org.rifidi.edge.core.commands.Command;
import org.rifidi.edge.core.readers.ReaderSession;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author Kyle Neumeier - kyle@pramari.com
 * 
 */
public abstract class AbstractReaderSession implements ReaderSession {

	/** Used to execute commands. */
	protected ScheduledThreadPoolExecutor executor;
	/** Status of the reader. */
	private SessionStatus status;
	/** Map containing the periodic commands with the process id as key. */
	protected Map<Integer, Command> commands;
	/** Map containing command process id as key and the future as value. */
	protected Map<Integer, CommandExecutionData> idToData;
	/** Job counter */
	private int counter = 0;
	/** JMS destination. */
	private Destination destination;
	/** Spring jms template */
	private JmsTemplate template;
	/** True if the executor is up and running. */
	protected AtomicBoolean processing = new AtomicBoolean(false);
	/** Queue for commands that get submitted while the executor is inactive. */
	protected Queue<Command> commandQueue = new ConcurrentLinkedQueue<Command>();

	public AbstractReaderSession(Destination destination, JmsTemplate template) {
		this.commands = new HashMap<Integer, Command>();
		this.idToData = new HashMap<Integer, CommandExecutionData>();
		this.template = template;
		this.destination = destination;
		status = SessionStatus.CREATED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rifidi.edge.core.readers.ReaderSession#currentCommands()
	 */
	@Override
	public Map<Integer, Command> currentCommands() {
		return new HashMap<Integer, Command>(commands);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rifidi.edge.core.readers.ReaderSession#getStatus()
	 */
	@Override
	public SessionStatus getStatus() {
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rifidi.edge.core.readers.ReaderSession#killComand(java.lang.Integer)
	 */
	@Override
	public void killComand(Integer id) {
		commands.remove(id);
		CommandExecutionData data = idToData.remove(id);
		if (data != null) {
			data.future.cancel(true);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rifidi.edge.core.readers.ReaderSession#submit(org.rifidi.edge.core
	 * .readers.Command)
	 */
	@Override
	public void submit(Command command) {
		command.setReaderSession(this);
		command.setTemplate(template);
		command.setDestination(destination);
		if (processing.get()) {
			executor.submit(command);
			return;
		}
		commandQueue.add(command);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rifidi.edge.core.readers.ReaderSession#submit(org.rifidi.edge.core
	 * .readers.Command, long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public Integer submit(Command command, long interval, TimeUnit unit) {
		synchronized (commands) {
			command.setReaderSession(this);
			command.setTemplate(template);
			command.setDestination(destination);
			Integer id = counter++;
			commands.put(id, command);
			CommandExecutionData data = new CommandExecutionData();
			data.interval = interval;
			data.unit = unit;
			if (processing.get()) {
				data.future = executor.scheduleWithFixedDelay(command, 0,
						interval, unit);
			}
			idToData.put(id, data);
			return id;
		}
	}

	protected class CommandExecutionData {
		public long interval;
		public TimeUnit unit;
		public ScheduledFuture<?> future;
	}

	protected void setStatus(SessionStatus status) {
		this.status = status;
	}

}