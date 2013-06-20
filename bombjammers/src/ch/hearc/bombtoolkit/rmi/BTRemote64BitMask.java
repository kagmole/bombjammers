package ch.hearc.bombtoolkit.rmi;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class BTRemote64BitMask extends BTAbstractRemoteBitMask 
		implements Externalizable {
	
	private static final int MSB_MASK = 63;
	
	private long mask;
	
	public BTRemote64BitMask() {
		mask = 0;
	}

	@Override
	public void readExternal(ObjectInput input) throws IOException,
			ClassNotFoundException {
		mask = input.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeLong(mask);
	}
}
