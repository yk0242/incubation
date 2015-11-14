package tf.customize.sdrsg;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Log formatter for sdrsg package.
 * ...not very efficient, but does the job...
 * based on http://stackoverflow.com/a/6898435
 * @author yk0242
 */
public class SdrsgLogFormatter extends Formatter{
	/* (non-Javadoc)
	 * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
	 */
	@Override
	public String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();
		sb.append(new Date(record.getMillis())).append('\n');
		sb.append(record.getLevel()).append(": ");
		sb.append(record.getMessage()).append("\n\n");
		return sb.toString();
	}
}
